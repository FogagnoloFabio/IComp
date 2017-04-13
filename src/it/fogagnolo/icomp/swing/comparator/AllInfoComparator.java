
package it.fogagnolo.icomp.swing.comparator;

import it.fogagnolo.icomp.FileInfoExt;

public class AllInfoComparator extends ByteArrayComparator {

    public int compare(FileInfoExt fi1, FileInfoExt fi2) {

        // gestione oggetti null
        if (fi1 == null && fi2 == null) return 0;
        if (fi1 == null) return -1;
        if (fi2 == null) return 1;

        // TODO decidere se è corretto fare la compare se uno dei due valori è null, o se debba in quel caso considerare
        // il comparator successivo

        // full digest
        if (fi1.getFullDigest() != null && fi2.getFullDigest() != null) {
            return super.compare(fi1.getFullDigest(), fi2.getFullDigest());
        }

        // fast digest
        if (fi1.getFastDigest() != null && fi2.getFastDigest() != null) {
            return super.compare(fi1.getFastDigest(), fi2.getFastDigest());
        }

        // initial digest
        if (fi1.getInitialDigestB64() != null && fi2.getInitialDigestB64() != null) {
            return super.compare(fi1.getInitialDigest(), fi2.getInitialDigest());
        }

        // size
        long fi1Size = fi1.getSize();
        long fi2Size = fi2.getSize();

        if (fi1Size < fi2Size) return -1;
        if (fi1Size > fi2Size) return 1;
        return 0;
    }
}
