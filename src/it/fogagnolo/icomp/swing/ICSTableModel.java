
package it.fogagnolo.icomp.swing;

import javax.swing.table.DefaultTableModel;

public class ICSTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 640188267477456547L;

    private boolean[]         _editable        = null;

    public ICSTableModel(String[] columnName, Object[][] data, boolean[] editable) {

        super(data, columnName);
        _editable = editable;
    }

    public boolean isCellEditable(int row, int col) {

        if (_editable == null) return false;
        if (col > _editable.length) return false;
        return _editable[col - 1];
    }

}
