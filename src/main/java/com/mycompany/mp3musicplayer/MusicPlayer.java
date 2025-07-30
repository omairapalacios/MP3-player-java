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

    //need reference so that we can update the gui in this class
    private MusicPlayerGUI musicPlayerGUI;

    //need a way to store our song's details, so we will be creating a song class
    private Song currentSong;

    //use JLayer library to create and AdvancePlayer obj which will handle playing the music
    private AdvancedPlayer advancedPlayer;

    //pause boolean flag used to indicate whether the layer has been paused
    private boolean isPaused;

    //stores in the last frame when the playback is finished ( used for pausing and resuming)
    private int currentFrame;

    //track how many milliseconds has passed since playing the song ( used for updating the slider
    private int currentTimeInMilli;

    //constructor
    public MusicPlayer(MusicPlayerGUI musicPlayerGUI) {
        this.musicPlayerGUI = musicPlayerGUI;
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
        if (currentSong == null) {
            return;
        }
        try {
            //read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            //create a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

            //start music
            startMusicThread();
            
            //start playback slider
            startPlaybackSliderThread();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startMusicThread() {
        new Thread(() -> {
            try {
                if (isPaused) {
                    //resume music from last frame
                    advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                } else {
                    advancedPlayer.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void startPlaybackSliderThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isPaused) {
                    try {
                        //increment current time milli
                        currentTimeInMilli++;
                        //calculate into frame value
                        int calculatedFrame = (int) ((double) currentTimeInMilli * currentSong.getFrameRatePerMilliseconds());
                        musicPlayerGUI.setPlaybackSliderValue(calculatedFrame);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        super.playbackStarted(evt);
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        super.playbackFinished(evt);

        System.out.println("Stopped @" + evt.getFrame());

        if (isPaused) {
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
        }
    }
}
