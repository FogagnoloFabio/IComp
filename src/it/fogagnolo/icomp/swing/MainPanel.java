
package it.fogagnolo.icomp.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import it.fogagnolo.icomp.FileInfoList;
import it.fogagnolo.icomp.swing.duplicati.DuplicatiTabella;
import it.fogagnolo.icomp.swing.elimina.EliminaTabella;
import it.fogagnolo.icomp.swing.regola.RegolaAttributi;
import it.fogagnolo.icomp.swing.regola.RegolaTabella;
import it.fogagnolo.icomp.swing.ricerca.RicercaTabella;

public class MainPanel implements Costanti_itf, ActionListener {

	private static final String DESCR = "IntelliComp";

	public MainPanel() {

		// finestra principale
		JFrame fMain = new JFrame(DESCR);
		// fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// schede
		JTabbedPane tMain = new JTabbedPane();

		ArrayList<RegolaAttributi> regole = new ArrayList<RegolaAttributi>();
		ICSComponent icsRicerca = new RegolaTabella(fMain, regole);
		tMain.add(icsRicerca.getName(), icsRicerca.getComponent());

		FileInfoList elenco = new FileInfoList();
		ICSComponent icsElenco = new RicercaTabella(fMain, regole, elenco);
		tMain.add(icsElenco.getName(), icsElenco.getComponent());

		ICSComponent icsDuplicati = new DuplicatiTabella(fMain, elenco);
		tMain.add(icsDuplicati.getName(), icsDuplicati.getComponent());

		// ICSComponent icsEscludi = new Escludi();
		// tMain.add(icsEscludi.getName(), icsEscludi.getComponent());

		ICSComponent icsElimina = new EliminaTabella(fMain, elenco);
		tMain.add(icsElimina.getName(), icsElimina.getComponent());

		fMain.getContentPane().add(tMain, BorderLayout.CENTER);
		fMain.setPreferredSize(new Dimension(800, 600));
		fMain.pack();
		fMain.setLocationRelativeTo(null);
		fMain.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

	}
}
