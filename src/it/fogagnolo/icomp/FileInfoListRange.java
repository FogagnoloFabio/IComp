
package it.fogagnolo.icomp;

public class FileInfoListRange {

    private int _start = 0;
    private int _end   = 0;

    FileInfoListRange(int start, int end) {

        _start = start;
        _end = end;
    }

    public int getStart() {

        return _start;
    }

    public int getEnd() {

        return _end;
    }
}
