package com.example.lovegame_project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class Love_Game extends Activity {
	private TextView txtRoot;
	private TextView txtNomeArq;
	private TextView txtSalvar;
	private TextView txtLer;
	private Spinner SpnListarArquivos;
	static Context context;
	private ArrayList<String> Arquivos = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		 try 
	     {
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		                WindowManager.LayoutParams.FLAG_FULLSCREEN);
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			 setContentView(R.layout.activity_love__game);
	      
	            
	         txtRoot = (TextView) findViewById(R.id.txtRoot2);
	         txtNomeArq = (TextView) findViewById(R.id.edtNomeArq);
	         txtSalvar = (TextView) findViewById(R.id.edtSalvar);
	         txtLer = (TextView) findViewById(R.id.edtLer);
	         SpnListarArquivos = (Spinner)  findViewById(R.id.spListarArquivos); 
	         txtRoot.append(ObterDiretorio());
	            
	         Listar();
	         
	        } 
	        catch (Exception e) 
	        {
	        	DebugLog.Get_Instance().Mensagem("Erro : "+e.getMessage());
	        }        
	   } 
	
	private void Listar() {
		File diretorio = new File(ObterDiretorio()); 
		   File[] arquivos = diretorio.listFiles();    
		if(arquivos != null)
		   { 
		      int length = arquivos.length; 
		      for(int i = 0; i < length; ++i)
		      { 
		          File f = arquivos[i]; 
		          if (f.isFile())
		          {
		              Arquivos.add(f.getName());
		          } 
		      }
		   
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
		(this,android.R.layout.simple_dropdown_item_1line, Arquivos);
		     SpnListarArquivos.setAdapter(arrayAdapter);
		    }   
		}
	
	private  String ObterDiretorio() {
		// Retorna o diretório de armazenamento externo
		 File root = android.os.Environment.getExternalStorageDirectory();
		  return root.toString();
		
	}
	public void click_Salvar(View v)
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
	          Listar();
	      } 
	      catch (Exception e) 
	      {
	    	  DebugLog.Get_Instance().Mensagem("Erro : " + e.getMessage());
	      }     
	}
	
	public void click_Carregar(View v)
	{
	    String lstrNomeArq;
	     File arq; 
	     String lstrlinha;
	     try
	     {
	lstrNomeArq = SpnListarArquivos.getSelectedItem().toString();
	 
	          txtLer.setText("");
	              
	arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
	BufferedReader br = new BufferedReader(new FileReader(arq));
	          
	          while ((lstrlinha = br.readLine()) != null) 
	          {
	               if (!txtLer.getText().toString().equals(""))
	                {
	                    txtLer.append("\n");
	                }
	                txtLer.append(lstrlinha);
	          }
	            
	          DebugLog.Get_Instance().Mensagem("Texto Carregado com sucesso!");
	             
	      } 
	      catch (Exception e) 
	      {
	    	  DebugLog.Get_Instance().Mensagem("Erro : " + e.getMessage());
	      }        
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.love__game, menu);
		return true;
	}

}
