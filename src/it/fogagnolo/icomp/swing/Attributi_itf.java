
package it.fogagnolo.icomp.swing;

import javax.swing.table.TableCellRenderer;

import it.fogagnolo.icomp.FileInfoExt;

public interface Attributi_itf {

    public Object get(int column);

    public void setFileInfo(FileInfoExt fi);

    public FileInfoExt getFileInfo();

    public int getColumnCount();

    public String getColumnName(int column);

    public Class<?> getColumnClass(int column);

    public boolean isCellEditable(int column);

    public Object clone();

    public TableCellRenderer getRenderer(int i);
}
