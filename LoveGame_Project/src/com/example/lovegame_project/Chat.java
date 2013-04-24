package com.example.lovegame_project;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Chat extends Activity {

	private static final String TAG = "CHAT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		MinhasCoisas.setCurrentActivity(this);

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
		MinhasCoisas.getCliente().write(editText.getText().toString());
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//MinhasCoisas.getCliente().cancel();
		
	}
}
