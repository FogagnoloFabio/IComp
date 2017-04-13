
package it.fogagnolo.icomp.swing;

public class RegolaElement {

    private boolean abilitata = true;
    private int     numero    = 0;
    private String  percorso  = null;
    private boolean ricorsiva = true;
    private boolean seguiLink = true;
    private String  includi   = null;
    private String  escludi   = null;
    private long    dimMin    = -1;
    private long    dimMax    = -1;

    public RegolaElement(boolean abilitata, int numero, String percorso, boolean ricorsiva, boolean seguiLink,
            String includi, String escludi, long dimMin, long dimMax) {

        super();
        this.abilitata = abilitata;
        this.numero = numero;
        this.percorso = percorso;
        this.ricorsiva = ricorsiva;
        this.seguiLink = seguiLink;
        this.includi = includi;
        this.escludi = escludi;
        this.dimMin = dimMin;
        this.dimMax = dimMax;
    }

    public boolean isAbilitata() {

        return abilitata;
    }

    public void setAbilitata(boolean abilitata) {

        this.abilitata = abilitata;
    }

    public int getNumero() {

        return numero;
    }

    public void setNumero(int numero) {

        this.numero = numero;
    }

    public String getPercorso() {

        return percorso;
    }

    public void setPercorso(String percorso) {

        this.percorso = percorso;
    }

    public boolean isRicorsiva() {

        return ricorsiva;
    }

    public void setRicorsiva(boolean ricorsiva) {

        this.ricorsiva = ricorsiva;
    }

    public boolean isSeguiLink() {

        return seguiLink;
    }

    public void setSeguiLink(boolean seguiLink) {

        this.seguiLink = seguiLink;
    }

    public String getIncludi() {

        return includi;
    }

    public void setIncludi(String includi) {

        this.includi = includi;
    }

    public String getEscludi() {

        return escludi;
    }

    public void setEscludi(String escludi) {

        this.escludi = escludi;
    }

    public long getDimMin() {

        return dimMin;
    }

    public void setDimMin(long dimMin) {

        this.dimMin = dimMin;
    }

    public long getDimMax() {

        return dimMax;
    }

    public void setDimMax(long dimMax) {

        this.dimMax = dimMax;
    }

    public Object[] getAsArray() {

        Object[] a = new Object[9];

        a[0] = isAbilitata();
        a[1] = getNumero();
        a[2] = getPercorso();
        a[3] = isRicorsiva();
        a[4] = isSeguiLink();
        a[5] = getIncludi();
        a[6] = getEscludi();
        a[7] = getDimMin();
        a[8] = getDimMax();

        return a;
    }
}
