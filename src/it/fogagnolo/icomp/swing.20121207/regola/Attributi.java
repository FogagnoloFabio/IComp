
package it.fogagnolo.icomp.swing.regola;

import java.io.Serializable;

import it.fogagnolo.icomp.swing.Attributi_itf;

public class Attributi implements Attributi_itf, Serializable {

    private static final long      serialVersionUID = -8391886038053908600L;
    private Boolean                _abilitata       = true;
    private String                 _percorso        = "";
    private Boolean                _ricorsiva       = true;
    private Boolean                _seguiLink       = true;
    private String                 _includi         = "";
    private String                 _escludi         = "";
    private long                   _dimMin          = -1;
    private long                   _dimMax          = -1;

    public static final String[]   COLUMN_NAMES     = { "Abilitata", "Percorso", "Ricorsiva", "Segui link", "Includi",
            "Escludi", "Dim min", "Dim max"        };

    public static final Class<?>[] COLUMN_CLASSES   = { Boolean.class, String.class, Boolean.class, Boolean.class,
            String.class, String.class, Integer.class, Integer.class };

    public Attributi() {

        super();
    }

    public Attributi(int numero, boolean abilitata, String percorso, boolean ricorsiva, boolean seguiLink,
            String includi, String escludi, long dimMin, long dimMax) {

        super();
        this._abilitata = abilitata;
        this._percorso = percorso;
        this._ricorsiva = ricorsiva;
        this._seguiLink = seguiLink;
        this._includi = includi;
        this._escludi = escludi;
        this._dimMin = dimMin;
        this._dimMax = dimMax;
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

        return _includi;
    }

    public void setIncludi(String includi) {

        this._includi = includi;
    }

    public String getEscludi() {

        return _escludi;
    }

    public void setEscludi(String escludi) {

        this._escludi = escludi;
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
}
