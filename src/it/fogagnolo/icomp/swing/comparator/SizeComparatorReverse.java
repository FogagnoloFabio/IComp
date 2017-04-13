
package it.fogagnolo.icomp.swing.comparator;

import it.fogagnolo.icomp.FileInfoExt;

public class SizeComparatorReverse extends SizeComparator {

    public int compare(FileInfoExt fi1, FileInfoExt fi2) {

        return -super.compare(fi1, fi2);
    }
}
