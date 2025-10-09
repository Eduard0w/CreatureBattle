package model.habilidades;

import model.Criatura;
import model.efeitos.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Congelar implements Habilidade {
	private int duracaoPturno = 2;
	private double danoCausado = 2;
	private TipoElemental te;
	
	@Override
	public String nomeHabilidade() {
		return "Congelar";
	}

	@Override
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		alvo.receberDano(danoHabilidade);
		System.out.println(jogador.getNome() + " congelou " + alvo.getNome() + "!");
		
		EfeitoStatus congelar = new EfeitoStatus("Congelar", duracaoPturno, (danoCausado - jogador.getElement().calcularVantagem(alvo.getElement())));
		alvo.adicionarEfeitoStatus(congelar);
		System.out.println(alvo.getNome() + " foi afetado por Congelar por " + duracaoPturno + " turnos!");
	}

	@Override
	public TipoElemental getElemento() {
		return te.AGUA;
	}


}
