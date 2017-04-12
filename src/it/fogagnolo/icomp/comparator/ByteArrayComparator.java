
package it.fogagnolo.icomp.comparator;

import it.fogagnolo.icomp.FileInfo;

import java.util.Comparator;

public abstract class ByteArrayComparator implements Comparator<FileInfo> {

    public int compare(byte[] ba1, byte[] ba2) {

        if (ba1 == null && ba2 == null) return 0;
        if (ba1 == null) return -1;
        if (ba2 == null) return 1;

        // confronto byte per byte
        int minLength = ba1.length < ba2.length ? ba1.length : ba2.length;
        int i = 0;
        while (i < minLength) {
            if (ba1[i] < ba2[i]) return -1;
            if (ba1[i] > ba2[i]) return 1;
            ++i;
        }

        // se tutti i byte presenti sono uguali, considero più piccolo l'array più corto
        if (ba1.length == ba2.length) return 0;
        if (ba1.length < ba2.length) return -1;
        return 1;
    }
}
