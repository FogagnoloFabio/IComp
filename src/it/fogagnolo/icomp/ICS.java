
package it.fogagnolo.icomp;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ICS {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                start();
            }
        });
    }

    private static void start() {

        // finestra principale
        JFrame fRicerca = new JFrame("IntelliComp");
        fRicerca.add(creaSchedaRicerca());
        fRicerca.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fRicerca.pack();
        fRicerca.setVisible(true);
    }

    private static Component creaSchedaRicerca() {

        Box bRicerca = Box.createVerticalBox();

        // campi di input
        JPanel pRLabels = new JPanel(new GridLayout(3, 1));
        pRLabels.add(new JLabel("Percorso"));
        pRLabels.add(new JLabel("Includi"));
        pRLabels.add(new JLabel("Escludi"));
        JPanel pRValues = new JPanel(new GridLayout(3, 1));
        pRValues.add(new JTextField(10));
        pRValues.add(new JTextField(10));
        pRValues.add(new JTextField(10));
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

        return bRicerca;
    }

}
