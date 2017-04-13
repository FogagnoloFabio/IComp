
package it.fogagnolo.icomp.swing.ricerca;

import java.io.Serializable;
import java.util.Date;

import javax.swing.table.TableCellRenderer;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.swing.Attributi_itf;
import it.fogagnolo.icomp.swing.renderer.DateCellRenderer;
import it.fogagnolo.icomp.swing.renderer.NumberCellRenderer;
import it.fogagnolo.icomp.swing.renderer.StringCellRenderer;

public class RicercaAttributi implements Attributi_itf, Serializable, Cloneable {

    private static final long      serialVersionUID = -8391886038053908666L;
    private FileInfoExt            _fi              = null;

    public static final String[]   COLUMN_NAMES     = { "Selezionato", "Folder name", "File name", "Dimensione",
            "Ultima modifica", "Initial Digest", "Fast Digest", "Full Digest", "Gruppo", "Regola" };

    public static final Class<?>[] COLUMN_CLASSES   = { Boolean.class, String.class, String.class, Integer.class,
            Date.class, String.class, String.class, String.class, Integer.class, Integer.class };

    public RicercaAttributi() {

        super();
    }

    @Override
    public Object get(int column) {

        Object value = null;
        if (_fi == null) return null;

        switch (column) {
            case 0:
                value = _fi.isSelected();
                break;
            case 1:
                value = _fi.getFolder();
                break;
            case 2:
                value = _fi.getName();
                break;
            case 3:
                // value = NumberFormat.getInstance().format(_fi.getSize());
                value = _fi.getSize();
                break;
            case 4:
                value = _fi.getUltimaModifica();
                break;
            case 5:
                value = _fi.getInitialDigestB64();
                break;
            case 6:
                value = _fi.getFastDigestB64();
                break;
            case 7:
                value = _fi.getFullDigestB64();
                break;
            case 8:
                value = _fi.getGruppo();
                break;
            case 9:
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
                // r = new DefaultTableCellRenderer();
                break;
            case 1:
                r = new StringCellRenderer();
                break;
            case 2:
                r = new StringCellRenderer();
                break;
            case 3:
                // value = NumberFormat.getInstance().format(_fi.getSize());
                r = new NumberCellRenderer();
                break;
            case 4:
                r = new DateCellRenderer();
                break;
            case 5:
                r = new StringCellRenderer();
                break;
            case 6:
                r = new StringCellRenderer();
                break;
            case 7:
                r = new StringCellRenderer();
                break;
            case 8:
                r = new NumberCellRenderer();
                break;
            case 9:
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
