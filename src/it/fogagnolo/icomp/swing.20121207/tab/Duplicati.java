
package it.fogagnolo.icomp.swing.tab;

import java.awt.Component;

import javax.swing.JComboBox;

import it.fogagnolo.icomp.swing.ICSComponent;

public class Duplicati extends ICSComponent {

    private static final long   serialVersionUID = 710298483964723106L;

    private static final String DESCR            = "Duplicati";

    public Duplicati() {

        setName(DESCR);
    };

    public Component getComponent() {

        // Create a combo box to show that you can use one in a table.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Item1");
        comboBox.addItem("Item2");
        comboBox.addItem("Item3");
        comboBox.addItem("Item4");
        comboBox.addItem("Item5");
        comboBox.addItem("Item6");
        comboBox.addItem("Item7");
        comboBox.addItem("Item8");
        comboBox.addItem("Item9");
        return comboBox;
    }

}
