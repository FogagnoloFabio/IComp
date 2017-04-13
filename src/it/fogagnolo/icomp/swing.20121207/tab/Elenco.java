
package it.fogagnolo.icomp.swing.tab;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.ICSComponent;
import it.fogagnolo.icomp.swing.ICSTableModel;

public class Elenco extends ICSComponent implements Costanti_itf {

    private static final long   serialVersionUID = 710298483964723106L;

    private static final String DESCR            = "Elenco";

    public Elenco() {

        setName(DESCR);
    };

    public Component getComponent() {

        final String[] columnName = { "Percorso", "Nome file", "Dimensione", "Ultima modifica", "Regola" };

        // Create the dummy data (a few rows of names)
        final Object[][] data = { { "D:\\Fogagnolo\\salva", "File1", 12345, new Date(1291242124), "Regola1" },
                { "D:\\Fogagnolo\\salva", "File2", 11111, new Date(914234124), "Regola2" },
                { "D:\\Fogagnolo\\salva", "File3", 12345, new Date(1291434124), "Regola3" },
                { "D:\\Fogagnolo\\salva", "File4", 11111, new Date(1291223124), "Regola4" } };

        // Create a model of the data.
        TableModel dataModel = new ICSTableModel(columnName, data, null);

        // Create the table
        JTable tableView = new JTable(dataModel);
        tableView.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    popup((JTable) e.getSource(), e.getPoint());
                }
            }
        });

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
        tableView.setRowSorter(sorter);

        tableView.setRowHeight(ROWHEIGHT);

        return new JScrollPane(tableView);
    }

    private static void popup(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col >= 0) { // se index == -1 allora il click non è su un elemento
            jTable.setRowSelectionInterval(row, row);
            Object selection = jTable.getModel().getValueAt(row, col);
            JPopupMenu popup = new JPopupMenu();
            popup.add("Hai fatto click su {" + selection + "}");
            popup.add("Modifica");
            popup.add("Elimina");
            popup.show(jTable, clickPoint.x, clickPoint.y);
        }
    }
}
