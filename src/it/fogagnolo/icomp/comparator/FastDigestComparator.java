
package it.fogagnolo.icomp.comparator;

import it.fogagnolo.icomp.FileInfo;

public class FastDigestComparator extends ByteArrayComparator {

    public int compare(FileInfo fi1, FileInfo fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        return compare(fi1.getFastDigest(), fi2.getFastDigest());
    }
}
