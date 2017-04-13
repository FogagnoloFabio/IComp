
package it.fogagnolo.icomp.swing.renderer;

import javax.swing.table.DefaultTableCellRenderer;

public class StringCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -1883014628745476146L;

    public StringCellRenderer() {

        super();
    }

    public void setValue(Object value) {

        setText((value == null) ? "" : value.toString());
    }
}