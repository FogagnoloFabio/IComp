
package it.fogagnolo.icomp.swing.duplicati;

import java.awt.event.ActionListener;
import java.util.List;

import it.fogagnolo.icomp.FileInfoExt;
import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.FileInfoListRange;
import it.fogagnolo.icomp.GroupState;
import it.fogagnolo.icomp.swing.Costanti_itf;
import it.fogagnolo.icomp.swing.FileInfoTableModel;
import it.fogagnolo.icomp.swing.ICSComponent;

public abstract class Selezione_base extends ICSComponent implements Costanti_itf, ActionListener {

    private static final long    serialVersionUID = -6281174604621381713L;

    protected FileInfoTableModel _tModel          = null;
    protected FileInfoList       _list            = null;

    protected void updateGroupState() {

        List<FileInfoListRange> gr = _list.getGroupRange();
        for (FileInfoListRange r : gr) {

            // System.out.println("SelectOldest da " + r.getStart() + " a " + r.getEnd());

            // conteggio elementi selezionati
            int selected = 0;
            for (int i = r.getStart(); i <= r.getEnd(); i++) {
                if (((FileInfoExt) _list.get(i)).isSelected()) ++selected;
            }

            // aggiorno il flag di stato gruppo
            GroupState gs = null;
            if (selected == 0) {
                gs = GroupState.NONE_SELECTED;
            } else if (selected == (r.getEnd() - r.getStart() + 1)) {
                gs = GroupState.ALL_SELECTED;
            } else {
                gs = GroupState.SOME_SELECTED;
            }

            for (int i = r.getStart(); i <= r.getEnd(); i++) {
                ((FileInfoExt) _list.get(i)).setGroupState(gs);
            }
        }
    }
}
