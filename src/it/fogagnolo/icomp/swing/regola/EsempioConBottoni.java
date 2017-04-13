
package it.fogagnolo.icomp.swing.regola;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EsempioConBottoni {

    private static final long serialVersionUID = 710298483964723106L;

    private RegolaAttributi         _attributi       = null;

    public EsempioConBottoni() {

        super();
        _attributi = new RegolaAttributi();
    }

    public EsempioConBottoni(RegolaAttributi attibuti) {

        super();
        _attributi = attibuti;
    }

    public Component showComponent(Window owner) {

        return showComponent(owner, -1);
    }

    public Component showComponent(Window owner, int numRegola) {

        Box bRicerca = Box.createVerticalBox();

        // campi di input
        JPanel pRLabels = new JPanel(new GridLayout(3, 1));
        pRLabels.add(new JLabel("Percorso"));
        pRLabels.add(new JLabel("Includi"));
        pRLabels.add(new JLabel("Escludi"));
        JPanel pRValues = new JPanel(new GridLayout(3, 1));
        JTextField jtfPercorso = new JTextField(10);
        jtfPercorso.setText(_attributi.getPercorso());
        pRValues.add(jtfPercorso);
        JTextField jtfIncludi = new JTextField(10);
        jtfIncludi.setText(_attributi.getIncludi());
        pRValues.add(jtfIncludi);
        JTextField jtfEscludi = new JTextField(10);
        jtfEscludi.setText(_attributi.getEscludi());
        pRValues.add(jtfEscludi);
        Box bRParams = Box.createHorizontalBox();
        bRParams.add(pRLabels);
        bRParams.add(pRValues);
        JPanel pRParams = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRParams.add(bRParams);
        bRicerca.add(pRParams);

        // tasti funzione
        JPanel pRAzioni = new JPanel();
        pRAzioni.setLayout(new BoxLayout(pRAzioni, BoxLayout.X_AXIS));
        JButton bRAAggiungi = new JButton("Aggiungi");
        JButton bRAAggiorna = new JButton("Aggiorna");
        JButton bRAElimina = new JButton("Elimina");
        pRAzioni.add(bRAAggiungi);
        pRAzioni.add(bRAAggiorna);
        pRAzioni.add(bRAElimina);
        bRicerca.add(pRAzioni);

        // list box
        // per il momento textfield vuoto
        JPanel pRListbox = new JPanel();
        pRListbox.add(new JTextField(50));
        bRicerca.add(pRListbox);

        // tasto Avvia
        JPanel pRAvvia = new JPanel();
        pRAvvia.setLayout(new BoxLayout(pRAvvia, BoxLayout.X_AXIS));
        JButton bRAAvvia = new JButton("AVVIA");
        pRAvvia.add(bRAAvvia);
        bRicerca.add(pRAvvia);

        // descrizione
        String descr = null;
        if (numRegola >= 0) {
            descr = "Regola " + numRegola;
        } else {
            descr = "Nuova regola";
        }

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(bRicerca);
        JDialog dialog = new JDialog(owner, descr, Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setContentPane(contentPane);
        dialog.pack();
        dialog.setVisible(true);

        return dialog;
    }

    public RegolaAttributi getAttributi() {

        return _attributi;
    }
}
