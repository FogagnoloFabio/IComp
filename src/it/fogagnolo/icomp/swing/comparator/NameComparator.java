
package it.fogagnolo.icomp.swing.comparator;

import java.util.Comparator;

import it.fogagnolo.icomp.FileInfoExt;

public class NameComparator implements Comparator<FileInfoExt> {

    public int compare(FileInfoExt fi1, FileInfoExt fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        String fi1Name = fi1.getName();
        String fi2Name = fi2.getName();

        return fi1Name.compareTo(fi2Name);
    }
}
