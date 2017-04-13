
package it.fogagnolo.icomp.swing.comparator;

import java.util.Comparator;

import it.fogagnolo.icomp.FileInfoExt;

public abstract class StringComparator implements Comparator<FileInfoExt> {

    public int compare(String s1, String s2) {

        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;

        return s1.compareTo(s2);
    }
}
