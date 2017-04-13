
package it.fogagnolo.icomp.swing;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ArrayListTableModel extends AbstractTableModel {

    private static final long        serialVersionUID = 7783967373455021111L;

    private ArrayList<Attributi_itf> _list            = null;
    private Attributi_itf            _attributi       = null;

    public ArrayListTableModel() {

        super();
    }

    public ArrayListTableModel(ArrayList<Attributi_itf> list, Attributi_itf attributi) {

        this();
        _list = list;
        _attributi = attributi;
    }

    public int getRowCount() {

        return _list.size();
    }

    public int getColumnCount() {

        return _attributi.getColumnCount();

    }

    public String getColumnName(int column) {

        return _attributi.getColumnName(column);
    }

    // public Class getColumnClass(int column) {
    //
    // if (column == 0 || column == 2 || column == 3) return Boolean.class;
    // else if (column == 6 || column == 7) return Integer.class;
    // else
    // return Object.class;
    // }

    public Object getValueAt(int row, int column) {

        if (row < 0 || row > _list.size()) {
            throw new IndexOutOfBoundsException("Row-index " + row + " is out of bounds.");
        }
        if (column < 0 || column > getColumnCount()) {
            throw new IndexOutOfBoundsException("Column-index " + column + " is out of bounds.");
        }

        return _list.get(row).get(column);
    }

    public boolean isCellEditable(int row, int column) {

        return _attributi.isCellEditable(column);
    }

    public void addRow(Attributi_itf attributi) {

        _list.add(attributi);
    }

    public void addRow(Attributi_itf attributi, int row) {

        _list.add(row, attributi);
    }

    public Attributi_itf removeRow(int row) {

        if (row < 0 || row > _list.size()) return null;
        return _list.remove(row);
    }

    public Attributi_itf getRow(int row) {

        if (row < 0 || row > _list.size()) return null;
        return _list.get(row);
    }

    @Override
    public Class<?> getColumnClass(int column) {

        return _attributi.getColumnClass(column);
    }
}