/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mp3musicplayer;

import javax.swing.SwingUtilities;

/**
 *
 * @author omairapalacios
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicPlayerGUI().setVisible(true);
        });
    }
}
