package com.example.lovegame_project;

import javax.xml.transform.Source;

import android.R.raw;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundManager {

	private String TAG = "Sound Manager";
	
	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;
	
	private SoundManager() {
		
	}

	public void playSound(int source)
	{
		MediaPlayer mp = MediaPlayer.create(MinhasCoisas.getCurrentActivity(), source);
		try{mp.prepare();
		}catch(Exception e){ Log.i(TAG, "erro ao preparar a droga do som"); }
		mp.start();
	}
}
