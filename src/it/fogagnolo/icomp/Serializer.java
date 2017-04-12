
package it.fogagnolo.icomp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import it.fogagnolo.icomp.util.Base64;
import it.fogagnolo.icomp.util.DateUtil;

public class Serializer {

    private String              _fname    = null;
    private static final String SEPARATOR = "|";

    public void setFileName(String fname) {

        _fname = fname;
    }

    public ArrayList<FileInfo> read() {

        ArrayList<FileInfo> files = new ArrayList<FileInfo>();

        if (_fname == null) return files;

        System.out.println(DateUtil.getCurrentDate() + " read save file " + _fname);

        try {
            BufferedReader br = new BufferedReader(new FileReader(_fname));

            // header
            String header = br.readLine();
            StringTokenizer st = new StringTokenizer(header, SEPARATOR, false);
            String version = st.nextToken();
            String num = st.nextToken();
            System.out.println(version + ", " + num + " entries");

            if ((IntelliCompFinder.class.getSimpleName() + IntelliCompFinder.VERSION).equals(version)) {
                // oggetti serializzati
                String riga = null;
                while ((riga = br.readLine()) != null) {
                    byte[] obj = Base64.decode(riga.trim());
                    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(obj));
                    FileInfo fi = (FileInfo) in.readObject();
                    files.add(fi);
                    in.close();
                }
            } else {
                System.out.println("Versione file non compatibile");
            }

            br.close();

            return files;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return files;
        } catch (FileNotFoundException e) {
            System.out.println("File [" + _fname + "]: non trovato");
            e.printStackTrace();
            return files;
        } catch (IOException e) {
            System.out.println("File [" + _fname + "]: errore nella lettura");
            e.printStackTrace();
            return files;
        }

    }

    public HashMap<Integer, ArrayList<FileInfo>> readIntoMap() {

        HashMap<Integer, ArrayList<FileInfo>> map = new HashMap<Integer, ArrayList<FileInfo>>();

        if (_fname == null) return map;

        System.out.println(DateUtil.getCurrentDate() + " read save file " + _fname);

        try {
            BufferedReader br = new BufferedReader(new FileReader(_fname));

            // header
            String header = br.readLine();
            StringTokenizer st = new StringTokenizer(header, SEPARATOR, false);
            String version = st.nextToken();
            String num = st.nextToken();
            System.out.println(version + ", " + num + " entries");

            if ((IntelliCompFinder.class.getSimpleName() + IntelliCompFinder.VERSION).equals(version)) {
                // oggetti serializzati
                String riga = null;
                while ((riga = br.readLine()) != null) {
                    byte[] obj = Base64.decode(riga.trim());
                    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(obj));
                    FileInfo fi = (FileInfo) in.readObject();

                    ArrayList<FileInfo> list = map.get(fi.getGroup());
                    if (list == null) list = new ArrayList<FileInfo>();
                    list.add(fi);
                    map.put(fi.getGroup(), list);

                    in.close();
                }
            } else {
                System.out.println("Versione file non compatibile");
            }

            br.close();

            return map;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return map;
        } catch (FileNotFoundException e) {
            System.out.println("File [" + _fname + "]: non trovato");
            e.printStackTrace();
            return map;
        } catch (IOException e) {
            System.out.println("File [" + _fname + "]: errore nella lettura");
            e.printStackTrace();
            return map;
        }

    }

    public boolean write(ArrayList<FileInfo> files) {

        if (_fname == null) return true;

        System.out.println(DateUtil.getCurrentDate() + " write save file " + _fname);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(_fname));

            // header
            String header = IntelliCompFinder.class.getSimpleName() + IntelliCompFinder.VERSION + SEPARATOR
                    + files.size();
            bw.write(header);
            bw.newLine();

            // oggetti serializzati
            for (FileInfo fi : files) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(baos);
                oo.writeObject(fi);
                oo.close();
                String b64 = Base64.encode(baos.toByteArray());
                bw.write(b64);
                bw.newLine();
            }

            bw.close();

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("File [" + _fname + "]: non trovato");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("File [" + _fname + "]: errore nella scrittura");
            e.printStackTrace();
            return false;
        }

    }

    public boolean write(HashMap<Integer, ArrayList<FileInfo>> map) {

        if (_fname == null) return true;

        System.out.println(DateUtil.getCurrentDate() + " write save file " + _fname);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(_fname));

            // header
            String header = IntelliCompFinder.class.getSimpleName() + IntelliCompFinder.VERSION + SEPARATOR
                    + map.size() + " groups";
            bw.write(header);
            bw.newLine();

            // oggetti serializzati
            Iterator<Entry<Integer, ArrayList<FileInfo>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                ArrayList<FileInfo> files = it.next().getValue();
                for (FileInfo fi : files) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutput oo = new ObjectOutputStream(baos);
                    oo.writeObject(fi);
                    oo.close();
                    String b64 = Base64.encode(baos.toByteArray());
                    bw.write(b64);
                    bw.newLine();
                }
            }

            bw.close();

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("File [" + _fname + "]: non trovato");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("File [" + _fname + "]: errore nella scrittura");
            e.printStackTrace();
            return false;
        }

    }

}
