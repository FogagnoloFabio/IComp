
package it.fogagnolo.icomp.swing;

import java.util.Iterator;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;

public class FileInfoTableModelSelected extends FileInfoTableModel {

	private static final long serialVersionUID = 7783967373455021112L;

	// private FileInfoList _list = null;
	// private Attributi_itf _attributi = null;
	// private TableColumnModel _tcm = null;

	public FileInfoTableModelSelected() {

		super();
	}

	public FileInfoTableModelSelected(FileInfoList list, Attributi_itf attributi) {

		super(list, attributi);
	}

	@Override
	public int getRowCount() {

		if (_list == null) {
			System.out.println("FileInfoTableModelSelected.getRowCount()=0");
			return 0;
		}

		int num = 0;
		Iterator<FileInfoExt> i = _list.iterator();
		while (i.hasNext()) {
			FileInfoExt fie = i.next();
			if (fie.isSelected())
				++num;
		}

		System.out.println("FileInfoTableModelSelected.getRowCount()=" + num);
		return num;
	}

	@Override
	public Object getValueAt(int row, int column) {

		// se row supera la vera dimensione degli oggetti selezionati me ne
		// accorgo nel loop
		if (row < 0 || row > _list.size()) {
			throw new IndexOutOfBoundsException("Row-index " + row + " is out of bounds.");
		}
		if (column < 0 || column > getColumnCount()) {
			throw new IndexOutOfBoundsException("Column-index " + column + " is out of bounds.");
		}

		// TODO _attributi dovrebbe essere usato solo per il rendering, i dati
		// presi dall'oggetto vero
		// return ((Attributi_itf) _list.get(row)).get(column);

		int num = -1;
		int realSize = -1;
		FileInfoExt fie = null;
		Iterator<FileInfoExt> i = _list.iterator();

		// TODO eccezione se row = 0
		while (i.hasNext()) {
			fie = i.next();
			++realSize;

			if (fie.isSelected()) {
				++num;
			}
			if (num == row) {
				break;
			}
		}
		if (num != row) {
			System.out.println("FileInfoTableModelSelected.getValueAt(" + row + "," + column + "): realSize=" + realSize + ", num=" + num);
			throw new IndexOutOfBoundsException("Row-index " + row + " is out of bounds. row=" + row + ", column=" + column + ", num=" + num);
		}
		_attributi.setFileInfo(fie);

		return _attributi.get(column);
	}

	@Override
	public void addRow(Attributi_itf attributi) {

		// TODO posizione non corretta, considerare isSelected()
		_list.add(attributi.getFileInfo());
	}

	@Override
	public void addRow(Attributi_itf attributi, int row) {

		// TODO posizione non corretta, considerare isSelected()
		_list.add(row, attributi.getFileInfo());
	}

	// public Attributi_itf removeRow(int row) {
	//
	// if (row < 0 || row > _list.size()) return null;
	// return _list.remove(row);
	// }

	@Override
	public Attributi_itf getRow(int row) {

		// se row supera la vera dimensione degli oggetti selezionati me ne
		// accorgo nel loop
		if (_list == null || row < 0 || row > _list.size())
			return null;
		Attributi_itf a = (Attributi_itf) _attributi.clone();

		int num = 0;
		FileInfoExt fie = null;
		Iterator<FileInfoExt> i = _list.iterator();
		while (i.hasNext()) {
			fie = i.next();
			if (fie.isSelected() && num == row) {
				break;
			}

			++num;
		}
		if (num == row) {
			a.setFileInfo(fie);
		}

		return a;
	}
}