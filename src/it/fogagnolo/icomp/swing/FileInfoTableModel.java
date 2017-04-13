
package it.fogagnolo.icomp.swing;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;

public class FileInfoTableModel extends AbstractTableModel {

	private static final long	serialVersionUID	= 7783967373455021111L;

	protected FileInfoList		_list				= null;
	protected Attributi_itf		_attributi			= null;
	protected TableColumnModel	_tcm				= null;

	public FileInfoTableModel() {

		super();
	}

	public FileInfoTableModel(FileInfoList list, Attributi_itf attributi) {

		this();
		_list = list;
		_attributi = attributi;
	}

	public TableColumnModel setCellRenderer(TableColumnModel tcm) {

		if (tcm == null) {
			// crea il renderer delle colonne
			tcm = new DefaultTableColumnModel();
		}
		// System.out.println("tcm column=" + tcm.getColumnCount());

		for (int i = 0; i < _attributi.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(_attributi.getRenderer(i));
		}

		return tcm;
	}

	public int getRowCount() {

		return _list.size();
	}

	public int getColumnCount() {

		return _attributi.getColumnCount();

	}

	public String getColumnName(int column) {

		return _attributi.getColumnName(column);
	}

	// public Class getColumnClass(int column) {
	//
	// if (column == 0 || column == 2 || column == 3) return Boolean.class;
	// else if (column == 6 || column == 7) return Integer.class;
	// else
	// return Object.class;
	// }

	public Object getValueAt(int row, int column) {

		if (row < 0 || row > _list.size()) {
			throw new IndexOutOfBoundsException("Row-index " + row + " is out of bounds.");
		}
		if (column < 0 || column > getColumnCount()) {
			throw new IndexOutOfBoundsException("Column-index " + column + " is out of bounds.");
		}

		// TODO _attributi dovrebbe essere usato solo per il rendering, i dati presi dall'oggetto vero
		// return ((Attributi_itf) _list.get(row)).get(column);
		_attributi.setFileInfo((FileInfoExt) _list.get(row));
		return _attributi.get(column);
	}

	public boolean isCellEditable(int row, int column) {

		return _attributi.isCellEditable(column);
	}

	public void addRow(Attributi_itf attributi) {

		_list.add(attributi.getFileInfo());
	}

	public void addRow(Attributi_itf attributi, int row) {

		_list.add(row, attributi.getFileInfo());
	}

	// public Attributi_itf removeRow(int row) {
	//
	// if (row < 0 || row > _list.size()) return null;
	// return _list.remove(row);
	// }

	public Attributi_itf getRow(int row) {

		if (row < 0 || row > _list.size())
			return null;
		Attributi_itf a = (Attributi_itf) _attributi.clone();
		a.setFileInfo((FileInfoExt) _list.get(row));
		return a;
	}

	@Override
	public Class<?> getColumnClass(int column) {

		return _attributi.getColumnClass(column);
	}
}