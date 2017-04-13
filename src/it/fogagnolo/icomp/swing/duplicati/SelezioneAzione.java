
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.FileInfoTableModel;

public class SelezioneAzione extends Selezione_base {

    private static final long   serialVersionUID = 4284859342526161222L;

    private static final String DESCR            = "Azione";

    private JButton             _bEscludi        = null;
    private JButton             _bCancella       = null;

    public SelezioneAzione(FileInfoTableModel tModel, FileInfoList list) {

        setName(DESCR);
        _tModel = tModel;
        _list = list;
    }

    public Component getComponent() {

        Box bRicerca = Box.createVerticalBox();

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));

        _bEscludi = new JButton("Escludi");
        _bEscludi.addActionListener(this);
        pRAzioni.add(_bEscludi);

        _bCancella = new JButton("Cancella");
        _bCancella.addActionListener(this);
        pRAzioni.add(_bCancella);

        bRicerca.add(pRAzioni);

        return bRicerca;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottoni
        if (eventSource.equals(_bEscludi)) {

            int initialSize = _list.size();

            Iterator<FileInfoExt> it = _list.iterator();
            while (it.hasNext()) {
                FileInfoExt fi = it.next();
                if (fi.isSelected()) {
                    System.out.println("- escludo " + fi.getFolder() + File.separatorChar + fi.getName());
                    it.remove();
                }
            }

            int finalSize = _list.size();
            System.out.println("Da " + initialSize + " a " + finalSize + " item");

            updateGroupState();
            _tModel.fireTableDataChanged();

        } else if (eventSource.equals(_bCancella)) {

            int initialSize = _list.size();

            Iterator<FileInfoExt> it = _list.iterator();
            while (it.hasNext()) {
                FileInfoExt fi = it.next();
                if (fi.isSelected()) {
                    System.out.println("- cancello " + fi.getFolder() + File.separatorChar + fi.getName());
                    if (fi.getFileObj().delete()) {
                        it.remove();
                    } else {
                        System.out.println("---> ERRORE");
                    }
                }
            }

            int finalSize = _list.size();
            System.out.println("Da " + initialSize + " a " + finalSize + " item");

            updateGroupState();
            _tModel.fireTableDataChanged();

        }
    }
}
