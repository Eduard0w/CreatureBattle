package model.habilidades;

import model.Criatura;
import model.efeitos.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Paralizar implements Habilidade{
	private int duracaoPturno = 6;
	private double danoCausado = 3;
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
		System.out.println(jogador.getNome() + " paralisou " + alvo.getNome() + "!");
		
		EfeitoStatus paralizar = new EfeitoStatus("Paralizar", duracaoPturno, danoCausado);
		alvo.adicionarEfeitoStatus(paralizar);
		System.out.println(alvo.getNome() + " foi afetado por Paralizar por " + duracaoPturno + " turnos!");
	}

	@Override
	public TipoElemental getElemento() {
		return te.AR;
	}
}

