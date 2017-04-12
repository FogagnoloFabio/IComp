
package it.fogagnolo.icomp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.fogagnolo.icomp.util.Base64;

public class FileInfo implements Serializable {

    private static final long             serialVersionUID  = 7930823258457889486L;

    private File                          _fileObj          = null;
    private String                        _name             = null;
    private long                          _size             = -1;
    private long                          _lastMod          = -1;

    private byte[]                        _content          = null;
    private byte[]                        _fastDigest       = null;
    private byte[]                        _fullDigest       = null;
    private boolean                       _contentLoaded    = false;
    private boolean                       _fastDigestLoaded = false;
    private boolean                       _fullDigestLoaded = false;
    private int                           _group            = 0;
    private boolean                       _selected         = false;

    private static final String           ALGORITMO         = "SHA-1";
    private static final SimpleDateFormat sdf               = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public FileInfo(File fileObj) {

        _fileObj = fileObj;
        _name = fileObj.getName();
        _size = fileObj.length();
        _lastMod = fileObj.lastModified();
    }

    public File getFileObj() {

        return _fileObj;
    }

    public String getName() {

        return _name;
    }

    /**
     * Questo metodo calcola il digest sui primi n byte del contenuto del file.
     * 
     * @return byte[] Il digest. In caso di errore, viene restituito null.
     */
    public byte[] getContent() {

        if (!_contentLoaded) {
            _contentLoaded = true;

            try {
                MessageDigest md = MessageDigest.getInstance(ALGORITMO);
                md.reset();

                FileInputStream fis = new FileInputStream(_fileObj);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte[] buffer = new byte[IntelliCompFinder._contentLength];
                bis.skip(IntelliCompFinder._offset);
                int length = bis.read(buffer);
                if (length != -1) md.update(buffer, 0, length);
                _content = md.digest();
                bis.close();
                fis.close();

            } catch (NoSuchAlgorithmException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nel recupero del contenuto");
                e.printStackTrace();
                _content = null;
            } catch (FileNotFoundException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: non trovato");
                e.printStackTrace();
                _content = null;
            } catch (IOException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nella lettura");
                e.printStackTrace();
                _content = null;
            }
        }

        return _content;
    }

    /**
     * Questo metodo calcola il fast digest: .
     * 
     * @return byte[] Il digest. In caso di errore, viene restituito null.
     */
    public byte[] getFastDigest() {

        if (!_fastDigestLoaded) {

            try {

                // se la dimensione del file è inferiore o uguale al buffer, calcolo direttamente il full digest
                if (_size <= IntelliCompFinder._bufferLength) {
                    getFullDigest();
                    _fastDigest = new byte[_fullDigest.length];
                    System.arraycopy(_fullDigest, 0, _fastDigest, 0, _fullDigest.length);
                } else {

                    MessageDigest md = MessageDigest.getInstance(ALGORITMO);
                    md.reset();

                    // la dimensione del salto tra una lettura e l'altra (e quindi quanti blocchi leggere) è determinato
                    // in base alla dimensione del file
                    long skip = 0;
                    if (_size < IntelliCompFinder._bufferLength * 20) skip = IntelliCompFinder._bufferLength * 3;
                    else if (_size < IntelliCompFinder._bufferLength * 50) skip = IntelliCompFinder._bufferLength * 5;
                    else if (_size < 1000000) skip = _size / 10;
                    else if (_size < 10000000) skip = _size / 25;
                    else if (_size < 100000000) skip = _size / 50;
                    else if (_size < 1000000000) skip = _size / 75;
                    else
                        skip = _size / 100;

                    FileInputStream fis = new FileInputStream(_fileObj);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] buffer = new byte[IntelliCompFinder._bufferLength];

                    // itero per leggere i blocchi
                    long pos = 0;
                    // System.out.print("fname: " + _name + " size " + _size + ": ");
                    // int salti = 0;
                    while (pos + IntelliCompFinder._bufferLength < _size) {
                        bis.skip(pos);
                        // ++salti;
                        // System.out.print(" ... skip a " + pos);
                        int length = bis.read(buffer);
                        if (length != -1) {
                            md.update(buffer, 0, length);
                        }
                        pos += skip;
                    }

                    // System.out.println(" : " + salti + " salti");
                    _fastDigest = md.digest();
                    bis.close();
                    fis.close();
                }

                _fastDigestLoaded = true;

            } catch (NoSuchAlgorithmException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nel calcolo del digest");
                e.printStackTrace();
                _fastDigest = null;
            } catch (FileNotFoundException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: non trovato");
                e.printStackTrace();
                _fastDigest = null;
            } catch (IOException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nella lettura");
                e.printStackTrace();
                _fastDigest = null;
            }
        }

