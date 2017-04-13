
package it.fogagnolo.icomp.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import it.fogagnolo.icomp.swing.regola.Tabella;
import it.fogagnolo.icomp.swing.tab.Duplicati;
import it.fogagnolo.icomp.swing.tab.Elenco;
import it.fogagnolo.icomp.swing.tab.Elimina;
import it.fogagnolo.icomp.swing.tab.Escludi;

public class Main {

    private static final String DESCR = "IntelliComp";

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                start();
            }
        });
    }

    private static void start() {

        // finestra principale
        JFrame fMain = new JFrame(DESCR);
        // fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // schede
        JTabbedPane tMain = new JTabbedPane();
        ICSComponent icsRicerca = new Tabella(fMain);
        tMain.add(icsRicerca.getName(), icsRicerca.getComponent());

        ICSComponent icsElenco = new Elenco();
        tMain.add(icsElenco.getName(), icsElenco.getComponent());

        ICSComponent icsDuplicati = new Duplicati();
        tMain.add(icsDuplicati.getName(), icsDuplicati.getComponent());

        ICSComponent icsEscludi = new Escludi();
        tMain.add(icsEscludi.getName(), icsEscludi.getComponent());

        ICSComponent icsElimina = new Elimina();
        tMain.add(icsElimina.getName(), icsElimina.getComponent());

        fMain.getContentPane().add(tMain, BorderLayout.CENTER);
        fMain.setPreferredSize(new Dimension(800, 600));
        fMain.pack();
        fMain.setLocationRelativeTo(null);
        fMain.setVisible(true);

    }
}
