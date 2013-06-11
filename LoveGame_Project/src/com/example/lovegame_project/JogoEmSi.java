package com.example.lovegame_project;

public class JogoEmSi {

	private static JogoEmSi instance;
	
	private boolean meuTurno;
	private int pontos;
	private String pergunta;
	private int rodada = 1;
	
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
	}
}
