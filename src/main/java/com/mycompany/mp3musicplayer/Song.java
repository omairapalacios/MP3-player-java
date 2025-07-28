/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mp3musicplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

    public Song(String resourceName) {
        try {
            // Cargar el archivo desde resources como stream
            InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName);
            if (input == null) {
                System.out.println("Archivo no encontrado en recursos: " + resourceName);
                songTitle = "N/A";
                songArtist = "N/A";
                return;
            }

            // Crear archivo temporal para que jaudiotagger pueda leerlo
            File tempFile = File.createTempFile("song-", ".mp3");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

            // Leer metadatos con jaudiotagger
            AudioFile audioFile = AudioFileIO.read(tempFile);
            Tag tag = audioFile.getTag();
            if (tag != null) {
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            } else {
                songTitle = "N/A";
                songArtist = "N/A";
            }

            filePath = resourceName;
        } catch (Exception e) {
            e.printStackTrace();
            songTitle = "Error";
            songArtist = "Error";
        }
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

}
