
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.FileInfoListRange;
import it.fogagnolo.icomp.GroupState;
import it.fogagnolo.icomp.swing.FileInfoTableModel;

public class SelezioneDimensione extends Selezione_base {

	private static final long serialVersionUID = 4284859342526161223L;

	private static final String DESCR = "Dimensione";

	// stile selezione
	private JRadioButton _jrbSelezVuoti = null;
	private JRadioButton _jrbSelezTutto = null;

	// campi di input
	private JRadioButton _jrbPiuPiccolo = null;
	private JRadioButton _jrbPiuGrande = null;
	private JRadioButton _jrbMinoreDi = null;
	private JRadioButton _jrbMaggioreDi = null;
	private JRadioButton _jrbRange = null;

	private JTextField _jtfDimMinore = null;
	private JTextField _jtfDimMaggiore = null;
	private JTextField _jtfRangeDa = null;
	private JTextField _jtfRangeA = null;

	// bottoni
	private JButton _bRASeleziona = null;
	private JButton _bRADeseleziona = null;

	public SelezioneDimensione(FileInfoTableModel tModel, FileInfoList list) {

		setName(DESCR);
		_tModel = tModel;
		_list = list;
	}

	public Component getComponent() {

		_jrbSelezVuoti = new JRadioButton("Solo gruppi senza selezione");
		_jrbSelezVuoti.setMnemonic(KeyEvent.VK_S);
		_jrbSelezVuoti.setSelected(true);

		_jrbSelezTutto = new JRadioButton("Tutti i gruppi");
		_jrbSelezTutto.setMnemonic(KeyEvent.VK_T);
		_jrbSelezTutto.setSelected(false);

		// Group the radio buttons
		ButtonGroup bgStileSelezione = new ButtonGroup();
		bgStileSelezione.add(_jrbSelezVuoti);
		bgStileSelezione.add(_jrbSelezTutto);

		_jrbPiuPiccolo = new JRadioButton("Più piccolo");
		_jrbPiuPiccolo.setMnemonic(KeyEvent.VK_P);
		_jrbPiuPiccolo.setSelected(true);

		_jrbPiuGrande = new JRadioButton("Più grande");
		_jrbPiuGrande.setMnemonic(KeyEvent.VK_G);
		_jrbPiuGrande.setSelected(false);

		_jrbMinoreDi = new JRadioButton("Minore di");
		_jrbMinoreDi.setMnemonic(KeyEvent.VK_M);
		_jrbMinoreDi.setSelected(false);

		_jrbMaggioreDi = new JRadioButton("Maggiore di");
		_jrbMaggioreDi.setMnemonic(KeyEvent.VK_A);
		_jrbMaggioreDi.setSelected(false);

		_jrbRange = new JRadioButton("Range");
		_jrbRange.setMnemonic(KeyEvent.VK_R);
		_jrbRange.setSelected(false);

		// Group the radio buttons
		ButtonGroup bgData = new ButtonGroup();
		bgData.add(_jrbPiuPiccolo);
		bgData.add(_jrbPiuGrande);
		bgData.add(_jrbMinoreDi);
		bgData.add(_jrbMaggioreDi);
		bgData.add(_jrbRange);

		// pannello con radio e input box
		// Box bLabel = Box.createVerticalBox();
		// bLabel.add(_jrbPiuPiccolo);
		// bLabel.add(_jrbPiuGrande);
		// bLabel.add(_jrbMinoreDi);
		// bLabel.add(_jrbMaggioreDi);
		// bLabel.add(_jrbRange);

		// Box bInput = Box.createVerticalBox();
		// bInput.add(new Container());
		// bInput.add(new Container());
		_jtfDimMinore = new JTextField(10);
		// bInput.add(_jtfDimMinore);
		_jtfDimMaggiore = new JTextField(10);
		// bInput.add(_jtfDimMaggiore);
		_jtfRangeDa = new JTextField(10);
		_jtfRangeA = new JTextField(10);
		JPanel pRange = new JPanel(new GridLayout(1, 4));
		pRange.add(_jrbRange);
		pRange.add(new JLabel("Da"));
		pRange.add(_jtfRangeDa);
		pRange.add(new JLabel("a"));
		pRange.add(_jtfRangeA);
		// bInput.add(pRange);

		// JPanel pMain = new JPanel(new GridLayout(1, 2));
		// pMain.add(bLabel);
		// pMain.add(bInput);

		// JPanel pMain = new JPanel(new GridLayout(4, 2));
		// pMain.add(_jrbPiuPiccolo);
		// pMain.add(new Container());
		// pMain.add(_jrbPiuGrande);
		// pMain.add(new Container());
		// pMain.add(_jrbMinoreDi);
		// pMain.add(_jtfDimMinore);
		// pMain.add(_jrbMaggioreDi);
		// pMain.add(_jtfDimMaggiore);
		// pMain.add(_jrbRange);
		// pMain.add(pRange);

		Box bV = Box.createVerticalBox();
		Box bH0 = Box.createHorizontalBox();
		Box bH1 = Box.createHorizontalBox();
		Box bH2 = Box.createHorizontalBox();
		Box bH3 = Box.createHorizontalBox();
		Box bH4 = Box.createHorizontalBox();
		Box bH5 = Box.createHorizontalBox();
		Box bH6 = Box.createHorizontalBox();

		bH0.add(Box.createRigidArea(new Dimension(10, 0)));
		bH0.add(Box.createRigidArea(new Dimension(50, 0)));

		bH1.add(_jrbSelezVuoti);
		bH1.add(_jrbSelezTutto);
		bH2.add(_jrbPiuPiccolo);
		bH2.add(new Container());
		bH3.add(_jrbPiuGrande);
		bH3.add(new Container());
		bH4.add(_jrbMinoreDi);
		bH4.add(_jtfDimMinore);
		bH5.add(_jrbMaggioreDi);
		bH5.add(_jtfDimMaggiore);
		bH6.add(_jrbRange);
		bH6.add(pRange);

		bV.add(bH1);
		bV.add(bH2);
		bV.add(bH3);
		bV.add(bH4);
		bV.add(bH5);
		bV.add(bH6);

		// tasti funzione
		JPanel pRAzioni = new JPanel();
		pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
		_bRASeleziona = new JButton("Seleziona");
		_bRASeleziona.addActionListener(this);
		_bRADeseleziona = new JButton("Deseleziona");
		_bRADeseleziona.addActionListener(this);
		pRAzioni.add(_bRASeleziona);
		pRAzioni.add(_bRADeseleziona);

		Box bRicerca = Box.createVerticalBox();
		// bRicerca.add(pMain);
		bRicerca.add(bV);
		bRicerca.add(pRAzioni);

		return bRicerca;
	}

