
package it.fogagnolo.icomp.swing.regola;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.fogagnolo.icomp.swing.ArrayListTableModel;
import it.fogagnolo.icomp.swing.Attributi_itf;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.ICSComponent;
import it.fogagnolo.icomp.swing.Serializer;

public class Tabella extends ICSComponent implements Costanti_itf, ActionListener {

    private static final long   serialVersionUID = 710298483964723106L;

    private static final String DESCR            = "Regole";
    private static final String FILE_DEMO        = "D:/temp/regole.icr";

    private JFrame              _fContainer      = null;
    private JMenuItem           _mNuovo          = null;
    private JMenuItem           _mModifica       = null;
    private JMenuItem           _mElimina        = null;

    private JButton             _bRicerca        = null;
    private JButton             _bSalva          = null;
    private JButton             _bCarica         = null;

    ArrayList<Attributi_itf>    _data            = null;
    JTable                      _jTable          = null;
    ArrayListTableModel         _tModel          = null;
    int                         _modelRow        = -1;

    // TODO:
    // . doppio click per modifica
    // . invio per conferma popoup
    // . popup con valori al posto di Jslider

    public Tabella(JFrame jf) {

        setContainer(jf);
        setName(DESCR);
    }

    private void setContainer(JFrame jf) {

        _fContainer = jf;
    }

    public Component getComponent() {

        _data = new ArrayList<Attributi_itf>();
        // _data.add(new Attributi(0, true, "D:\\Fogagnolo\\salva", true, false, "*.nef, *.jpg, *.jpeg", "*.tmp", 1,
        // -1));
        // _data.add(new Attributi(1, false, "D:\\temp", true, false, "*.nef, *.jpg, *.jpeg", "*.tmp", 1, -1));
        // _data.add(new Attributi(2, true, "D:\\tmp", true, false, "*.nef, *.jpg, *.jpeg", "*.tmp", 1, -1));
        // _data.add(new Attributi(3, true, "Z:\\foto", true, true, "*.nef, *.jpg, *.jpeg", "*.tmp", 1, -1));
        // _data.add(new Attributi(4, false, "D:\\Fogagnolo\\salva", true, false, "*.nef, *.jpg, *.jpeg", "*.tmp", 0,
        // 99999999));
        // _data.add(new Attributi(5, false, "D:\\Fogagnolo\\salva", true, false, "*.nef, *.jpg, *.jpeg", "*.tmp", 0,
        // 99999999));

        ArrayListTableModel dataModel = new ArrayListTableModel(_data, new Attributi());
        _tModel = dataModel;

        // Create the table
        JTable table = new JTable(dataModel);
        table.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    popup((JTable) e.getSource(), e.getPoint());
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    modifica((JTable) e.getSource(), e.getPoint());
                }
            }
        });

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
        table.setRowSorter(sorter);
        table.setRowHeight(ROWHEIGHT);
        _jTable = table;

        JScrollPane jspane = new JScrollPane(table);
        jspane.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    popupNuovo((JScrollPane) e.getSource(), e.getPoint());
                }
            }
        });

        Box bRegola = Box.createVerticalBox();
        bRegola.add(jspane);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        _bRicerca = new JButton("Esegui ricerca");
        _bRicerca.addActionListener(this);
        _bSalva = new JButton("Salva");
        _bSalva.addActionListener(this);
        _bCarica = new JButton("Carica");
        _bCarica.addActionListener(this);
        pRAzioni.add(_bRicerca);
        pRAzioni.add(_bSalva);
        pRAzioni.add(_bCarica);
        bRegola.add(pRAzioni);

        return bRegola;
    }

    private void popup(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col >= 0) { // se index == -1 allora il click non � su un elemento
            jTable.setRowSelectionInterval(row, row);
            _modelRow = jTable.convertRowIndexToModel(row);
            int modelCol = jTable.convertColumnIndexToModel(col);
            Object selection = jTable.getValueAt(row, col);
            JPopupMenu popup = new JPopupMenu();
            System.out.println("Hai selezionato: view row=" + row + ", model=(" + _modelRow + "," + modelCol
                    + "), e fatto click su '" + selection + "'");

            // creating MenuItems
            _mNuovo = new JMenuItem("Nuovo");
            _mNuovo.addActionListener(this);
            popup.add(_mNuovo);
            _mModifica = new JMenuItem("Modifica");
            _mModifica.addActionListener(this);
            popup.add(_mModifica);
            _mElimina = new JMenuItem("Elimina");
            _mElimina.addActionListener(this);
            popup.add(_mElimina);

            popup.show(jTable, clickPoint.x, clickPoint.y);
        }
    }

    private void modifica(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col >= 0) { // se index == -1 allora il click non è su un elemento
            jTable.setRowSelectionInterval(row, row);
            _modelRow = jTable.convertRowIndexToModel(row);
            int modelCol = jTable.convertColumnIndexToModel(col);
            Object selection = jTable.getValueAt(row, col);
            System.out.println("Hai selezionato: view row=" + row + ", model=(" + _modelRow + "," + modelCol
                    + "), e fatto click su '" + selection + "'");

            Attributi a = (Attributi) _data.get(_modelRow);
            Dettaglio r = new Dettaglio(a);
            r.showComponent(_fContainer);
            a = r.getAttributi();
            if (a != null) _data.set(_modelRow, a);
            _tModel.fireTableDataChanged();
        }
    }

    private void popupNuovo(JScrollPane jsp, Point clickPoint) {

        JPopupMenu popup = new JPopupMenu();

        // creating MenuItems
        _mNuovo = new JMenuItem("Nuovo");
        _mNuovo.addActionListener(this);
        popup.add(_mNuovo);

        popup.show(jsp, clickPoint.x, clickPoint.y);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // menu popup
        if (eventSource.equals(_mNuovo)) {
            Dettaglio r = new Dettaglio();
            r.showComponent(_fContainer);
            Attributi a = r.getAttributi();
            if (a != null) {
                _data.add(a);
                _tModel.fireTableDataChanged();
                _tModel.fireTableStructureChanged();
            }
        } else if (eventSource.equals(_mModifica)) {
            Attributi a = (Attributi) _data.get(_modelRow);
            Dettaglio r = new Dettaglio(a);
            r.showComponent(_fContainer);
            a = r.getAttributi();
            if (a != null) _data.set(_modelRow, a);
            _tModel.fireTableDataChanged();
        } else if (eventSource.equals(_mElimina)) {
            _data.remove(_modelRow);
            _tModel.fireTableDataChanged();
        } else

        // bottoni
        if (eventSource.equals(_bRicerca)) {
        } else if (eventSource.equals(_bSalva)) {
            Serializer s = new Serializer(TYPE_REGOLA);
            s.setFileName(FILE_DEMO);
            s.write(_data);
        } else if (eventSource.equals(_bCarica)) {
            _data.clear();
            Serializer s = new Serializer(TYPE_REGOLA);
            s.setFileName(FILE_DEMO);
            _data.addAll((ArrayList<Attributi_itf>) s.read());
            _tModel.fireTableDataChanged();
        }
    }
}
