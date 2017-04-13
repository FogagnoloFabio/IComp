
package it.fogagnolo.icomp.swing.elimina;

import java.io.Serializable;
import java.util.Date;

import javax.swing.table.TableCellRenderer;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.GroupState;
import it.fogagnolo.icomp.swing.Attributi_itf;
import it.fogagnolo.icomp.swing.renderer.DateCellRenderer;
import it.fogagnolo.icomp.swing.renderer.NumberCellRenderer;
import it.fogagnolo.icomp.swing.renderer.StringCellRenderer;

public class EliminaAttributi implements Attributi_itf, Serializable, Cloneable {

    private static final long      serialVersionUID = -8391886038053908665L;
    private FileInfoExt            _fi              = null;

    public static final String[]   COLUMN_NAMES     = {  "Gruppo", "Stato Gruppo", "Folder name",
            "File name", "Dimensione", "Ultima modifica", "Regola" };

    public static final Class<?>[] COLUMN_CLASSES   = { Integer.class, GroupState.class, String.class,
            String.class, Integer.class, Date.class, Integer.class };

    public EliminaAttributi() {

        super();
    }

    @Override
    public Object get(int column) {

        Object value = null;
        if (_fi == null) return null;

        switch (column) {
            case 0:
                value = _fi.getGruppo();
                break;
            case 1:
                value = _fi.getGroupState();
                break;
            case 2:
                value = _fi.getFolder();
                break;
            case 3:
                value = _fi.getName();
                break;
            case 4:
                value = _fi.getSize();
                break;
            case 5:
                value = _fi.getUltimaModifica();
                break;
            case 6:
                value = _fi.getRegola();
                break;
        }

        return value;
    }

    @Override
    public TableCellRenderer getRenderer(int column) {

        TableCellRenderer r = null;

        switch (column) {
            case 0:
                r = new NumberCellRenderer();
                break;
            case 1:
                r = new StringCellRenderer();
                break;
            case 2:
                r = new StringCellRenderer();
                break;
            case 3:
                r = new StringCellRenderer();
                break;
            case 4:
                r = new NumberCellRenderer();
                break;
            case 5:
                r = new DateCellRenderer();
                break;
            case 6:
                r = new NumberCellRenderer();
                break;
        }

        return r;
    }

    public int getColumnCount() {

        return COLUMN_NAMES.length;
    }

    public Class<?> getColumnClass(int column) {

        if (column > COLUMN_CLASSES.length) return Object.class;
        return COLUMN_CLASSES[column];
    }

    public String getColumnName(int column) {

        if (column > COLUMN_NAMES.length) return "";
        return COLUMN_NAMES[column];
    }

    public boolean isCellEditable(int column) {

        return (column == 0);
    }

    public void setFileInfo(FileInfoExt fi) {

        this._fi = fi;
    }

    public FileInfoExt getFileInfo() {

        return _fi;
    }

    @Override
    public Attributi_itf clone() {

        Attributi_itf c;
        try {
            c = (Attributi_itf) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }

        // Deep clone member fields here

        return c;
    }
}
