package it.fogagnolo.icomp.swing.elimina;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.sun.jna.platform.FileUtils;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.FileInfoTableModel;
import it.fogagnolo.icomp.swing.ICSComponent;

public class EliminaOpzioni extends ICSComponent implements Costanti_itf, ActionListener {

	private static final long		serialVersionUID	= 4284859342526161223L;

	private static final String		DESCR				= "Opzioni";

	protected FileInfoTableModel	_tModel				= null;
	protected FileInfoList			_list				= null;

	// campi di input
	private JRadioButton			_jrbDelete			= null;
	private JRadioButton			_jrbCestino			= null;
	private JRadioButton			_jrbAltroFolder		= null;
	private JTextField				_jtfFolder			= null;
	private JButton					_bAggiorna			= null;
	private JButton					_bVai				= null;

	public EliminaOpzioni(FileInfoTableModel tModel, FileInfoList list) {

		setName(DESCR);
		_tModel = tModel;
		_list = list;
	}

	public Component getComponent() {

		Box bElimina = Box.createVerticalBox();

		_jrbDelete = new JRadioButton("Cancellazione definitiva");
		_jrbDelete.setMnemonic(KeyEvent.VK_P);
		_jrbDelete.setSelected(false);

		_jrbCestino = new JRadioButton("Metti nel cestino");
		_jrbCestino.setMnemonic(KeyEvent.VK_G);
		_jrbCestino.setSelected(true);

		_jrbAltroFolder = new JRadioButton("Sposta in altra cartella");
		_jrbAltroFolder.setMnemonic(KeyEvent.VK_M);
		_jrbAltroFolder.setSelected(false);

		// Group the radio buttons
		ButtonGroup bgOpzioni = new ButtonGroup();
		bgOpzioni.add(_jrbDelete);
		bgOpzioni.add(_jrbCestino);
		bgOpzioni.add(_jrbAltroFolder);

		// etichette
		JPanel pRLabels = new JPanel(new GridLayout(4, 1));
		pRLabels.add(_jrbDelete);
		pRLabels.add(_jrbCestino);
		pRLabels.add(_jrbAltroFolder);

		_bAggiorna = new JButton("Aggiorna");
		_bAggiorna.addActionListener(this);
		pRLabels.add(_bAggiorna);

		// campi di input
		JPanel pRValues = new JPanel(new GridLayout(4, 1));
		pRValues.add(new Label());
		pRValues.add(new Label());
		_jtfFolder = new JTextField(40);
		pRValues.add(_jtfFolder);

		_bVai = new JButton("VAI!!");
		_bVai.addActionListener(this);
		pRValues.add(_bVai);

		Box bRParams = Box.createHorizontalBox();
		bRParams.add(pRLabels);
		bRParams.add(pRValues);
		JPanel pRParams = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pRParams.add(bRParams);
		bElimina.add(pRParams);

		return bElimina;
	}

	@Override
	public void actionPerformed(ActionEvent actionevent) {
		Object eventSource = actionevent.getSource();
		int num = 0;
		boolean useTrash = false;

		// bottoni
		if (eventSource.equals(_bVai)) {

			FileUtils fileUtils = FileUtils.getInstance();
			if (fileUtils.hasTrash()) {
				useTrash = true;
			}

			Iterator<FileInfoExt> i = _list.iterator();
			while (i.hasNext()) {
				FileInfoExt fie = i.next();
				if (fie.isSelected()) {
					if (useTrash) {
						try {
							fileUtils.moveToTrash(new File[] { new File("c:/tmp/cancella") });
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					} else {
						fie.getFileObj().delete();
					}
					++num;
				}
			}

			System.out.println("Eliminati " + num + " file");
		} else if (eventSource.equals(_bAggiorna)) {
			_tModel.fireTableDataChanged();
		}
	}

}
