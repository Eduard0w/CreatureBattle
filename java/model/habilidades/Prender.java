package model.habilidades;

import model.Criatura;
import model.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Prender implements Habilidade{
	private int duracaoPturno = 2;
	private double danoCausado = 0;
	private TipoElemental te;
	
	@Override
	public String nomeHabilidade() {
		return "Paralizar";
	}

	@Override
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		alvo.receberDano(danoHabilidade);
		System.out.println(jogador.getNome() + " prendeu " + alvo.getNome() + "!");
		
		EfeitoStatus congelar = new EfeitoStatus("Paralizar", duracaoPturno, danoCausado);
		alvo.adicionarEfeitoStatus(congelar);
		System.out.println(alvo.getNome() + " foi afetado por Prender, por " + duracaoPturno + " turnos!");
		
	}

	@Override
	public TipoElemental getElemento() {
		return te.TERRA;
	}
}
