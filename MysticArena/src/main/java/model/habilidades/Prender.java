package model.habilidades;

import model.Criatura;
import model.efeitos.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Prender implements Habilidade{
	private int duracaoPturno = 2;
	private double danoCausado = 5.0; // Ajustado para ter algum efeito
	private TipoElemental te;
	
	@Override
	public String nomeHabilidade() {
		return "Prender"; // Corrigido o nome da habilidade
	}

	@Override
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		alvo.receberDano(danoHabilidade);
		System.out.println(jogador.getNome() + " prendeu " + alvo.getNome() + "!");
		
		EfeitoStatus prender = new EfeitoStatus("Prender", duracaoPturno, danoCausado); // Corrigido o nome do efeito
		alvo.adicionarEfeitoStatus(prender);
		System.out.println(alvo.getNome() + " foi afetado por Prender, por " + duracaoPturno + " turnos!");
		
	}

	@Override
	public TipoElemental getElemento() {
		return te.TERRA;
	}
}
