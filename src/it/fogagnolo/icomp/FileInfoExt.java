
package it.fogagnolo.icomp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.fogagnolo.icomp.util.Base64;

public class FileInfoExt extends FileInfo {

    private static final long             serialVersionUID = -8391886038053966666L;
    private int                           _regola          = -1;
    private boolean                       _selected        = false;
    private int                           _gruppo          = 0;
    private GroupState                    _gState          = GroupState.NONE_SELECTED;
    private int                           _gruppoSize      = 0;

    private static final SimpleDateFormat _sdf             = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public FileInfoExt(File f) {

        super(f);
        this._regola = -1;
        this._gruppo = 0;
        this._gruppoSize = 0;
    }

    public FileInfoExt(File f, int regola) {

        super(f);
        this._regola = regola;
        this._gruppo = 0;
        this._gruppoSize = 0;
    }

    public FileInfoExt(FileInfo fi, int regola) {

        super(fi.getFileObj());
        this._regola = regola;
        this._gruppo = 0;
        this._gruppoSize = 0;
    }

    public void switchSelected() {

        _selected = !_selected;
    }

    public void setSelected(boolean selected) {

        _selected = selected;
    }

    public Boolean isSelected() {

        return _selected;
    }

    public void setRegola(int regola) {

        _regola = regola;
    }

    public int getRegola() {

        return _regola;
    }

    public void setGruppo(int gruppo) {

        _gruppo = gruppo;
    }

    public int getGruppo() {

        return _gruppo;
    }

    public void setGruppoSize(int size) {

        _gruppoSize = size;
    }

    public int getGruppoSize() {

        return _gruppoSize;
    }

    public GroupState getGroupState() {

        return _gState;
    }

    public void setGroupState(GroupState gState) {

        this._gState = gState;
    }

    public Date getUltimaModifica() {

        // return _sdf.format(new Date(getLastMod()));
        return new Date(getLastMod());
    }

    public String getInitialDigestB64() {

        return Base64.encode(getInitialDigest());
    }

    public String getFastDigestB64() {

        return Base64.encode(getFastDigest());
    }

    public String getFullDigestB64() {

        return Base64.encode(getFullDigest());
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[");

        try {
            sb.append("name=").append(getFileObj().getCanonicalPath());
        } catch (IOException e) {
            sb.append("name=").append(getFileObj().getAbsolutePath());
        }

        sb.append(", size=").append(getSize());

        sb.append(", initialDigest=").append(getInitialDigestB64());
        sb.append(", fastDigest=").append(getFastDigestB64());
        sb.append(", fullDigest=").append(getFullDigestB64());

        sb.append(", group=").append(getGruppo());
        sb.append(", isSelected=").append(isSelected());

        sb.append("]");

        return new String(sb);
    }

}
