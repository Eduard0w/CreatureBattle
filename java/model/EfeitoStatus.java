package model;

public class EfeitoStatus {
	private String nome;
	private int duracao;
	private double valorEfeito;
	
	public EfeitoStatus(String nome, int duracao, double valorEfeito) {
	    this.nome = nome;
	    this.duracao = duracao;
	    this.valorEfeito = valorEfeito;
	}
	
	public String getNome() {
	    return nome;
	}
	
	public int getDuracao() {
	    return duracao;
	}
	
	public void decrementarDuracao() {
	    this.duracao--;
	}
	
	public double getValorEfeito() {
	    return valorEfeito;
	}
	
	public boolean estaAtivo() {
	    return duracao > 0;
	}
}
