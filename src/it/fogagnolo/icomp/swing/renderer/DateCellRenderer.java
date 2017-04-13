
package it.fogagnolo.icomp.swing.renderer;

import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class DateCellRenderer extends DefaultTableCellRenderer {

    private static final long             serialVersionUID = -3263484016827466001L;
    private static final SimpleDateFormat sdf              = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public DateCellRenderer() {

        super();
    }

    public void setValue(Object value) {

        setText((value == null) ? "" : sdf.format(value));
        String f = sdf.format(value);
    }
}