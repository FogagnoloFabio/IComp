
package it.fogagnolo.icomp.swing;

public interface Attributi_itf {

    public Object get(int column);

    public int getColumnCount();

    public String getColumnName(int column);

    public Class<?> getColumnClass(int column);

    public boolean isCellEditable(int column);
}
