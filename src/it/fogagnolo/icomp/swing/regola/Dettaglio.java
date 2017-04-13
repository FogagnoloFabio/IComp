
package it.fogagnolo.icomp.swing.regola;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class Dettaglio implements ActionListener {

    private static final long serialVersionUID = 710298483964723106L;

    // oggetto dati
    private Attributi         _attributi       = null;

    // campi di input
    private JCheckBox         _jcbAbilitata    = null;
    private JTextField        _jtfPercorso     = null;
    private JCheckBox         _jcbRicorsiva    = null;
    private JCheckBox         _jcbSeguiLink    = null;
    private JTextField        _jtfIncludi      = null;
    private JTextField        _jtfEscludi      = null;
    private JSlider           _jtfDimMin       = null;
    private JSlider           _jtfDimMax       = null;

    // bottoni
    private JButton           _bRAOk           = null;
    private JButton           _bRAAnnulla      = null;

    // finestra
    private JDialog           _dialog          = null;

    public Dettaglio() {

        super();
        _attributi = new Attributi();
    }

    public Dettaglio(Attributi attibuti) {

        super();
        _attributi = attibuti;
    }

    public Component showComponent(Window owner) {

        Box bRicerca = Box.createVerticalBox();

        // etichette
        JPanel pRLabels = new JPanel(new GridLayout(8, 1));
        pRLabels.add(new JLabel("Abilitata"));
        pRLabels.add(new JLabel("Path"));
        pRLabels.add(new JLabel("Ricorsiva"));
        pRLabels.add(new JLabel("Segui Symlink"));
        pRLabels.add(new JLabel("Includi"));
        pRLabels.add(new JLabel("Escludi"));
        pRLabels.add(new JLabel("Dimensione minima"));
        pRLabels.add(new JLabel("Dimensione massima"));

        // campi di input
        JPanel pRValues = new JPanel(new GridLayout(8, 1));
        _jcbAbilitata = new JCheckBox();
        _jcbAbilitata.setSelected(_attributi.isAbilitata());
        pRValues.add(_jcbAbilitata);
        _jtfPercorso = new JTextField(100);
        _jtfPercorso.setText(_attributi.getPercorso());
        pRValues.add(_jtfPercorso);
        _jcbRicorsiva = new JCheckBox();
        _jcbRicorsiva.setSelected(_attributi.isRicorsiva());
        pRValues.add(_jcbRicorsiva);
        _jcbSeguiLink = new JCheckBox();
        _jcbSeguiLink.setSelected(_attributi.isSeguiLink());
        pRValues.add(_jcbSeguiLink);
        _jtfIncludi = new JTextField(40);
        _jtfIncludi.setText(_attributi.getIncludi());
        pRValues.add(_jtfIncludi);
        _jtfEscludi = new JTextField(40);
        _jtfEscludi.setText(_attributi.getEscludi());
        pRValues.add(_jtfEscludi);
        // TODO JSlider con valori mostrati e tick a 0 byte, 1Kb, 100Kb, 1Mb....
        _jtfDimMin = new JSlider(0, 100);
        _jtfDimMin.setValue((int) _attributi.getDimMin());
        pRValues.add(_jtfDimMin);
        _jtfDimMax = new JSlider(0, 100);
        _jtfDimMax.setValue((int) _attributi.getDimMax());
        pRValues.add(_jtfDimMax);

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
        _dialog = new JDialog(owner, "Regola", Dialog.ModalityType.DOCUMENT_MODAL);
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

            _attributi.setAbilitata(_jcbAbilitata.isSelected());
            _attributi.setPercorso(_jtfPercorso.getText());
            _attributi.setRicorsiva(_jcbRicorsiva.isSelected());
            _attributi.setSeguiLink(_jcbSeguiLink.isSelected());
            _attributi.setIncludi(_jtfIncludi.getText());
            _attributi.setEscludi(_jtfEscludi.getText());
            _attributi.setDimMin(_jtfDimMin.getValue());
            _attributi.setDimMax(_jtfDimMax.getValue());

            _dialog.dispose();
        } else if (eventSource.equals(_bRAAnnulla)) {
            _attributi = null;
            _dialog.dispose();
        }
    }

    public Attributi getAttributi() {

        return _attributi;
    }
}
