package model.habilidades;

import model.Criatura;
import model.Habilidade;
import model.TipoElemental;

public class JatoDagua implements Habilidade{
	private double danoCausado = 5;
	private TipoElemental te;

	@Override
	public String nomeHabilidade() {
		return "Jato d'agua";
	}

	@Override
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		System.out.println(jogador.getNome() + " Atacou " + nomeHabilidade() + " em " + alvo.getNome());
		alvo.receberDano(danoHabilidade);
	}

	@Override
	public TipoElemental getElemento() {
		return te.AGUA;
	}

}
