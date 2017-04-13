
package it.fogagnolo.icomp.swing.elimina;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.FileInfoTableModel;
import it.fogagnolo.icomp.swing.FileInfoTableModelSelected;
import it.fogagnolo.icomp.swing.ICSComponent;
import it.fogagnolo.icomp.swing.Serializer;
import it.fogagnolo.icomp.swing.ricerca.RicercaDettaglio;

public class EliminaTabella extends ICSComponent implements Costanti_itf, ActionListener {

	private static final long serialVersionUID = 710298483964723109L;

	private static final String	DESCR		= "Elimina";
	private static final String	FILE_DEMO	= "D:/temp/Elimina.icr";

	private JFrame			_fContainer	= null;
	private FileInfoList	_list		= null;

	private JButton	_bAggiorna	= null;
	private JButton	_bSalva		= null;
	private JButton	_bCarica	= null;

	private JTable				_jTable	= null;
	private FileInfoTableModel	_tModel	= null;

	public EliminaTabella(JFrame jf, FileInfoList fil) {

		setContainer(jf);
		setName(DESCR);
		_list = fil;
	};

	private void setContainer(JFrame jf) {

		_fContainer = jf;
	}

	public Component getComponent() {

		// tabella con i risultati della ricerca
		// _jTable = new JTable();
		_tModel = new FileInfoTableModelSelected(_list, new EliminaAttributi());
		_jTable = new JTable(_tModel);

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
		_jTable.setColumnModel(_tModel.setCellRenderer(_jTable.getColumnModel())); // TODO perché qui va?
		JScrollPane jspElimina = new JScrollPane(_jTable);

		// schede
		JTabbedPane tpFlag = new JTabbedPane();

		ICSComponent icsOpzioni = new EliminaOpzioni(_tModel, _list);
		tpFlag.add(icsOpzioni.getName(), icsOpzioni.getComponent());

		// pannello con tabbed e tasti
		JPanel pMain = new JPanel();
		pMain.setLayout(new BoxLayout(pMain, BoxLayout.X_AXIS));
		Box bMain = Box.createVerticalBox();
		bMain.add(tpFlag);

		Box bElimina = Box.createVerticalBox();
		bElimina.add(jspElimina);
		bElimina.add(bMain);

		return bElimina;
	}

	private void switchSelect(JTable jTable, Point clickPoint) {

		int row = jTable.rowAtPoint(clickPoint);
		int col = jTable.columnAtPoint(clickPoint);
		if (row >= 0 && col == 0) {
			int modelRow = jTable.convertRowIndexToModel(row);
			_list.switchSelect(modelRow);
			_list.updateGroupStateByGroup(modelRow);
			_tModel.fireTableDataChanged();
		}
	}

	private void dettaglio(JTable jTable, Point clickPoint) {

		int row = jTable.rowAtPoint(clickPoint);
		int col = jTable.columnAtPoint(clickPoint);
		// se index == -1 allora il click non è su un elemento
		if (row >= 0 && col >= 0) {
			jTable.setRowSelectionInterval(row, row);
			int modelRow = jTable.convertRowIndexToModel(row);
			// int modelCol = jTable.convertColumnIndexToModel(col);
			// Object selection = jTable.getValueAt(row, col);
			// System.out.println("Hai selezionato: view row=" + row +
			// ", model=(" + _modelRow + "," + modelCol
			// + "), e fatto click su '" + selection + "'");

			FileInfoExt fi = (FileInfoExt) _list.get(modelRow);
			RicercaDettaglio r = new RicercaDettaglio(fi);
			r.showComponent(_fContainer);
			// TODO vedere se serve DettagliAttributi.getAttributi()
			// fi = r.getAttributi();
			if (fi != null)
				_list.set(modelRow, fi);
			_tModel.fireTableDataChanged();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent actionevent) {

		Object eventSource = actionevent.getSource();

		// bottoni
		if (eventSource.equals(_bAggiorna)) {
			_tModel.fireTableDataChanged();
		} else if (eventSource.equals(_bSalva)) {
			Serializer s = new Serializer(TYPE_RICERCA);
			s.setFileName(FILE_DEMO);
			s.write(_list);
		} else if (eventSource.equals(_bCarica)) {
			_list.clear();
			Serializer s = new Serializer(TYPE_RICERCA);
			s.setFileName(FILE_DEMO);

			// potrei salvare l'oggetto FileInfoList, ma questo non permetterebbe di modificare a mano il file né di contare gli oggetti
			_list.addAll((ArrayList<FileInfoExt>) s.read());
			_tModel.fireTableDataChanged();
		}
	}
}
