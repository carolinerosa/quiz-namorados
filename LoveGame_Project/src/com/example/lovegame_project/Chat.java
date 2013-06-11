package com.example.lovegame_project;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Chat extends Activity {

	private static final String TAG = "CHAT";
	
	private String pergunta;
	public static boolean suaVez = false;
	private int pontos = 0;
	private boolean corrigir = false;
	private int rodada = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		MinhasCoisas.setCurrentActivity(this);
		
		MinhasCoisas.Show(String.valueOf(suaVez));
		
		// ---------- pegar pergunta do BD
			pergunta = "Você é um bunda?";
		// ----------
			
		if(suaVez)
		{
			
		}else
		{
			
		}
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
		if(suaVez){
		Log.i(TAG, "tentativa de enviar mensagem");
		EditText editText = (EditText) findViewById(R.id.edit_text);
		MinhasCoisas.getCliente().write(editText.getText().toString());
		}else
		{
			MinhasCoisas.Show("Não é sua vez!");
		}
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
