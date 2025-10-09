package service;

import java.util.Scanner;

import model.itens.Item;
import model.Jogador;
import model.itens.Antidoto;
import model.itens.PocaoCura;
import model.itens.PocaoForca;

public class Loja {
	 private Scanner sc;

	 public Loja(Scanner sc) {
		 this.sc = sc;
	 }

	    public void abrirLoja(Jogador jogador) {
	        System.out.println("\n🛒 Bem-vindo à Loja! Você tem " + jogador.getCoins() + " coins.");
	        System.out.println("1 - Poção de Cura (20$) → Cura 30 HP");
	        System.out.println("2 - Poção de Força (25$) → +10 ATK");
	        System.out.println("3 - Antídoto (30$) → Remove status");
	        System.out.print("Escolha um item: ");

	        int escolha = sc.nextInt();
	        Item item = null;

	        switch (escolha) {
	            case 1:
	                if (jogador.getCoins() >= 20) {
	                    item = new PocaoCura();
	                    jogador.gastarCoins(20);
	                }
	                break;
	            case 2:
	                if (jogador.getCoins() >= 25) {
	                    item = new PocaoForca();
	                    jogador.gastarCoins(25);
	                }
	                break;
	            case 3:
	                if (jogador.getCoins() >= 30) {
	                    item = new Antidoto();
	                    jogador.gastarCoins(30);
	                }
	                break;
	            default:
	                System.out.println("Opção inválida!");
	        }

	        if (item != null) {
	            jogador.getCriatura().adicionarItem(item);
	            System.out.println("✔ Você comprou: " + item.getNome());
	        }
	    }
}
