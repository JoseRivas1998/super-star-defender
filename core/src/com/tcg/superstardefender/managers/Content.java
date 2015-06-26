package com.tcg.superstardefender.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Content {
	
	private HashMap<String, Music> music;
	private HashMap<String, Sound> sound;
	private HashMap<String, BitmapFont> font;
	GlyphLayout gl;
	
	public Content() {
		music = new HashMap<String, Music>();
		sound = new HashMap<String, Sound>();
		font = new HashMap<String, BitmapFont>();
		gl = new GlyphLayout();
	}
	
	/*
	 * Music
	 */
	
	public void loadMusic(String folder, String path, String key, boolean looping) {
		Music m = Gdx.audio.newMusic(Gdx.files.internal(folder + "/" + path));
		m.setLooping(looping);
		music.put(key, m);
	}
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public void setVolumeAll(float vol) {
		for(Object o : music.values()) {
			Music music = (Music) o;
			music.setVolume(vol);
		}
	}
	
	/*
	 * Sound
	 */
	
	public void loadSound(String folder, String path, String key) {
		Sound s = Gdx.audio.newSound(Gdx.files.internal(folder + "/" + path));
		sound.put(key, s);
	}
	
	public Sound getSound(String key) {
		return sound.get(key);
	}
	
	/*
	 * Bitmap Font
	 */
	
	@SuppressWarnings("deprecation")
	public void loadBitmapFont(String folder, String path, String key, int size, Color color) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(folder + "/" + path));
		BitmapFont bmf = gen.generateFont(size);
		bmf.setColor(color);
		font.put(key, bmf);
		gen.dispose();
	}
	
	public BitmapFont getFont(String key) {
		return font.get(key);
	}

	public float getWidth(String key, String s, float targetWidth, int halign, boolean wrap) {
		gl.setText(font.get(key), s, font.get(key).getColor(), targetWidth, halign, wrap);
		return gl.width;
	}
	
	public float getHeight(String key, String s, float targetWidth, int halign, boolean wrap) {
		gl.setText(font.get(key), s, font.get(key).getColor(), targetWidth, halign, wrap);
		return gl.height - font.get(key).getDescent();
	}
	
	/*
	 * Other
	 */
	
	public void removeAll() {
		for(Object o : music.values()) {
			Music m = (Music) o;
			m.dispose();
		}
		for(Object o : sound.values()) {
			Sound s = (Sound) o;
			s.dispose();
		}
		for(Object o : font.values()) {
			BitmapFont bmf = (BitmapFont) o;
			bmf.dispose();
		}
	}
	
	public void stopSound() {
		for(Object o : sound.values()) {
			Sound s = (Sound) o;
			s.stop();
		}
	}
	
	public void stopMusic() {
		for(Object o : music.values()) {
			Music m = (Music) o;
			m.stop();
		}
	}
	
	public void stopAllSound() {
		stopSound();
		stopMusic();
	}
}
