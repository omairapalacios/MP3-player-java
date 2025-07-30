package com.mycompany.mp3musicplayer;

import com.mpatric.mp3agic.Mp3File;
import java.io.File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 *
 * @author omairapalacios
 */
public class Song {

    private String songTitle;
    private String songArtist;
    private String songLength;
    private String filePath;
    private Mp3File mp3File;
    private double frameRatePerMilliseconds;

    public Song(String filePath) {
        try {

            mp3File = new Mp3File(filePath);
            frameRatePerMilliseconds = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
            songLength = convertirToSongLengthFormat();

            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Archivo no encontrado: " + filePath);
                songTitle = "N/A";
                songArtist = "N/A";
                return;
            }

            // Leer metadatos con jaudiotagger directamente
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            if (tag != null) {
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);

                // Si están vacíos, usar el nombre del archivo como fallback
                if (songTitle == null || songTitle.isEmpty()) {
                    songTitle = file.getName();
                }
                if (songArtist == null || songArtist.isEmpty()) {
                    songArtist = "Desconocido";
                }

            } else {
                songTitle = file.getName();
                songArtist = "Desconocido";
            }

            this.filePath = filePath;

        } catch (Exception e) {
            e.printStackTrace();
            songTitle = "Error";
            songArtist = "Error";
        }
    }
    private String convertirToSongLengthFormat() {
        long minutes = mp3File.getLengthInSeconds() / 60;
        long seconds = mp3File.getLengthInSeconds() % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        return formattedTime;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getFilePath() {
        return filePath;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }

    public double getFrameRatePerMilliseconds() {
        return frameRatePerMilliseconds;
    }
}
