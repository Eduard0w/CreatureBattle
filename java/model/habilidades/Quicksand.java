package model.habilidades;

import model.Criatura;
import model.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Quicksand implements Habilidade{
	private int duracaoPturno = 4; // quanto mais rapido a criatura menor a probabilidade dele sair
	private double danoCausado = 5;
	private TipoElemental te;
	
	@Override
	public String nomeHabilidade() {
		// TODO Auto-generated method stub
		return "quickSand";
	}

	@Override 
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		System.out.println(jogador.getNome() + " Atacou " + nomeHabilidade() + " em " + alvo.getNome() 
		+ "\n Dano sofrido: "+ danoHabilidade);
		alvo.receberDano(danoHabilidade);
		
		EfeitoStatus quickSand = new EfeitoStatus("Paralizar", duracaoPturno, danoCausado);
		alvo.adicionarEfeitoStatus(quickSand);
		System.out.println(alvo.getNome() + " está preso na areia movediça " + duracaoPturno + " turnos!");
		
	}

	@Override
	public TipoElemental getElemento() {
		return te.TERRA;
	}
}
