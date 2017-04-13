
package it.fogagnolo.icomp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import it.fogagnolo.icomp.util.DateUtil;

public class IntelliCompCleaner {

    /*
     * 
     * cmd> r Exception in thread "main" java.util.ConcurrentModificationException at
     * java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372) at
     * java.util.AbstractList$Itr.next(AbstractList.java:343) at
     * it.fogagnolo.icomp.IntelliCompCleaner.cmdDelete(IntelliCompCleaner.java:320) at
     * it.fogagnolo.icomp.IntelliCompCleaner.elabora(IntelliCompCleaner.java:115) at
     * it.fogagnolo.icomp.IntelliCompCleaner.main(IntelliCompCleaner.java:58)
     */

    private static final int                                MODULO                         = 100;

    // parametri
    private static boolean                                  _swDebug                       = false;
    private static HashMap<Integer, ArrayList<FileInfoExt>> _mapFiles                      = null;
    private static String                                   _inputfile                     = null;
    private static String                                   _savefile                      = null;

    private static int                                      _num                           = 0;

    private static final String                             CMD_LIST                       = "l";
    private static final String                             CMD_CHECK_SELECTION            = "c";
    private static final String                             CMD_CHECK_EXISTANCE            = "e";
    private static final String                             CMD_CHECK_EXISTANCE_AND_REMOVE = "e+r";
    private static final String                             CMD_SELECT_OLDEST              = "o";
    private static final String                             CMD_SELECT_PATTERN             = "p";
    private static final String                             CMD_SELECT_MANUAL              = "m";
    private static final String                             CMD_REMOVE                     = "r";
    private static final String                             CMD_MOVE_TO_FOLDER             = "r+f";
    private static final String                             CMD_HELP                       = "h";
    private static final String                             CMD_EXIT                       = "x";

    private static final String                             PROMPT_DELIMITER               = "> ";
    private static final String                             PROMPT_CMD                     = "cmd";
    private static final String                             PROMPT_SEL                     = "sel";
    private static final String                             PROMPT_PATTERN                 = "pattern";

    public static void main(String[] args) {

        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        parseParams(args);
        long inizio = Calendar.getInstance().getTimeInMillis();

        Serializer s = new Serializer();
        s.setFileName(_inputfile);
        _mapFiles = s.readIntoMap();
        System.out.println("Letti " + _mapFiles.size() + " gruppi");

        elabora();

        s.setFileName(_savefile);
        s.write(_mapFiles);

        String elapsed = DateUtil.getElapsed(inizio, Calendar.getInstance().getTimeInMillis());
        System.out.println(DateUtil.getCurrentDate() + " tempo di elaborazione: " + elapsed);
        System.out.println(DateUtil.getCurrentDate() + " fine");

    }

    private static void parseParams(String[] args) {

        ArrayList<String> params = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            params.add(args[i]);
        }