	@Override
	public void actionPerformed(ActionEvent actionevent) {

		Object eventSource = actionevent.getSource();

		// bottoni
		if (eventSource.equals(_bRASeleziona)) {

			if (_jrbPiuPiccolo.isSelected()) {
				selectSize(_jrbSelezTutto.isSelected(), _jrbPiuGrande.isSelected(), eventSource.equals(_bRASeleziona));
			} else if (_jrbPiuGrande.isSelected()) {
				selectSize(_jrbSelezTutto.isSelected(), _jrbPiuGrande.isSelected(), eventSource.equals(_bRASeleziona));
			} else if (_jrbMinoreDi.isSelected()) {
				selectMinoreDi(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfDimMinore.getText()));
			} else if (_jrbMaggioreDi.isSelected()) {
				selectMaggioreDi(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfDimMaggiore.getText()));
			} else if (_jrbRange.isSelected()) {
				selectRange(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfRangeDa.getText()),
						Integer.parseInt(_jtfRangeA.getText()));
			}

			updateGroupState();
			_tModel.fireTableDataChanged();

		} else if (eventSource.equals(_bRADeseleziona)) {

			if (_jrbPiuPiccolo.isSelected()) {
				selectSize(_jrbSelezTutto.isSelected(), _jrbPiuGrande.isSelected(), eventSource.equals(_bRASeleziona));
			} else if (_jrbPiuGrande.isSelected()) {
				selectSize(_jrbSelezTutto.isSelected(), _jrbPiuGrande.isSelected(), eventSource.equals(_bRASeleziona));
			} else if (_jrbMinoreDi.isSelected()) {
				selectMinoreDi(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfDimMinore.getText()));
			} else if (_jrbMaggioreDi.isSelected()) {
				selectMaggioreDi(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfDimMaggiore.getText()));
			} else if (_jrbRange.isSelected()) {
				selectRange(_jrbSelezTutto.isSelected(), eventSource.equals(_bRASeleziona), Integer.parseInt(_jtfRangeDa.getText()),
						Integer.parseInt(_jtfRangeA.getText()));
			}

			updateGroupState();
			_tModel.fireTableDataChanged();

		}

	}

	private void selectSize(boolean allGroups, boolean bigger, boolean selected) {

		List<FileInfoListRange> gr = _list.getGroupRange();
		for (FileInfoListRange r : gr) {
			// ricerca del timestamp interessato
			long size = ((FileInfoExt) _list.get(r.getStart())).getSize();
			for (int i = r.getStart() + 1; i <= r.getEnd(); i++) {
				FileInfoExt currentFI = (FileInfoExt) _list.get(i);

				if (!allGroups && !currentFI.getGroupState().equals(GroupState.NONE_SELECTED))
					break;

				if ((bigger && currentFI.getSize() < size) || (!bigger && currentFI.getSize() > size)) {
					size = currentFI.getSize();
				}
			}

			// selezione degli elementi interessati
			for (int i = r.getStart(); i <= r.getEnd(); i++) {
				FileInfoExt currentFI = (FileInfoExt) _list.get(i);
				if (currentFI.getSize() == size) {
					currentFI.setSelected(selected);
				}
			}
		}
	}

	private void selectMinoreDi(boolean allGroups, boolean selected, int size) {

		List<FileInfoListRange> gr = _list.getGroupRange();
		for (FileInfoListRange r : gr) {
			// selezione degli elementi interessati
			for (int i = r.getStart(); i <= r.getEnd(); i++) {
				FileInfoExt currentFI = (FileInfoExt) _list.get(i);

				if (!allGroups && !currentFI.getGroupState().equals(GroupState.NONE_SELECTED))
					break;

				if (currentFI.getSize() <= size) {
					currentFI.setSelected(selected);
				}
			}
		}
	}

	private void selectMaggioreDi(boolean allGroups, boolean selected, int size) {

		List<FileInfoListRange> gr = _list.getGroupRange();
		for (FileInfoListRange r : gr) {
			// selezione degli elementi interessati
			for (int i = r.getStart(); i <= r.getEnd(); i++) {
				FileInfoExt currentFI = (FileInfoExt) _list.get(i);

				if (!allGroups && !currentFI.getGroupState().equals(GroupState.NONE_SELECTED))
					break;

				if (currentFI.getSize() >= size) {
					currentFI.setSelected(selected);
				}
			}
		}
	}

	private void selectRange(boolean allGroups, boolean selected, int min, int max) {

		List<FileInfoListRange> gr = _list.getGroupRange();
		for (FileInfoListRange r : gr) {
			// selezione degli elementi interessati
			for (int i = r.getStart(); i <= r.getEnd(); i++) {
				FileInfoExt currentFI = (FileInfoExt) _list.get(i);

				if (!allGroups && !currentFI.getGroupState().equals(GroupState.NONE_SELECTED))
					break;

				if (currentFI.getSize() >= min && currentFI.getSize() <= max) {
					currentFI.setSelected(selected);
				}
			}
		}
	}

}
