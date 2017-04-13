
package it.fogagnolo.icomp.swing.regola;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.TableCellRenderer;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.swing.Attributi_itf;
import it.fogagnolo.icomp.swing.renderer.NumberCellRenderer;
import it.fogagnolo.icomp.swing.renderer.StringCellRenderer;

public class RegolaAttributi implements Attributi_itf, Serializable {

    private static final long      serialVersionUID = -8391886038053908600L;
    private int                    _numero          = 0;
    private Boolean                _abilitata       = true;
    private String                 _percorso        = "";
    private Boolean                _ricorsiva       = true;
    private Boolean                _seguiLink       = true;
    // elenco delle estensioni da considerare, null per considerare tutto
    private ArrayList<String>      _includi         = new ArrayList<String>();
    // elenco dei folder da escludere, percorso completo di File.getAbsolutePath()
    private ArrayList<String>      _escludi         = new ArrayList<String>();
    private long                   _dimMin          = -1;
    private long                   _dimMax          = -1;

    public static final String[]   COLUMN_NAMES     = { "Abilitata", "Percorso", "Ricorsiva", "Segui link", "Includi",
            "Escludi", "Dim min", "Dim max"        };

    public static final Class<?>[] COLUMN_CLASSES   = { Boolean.class, String.class, Boolean.class, Boolean.class,
            String.class, String.class, Integer.class, Integer.class };

    public RegolaAttributi() {

        super();
    }

    // public RegolaAttributi(int numero, boolean abilitata, String percorso, boolean ricorsiva, boolean seguiLink,
    // String includi, String escludi, long dimMin, long dimMax) {
    //
    // super();
    // this._numero = numero;
    // this._abilitata = abilitata;
    // this._percorso = percorso;
    // this._ricorsiva = ricorsiva;
    // this._seguiLink = seguiLink;
    // clearIncludi();
    // addIncludi(includi);
    // clearEscludi();
    // addEscludi(escludi);
    // this._dimMin = dimMin;
    // this._dimMax = dimMax;
    // }

    public int getNumero() {

        return _numero;
    }

    public void setNumero(int numero) {

        this._numero = numero;
    }

    public Boolean isAbilitata() {

        return _abilitata;
    }

    public void setAbilitata(Boolean abilitata) {

        this._abilitata = abilitata;
    }

    public String getPercorso() {

        return _percorso;
    }

    public void setPercorso(String percorso) {

        this._percorso = percorso;
    }

    public Boolean isRicorsiva() {

        return _ricorsiva;
    }

    public void setRicorsiva(Boolean ricorsiva) {

        this._ricorsiva = ricorsiva;
    }

    public Boolean isSeguiLink() {

        return _seguiLink;
    }

    public void setSeguiLink(Boolean seguiLink) {

        this._seguiLink = seguiLink;
    }

    public String getIncludi() {

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : _includi) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public ArrayList<String> getIncludiList() {

        return _includi;
    }

    public void setIncludi(String includi) {

        clearIncludi();
        String[] sa = includi.split("[;,]");
        for (String s : sa) {
            this._includi.add(s.toLowerCase().trim());
        }
    }

    public void clearIncludi() {

        this._includi.clear();
    }

    public void addIncludi(String includi) {

        this._includi.add(includi.toLowerCase());
    }

    public String getEscludi() {

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : _escludi) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public ArrayList<String> getEscludiList() {

        return _escludi;
    }

    public void setEscludi(String escludi) {

        clearEscludi();
        String[] sa = escludi.split("[;,]");
        for (String s : sa) {
            this._escludi.add(s.toLowerCase().trim());
        }
    }

    public void clearEscludi() {

        this._escludi.clear();
    }

    public void addEscludi(String escludi) {

        this._escludi.add(escludi.toLowerCase());
    }

    public long getDimMin() {

        return _dimMin;
    }

    public void setDimMin(long dimMin) {

        this._dimMin = dimMin;
    }

    public long getDimMax() {

        return _dimMax;
    }

    public void setDimMax(long dimMax) {

        this._dimMax = dimMax;
    }

    @Override
    public Object get(int column) {

        Object value = null;

        switch (column) {
            case 0:
                value = isAbilitata();
                break;
            case 1:
                value = getPercorso();
                break;
            case 2:
                value = isRicorsiva();
                break;
            case 3:
                value = isSeguiLink();
                break;
            case 4:
                value = getIncludi();
                break;
            case 5:
                value = getEscludi();
                break;
            case 6:
                value = getDimMin();
                break;
            case 7:
                value = getDimMax();
                break;
        }

        return value;
    }

    @Override
    public TableCellRenderer getRenderer(int column) {

        TableCellRenderer r = null;
        // if (_fi == null) return null;

        switch (column) {
            case 0:
                r = new StringCellRenderer();
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
                r = new StringCellRenderer();
                break;
            case 5:
                r = new StringCellRenderer();
                break;
            case 6:
                r = new NumberCellRenderer();
                break;
            case 7:
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

        return false;
    }

    @Override
    public void setFileInfo(FileInfoExt fi) {

        // TODO Auto-generated method stub

    }

    @Override
    public FileInfoExt getFileInfo() {

        // TODO Auto-generated method stub
        return null;
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
