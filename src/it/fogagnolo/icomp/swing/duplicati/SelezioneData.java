
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.FileInfoListRange;
import it.fogagnolo.icomp.swing.FileInfoTableModel;

public class SelezioneData extends Selezione_base {

    private static final long   serialVersionUID = 4284859342526161222L;

    private static final String DESCR            = "Data";

    // campi di input
    private JRadioButton        _jrbNuovo        = null;
    private JRadioButton        _jrbVecchio      = null;

    // bottoni
    private JButton             _bRASeleziona    = null;
    private JButton             _bRADeseleziona  = null;

    public SelezioneData(FileInfoTableModel tModel, FileInfoList list) {

        setName(DESCR);
        _tModel = tModel;
        _list = list;
    }

    public Component getComponent() {

        Box bRicerca = Box.createVerticalBox();

        _jrbNuovo = new JRadioButton("Più nuovo");
        _jrbNuovo.setMnemonic(KeyEvent.VK_N);
        _jrbNuovo.setSelected(false);
        _jrbVecchio = new JRadioButton("Più vecchio");
        _jrbVecchio.setMnemonic(KeyEvent.VK_V);
        _jrbVecchio.setSelected(true);

        // Group the radio buttons
        ButtonGroup bgData = new ButtonGroup();
        bgData.add(_jrbNuovo);
        bgData.add(_jrbVecchio);

        JPanel jplRadio = new JPanel();
        jplRadio.setLayout(new GridLayout(0, 1));
        jplRadio.add(_jrbNuovo);
        jplRadio.add(_jrbVecchio);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        _bRASeleziona = new JButton("Seleziona");
        _bRASeleziona.addActionListener(this);
        _bRADeseleziona = new JButton("Deseleziona");
        _bRADeseleziona.addActionListener(this);
        pRAzioni.add(_bRASeleziona);
        pRAzioni.add(_bRADeseleziona);

        bRicerca.add(jplRadio);
        bRicerca.add(pRAzioni);

        return bRicerca;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bRASeleziona)) {
            selectData(_jrbVecchio.isSelected(), eventSource.equals(_bRASeleziona));
            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bRADeseleziona)) {
            selectData(_jrbVecchio.isSelected(), eventSource.equals(_bRASeleziona));
            updateGroupState();
            _tModel.fireTableDataChanged();
        }
    }

    private void selectData(boolean oldest, boolean selected) {

        List<FileInfoListRange> gr = _list.getGroupRange();
        for (FileInfoListRange r : gr) {
            // ricerca del timestamp interessato
            long ts = ((FileInfoExt) _list.get(r.getStart())).getLastMod();
            for (int i = r.getStart() + 1; i <= r.getEnd(); i++) {
                FileInfoExt currentFI = (FileInfoExt) _list.get(i);
                if ((oldest && currentFI.getLastMod() < ts) || (!oldest && currentFI.getLastMod() > ts)) {
                    ts = currentFI.getLastMod();
                }
            }

            // selezione degli elementi interessati
            for (int i = r.getStart(); i <= r.getEnd(); i++) {
                FileInfoExt currentFI = (FileInfoExt) _list.get(i);
                if (currentFI.getLastMod() == ts) {
                    currentFI.setSelected(selected);
                }
            }
        }
    }

}
