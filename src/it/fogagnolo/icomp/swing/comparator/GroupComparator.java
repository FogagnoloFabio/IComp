
package it.fogagnolo.icomp.swing.comparator;

import java.util.Comparator;

import it.fogagnolo.icomp.FileInfoExt;

public class GroupComparator implements Comparator<FileInfoExt> {

    public int compare(FileInfoExt fi1, FileInfoExt fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        long fi1Size = fi1.getGruppo();
        long fi2Size = fi2.getGruppo();

        if (fi1Size < fi2Size) return -1;
        if (fi1Size > fi2Size) return 1;
        // TODO se uguali ordinare per filename?
        return 0;
    }
}
