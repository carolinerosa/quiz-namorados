package com.example.lovegame_project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Chat extends Activity {

	private static final String TAG = "CHAT";

	private String pergunta;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// Screen adjustments
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


		setContentView(R.layout.activity_chat);
		MinhasCoisas.setCurrentActivity(this);
		
		MinhasCoisas.Show(String.valueOf(JogoEmSi.get().getTurn()));
	}

	public void ChangeVisualization(boolean turn)
	{

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	public void onClick_SendButton(View v)
	{
		Log.i(TAG, "tentativa de enviar mensagem");
		EditText editText = (EditText) findViewById(R.id.edit_text);
		//MinhasCoisas.getCliente().write(editText.getText().toString());
		JogoEmSi.get().Send(editText.getText().toString());
	}

	public void MudarPergunta()
	{

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//MinhasCoisas.getCliente().cancel();

	}
}
