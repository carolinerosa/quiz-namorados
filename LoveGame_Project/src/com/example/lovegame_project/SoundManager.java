package com.example.lovegame_project;

import javax.xml.transform.Source;

import android.R.raw;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.util.Log;

public class SoundManager {

	private String TAG = "Sound Manager";
	
	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;
	
	private static SoundManager instance;
	
	private SoundManager() {
		audioManager = (AudioManager)MinhasCoisas.getCurrentActivity().getSystemService(Context.AUDIO_SERVICE);
	}
	public static SoundManager getInstance()
	{
		if(instance == null)
		{
			instance = new SoundManager();
		}
		
		return instance;
	}

	public void playSound(final int source)
	{
		
		final MediaPlayer mp = MediaPlayer.create(MinhasCoisas.getCurrentActivity(), source);
		mp.setVolume(100, 100);
		
		try{
			mp.prepare();
			mp.start();
			
			
		}catch(Exception e)
		{
			mp.stop();
			Log.i(TAG, "Erro no som"); 
		}
		
//		Thread thread = new Thread(new Runnable()
//		{
//			@Override
//			public void run() {
//				
//				MediaPlayer mp = MediaPlayer.create(MinhasCoisas.getCurrentActivity(), source);
//				try{
//					mp.prepare();
//					mp.start();
//					
//					
//				}catch(Exception e)
//				{
//					mp.stop();
//					Log.i(TAG, "Erro no som"); 
//				}
//				
//			}
//		});
//		thread.start();
	}
}
