package com.example.lovegame_project;

import android.app.Activity;
import android.content.Intent;

public class ChangeLayout {
	//Intent trocatela = new Intent(Menu_main.this,Love_Game.class);	
	public ChangeLayout(Activity Class1,Class<?> Class2)
	{
		Intent trocatela = new Intent(Class1,Class2);	
		Class1.startActivity(trocatela);
		Class1.finish();
	}
}
