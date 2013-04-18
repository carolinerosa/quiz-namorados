package com.example.lovegame_project;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class SalvarPergunta extends Activity{
	private TextView txtSalvar= (TextView) findViewById(R.id.edtSalvar);
	private TextView txtNomeArq=(TextView) findViewById(R.id.edtNomeArq);
	public String lstrNomeArq;
    public File arq;
    public byte[] dados;
	//Singleton Area
	private static SalvarPergunta Instance;
	private SalvarPergunta()
	{
		
	}
	static SalvarPergunta GetInstance()
	{
		return Instance;
	}
	
	public void Salvar()
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
			          DebugLog.Get_Instance().Mensagem("Texto Salvo com sucesso!");
			          //Listar();
			      } 
			      catch (Exception e) 
			      {
			    	  DebugLog.Get_Instance().Mensagem("Erro : " + e.getMessage());
			      }   
}
	
}
