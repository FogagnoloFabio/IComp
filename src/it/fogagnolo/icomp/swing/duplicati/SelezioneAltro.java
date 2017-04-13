
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.FileInfoTableModel;

public class SelezioneAltro extends Selezione_base {

    private static final long   serialVersionUID = 4284859342526161222L;

    private static final String DESCR            = "Altro";

    private JButton             _bTutto          = null;
    private JButton             _bNiente         = null;
    private JButton             _bInverti        = null;
    private JButton             _bAggiorna       = null;

    public SelezioneAltro(FileInfoTableModel tModel, FileInfoList list) {

        setName(DESCR);
        _tModel = tModel;
        _list = list;
    }

    public Component getComponent() {

        Box bRicerca = Box.createVerticalBox();

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));

        _bTutto = new JButton("Tutto");
        _bTutto.addActionListener(this);
        pRAzioni.add(_bTutto);

        _bNiente = new JButton("Niente");
        _bNiente.addActionListener(this);
        pRAzioni.add(_bNiente);

        _bInverti = new JButton("Inverti");
        _bInverti.addActionListener(this);
        pRAzioni.add(_bInverti);

        _bAggiorna = new JButton("Aggiorna");
        _bAggiorna.addActionListener(this);
        pRAzioni.add(_bAggiorna);

        bRicerca.add(pRAzioni);

        return bRicerca;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bTutto)) {

            for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
                fi.setSelected(true);
            }
            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bNiente)) {
            for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
                fi.setSelected(false);
            }
            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bInverti)) {

            for (FileInfoExt fi : (Collection<FileInfoExt>) _list) {
                fi.switchSelected();
            }
            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bAggiorna)) {
            _tModel.fireTableDataChanged();
        }
    }
}
