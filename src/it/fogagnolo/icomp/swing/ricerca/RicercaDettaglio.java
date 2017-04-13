
package it.fogagnolo.icomp.swing.ricerca;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.fogagnolo.icomp.FileInfoExt;

public class RicercaDettaglio implements ActionListener {

    // oggetto dati
    private FileInfoExt                   _attributi         = null;

    // campi di input
    private JCheckBox                     _jcbSelezionata    = null;
    private JTextField                    _jtfPercorso       = null;
    private JTextField                    _jtfNome           = null;
    private JTextField                    _jtfDimensione     = null;
    private JTextField                    _jcbUltimaModifica = null;
    private JTextField                    _jtfContentDigest  = null;
    private JTextField                    _jtfFastDigest     = null;
    private JTextField                    _jtfFullDigest     = null;
    private JTextField                    _jtfGruppo         = null;
    private JTextField                    _jtfRegola         = null;

    // bottoni
    private JButton                       _bRAOk             = null;
    private JButton                       _bRAAnnulla        = null;

    // finestra
    private JDialog                       _dialog            = null;

    private static final SimpleDateFormat _sdf               = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public RicercaDettaglio(FileInfoExt fi) {

        super();
        _attributi = fi;
    }

    public Component showComponent(Window owner) {

        Box bRicerca = Box.createVerticalBox();

        // etichette
        JPanel pRLabels = new JPanel(new GridLayout(10, 1));
        pRLabels.add(new JLabel("Selezionata"));
        pRLabels.add(new JLabel("Folder"));
        pRLabels.add(new JLabel("Nome"));
        pRLabels.add(new JLabel("Dimensione"));
        pRLabels.add(new JLabel("Ultima modifica"));
        pRLabels.add(new JLabel("Content digest"));
        pRLabels.add(new JLabel("Fast digest"));
        pRLabels.add(new JLabel("Full digest"));
        pRLabels.add(new JLabel("Gruppo"));
        pRLabels.add(new JLabel("Regola"));

        // campi di input
        JPanel pRValues = new JPanel(new GridLayout(10, 1));
        _jcbSelezionata = new JCheckBox();
        _jcbSelezionata.setSelected(_attributi.isSelected());
        pRValues.add(_jcbSelezionata);
        _jtfPercorso = new JTextField(50);
        _jtfPercorso.setText(_attributi.getFolder());
        _jtfPercorso.setCaretPosition(0);
        pRValues.add(_jtfPercorso);
        _jtfNome = new JTextField(50);
        _jtfNome.setText(_attributi.getName());
        _jtfNome.setCaretPosition(0);
        pRValues.add(_jtfNome);
        _jtfDimensione = new JTextField(20);
        _jtfDimensione.setText(String.valueOf(_attributi.getSize()));
        _jtfDimensione.setCaretPosition(0);
        pRValues.add(_jtfDimensione);
        _jcbUltimaModifica = new JTextField(20);
        _jcbUltimaModifica.setText(_sdf.format(_attributi.getUltimaModifica()));
        _jcbUltimaModifica.setCaretPosition(0);
        pRValues.add(_jcbUltimaModifica);
        _jtfContentDigest = new JTextField(50);
        _jtfContentDigest.setText(_attributi.getInitialDigestB64());
        _jtfContentDigest.setCaretPosition(0);
        pRValues.add(_jtfContentDigest);
        _jtfFastDigest = new JTextField(50);
        _jtfFastDigest.setText(_attributi.getFastDigestB64());
        _jtfFastDigest.setCaretPosition(0);
        pRValues.add(_jtfFastDigest);
        _jtfFullDigest = new JTextField(50);
        _jtfFullDigest.setText(_attributi.getFullDigestB64());
        _jtfFullDigest.setCaretPosition(0);
        pRValues.add(_jtfFullDigest);
        _jtfGruppo = new JTextField(10);
        _jtfGruppo.setText(String.valueOf(_attributi.getGruppo()));
        _jtfGruppo.setCaretPosition(0);
        pRValues.add(_jtfGruppo);
        _jtfRegola = new JTextField(10);
        _jtfRegola.setText(String.valueOf(_attributi.getRegola()));
        _jtfRegola.setCaretPosition(0);
        pRValues.add(_jtfRegola);

        Box bRParams = Box.createHorizontalBox();
        bRParams.add(pRLabels);
        bRParams.add(pRValues);
        JPanel pRParams = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRParams.add(bRParams);
        bRicerca.add(pRParams);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        _bRAOk = new JButton("OK");
        _bRAOk.addActionListener(this);
        _bRAAnnulla = new JButton("Annulla");
        _bRAAnnulla.addActionListener(this);
        pRAzioni.add(_bRAOk);
        pRAzioni.add(_bRAAnnulla);
        bRicerca.add(pRAzioni);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(bRicerca);
        _dialog = new JDialog(owner, "Elemento della ricerca", Dialog.ModalityType.DOCUMENT_MODAL);
        _dialog.setContentPane(contentPane);
        _dialog.pack();
        _dialog.setVisible(true);

        return _dialog;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {

        Object eventSource = actionevent.getSource();

        // bottone OK
        if (eventSource.equals(_bRAOk)) {
            _attributi.setSelected(_jcbSelezionata.isSelected());
            _dialog.dispose();
        } else if (eventSource.equals(_bRAAnnulla)) {
            _attributi = null;
            _dialog.dispose();
        }
    }

    public FileInfoExt getAttributi() {

        return _attributi;
    }
}
