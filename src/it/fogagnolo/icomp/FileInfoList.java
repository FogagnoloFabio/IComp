
package it.fogagnolo.icomp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

import it.fogagnolo.icomp.swing.comparator.AllInfoComparator;
import it.fogagnolo.icomp.swing.comparator.GroupComparator;
import it.fogagnolo.icomp.util.DateUtil;

public class FileInfoList extends ArrayList {

    // private ArrayList<FileInfoExt> _list = null;

    private static final long serialVersionUID = -2052433774809584234L;

    public FileInfoList() {

        super();
    }

    // public FileInfoList(ArrayList<FileInfoExt> list) {
    //
    // _list = list;
    // }
    //
    public List<FileInfoListRange> getGroupRange() {

        List<FileInfoListRange> range = new ArrayList<FileInfoListRange>();

        if (size() > 0) {
            int start = 0;
            int group = ((FileInfoExt) get(start)).getGruppo();
            for (int i = 1; i < size(); ++i) {
                FileInfoExt currentFI = (FileInfoExt) get(i);
                if (currentFI.getGruppo() != group) {
                    // cambio gruppo
                    int end = i - 1;
                    range.add(new FileInfoListRange(start, end));

                    // memorizzo il nuovo inizio
                    start = i;
                    group = currentFI.getGruppo();
                }
            }

            // ultimo giro
            range.add(new FileInfoListRange(start, size() - 1));
        }

        return range;
    }

    //
    // public FileInfoExt get(int i) {
    //
    // return _list.get(i);
    // }

    // @Override
    // public Iterator<FileInfoExt> iterator() {
    //
    // return _list.iterator();
    // }

    // public final int size() {
    //
    // return _list.size();
    // }

    public void setGroups() {

        // gestione casi particolari
        if (size() < 1) return;

        // ordino sull'info più significativa
        Comparator<FileInfoExt> comp = new AllInfoComparator();
        Collections.sort(this, comp);

        // vera elaborazione
        int group = 1;
        FileInfoExt previous = (FileInfoExt) get(0);
        previous.setGruppo(group);
        FileInfoExt current = null;
        int zeroGroup = 0;
        for (int i = 1; i < size(); ++i) {
            current = (FileInfoExt) get(i);
            if (comp.compare(previous, current) != 0) ++group;

            if (current.getSize() == 0) {
                zeroGroup = group;
            }
            if (group == zeroGroup) {
                System.out.println("gruppo " + group + ", size " + current.getSize() + ", " + current.getName());
            }

            current.setGruppo(group);
            previous = current;
        }

        /*
         * // vera elaborazione int group = 1; FileInfoExt current = _list.get(0); current.setGruppo(group); for (int i
         * = 1; i < _list.size(); ++i) { current = _list.get(i); if (comp.compare(current, _list.get(i - 1)) != 0)
         * ++group; current.setGruppo(group); }
         */
    }

    public void fabio() {

        int i = 0;
        int max = size();
        Iterator<FileInfoExt> it = iterator();
        while (it.hasNext()) {
            FileInfoExt fi = it.next();
            ++i;
            if (fi.isSelected()) {
                fi.calculateInitialDigestForce();
                fi.calculateFastDigestForce();
                fi.calculateFullDigestForce();
                System.out.println(fi);
            }
            if (i % 100 == 0) System.out.println(i + "/" + max + "...");
            if (fi.getFullDigestB64() != null) {
                String full = fi.getFullDigestB64();
                fi.calculateFullDigestForce();
                if (!full.equals(fi.getFullDigestB64())) {
                    System.out.println("ERRATO: " + full + ">> " + fi);
                }
            }
        }

    }

    public void validate(DefaultListModel<String> logModel) {

        logModel.addElement("Inizio validazione elementi...");
        System.out.println("Inizio validazione elementi...");
        int num = 0;
        int max = size();
        int eliminati = 0;

        while (num < size()) {
            FileInfoExt ra = (FileInfoExt) get(num);
            if (ra.getFileObj().exists()) {
                ++num;

                if (num % 100 == 0) {
                    logModel.addElement(DateUtil.getCurrentDate() + " - " + num + "/" + max + " item....");
                    System.out.println(DateUtil.getCurrentDate() + " - " + num + "/" + max + " item....");
                }
            } else {
                ++eliminati;
                remove(num);
            }
        }

        logModel.addElement("Fine validazione elementi: eliminati " + eliminati + " item non validi, rimangono " + num
                + " item");
        System.out.println("Fine validazione elementi: eliminati " + eliminati + " item non validi, rimangono " + num
                + " item");

    }

    public void loadInitialDigest(DefaultListModel<String> log) {

        log.addElement("Calcolo Initial Digest...");
        System.out.println("Calcolo Initial Digest...");

        int num = 0;
        Iterator<FileInfoExt> it = iterator();
        while (it.hasNext()) {
            FileInfoExt fi = it.next();
            ++num;
            if (num % 100 == 0) {
                log.addElement(DateUtil.getCurrentDate() + " - " + num + " item....");
                System.out.println(DateUtil.getCurrentDate() + " - " + num + " item....");
            }

            fi.calculateInitialDigest();
        }

        log.addElement("Fine Initial Digest...");
        System.out.println("Fine Initial Digest...");
    }

