package com.example.lovegame_project;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.*;

public class Menu_main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menumain);
		Button	btcomecar = (Button)findViewById (R.id.bt_comecar);
		Button btinstrucoes = (Button)findViewById (R.id.bt_comecar);
		Button btcreditos = (Button)findViewById (R.id.bt_comecar);
		
		//Button Comecar
		btcomecar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
				ChangeLayout cl = new ChangeLayout(Menu_main.this,Love_Game.class);
				}catch(Exception e)
				{
					Mensagem("erro"+e.getMessage());
				}
				
			}
			
		});
		
		
	}
	private void Mensagem(String msg) {
		//  apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}
