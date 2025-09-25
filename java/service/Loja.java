package service;

import java.util.Scanner;

import model.Item;
import model.Jogador;

public class Loja {
	 private Scanner sc = new Scanner(System.in);

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
	                    item = new Item("Poção de Cura", "cura", 30);
	                    jogador.gastarCoins(20);
	                }
	                break;
	            case 2:
	                if (jogador.getCoins() >= 25) {
	                    item = new Item("Poção de Força", "ataque", 10);
	                    jogador.gastarCoins(25);
	                }
	                break;
	            case 3:
	                if (jogador.getCoins() >= 30) {
	                    item = new Item("Antídoto", "limpar", 0);
	                    jogador.gastarCoins(30);
	                }
	                break;
	            default:
	                System.out.println("Opção inválida!");
	        }

	        if (item != null) {
	            jogador.getInventario().add(item);
	            System.out.println("✔ Você comprou: " + item.getNome());
	        }
	    }
}
