
package it.fogagnolo.icomp.comparator;

import it.fogagnolo.icomp.FileInfo;

import java.util.Comparator;

public class SizeComparator implements Comparator<FileInfo> {

    public int compare(FileInfo fi1, FileInfo fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        long fi1Size = fi1.getSize();
        long fi2Size = fi2.getSize();

        if (fi1Size < fi2Size) return -1;
        if (fi1Size > fi2Size) return 1;
        return 0;
    }
}
