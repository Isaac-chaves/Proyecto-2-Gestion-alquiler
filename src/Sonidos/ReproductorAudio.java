/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sonidos;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author isaac
 */
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class ReproductorAudio {
    private Clip clip;

    public ReproductorAudio(String rutaRelativa) {
        try {
            InputStream is = getClass().getResourceAsStream(rutaRelativa);
            if (is == null) {
                System.out.println("Archivo no encontrado en recursos: " + rutaRelativa);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.out.println("Error al cargar el audio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

