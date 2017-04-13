
package it.fogagnolo.icomp.swing.ricerca;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.Engine;
import it.fogagnolo.icomp.swing.FileInfoTableModel;
import it.fogagnolo.icomp.swing.ICSComponent;
import it.fogagnolo.icomp.swing.Serializer;
import it.fogagnolo.icomp.swing.comparator.FastDigestComparator;
import it.fogagnolo.icomp.swing.comparator.FullDigestComparator;
import it.fogagnolo.icomp.swing.comparator.InitialDigestComparator;
import it.fogagnolo.icomp.swing.comparator.SizeComparator;
import it.fogagnolo.icomp.swing.regola.RegolaAttributi;

public class RicercaTabella extends ICSComponent implements Costanti_itf, ActionListener {

    private static final long          serialVersionUID         = 710298483964723107L;

    private static final String        DESCR                    = "Ricerca";
    private static final String        FILE_DEMO                = "D:/temp/ricerca.icr";

    private JFrame                     _fContainer              = null;
    private ArrayList<RegolaAttributi> _regole                  = null;
    private FileInfoList               _list                    = null;
    // private Vector<String> _log = null;
    private DefaultListModel<String>   _logModel                = null;

    private JButton                    _bRicerca                = null;
    private JButton                    _bStop                   = null;
    private JButton                    _bSvuota                 = null;
    private JButton                    _bDuplicatiDimensione    = null;
    private JButton                    _bDuplicatiInitialDigest = null;
    private JButton                    _bDuplicatiFastDigest    = null;
    private JButton                    _bDuplicatiFullDigest    = null;
    private JButton                    _bSetGroups              = null;
    private JButton                    _bSalva                  = null;
    private JButton                    _bCarica                 = null;
    private JButton                    _bValida                 = null;

    private JTable                     _jTable                  = null;
    private JList                      _jListLog                = null;
    private FileInfoTableModel         _tModel                  = null;
    private int                        _modelRow                = -1;
    private Engine                     _engine                  = null;

    // TODO da mettere in file di configurazione
    private static boolean             _compareSize             = true;
    private static boolean             _compareInitialDigest    = true;
    private static boolean             _compareFastDigest       = true;
    private static boolean             _compareFullDigest       = true;

    public RicercaTabella(JFrame jf, ArrayList<RegolaAttributi> regole, FileInfoList list) {

        setContainer(jf);
        setName(DESCR);
        _regole = regole;
        _list = list;
        // _log = new Vector<String>();
    };

    private void setContainer(JFrame jf) {

        _fContainer = jf;
    }

