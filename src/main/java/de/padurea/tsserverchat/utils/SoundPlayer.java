package de.padurea.tsserverchat.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class SoundPlayer {
	
	private static List<Clip> clips = new ArrayList<Clip>();
	
	private static boolean debugMode = false;
	
	public static void playSound(String path, boolean loop) {
		
		if (debugMode) {
			System.out.println("Try to play sound...");
		}
		try {
			if (!debugMode) {
				URL url = SoundPlayer.class.getResource(path);
			    AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
				Clip clip = AudioSystem.getClip();
				
				//stopAllClips();
				clips.add(clip);
				clip.open(audioInput);
				if (loop) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				clip.start();
				if (debugMode) {
					System.out.println("Successfully played sound!");
				}
			} else {
				if (debugMode) {
					System.out.println("Can't find sound file.");
				}
			}
		} catch (Exception e) {
			if (debugMode) {
				System.out.println("Can't play sound.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Playing sound out of source \n
	 * Example: C:\Users\USER\Desktop\sound.wav
	 * */
	public static void playSoundOOSrc(String path, boolean loop) {
		
		if (debugMode) {
			System.out.println("Try to play sound...");
		}
		try {
			if (!debugMode) {
			    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(path));
				Clip clip = AudioSystem.getClip();
				
				//stopAllClips();
				clips.add(clip);
				clip.open(audioInput);
				if (loop) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				clip.start();
				if (debugMode) {
					System.out.println("Successfully played sound!");
				}
			} else {
				if (debugMode) {
					System.out.println("Can't find sound file.");
				}
			}
		} catch (Exception e) {
			if (debugMode) {
				System.out.println("Can't play sound.");
				e.printStackTrace();
			}
		}
	}
	
	public static Line getMicrophone() throws LineUnavailableException {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
		return microphone;
	}
	
	public static AudioFormat getMicrophoneAudioFormat() throws LineUnavailableException {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		return format;
	}
	
	public static void stopAllClips() {
		for (Clip all : clips) {
			all.stop();
		}
		clips.clear();
	}
	
	public static String getSoundPathInProject(String soundName) {
		return "/de/padurea/tsserverchat/sound/" + soundName;
	}
}
