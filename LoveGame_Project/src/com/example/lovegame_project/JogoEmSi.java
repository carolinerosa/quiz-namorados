package com.example.lovegame_project;

import android.widget.Button;
import android.widget.EditText;;

public class JogoEmSi {

	private static JogoEmSi instance;
	
	private int pontos;
	private int rodada = 1;
	
	private String pergunta;
	private String[] pecas;
	
	private JogoEmSi() {
	}
	
	public static JogoEmSi get()
	{
		if(instance == null)
		{
			instance = new JogoEmSi();
		}
		
		return instance;
	}
	
	public void Send(String mensagem)
	{
		MinhasCoisas.getCliente().write(mensagem);
	}
	
	public void Handle(String mensagem)
	{
		// handle the message
		MinhasCoisas.Show(mensagem);
	}
}
