
package it.fogagnolo.icomp;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

    private static final String PUNTO = ".";

    public boolean accept(File dir, String fname) {

        if (fname.startsWith(PUNTO)) return false;
        return true;
    }

}
