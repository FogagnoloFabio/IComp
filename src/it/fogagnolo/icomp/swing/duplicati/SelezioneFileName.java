
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.FileInfoTableModel;

public class SelezioneFileName extends Selezione_base {

    private static final long   serialVersionUID = 4284859342526161222L;

    private static final String DESCR            = "Nome file";

    // campi di input
    private JTextField          _jtfPercorso     = null;
    private JTextField          _jtfFileName     = null;

    // bottoni
    private JButton             _bRASeleziona    = null;
    private JButton             _bRADeseleziona  = null;

    public SelezioneFileName(FileInfoTableModel tModel, FileInfoList list) {

        setName(DESCR);
        _tModel = tModel;
        _list = list;
    }

    public Component getComponent() {

        Box bRicerca = Box.createVerticalBox();

        // etichette
        JPanel pRLabels = new JPanel(new GridLayout(2, 1));
        pRLabels.add(new JLabel("Path"));
        pRLabels.add(new JLabel("File name"));

        // campi di input
        JPanel pRValues = new JPanel(new GridLayout(2, 1));
        _jtfPercorso = new JTextField(20);
        pRValues.add(_jtfPercorso);
        _jtfFileName = new JTextField(20);
        pRValues.add(_jtfFileName);

        Box bRParams = Box.createHorizontalBox();
        bRParams.add(pRLabels);
        bRParams.add(pRValues);
        JPanel pRParams = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRParams.add(bRParams);
        bRicerca.add(pRParams);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        _bRASeleziona = new JButton("Seleziona");
        _bRASeleziona.addActionListener(this);
        _bRADeseleziona = new JButton("Deseleziona");
        _bRADeseleziona.addActionListener(this);
        pRAzioni.add(_bRASeleziona);
        pRAzioni.add(_bRADeseleziona);
        bRicerca.add(pRAzioni);

        return bRicerca;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bRASeleziona)) {

            if (!"".equals(_jtfPercorso.getText()) && !"".equals(_jtfFileName.getText())) {
                selectPathName(_jtfPercorso.getText(), _jtfFileName.getText(), true);
            } else if (!"".equals(_jtfPercorso.getText())) {
                selectPath(_jtfPercorso.getText(), true);
            } else if (!"".equals(_jtfFileName.getText())) {
                selectName(_jtfFileName.getText(), true);
            }
            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bRADeseleziona)) {

            if (!"".equals(_jtfPercorso.getText()) && !"".equals(_jtfFileName.getText())) {
                selectPathName(_jtfPercorso.getText(), _jtfFileName.getText(), false);
            } else if (!"".equals(_jtfPercorso.getText())) {
                selectPath(_jtfPercorso.getText(), false);
            } else if (!"".equals(_jtfFileName.getText())) {
                selectName(_jtfFileName.getText(), false);
            }
            updateGroupState();
            _tModel.fireTableDataChanged();
        }
    }

    private void selectPathName(String path, String name, boolean selected) {

        for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
            if (fi.getFolder().contains(path) && fi.getName().contains(name)) fi.setSelected(selected);
        }
    }

    private void selectPath(String path, boolean selected) {

        for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
            if (fi.getFolder().contains(path)) fi.setSelected(selected);
        }
    }

    private void selectName(String name, boolean selected) {

        for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
            if (fi.getName().contains(name)) fi.setSelected(selected);
        }
    }
}