    public void loadFastDigest(DefaultListModel<String> log) {

        log.addElement("Calcolo Fast Digest...");
        System.out.println("Calcolo Fast Digest...");

        int num = 0;
        Iterator<FileInfoExt> it = iterator();
        while (it.hasNext()) {
            FileInfoExt fi = it.next();
            ++num;
            if (num % 100 == 0) {
                log.addElement(DateUtil.getCurrentDate() + " - " + num + " item....");
                System.out.println(DateUtil.getCurrentDate() + " - " + num + " item....");
            }

            if (fi.getSize() > 50000000) {
                log.addElement(DateUtil.getCurrentDate() + " - Item " + fi.getName() + " di " + fi.getSize() + " byte");
                System.out.println(DateUtil.getCurrentDate() + " - Item " + fi.getName() + " di " + fi.getSize()
                        + " byte");
            }
            fi.calculateFastDigest();
        }

        log.addElement("Fine Fast Digest...");
        System.out.println("Fine Fast Digest...");
    }

    public void loadFullDigest(DefaultListModel<String> log) {

        log.addElement("Calcolo Full Digest...");
        System.out.println("Calcolo Full Digest...");

        int num = 0;
        Iterator<FileInfoExt> it = iterator();
        while (it.hasNext()) {
            FileInfoExt fi = it.next();
            ++num;
            if (num % 100 == 0) {
                log.addElement(DateUtil.getCurrentDate() + " - " + num + " item....");
                System.out.println(DateUtil.getCurrentDate() + " - " + num + " item....");
            }

            if (fi.getSize() > 10000000) {
                log.addElement(DateUtil.getCurrentDate() + " - Item " + fi.getName() + " di " + fi.getSize() + " byte");
                System.out.println(DateUtil.getCurrentDate() + " - Item " + fi.getName() + " di " + fi.getSize()
                        + " byte");
            }
            fi.calculateFullDigest();
        }

        log.addElement("Fine Full Digest...");
        System.out.println("Fine Full Digest...");
    }

    public void switchSelect(int row) {

        ((FileInfoExt) get(row)).switchSelected();
    }

    public void filter(String name, Comparator<FileInfoExt> comp, DefaultListModel<String> log) {

        log.addElement(DateUtil.getCurrentDate() + " " + name + ": ordinamento");
        System.out.println(DateUtil.getCurrentDate() + " " + name + ": ordinamento");
        Collections.sort(this, comp);

        log.addElement(DateUtil.getCurrentDate() + " " + name + ": ricerca duplicati....");
        System.out.println(DateUtil.getCurrentDate() + " " + name + ": ricerca duplicati....");
        int rimossi = removeNonDuplicate(comp);

        log.addElement(DateUtil.getCurrentDate() + " " + name + ": " + rimossi + " item rimossi, " + size()
                + " item rimasti");
        System.out.println(DateUtil.getCurrentDate() + " " + name + ": " + rimossi + " item rimossi, " + size()
                + " item rimasti");

    }

    // rimuove gli elementi non duplicati
    public int removeNonDuplicate(Comparator<FileInfoExt> comparator) {

        // gestione casi particolari
        if (size() == 0) return 0;
        if (size() == 1) {
            remove(0);
            return 1;
        }

        // vera elaborazione
        int removed = 0;
        int posPrev = 0;
        FileInfoExt fiPrev = (FileInfoExt) get(posPrev);
        int count = 1;

        for (int i = 1; i < size(); ++i) {
            if (comparator.compare((FileInfoExt) get(i), fiPrev) == 0) {
                ++count;
            } else {
                if (count == 1) {
                    remove(posPrev);
                    --i; // l'istruzione remove ha tolto un elemento
                    ++removed;
                } else {
                    posPrev = i;
                }
                fiPrev = (FileInfoExt) get(posPrev);
                count = 1;
            }
        }
        // giro fuori dal loop per gestire l'ultimo elemento
        if (count == 1) {
            remove(posPrev);
            ++removed;
        }

        return removed;
    }

    public void updateGroupStateByGroup(int pos) {

        FileInfoExt fi = (FileInfoExt) get(pos);
        int gruppo = fi.getGruppo();

        // ordino per gruppo
        Comparator<FileInfoExt> comp = new GroupComparator();
        Collections.sort(this, comp);

        // cerco l'inizio del gruppo
        int inizio = pos;
        while (inizio >= 0 && ((FileInfoExt) get(inizio)).getGruppo() == gruppo)
            --inizio;
        ++inizio;
        // if (inizio < 0) inizio = 0;
        // cerco la fine del gruppo
        int fine = pos;
        while (fine < size() && ((FileInfoExt) get(fine)).getGruppo() == gruppo)
            ++fine;
        --fine;
        // if (fine >= _list.size()) fine = _list.size() - 1;

        updateGroupState(inizio, fine);
    }

    public void updateGroupState(int inizio, int fine) {

        // selezione degli elementi più vecchi, e relativo conteggio
        int selected = 0;
        for (int i = inizio; i <= fine; i++) {
            FileInfoExt currentFI = (FileInfoExt) get(i);
            if (currentFI.isSelected()) ++selected;
        }

        // aggiorno il flag di stato gruppo
        GroupState gs = null;
        if (selected == 0) gs = GroupState.NONE_SELECTED;
        else if (selected == (fine - inizio + 1)) gs = GroupState.ALL_SELECTED;
        else
            gs = GroupState.SOME_SELECTED;

        for (int i = inizio; i <= fine; i++) {
            ((FileInfoExt) get(i)).setGroupState(gs);
        }

        System.out.println("updateGroupState - Da " + inizio + " a " + fine + " con " + gs);
    }

    public Iterator<FileInfoExt> iterator() {

        return super.iterator();
    }
}
