package com.example.lovegame_project;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Achievements_menu extends Activity {
	private TextView teste;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievements_menu);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try 
	     {
		
		
	     }catch(Exception e)
	     {
	    	Mensagem("erro"+e.getMessage());
			}
		
	}
	private void Mensagem(String msg) {
		//  apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.achievements_menu, menu);
		return true;
	}

}
