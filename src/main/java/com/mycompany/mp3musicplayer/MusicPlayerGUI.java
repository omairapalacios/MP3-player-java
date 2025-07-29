/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mp3musicplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author omairapalacios
 */
public class MusicPlayerGUI extends JFrame {

    // color configurations
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private MusicPlayer musicPlayer;
    //allow us to use file explorer in our app
    private JFileChooser jFileChooser;

    private JLabel songTitle, songArtist;
    private JPanel playbackBtns;

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

        musicPlayer = new MusicPlayer();
        jFileChooser = new JFileChooser();

        //set a default path for file explorer
        jFileChooser.setCurrentDirectory(new File(""));

        //filter file
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        addGuiComponents();
    }

    private void addGuiComponents() {
        //add toolbar
        addToolbar();

        //load record image
        JLabel songImage = new JLabel(loadImage("record.png"));
        songImage.setBounds(0, 50, getWidth() - 20, 225);
        add(songImage);

        //song title
        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 20, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        //song artist
        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 320, getWidth() - 20, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        //play slider
        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth() / 2 - 300 / 2, 365, 300, 40);
        playbackSlider.setBackground(null);
        add(playbackSlider);

        //playback buttons (i.e previous, play, next)
        addPlaybackBtns();

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

        // add actions 
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open file system
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();
                if (result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
                    //create a song obj based on selected file
                    Song song = new Song(selectedFile.getPath());
                    //load song music player
                    musicPlayer.loadSong(song);

                    updateSongTitleAndArtist(song);

                    //togle on pause button and toggle off play button
                    enablePauseButtonDisablePlayButton();
                }
            }
        });

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

    private void addPlaybackBtns() {
        playbackBtns = new JPanel();
        playbackBtns.setBounds(0, 435, getWidth(), 80);
        playbackBtns.setBackground(null);

        //previous button
        JButton prevButton = new JButton(loadImage("previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playbackBtns.add(prevButton);

        //play  button
        JButton playButton = new JButton(loadImage("play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // toggle off play button and toggle on pause button
                enablePauseButtonDisablePlayButton();

                //play or resume song
                musicPlayer.playCurrentSong();
            }
        });
        playbackBtns.add(playButton);

        //pause button
        JButton pauseButton = new JButton(loadImage("pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        //add actions
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //togle off pause button and toggle on play button
                enablePlayButtonDisablePauseButton();
                //pause the song
                musicPlayer.pauseSong();
            }
        });

        playbackBtns.add(pauseButton);

        //next button
        JButton nextButton = new JButton(loadImage("next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playbackBtns.add(nextButton);

        add(playbackBtns);

    }

    private void updateSongTitleAndArtist(Song song) {
        System.out.println("SONG TITLE" + song.getSongTitle());
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void enablePauseButtonDisablePlayButton() {
        //retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        //turn off play button
        playButton.setVisible(false);
        playButton.setEnabled(false);

        //turn on pause button
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    private void enablePlayButtonDisablePauseButton() {
        //retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        //turn off play button
        playButton.setVisible(true);
        playButton.setEnabled(true);

        //turn on pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
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
