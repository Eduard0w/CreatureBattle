package model.itens;

import model.Criatura;

public class Item {
	private String nome;
    private String efeito;
    private int valor;

    public Item(String nome, String efeito, int valor) {
        this.nome = nome;
        this.efeito = efeito;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public String getEfeito() { return efeito; }
    public int getValor() { return valor; }

    public boolean usar(Criatura criatura) {
        switch (efeito.toLowerCase()) {
            case "cura":
            	if(criatura.getHp() < 100) {
            		criatura.curar(valor);
            		return true;
                }else {
                	System.out.println("HP máximo...");
                	return false;
                }
            case "ataque":
                criatura.setAtaque(criatura.getAtk() + valor);
                System.out.println(criatura.getNome() + " recebeu +" + valor + " de ATK!");
                return true;
            case "limpar":
            	if (!criatura.getEfeitosAtivos().isEmpty()) {
                    criatura.getEfeitosAtivos().clear();
                    System.out.println("Status negativos removidos de " + criatura.getNome());
                    return true;
                } else {
                    System.out.println("Nenhum status para ser removido");
                    return false;
                }
            default:
                System.out.println("Efeito não implementado!");
                return false;
        }
    }
}

