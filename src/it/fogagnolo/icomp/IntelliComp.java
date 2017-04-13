
package it.fogagnolo.icomp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import it.fogagnolo.icomp.comparator.ContentComparator;
import it.fogagnolo.icomp.comparator.FullDigestComparator;
import it.fogagnolo.icomp.comparator.SizeComparator;
import it.fogagnolo.icomp.util.DateUtil;

public class IntelliComp {

    public static final String            VERSION        = " v1.6";
    private static final String           PUNTO          = ".";
    private static final int              MODULO         = 100;
    private static final int              MODULO_FILES   = 1000;

    // parametri
    private static ArrayList<File>        _roots         = new ArrayList<File>();
    private static ArrayList<String>      _exclude       = new ArrayList<String>();
    private static String                 _filter        = "";
    public static long                    _offset        = 0;
    public static long                    _length        = -1;
    public static int                     _contentLength = 1024;
    public static int                     _bufferLength  = 8192;
    private static boolean                _swDebug       = false;
    private static ArrayList<FileInfoExt> _files         = null;
    private static String                 _inputfile     = null;
    private static String                 _savefile      = null;
    private static boolean                _sizeFilter    = true;
    private static boolean                _contentFilter = true;
    private static boolean                _digestFilter  = true;
    private static boolean                _followSymlink = false;

