package com.example.lovegame_project;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MandarPergunta extends Activity {
	private TextView txtNomeArq;
	private TextView txtSalvar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try{
			setContentView(R.layout.activity_mandar_pergunta);
		txtNomeArq = (TextView) findViewById(R.id.nomeTxt);
		txtSalvar = (TextView) findViewById(R.id.corpoTxt);
		
		
		}catch(Exception e)
		{
			
		}
	 
		
	}
	public void click_enviarSalvo(View v)
	{
		SalvarPergunta();
	}
	void SalvarPergunta()
	{
		//pega o texto criado, e o diretorio(file),pega os bytes do texto e manda usando o FileOutputStream, dando o diretório e o byte a ser enviado.
				String lstrNomeArq;
			     File arq;
			     byte[] dados;
			      try
			      {
			          lstrNomeArq = txtNomeArq.getText().toString();
			             
			arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
			          FileOutputStream fos;
			             
			dados = txtSalvar.getText().toString().getBytes();
			             
			          fos = new FileOutputStream(arq);
			          fos.write(dados);
			          fos.flush();
			          fos.close();
			         Mensagem("Texto Salvo com sucesso!");
			          //Listar();
			      } 
			      catch (Exception e) 
			      {
			    	 
			      }     
	}
	private void Mensagem(String msg) {
		//  apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mandar_pergunta, menu);
		return true;
	}

}
