/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mauro.wavexprototipo.view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author MauricioGabriel
 */
public class Mezclador {
    public static String ruta = "bases\\";
    public static String[] archs = {"Clap", "Kick", "Snare"};
    public static String temp = "temp\\temp.wav";
    public static Mixer.Info[] mixInfo;
    public static Mixer mixer;
    public static Clip clip;
    
    public static void mezclar(int n, int freq) throws LineUnavailableException,
            UnsupportedAudioFileException, IOException {
        mixInfo = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfo[0]);
        DataLine.Info dInfo = new DataLine.Info(Clip.class, null);
        
        clip = (Clip) mixer.getLine(dInfo);
        
        File file = new File(ruta + archs[n] + ".wav");
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        AudioFormat formato = getOutFormat(audio.getFormat(), freq);
        
        AudioInputStream fin = AudioSystem.getAudioInputStream(formato, audio);
        AudioSystem.write(fin, AudioFileFormat.Type.WAVE, new File(temp));
    }
    
    public static void abrir() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        mixInfo = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfo[0]);
        DataLine.Info dInfo = new DataLine.Info(Clip.class, null);
        
        clip = (Clip) mixer.getLine(dInfo);
        File file = new File(temp);
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        clip.open(audio);
        
    }
    
    private static AudioFormat getOutFormat(AudioFormat inFormat, int freq) {
		int ch = inFormat.getChannels();
		float rate = inFormat.getSampleRate();	
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, freq * 300,
                        16, ch, ch * 2, rate, inFormat.isBigEndian());
	}
    
}