    private static int                    _num           = 0;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println(IntelliComp.class.getSimpleName() + VERSION);
            System.out.println("Usage: " + IntelliComp.class.getSimpleName()
                    + " -root path [-exclude path] [-filter pattern] [-debug]\n"
                    + " [-offset comparisonStartOffset] [-length comparisonLength|-1]"
                    + " [-contentLength 1024] [-bufferLength 8192]\n"
                    + " [-input filename] [-save filename] [-follow_symlink]");
            System.exit(1);
        }

        parseParams(args);
        long inizio = Calendar.getInstance().getTimeInMillis();

        Serializer s = new Serializer();
        s.setFileName(_inputfile);
        _files = s.read();

        getFileList(_roots, _exclude, _filter);
        if (_swDebug) dump();
        filter("filterOnSize", new SizeComparator());
        loadContent();
        filter("filterOnContent", new ContentComparator());
        loadDigest();
        filter("filterOnDigest", new FullDigestComparator());
        printParams();
        listEquals(new FullDigestComparator());

        s.setFileName(_savefile);
        s.write(_files);

        String elapsed = DateUtil.getElapsed(inizio, Calendar.getInstance().getTimeInMillis());
        System.out.println(DateUtil.getCurrentDate() + " tempo di elaborazione: " + elapsed);
        System.out.println(DateUtil.getCurrentDate() + " fine");

    }

    private static void printParams() {

        System.out.println("Parametri di esecuzione:");

        System.out.println("  roots:");
        Iterator<File> it = _roots.iterator();
        while (it.hasNext()) {
            File root = it.next();
            System.out.println("    " + root.getAbsolutePath());
        }

        System.out.println("  exclude:");
        for (String ex : _exclude) {
            System.out.println("    " + ex);
        }

        System.out.println("  filter: " + _filter);
        System.out.println("  start offset: " + _offset);
        System.out.println("  comparison length: " + _length);
        System.out.println("  content length: " + _contentLength);
        System.out.println("  buffer length: " + _bufferLength);
        System.out.println("  input file: " + _inputfile);
        System.out.println("  save file: " + _savefile);
        System.out.println("  follow symlink: " + _followSymlink);

    }

    private static void parseParams(String[] args) {

        ArrayList<String> params = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            params.add(args[i]);
        }

        while (!params.isEmpty()) {
            if (params.get(0).toLowerCase().startsWith("-r")) {
                params.remove(0);
                String root = params.get(0);
                params.remove(0);

                File f = new File(root);
                if (f.isDirectory()) {
                    _roots.add(f);
                } else {
                    System.out.println("Root " + root + " non esiste o non e' una directory");
                }
            } else if (params.get(0).toLowerCase().startsWith("-e")) {
                params.remove(0);
                String root = params.get(0);
                params.remove(0);

                File f = new File(root);
                if (f.isDirectory()) {
                    _exclude.add(f.getAbsolutePath());
                } else {
                    System.out.println("Root " + root + " non esiste o non e' una directory");
                }
            } else if (params.get(0).toLowerCase().startsWith("-fi")) {
                params.remove(0);
                _filter = params.get(0).toLowerCase();
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-d")) {
                _swDebug = true;
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-o")) {
                params.remove(0);
                _offset = Long.parseLong(params.get(0));
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-l")) {
                params.remove(0);
                _length = Long.parseLong(params.get(0));
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-c")) {
                params.remove(0);
                _contentLength = Integer.parseInt(params.get(0));
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-b")) {
                params.remove(0);
                _bufferLength = Integer.parseInt(params.get(0));
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-i")) {
                params.remove(0);
                _inputfile = params.get(0);
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-s")) {
                params.remove(0);
                _savefile = params.get(0);
                params.remove(0);
            } else if (params.get(0).toLowerCase().startsWith("-fo")) {
                params.remove(0);
                _followSymlink = true;
            } else {
                System.out.println("Parametro errato: " + params.remove(0));
            }
        }
    }

    private static void getFileList(ArrayList<File> roots, ArrayList<String> exclude, String filter) {

        System.out.println(DateUtil.getCurrentDate() + " ricerca item");

        int addedFile = 0;
        Iterator<File> it = roots.iterator();
        while (it.hasNext()) {
            File root = it.next();
            System.out.println(DateUtil.getCurrentDate() + "   elaboro root " + root.getAbsolutePath());
            _num = 0;
            addedFile += getFileList(_files, root, exclude, filter);
        }

        System.out.println(DateUtil.getCurrentDate() + " getFileList: " + addedFile + " item trovati");
    }

    private static int getFileList(ArrayList<FileInfoExt> list, File fileObj, ArrayList<String> exclude, String filter) {

        FileFilter fileFilter = new FileFilter() {

            public boolean accept(File file) {

                if (file.getName().startsWith(PUNTO)) return false;
                return true;
            }
        };

        int addedFile = 0;
        File[] files = fileObj.listFiles(fileFilter);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];

                if (f.isDirectory()) {
                    if (!exclude.contains(f.getAbsolutePath())) {
                        addedFile += getFileList(list, f, exclude, filter);
                    } else {
                        System.out.println(">> exclude " + f.getAbsolutePath());
                    }
                } else {
                    if (f.getName().toLowerCase().endsWith(filter)) {
                        list.add(new FileInfoExt(f));
                        ++addedFile;
                        incrementNumFiles();
                    }
                }
            }
        }

        return addedFile;
    }

    private static void filter(String name, Comparator comp) {

        System.out.println(DateUtil.getCurrentDate() + " " + name + ": ordinamento");
        Collections.sort(_files, comp);

        System.out.println(DateUtil.getCurrentDate() + " " + name + ": ricerca duplicati....");
        int rimossi = removeNonDuplicate(comp);

        System.out.println(DateUtil.getCurrentDate() + " " + name + ": " + rimossi + " item rimossi, " + _files.size()
                + " item rimasti");
        if (_swDebug) dump();

    }

    private static void loadContent() {

        // recupero i primi byte di ogni file
        System.out.println(DateUtil.getCurrentDate() + " caricamento content....");
        _num = 0;
        for (FileInfo fi : _files) {
            fi.calculateInitialDigest();
            incrementNum();
        }
    }

    private static void loadDigest() {

        // recupero il digest di ogni file
        System.out.println(DateUtil.getCurrentDate() + " caricamento digest....");
        _num = 0;
        for (FileInfo fi : _files) {
            fi.calculateFullDigest();
            incrementNum();
        }
    }

    // rimuove gli elementi non duplicati
    private static int removeNonDuplicate(Comparator comparator) {

        // gestione casi particolari
        if (_files.size() == 0) return 0;
        if (_files.size() == 1) {
            _files.remove(0);
            return 1;
        }

        // vera elaborazione
        int removed = 0;
        int posPrev = 0;
        FileInfo fiPrev = _files.get(posPrev);
        int count = 1;

        for (int i = 1; i < _files.size(); ++i) {
            if (comparator.compare(_files.get(i), fiPrev) == 0) {
                ++count;
            } else {
                if (count == 1) {
                    _files.remove(posPrev);
                    --i; // l'istruzione remove ha tolto un elemento
                    ++removed;
                } else {
                    posPrev = i;
                }
                fiPrev = _files.get(posPrev);
                count = 1;
            }
        }
        // giro fuori dal loop per gestire l'ultimo elemento
        if (count == 1) {
            _files.remove(posPrev);
            ++removed;
        }

        return removed;
    }

    private static void listEquals(Comparator comparator) {

        int groups = 0;
        if (_files.size() > 0) {
            int posPrev = 0;
            FileInfoExt fiPrev = _files.get(posPrev);

            for (int i = 1; i < _files.size(); ++i) {
                if (comparator.compare(_files.get(i), fiPrev) != 0) {
                    ++groups;
                    for (int j = posPrev; j < i; j++) {
                        FileInfoExt fi = _files.get(j);
                        fi.setGruppo(groups);
                        System.out.println(fi);
                    }
                    posPrev = i;
                    fiPrev = _files.get(posPrev);
                }
            }
            // giro fuori dal loop per gestire l'ultimo elemento
            ++groups;
            for (int j = posPrev; j < _files.size(); j++) {
                FileInfoExt fi = _files.get(j);
                fi.setGruppo(groups);
                System.out.println(fi);
            }
        }

        System.out.println("----Totali----");
        System.out.println("Gruppi: " + groups);
        System.out.println("Item: " + _files.size());
    }

    private static void incrementNum() {

        ++_num;
        if (_num % MODULO == 0) System.out.println("                        " + _num + " elementi....");
    }

    private static void incrementNumFiles() {

        ++_num;
        if (_num % MODULO_FILES == 0) System.out.println("                            " + _num + " elementi....");
    }

    private static void dump() {

        System.out.println("  " + _files.size() + " items:");
        int i = 0;
        for (FileInfoExt fi : _files) {
            i++;
            System.out.println("  Item " + i + ": " + fi);
        }
    }
}
