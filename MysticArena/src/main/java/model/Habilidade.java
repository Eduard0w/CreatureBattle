package model;

public interface Habilidade {
	public String nomeHabilidade();
	public TipoElemental getElemento();
	public void atacarHabilidade(Criatura jogador, Criatura alvo);
}
