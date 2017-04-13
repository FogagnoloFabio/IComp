
package it.fogagnolo.icomp.swing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import it.fogagnolo.icomp.FileFilter;
import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.swing.regola.RegolaAttributi;
import it.fogagnolo.icomp.util.DateUtil;

public class Engine {

    private ArrayList<RegolaAttributi> _regole  = null;
    private ArrayList<FileInfoExt>     _ricerca = null;
    private DefaultListModel<String>   _log     = null;

    private boolean                    _canRun  = false;

    public Engine(ArrayList<RegolaAttributi> regole, ArrayList<FileInfoExt> ricerca, DefaultListModel<String> log) {

        _regole = regole;
        _ricerca = ricerca;
        _log = log;
        _canRun = true;
    }

    public void start() {

        _log.addElement(DateUtil.getCurrentDate() + " ricerca item");
        System.out.println(DateUtil.getCurrentDate() + " ricerca item");
        for (RegolaAttributi regola : _regole) {
            if (regola.isAbilitata()) {
                _log.addElement(DateUtil.getCurrentDate() + " Elaborazione regola " + regola.getNumero());
                System.out.println(DateUtil.getCurrentDate() + " Elaborazione regola " + regola.getNumero());
                File f = new File(regola.getPercorso());
                if (f != null) {
                    _log.addElement(DateUtil.getCurrentDate() + " Regola " + regola.getNumero() + " - Root folder: "
                            + f.getAbsolutePath());
                    System.out.println(DateUtil.getCurrentDate() + " Regola " + regola.getNumero() + " - Root folder: "
                            + f.getAbsolutePath());
                    int addedFile = getFileList(f, regola);
                    _log.addElement(DateUtil.getCurrentDate() + " " + addedFile + " item trovati");
                    System.out.println(DateUtil.getCurrentDate() + " " + addedFile + " item trovati");
                } else {
                    _log.addElement(DateUtil.getCurrentDate() + " Il folder non esiste: " + regola.getPercorso());
                    System.out.println(DateUtil.getCurrentDate() + " Il folder non esiste: " + regola.getPercorso());
                }
            }
        }
        _log.addElement(DateUtil.getCurrentDate() + " fine ricerca");
        _log.addElement("----------");
        System.out.println(DateUtil.getCurrentDate() + " fine ricerca");
        System.out.println("----------");
    }

    private int getFileList(File fileObj, RegolaAttributi regola) {

        FileFilter fileFilter = new FileFilter();

        int addedFile = 0;
        if (_canRun) {
            File[] files = fileObj.listFiles(fileFilter);
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];

                    if (f.isDirectory()) {
                        if (!regola.getEscludiList().contains(f.getAbsolutePath().toLowerCase())) {
                            int num = getFileList(f, regola);
                            addedFile += num;
                            System.out.println("++ " + f.toString() + ": " + num + " item");
                        } else {
                            _log.addElement(">> exclude " + f.getAbsolutePath());
                        }
                    } else {
                        boolean include = false;
                        if (regola.getIncludiList().isEmpty()) {
                            include = true;
                        } else {
                            String fname = f.getName().toLowerCase();
                            for (String includeExt : regola.getIncludiList()) {
                                if (fname.endsWith(includeExt)) {
                                    include = true;
                                }
                            }
                        }

                        if (include) {
                            if (regola.isSeguiLink()) {
                                _ricerca.add(new FileInfoExt(f, regola.getNumero()));
                                ++addedFile;
                            } else {
                                if (!isLink(f)) {
                                    _ricerca.add(new FileInfoExt(f, regola.getNumero()));
                                    ++addedFile;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            _log.addElement(">> execution stopped");
        }

        return addedFile;
    }

    private boolean isLink(File f) {

        try {
            if (!f.exists()) return true;
            else {
                String cnnpath = f.getCanonicalPath();
                String abspath = f.getAbsolutePath();
                return !abspath.equals(cnnpath);
            }
        } catch (IOException ex) {
            System.err.println(ex);
            return true;
        }

    }

    public void stop() {

        _canRun = false;
        _log.addElement(">> stop requested");
    }

}
