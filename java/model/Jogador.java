package model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private Criatura criatura;
    private List<Item> inventario;
    private int coins;

    public Jogador(String nome, Criatura criatura, int coins) {
        this.nome = nome;
        this.criatura = criatura;
        this.coins = coins;
        this.inventario = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public Criatura getCriatura() { return criatura; }
    public List<Item> getInventario() { return inventario; }
    public int getCoins() { return coins; }

    public void gastarCoins(int valor) {
        if (coins >= valor) {
            coins = coins - valor;
        } else {
            System.out.println("Você não tem coins suficientes!");
        }
    }

    public void adicionarCoins(int valor) {
        coins += valor;
    }

    public boolean aindaPodeLutar() {
        return criatura.estaVivo();
    }
}
