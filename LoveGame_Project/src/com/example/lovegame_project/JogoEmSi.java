package com.example.lovegame_project;

import android.widget.Button;
import android.widget.EditText;;

public class JogoEmSi {

	private static JogoEmSi instance;
	
	private int pontos;
	private int rodada = 1;
	
	private String pergunta;
	private String[] pecas;
	
	private boolean turn = false;
	
	
	public boolean getTurn()
	{
		return this.turn;
	}
	public void setTurn(boolean turn)
	{
		this.turn = turn;
	}
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
		try{
		if(this.turn){
			MinhasCoisas.getCliente().write(mensagem);
			this.turn = !turn;
		}else
		{
			MinhasCoisas.Show("Sinto muito, mas não é sua vez... :(");
		}
		}catch(Exception e)
		{
			MinhasCoisas.Show("Desculpas. Devido a força centrípeta da lua em rotação à estrela H12KL7, ocorreu um erro na transferência de dados. Tente novamente.");
		}
	}
	
	public void Handle(String mensagem)
	{
		// handle the message
		MinhasCoisas.Show(mensagem);
		this.turn = !turn;
		
		MinhasCoisas.Show(String.valueOf(this.turn));
	}
}
