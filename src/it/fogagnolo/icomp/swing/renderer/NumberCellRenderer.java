
package it.fogagnolo.icomp.swing.renderer;

import java.text.DecimalFormat;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class NumberCellRenderer extends DefaultTableCellRenderer {

    private static final long          serialVersionUID = -892580039022404489L;
    private static final DecimalFormat df               = new DecimalFormat("#,##0");

    public NumberCellRenderer() {

        super();
    }

    public void setValue(Object value) {

        if (value == null) {
            setText("");
            return;
        }

        String f = df.format(value);
        setText(df.format(value));
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
}