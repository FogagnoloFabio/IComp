
package it.fogagnolo.icomp.swing;

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
import java.util.StringTokenizer;

import it.fogagnolo.icomp.IntelliCompFinder;
import it.fogagnolo.icomp.util.Base64;
import it.fogagnolo.icomp.util.DateUtil;

public class Serializer {

    private String              _type     = null;
    private String              _fname    = null;
    private static final String SEPARATOR = "|";

    public Serializer(String type) {

        _type = type;
    }

    public void setFileName(String fname) {

        _fname = fname;
    }

    public ArrayList<?> read() {

        ArrayList<Object> list = new ArrayList<Object>();

        if (_fname == null) return list;

        System.out.println(DateUtil.getCurrentDate() + " read " + _type + " save file " + _fname);

        try {
            BufferedReader br = new BufferedReader(new FileReader(_fname));

            // header
            String header = br.readLine();
            StringTokenizer st = new StringTokenizer(header, SEPARATOR, false);
            String version = st.nextToken();
            String num = st.nextToken();

            if ((IntelliCompFinder.class.getSimpleName() + _type + IntelliCompFinder.VERSION).equals(version)) {
                // oggetti serializzati
                String riga = null;
                while ((riga = br.readLine()) != null) {
                    byte[] obj = Base64.decode(riga.trim());
                    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(obj));
                    list.add(in.readObject());
                    in.close();
                }
            } else {
                System.out.println("Tipo file o Versione non compatibile");
            }

            br.close();

            System.out.println(DateUtil.getCurrentDate() + " Letti " + list.size() + " elementi");

            return list;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("File [" + _fname + "]: non trovato");
            e.printStackTrace();
            return list;
        } catch (IOException e) {
            System.out.println("File [" + _fname + "]: errore nella lettura");
            e.printStackTrace();
            return list;
        }

    }

    public boolean write(ArrayList<?> list) {

        if (_fname == null) return true;

        System.out.println(DateUtil.getCurrentDate() + " write " + _type + " save file " + _fname);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(_fname));

            // header
            String header = IntelliCompFinder.class.getSimpleName() + _type + IntelliCompFinder.VERSION + SEPARATOR
                    + list.size();
            bw.write(header);
            bw.newLine();

            // oggetti serializzati
            for (Object elem : list) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(baos);
                oo.writeObject(elem);
                oo.close();
                String b64 = Base64.encode(baos.toByteArray());
                bw.write(b64);
                bw.newLine();
            }

            bw.close();

            System.out.println(DateUtil.getCurrentDate() + " Scritti " + list.size() + " elementi");

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
