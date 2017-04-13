
package it.fogagnolo.icomp.swing;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                start();
            }
        });
    }

    private static void start() {

        new MainPanel();

    }
}