        return _fastDigest;
    }

    /**
     * Questo metodo calcola il digest sull'intero contenuto del file.
     * 
     * @return byte[] Il digest. In caso di errore, viene restituito null.
     */
    public byte[] getFullDigest() {

        if (!_fullDigestLoaded) {

            try {
                MessageDigest md = MessageDigest.getInstance(ALGORITMO);
                md.reset();

                long byte2read = IntelliCompFinder._length;
                if (byte2read == -1) byte2read = _fileObj.length();

                FileInputStream fis = new FileInputStream(_fileObj);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte[] buffer = new byte[IntelliCompFinder._bufferLength];
                bis.skip(IntelliCompFinder._offset);
                int length;
                long byteRed = 0;
                while (((length = bis.read(buffer)) != -1) && byteRed < byte2read) {
                    md.update(buffer, 0, length);
                    byteRed += length;
                }
                _fullDigest = md.digest();
                bis.close();
                fis.close();

                _fullDigestLoaded = true;

            } catch (NoSuchAlgorithmException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nel calcolo del digest");
                e.printStackTrace();
                _fullDigest = null;
            } catch (FileNotFoundException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: non trovato");
                e.printStackTrace();
                _fullDigest = null;
            } catch (IOException e) {
                System.out.println("File [" + _fileObj.getAbsolutePath() + "]: errore nella lettura");
                e.printStackTrace();
                _fullDigest = null;
            }
        }

        return _fullDigest;
    }

    public long getSize() {

        return _size;
    }

    public void setGroup(int g) {

        _group = g;
    }

    public int getGroup() {

        return _group;
    }

    public boolean isSelected() {

        return _selected;
    }

    public void setSelected(boolean b) {

        _selected = b;
    }

    public long getLastMod() {

        return _lastMod;
    }

    public String toStringFinder() {

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("group=").append(_group);

        try {
            sb.append(",name=").append(_fileObj.getCanonicalPath());
        } catch (IOException e) {
            sb.append(",name=").append(_fileObj.getAbsolutePath());
        }

        sb.append(",size=").append(_size);

        String b64 = Base64.encode(_content);
        if (b64 != null) b64 = b64.trim();
        sb.append(",content=").append(b64);

        b64 = Base64.encode(_fastDigest);
        if (b64 != null) b64 = b64.trim();
        sb.append(",fastDigest=").append(b64);

        b64 = Base64.encode(_fullDigest);
        if (b64 != null) b64 = b64.trim();
        sb.append(",fullDigest=").append(b64);

        sb.append("]");

        return new String(sb);
    }

    public String toStringCleaner() {

        StringBuffer sb = new StringBuffer();

        if (_selected) {
            sb.append("X");
        } else {
            sb.append(" ");
        }

        sb.append(" [");
        sb.append("group=").append(_group);

        String lastmod = sdf.format(new Date(_lastMod));
        sb.append(",lastMod=").append(lastmod);

        try {
            sb.append(",name=").append(_fileObj.getCanonicalPath());
        } catch (IOException e) {
            sb.append(",name=").append(_fileObj.getAbsolutePath());
        }

        sb.append("]");

        return new String(sb);
    }
}
