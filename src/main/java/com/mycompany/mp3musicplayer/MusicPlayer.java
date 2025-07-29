/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mp3musicplayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 *
 * @author omairapalacios
 */
public class MusicPlayer extends PlaybackListener {

    //we will need a way to store our song's details, so we will be creating a song class
    private Song currentSong;

    //use JLayer library to create and AdvancePlayer obj which will handle playing the music
    private AdvancedPlayer advancedPlayer;

    //pause boolean flag used to indicate whether the layer has been paused
    private boolean isPaused;

    //constructor
    public MusicPlayer() {

    }

    public void loadSong(Song song) {
        currentSong = song;

        //play the current song if not null
        if (currentSong != null) {
            playCurrentSong();
        }
    }

    public void pauseSong() {
        if (advancedPlayer != null) {
            //update isPaused flag
            isPaused = true;

            //then we want to stop the player
            stopSong();
        }
    }

    public void stopSong() {
        if (advancedPlayer != null) {
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void playCurrentSong() {
        try {
            //read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            //create a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

            //start music
            startMusicThread();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startMusicThread() {
        new Thread(() -> {
            try {
                advancedPlayer.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
    
    @Override
    public void playbackStarted(PlaybackEvent evt){
        super.playbackStarted(evt);
    }
    
    @Override
    public void playbackFinished(PlaybackEvent evt){
        super.playbackFinished(evt);
    }
}
