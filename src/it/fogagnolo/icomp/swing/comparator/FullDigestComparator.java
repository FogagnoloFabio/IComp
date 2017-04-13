
package it.fogagnolo.icomp.swing.comparator;

import it.fogagnolo.icomp.FileInfoExt;

public class FullDigestComparator extends ByteArrayComparator {

    public int compare(FileInfoExt fi1, FileInfoExt fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        return super.compare(fi1.getFullDigest(), fi2.getFullDigest());
    }
}