        while (!params.isEmpty()) {
            if (params.get(0).toLowerCase().startsWith("-d")) {
                _swDebug = true;
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-i")) {
                params.remove(0);
                _inputfile = params.get(0);
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-s")) {
                params.remove(0);
                _savefile = params.get(0);
                params.remove(0);
            } else {
                System.out.println("Parametro errato: " + params.remove(0));
            }
        }
    }

    private static void elabora() {

        String cmd = null;
        while (!CMD_EXIT.equals(cmd = readLine(PROMPT_CMD))) {
            if ("".equals(cmd.trim())) {
            } else if (CMD_LIST.equals(cmd)) {
                cmdList();
            } else if (CMD_SELECT_OLDEST.equals(cmd)) {
                cmdSelectOldest();
            } else if (CMD_SELECT_PATTERN.equals(cmd)) {
                cmdSelectPattern();
            } else if (CMD_SELECT_MANUAL.equals(cmd)) {
                cmdSelectManual();
            } else if (CMD_CHECK_SELECTION.equals(cmd)) {
                cmdCheckSelection();
            } else if (CMD_CHECK_EXISTANCE.equals(cmd)) {
                cmdCheckExistance();
            } else if (CMD_CHECK_EXISTANCE_AND_REMOVE.equals(cmd)) {
                cmdCheckExistance(true);
                clean();
            } else if (CMD_REMOVE.equals(cmd)) {
                cmdDelete();
                clean();
            } else if (CMD_MOVE_TO_FOLDER.equals(cmd)) {
                cmdMoveToFolder();
                clean();
            } else if (CMD_HELP.equals(cmd)) {
                cmdHelp();
            } else {
                System.out.println("  Comando non valido [" + cmd + "]");
            }
        }
    }

    private static void cmdSelectManual() {

        final String SUBCMD_EXIT = "x";
        final String SUBCMD_NEXT = "n";

        System.out.println("Subcommands: [0-9] switch selection flag, '" + SUBCMD_NEXT + "' next group, '"
                + SUBCMD_EXIT + "' end command");

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();

        print(it.next().getValue());

        ArrayList<FileInfoExt> files = null;
        String subcmd = "";
        while (!SUBCMD_EXIT.equals(subcmd)) {
            subcmd = readLine(PROMPT_SEL);

            // next group, exit if last group
            if (SUBCMD_NEXT.equals(subcmd)) {
                if (it.hasNext()) {
                    files = it.next().getValue();
                    print(files);
                } else {
                    subcmd = SUBCMD_EXIT;
                }
            }

            // switch selection flag
            try {
                int num = Integer.parseInt(subcmd);
                if (num > 0 && num <= files.size()) {
                    FileInfoExt fi = files.get(num - 1);
                    fi.setSelected(!fi.isSelected());
                }
            } catch (Throwable t) {
            }

        }
    }

    private static void print(ArrayList<FileInfoExt> files) {

        int i = 1;
        for (FileInfoExt fi : files) {
            System.out.println("  " + i + ": " + fi.toStringCleaner());
            i++;
        }

    }

    private static void cmdSelectOldest() {

        int selected = 0;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            ArrayList<FileInfoExt> files = it.next().getValue();

            long lastMod = -1;
            FileInfoExt lastFile = null;

            for (FileInfoExt fi : files) {
                if (lastMod == -1 || fi.getLastMod() < lastMod) {
                    lastMod = fi.getLastMod();
                    lastFile = fi;
                }
            }

            if (lastFile != null && !lastFile.isSelected()) {
                lastFile.setSelected(true);
                ++selected;
            }
        }

        System.out.println(PROMPT_DELIMITER + selected + " item selezionati");

    }

    private static void cmdSelectPattern() {

        String pattern = readLine(PROMPT_PATTERN);

        if (pattern != null && !pattern.trim().equals("")) {
            int selected = 0;
            Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
            while (it.hasNext()) {
                ArrayList<FileInfoExt> files = it.next().getValue();
                for (FileInfoExt fi : files) {
                    if (!fi.isSelected() && fi.getName().contains(pattern)) {
                        fi.setSelected(true);
                        ++selected;
                    }
                }
            }

            System.out.println(PROMPT_DELIMITER + selected + " item selezionati");
        }

    }

    /**
     * Questo metodo elenca i gruppi con tutti gli item selezionati
     * 
     */
    private static void cmdCheckSelection() {

        System.out.println("Elenco gruppi con tutti gli item selezionati:");
        boolean found = false;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, ArrayList<FileInfoExt>> key = it.next();
            ArrayList<FileInfoExt> files = key.getValue();

            if (files.size() > 0) {
                boolean allSelected = true;
                for (FileInfoExt fi : files) {
                    if (!fi.isSelected()) allSelected = false;
                }

                if (allSelected) {
                    found = true;
                    for (FileInfoExt fi : files) {
                        System.out.println("  " + fi.toStringCleaner());
                    }
                }
            }
        }

        if (!found) System.out.println("Nessuno");
    }

    private static void cmdCheckExistance() {

        cmdCheckExistance(false);
    }

    private static void cmdCheckExistance(boolean remove) {

        System.out.println("Elenco file inesistenti:");
        boolean found = false;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            ArrayList<FileInfoExt> files = it.next().getValue();

            for (FileInfoExt fi : files) {
                if (!fi.getFileObj().exists()) {
                    System.out.println("  " + fi.toStringCleaner());
                    if (remove) files.remove(fi);
                    found = true;
                }
            }
        }

        if (!found) System.out.println("Nessuno");

    }

    /**
     * Questo metodo rimuove dalla mappa i gruppi composti da un solo elemento
     * 
     */
    private static void clean() {

        System.out.println("Rimozione gruppi vuoti:");
        int num = 0;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, ArrayList<FileInfoExt>> key = it.next();
            ArrayList<FileInfoExt> files = key.getValue();

            if (files.size() <= 1) {
                _mapFiles.remove(key);
                ++num;
            }
        }

        System.out.println(num + " gruppi rimossi");

    }

    private static void cmdDelete() {

        int num = 0;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, ArrayList<FileInfoExt>> key = it.next();
            ArrayList<FileInfoExt> files = key.getValue();

            for (FileInfoExt fi : files) {
                if (fi.isSelected()) {
                    fi.toStringCleaner();
                    fi.getFileObj().delete();
                    files.remove(fi);
                    ++num;
                }
            }
        }

        System.out.println(num + " file rimossi");

    }

    private static void cmdMoveToFolder() {

        System.out.println("Specificare la cartella di destinazione: ");
        boolean folderSelected = false;
        File folder = null;
        while (!folderSelected) {
            String folderName = readLine(PROMPT_DELIMITER);
            folder = new File(folderName);
            if (folder.exists()) {
                folderSelected = true;
            } else {
                System.out.println("La cartella non esiste");
            }
        }

        String conferma = readLine("Confermi? [S/N]");
        if (conferma.trim().toUpperCase().startsWith("S")) {

            int num = 0;

            Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Integer, ArrayList<FileInfoExt>> key = it.next();
                ArrayList<FileInfoExt> files = key.getValue();

                for (FileInfoExt fi : files) {
                    if (fi.isSelected()) {
                        fi.toStringCleaner();
                        fi.getFileObj().renameTo(folder);
                        files.remove(fi);
                        ++num;
                    }
                }
            }

            System.out.println(num + " file spostati");
        }
    }

    private static void cmdList() {

        int items = 0;

        Iterator<Entry<Integer, ArrayList<FileInfoExt>>> it = _mapFiles.entrySet().iterator();
        while (it.hasNext()) {
            ArrayList<FileInfoExt> files = it.next().getValue();
            for (FileInfoExt fi : files) {
                items++;
                System.out.println("  Item " + items + ": " + fi.toStringCleaner());
            }
        }

        System.out.println("  " + items + " items");
        System.out.println("  " + _mapFiles.size() + " groups");

    }

    private static void cmdHelp() {

        System.out.println("  " + CMD_LIST + "\tElenco dei file in elaborazione");
        System.out.println("  " + CMD_CHECK_SELECTION
                + "\tVerifica se per un gruppo sono stati selezionati tutti gli elementi");
        System.out.println("  " + CMD_CHECK_EXISTANCE + "\tVerifica l'esistenza dei file");
        System.out.println("  " + CMD_CHECK_EXISTANCE_AND_REMOVE
                + "\tVerifica l'esistenza dei file e rimuove quelli non validi");
        System.out.println("  " + CMD_SELECT_OLDEST
                + "\tPer ogni gruppo, seleziona il file duplicato con data modifica piu' vecchia");
        System.out.println("  " + CMD_SELECT_PATTERN + "\tSeleziona gli elementi in base alla stringa fornita");
        System.out.println("  " + CMD_SELECT_MANUAL + "\tSeleziona manualmente gli elementi");
        System.out.println("  " + CMD_REMOVE + "\tElimina gli elementi selezionati");
        System.out.println("  " + CMD_MOVE_TO_FOLDER + "\tSposta gli elementi selezionati nella cartella specificata");
        System.out.println("  " + CMD_HELP + "\tElenco dei comandi disponibili");
        System.out.println("  " + CMD_EXIT + "\tTermina l'elaborazione");
    }

    private static String readLine(String prompt) {

        System.out.println();
        System.out.print(prompt + PROMPT_DELIMITER);

        String input = null;
        do {
            input = readLine().trim();
        } while ("".equals(input));

        return input;
    }

    private static String readLine() {

        int c = 0;
        StringBuffer line = new StringBuffer();
        try {
            while (true) {
                c = System.in.read();
                if (c == 13 || c == 10) break;
                line.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(line);
    }

    private static void printUsage() {

        System.out.println(IntelliCompCleaner.class.getSimpleName() + IntelliCompFinder.VERSION);
        System.out.println("Usage: " + IntelliCompCleaner.class.getSimpleName()
                + " -input filename [-save filename] [-debug]");

    }

    private static void printParams() {

        System.out.println("Parametri di esecuzione:");

        System.out.println("  input file: " + _inputfile);
        System.out.println("  save file: " + _savefile);

    }

    private static void incrementNum() {

        ++_num;
        if (_num % MODULO == 0) System.out.println("                        " + _num + " elementi....");
    }
}
