package model.habilidades;

import model.Criatura;
import model.efeitos.EfeitoStatus;
import model.Habilidade;
import model.TipoElemental;

public class Queimar implements Habilidade{
	private int duracaoPturno = 4;
	private double danoCausado = 5;
	private TipoElemental te;
	
	@Override
	public String nomeHabilidade() {
		// TODO Auto-generated method stub
		return "Queimar";
	}

	@Override 
	public void atacarHabilidade(Criatura jogador, Criatura alvo) {
		double multiplicador = jogador.getElement().calcularVantagem(alvo.getElement());
		double danoHabilidade = (danoCausado + jogador.getAtk()) * multiplicador;
		System.out.println(jogador.getNome() + " Atacou " + nomeHabilidade() + " em " + alvo.getNome() 
		+ "\n Dano sofrido: "+ danoHabilidade);
		alvo.receberDano(danoHabilidade);
		
		EfeitoStatus queimadura = new EfeitoStatus("Queimadura", duracaoPturno, danoCausado);
		alvo.adicionarEfeitoStatus(queimadura);
		System.out.println(alvo.getNome() + " foi afetado por Queimadura por " + duracaoPturno + " turnos!");
		
	}

	@Override
	public TipoElemental getElemento() {
		return te.FOGO;
	}

}
