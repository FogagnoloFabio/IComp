
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.ICSComponent;
import it.fogagnolo.icomp.swing.Serializer;
import it.fogagnolo.icomp.swing.comparator.GroupComparator;
import it.fogagnolo.icomp.swing.ricerca.RicercaDettaglio;

public class DuplicatiTabellaTree extends ICSComponent implements Costanti_itf, ActionListener, TreeSelectionListener {

    private static final long      serialVersionUID      = 710298483964723108L;

    private static final String    DESCR                 = "Duplicati";
    private static final String    FILE_DEMO             = "D:/temp/duplicati.icr";

    private JFrame                 _fContainer           = null;
    private ArrayList<FileInfoExt> _ricerca              = null;
    private DefaultMutableTreeNode _treeData             = new DefaultMutableTreeNode("Root");
    private DefaultTreeModel       _treeModel            = null;

    private JTree                  _jTree                = null;
    private JTextField             currentSelectionField = null;

    private JButton                _bAggiorna            = null;
    private JButton                _bSalva               = null;
    private JButton                _bCarica              = null;

    private int                    _modelRow             = -1;

    public DuplicatiTabellaTree(JFrame jf, ArrayList<FileInfoExt> ricerca) {

        setContainer(jf);
        setName(DESCR);
        _ricerca = ricerca;
    };

    private void setContainer(JFrame jf) {

        _fContainer = jf;
    }

    public Component getComponent() {

        // tabella con i duplicati
        updateTree();
        _jTree = new JTree(_treeData);
        expandAll(_jTree);
        _treeModel = ((DefaultTreeModel) _jTree.getModel());
        _jTree.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    dettaglio((JTable) e.getSource(), e.getPoint());
                }
            }
        });
        _jTree.addTreeSelectionListener(this);
        _treeModel.nodeStructureChanged(_treeData);
        JScrollPane jspDuplicati = new JScrollPane(_jTree);

        Box bDuplicati = Box.createVerticalBox();
        bDuplicati.add(jspDuplicati);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        _bAggiorna = new JButton("Aggiorna");
        _bAggiorna.addActionListener(this);
        _bSalva = new JButton("Salva");
        _bSalva.addActionListener(this);
        _bCarica = new JButton("Carica");
        _bCarica.addActionListener(this);
        pRAzioni.add(_bAggiorna);
        pRAzioni.add(_bSalva);
        pRAzioni.add(_bCarica);
        bDuplicati.add(pRAzioni);

        return bDuplicati;
    }

    private void dettaglio(JTable jTable, Point clickPoint) {

        int row = jTable.rowAtPoint(clickPoint);
        int col = jTable.columnAtPoint(clickPoint);
        if (row >= 0 && col >= 0) { // se index == -1 allora il click non è su
                                    // un elemento
            jTable.setRowSelectionInterval(row, row);
            _modelRow = jTable.convertRowIndexToModel(row);
            // int modelCol = jTable.convertColumnIndexToModel(col);
            // Object selection = jTable.getValueAt(row, col);
            // System.out.println("Hai selezionato: view row=" + row +
            // ", model=(" + _modelRow + "," + modelCol
            // + "), e fatto click su '" + selection + "'");

            FileInfoExt fi = _ricerca.get(_modelRow);
            RicercaDettaglio r = new RicercaDettaglio(fi);
            r.showComponent(_fContainer);
            fi = r.getAttributi();
            if (fi != null) _ricerca.set(_modelRow, fi);
            // _tModel.fireTableDataChanged();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bAggiorna)) {
            // TODO comporre treelist da _ricerca
            updateTree();
            expandAll(_jTree);
            _treeModel.nodeStructureChanged(_treeData);
        } else if (eventSource.equals(_bSalva)) {
            Serializer s = new Serializer(TYPE_DUPLICATI);
            s.setFileName(FILE_DEMO);
            s.write(_ricerca);
        } else if (eventSource.equals(_bCarica)) {
            _ricerca.clear();
            Serializer s = new Serializer(TYPE_DUPLICATI);
            s.setFileName(FILE_DEMO);
            _ricerca.addAll((ArrayList<FileInfoExt>) s.read());
            // _tModel.fireTableDataChanged();
        }
    }

    private void updateTree() {

        // ordinamento per gruppo
        Comparator comp = new GroupComparator();
        Collections.sort(_ricerca, comp);

        _treeData.removeAllChildren();
        DefaultMutableTreeNode gruppo = null;
        DefaultMutableTreeNode elemento = null;
        int oldGruppo = -1;
        for (FileInfoExt ra : _ricerca) {
            if (oldGruppo != ra.getGruppo()) {
                gruppo = new DefaultMutableTreeNode("Gruppo " + ra.getGruppo());
                _treeData.add(gruppo);
                oldGruppo = ra.getGruppo();
            }
            elemento = new DefaultMutableTreeNode(ra.getFolder() + File.pathSeparatorChar + ra.getName());
            gruppo.add(elemento);
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {

        // TODO Auto-generated method stub
        currentSelectionField.setText("Current Selection: " + _jTree.getLastSelectedPathComponent().toString());
    }

    private void expandAll(JTree tree) {

        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }
}
