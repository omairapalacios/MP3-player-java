/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mp3musicplayer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

/**
 *
 * @author omairapalacios
 */
public class MusicPlayerGUI extends JFrame {

    // color configurations
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    public MusicPlayerGUI() {

        // calls JFrame constructor to configure out GUI and set the title header to "Music Player"
        super("Music Player");

        // set the width & height
        setSize(400, 600);

        // end process when app is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // launch the app at the center of the screen
        setLocationRelativeTo(null);

        // prevent the app from being resized
        setResizable(false);

        // set layout to null which allow us to control the (x,y) coordinates our components and set height & width
        setLayout(null);

        // change the frame color
        getContentPane().setBackground(FRAME_COLOR);

        addGuiComponents();
    }

    private void addGuiComponents() {
        //add toolbar
        addToolbar();

        JLabel songImage = new JLabel(loadImage("record.png"));
        songImage.setBounds(0, 50, getWidth() - 20, 225);
        add(songImage);
    }

    private void addToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);

        // prevent toolbar from being moved
        toolBar.setFloatable(false);

        // create a drop drown menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // add items to menu
        // add item 1
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        JMenuItem loadSong = new JMenuItem("Load Song");
        songMenu.add(loadSong);

        // add item 2
        JMenu playListMenu = new JMenu("Playlist");
        menuBar.add(playListMenu);

        JMenuItem createPlayList = new JMenuItem("Create Playlist");
        playListMenu.add(createPlayList);
        JMenuItem loadPlayList = new JMenuItem("Load Playlist");
        playListMenu.add(loadPlayList);

        add(toolBar);

    }

    private ImageIcon loadImage(String imagePath) {
        try {
            // read the image file from the given path
            InputStream imageStream = getClass().getClassLoader().getResourceAsStream(imagePath);
            BufferedImage image = ImageIO.read(imageStream);

            // returns an image icon so that our component can render the image
            return new ImageIcon(image);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