    public Component getComponent() {

        // tabella con i risultati della ricerca
        // _jTable = new JTable();
        _tModel = new FileInfoTableModel(_list, new RicercaAttributi());
        _jTable = new JTable(_tModel);
        // _jTable.setColumnModel(_tModel.getTableColumnModel());
        // _tModel.getTableColumnModel(_jTable.getColumnModel());
        // _jTable.setColumnModel(_tModel.getTableColumnModel());

        _jTable.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    switchSelect((JTable) e.getSource(), e.getPoint());
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    dettaglio((JTable) e.getSource(), e.getPoint());
                }
            }
        });

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(_tModel);
        _jTable.setRowSorter(sorter);
        _jTable.setRowHeight(ROWHEIGHT);
        // dumpColumnModel(_jTable.getColumnModel());
        _jTable.setColumnModel(_tModel.setCellRenderer(_jTable.getColumnModel())); // TODO perché qui va?
        // dumpColumnModel(_jTable.getColumnModel());
        JScrollPane jspRicerca = new JScrollPane(_jTable);

        // tabella con i log
        // _jListLog = new JList(_log);
        _logModel = new DefaultListModel<String>();
        _jListLog = new JList(_logModel);
        JScrollPane jspLog = new JScrollPane(_jListLog);
        // jspLog.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
        //
        // public void adjustmentValueChanged(AdjustmentEvent ae) {
        //
        // ae.getAdjustable().setValue(ae.getAdjustable().getMaximum());
        // }
        // });

        Box bRicerca = Box.createVerticalBox();
        bRicerca.add(jspRicerca);
        bRicerca.add(jspLog);

        // tasti funzione
        _bRicerca = new JButton("Ricerca");
        _bRicerca.addActionListener(this);
        _bStop = new JButton("Ferma ricerca");
        _bStop.addActionListener(this);
        _bDuplicatiDimensione = new JButton("Duplicati dimensione");
        _bDuplicatiDimensione.addActionListener(this);
        _bDuplicatiInitialDigest = new JButton("Duplicati Initial digest");
        _bDuplicatiInitialDigest.addActionListener(this);
        _bDuplicatiFastDigest = new JButton("Duplicati Fast digest");
        _bDuplicatiFastDigest.addActionListener(this);
        _bDuplicatiFullDigest = new JButton("Duplicati Full digest");
        _bDuplicatiFullDigest.addActionListener(this);
        _bSetGroups = new JButton("setGroups()");
        _bSetGroups.addActionListener(this);
        _bSvuota = new JButton("Svuota");
        _bSvuota.addActionListener(this);
        _bSalva = new JButton("Salva");
        _bSalva.addActionListener(this);
        _bCarica = new JButton("Carica");
        _bCarica.addActionListener(this);
        _bValida = new JButton("Valida");
        _bValida.addActionListener(this);

        JPanel pRAzioni1 = new JPanel();
        pRAzioni1.setLayout(new BoxLayout(pRAzioni1, BoxLayout.X_AXIS));
        pRAzioni1.add(_bRicerca);
        pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        // pRAzioni1.add(_bStop);
        // pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni1.add(_bDuplicatiDimensione);
        pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni1.add(_bDuplicatiInitialDigest);
        pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni1.add(_bDuplicatiFastDigest);
        pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni1.add(_bDuplicatiFullDigest);
        pRAzioni1.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni1.add(_bSetGroups);
        bRicerca.add(pRAzioni1);
        JPanel pRAzioni2 = new JPanel();
        pRAzioni2.setLayout(new BoxLayout(pRAzioni2, BoxLayout.X_AXIS));
        pRAzioni2.add(_bSvuota);
        pRAzioni2.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni2.add(_bSalva);
        pRAzioni2.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni2.add(_bCarica);
        pRAzioni2.add(Box.createRigidArea(new Dimension(5, 0)));
        pRAzioni2.add(_bValida);
        bRicerca.add(pRAzioni2);

        return bRicerca;
    }

    private void switchSelect(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col == 0) {
            _modelRow = jTable.convertRowIndexToModel(row);
            _list.switchSelect(_modelRow);
            _tModel.fireTableDataChanged();
        }
    }

    private void dettaglio(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col >= 0) { // se index == -1 allora il click non Ã¨ su
                                    // un elemento
            jTable.setRowSelectionInterval(row, row);
            _modelRow = jTable.convertRowIndexToModel(row);
            // int modelCol = jTable.convertColumnIndexToModel(col);
            // Object selection = jTable.getValueAt(row, col);
            // System.out.println("Hai selezionato: view row=" + row +
            // ", model=(" + _modelRow + "," + modelCol
            // + "), e fatto click su '" + selection + "'");

            FileInfoExt fi = (FileInfoExt) _list.get(_modelRow);
            RicercaDettaglio r = new RicercaDettaglio(fi);
            r.showComponent(_fContainer);
            fi = r.getAttributi();
            if (fi != null) _list.set(_modelRow, fi);
            _tModel.fireTableDataChanged();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bRicerca)) {
            _engine = new Engine(_regole, _list, _logModel);
            _engine.start();
            _tModel.fireTableDataChanged();
            // _jListLog.setListData(_log);
            // _jListLog.revalidate();
            // _jListLog.repaint();
        } else if (eventSource.equals(_bStop)) {
            if (_engine != null) _engine.stop();
        } else if (eventSource.equals(_bDuplicatiDimensione)) {

            Comparator comp = null;

            if (_compareSize) {
                comp = new SizeComparator();
                _list.filter("filterOnSize", comp, _logModel);
            }

            _list.setGroups();

        } else if (eventSource.equals(_bDuplicatiInitialDigest)) {

            Comparator comp = null;

            if (_compareSize) {
                comp = new SizeComparator();
                _list.filter("filterOnSize", comp, _logModel);
            }

            if (_compareInitialDigest) {
                _list.loadInitialDigest(_logModel);
                _tModel.fireTableDataChanged();
                comp = new InitialDigestComparator();
                _list.filter("Initial Digest", comp, _logModel);
            }

            _list.setGroups();

        } else if (eventSource.equals(_bDuplicatiFastDigest)) {

            Comparator comp = null;

            if (_compareSize) {
                comp = new SizeComparator();
                _list.filter("filterOnSize", comp, _logModel);
            }

            if (_compareFastDigest) {
                _list.loadFastDigest(_logModel);
                _tModel.fireTableDataChanged();
                comp = new FastDigestComparator();
                _list.filter("Fast Digest", comp, _logModel);
            }

            _list.setGroups();

        } else if (eventSource.equals(_bDuplicatiFullDigest)) {

            Comparator comp = null;

            if (_compareSize) {
                comp = new SizeComparator();
                _list.filter("filterOnSize", comp, _logModel);
            }

            if (_compareFullDigest) {
                _list.loadFullDigest(_logModel);
                _tModel.fireTableDataChanged();
                comp = new FullDigestComparator();
                _list.filter("Full Digest", comp, _logModel);
            }

            _list.setGroups();

        } else if (eventSource.equals(_bSetGroups)) {
            _list.fabio();
            _list.setGroups();
        } else if (eventSource.equals(_bSvuota)) {
            _list.clear();
            System.out.println("Ricerca flussi svuotata");
        } else if (eventSource.equals(_bSalva)) {
            Serializer s = new Serializer(TYPE_RICERCA);
            s.setFileName(FILE_DEMO);
            s.write(_list);
        } else if (eventSource.equals(_bCarica)) {
            _list.clear();
            Serializer s = new Serializer(TYPE_RICERCA);
            s.setFileName(FILE_DEMO);
            _list.addAll((ArrayList<FileInfoExt>) s.read());
            _tModel.fireTableDataChanged();
        } else if (eventSource.equals(_bValida)) {
            _list.validate(_logModel);
        }
    }

    public void dumpColumnModel(TableColumnModel tcm) {

        System.out.println(tcm.getColumnCount() + " colonne");
        for (int i = 0; i < tcm.getColumnCount(); i++) {
            Object o = tcm.getColumn(i).getCellRenderer();
            if (o != null) {
                System.out.println("tcm (" + i + "): "
                        + tcm.getColumn(i).getCellRenderer().getClass().getCanonicalName());
            }
        }
    }
}
