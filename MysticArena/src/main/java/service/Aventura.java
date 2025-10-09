package service;

import java.util.Random;
import java.util.Scanner;

import model.Criatura;
import model.Jogador;
import model.TipoElemental;
import model.personagens.Boss;
import model.personagens.DragonFly;
import model.personagens.Golem;
import model.personagens.MiniDragao;
import model.personagens.Slime;

public class Aventura {
    private Jogador jogador;
    private int rodada;
    private Scanner sc;
    private Random random;
    private int inimigosDerrotados = 0;
    private BatalhaService batalhaService;
    private Loja loja;

    public Aventura(Jogador jogador, BatalhaService batalhaService, Scanner sc, Random random, Loja loja) {
        this.jogador = jogador;
        this.rodada = 1;
        this.batalhaService = batalhaService;
        this.sc = sc;
        this.random = random;
        this.loja = loja;
    }

    public void iniciar() {
        System.out.println("Bem-vindo à Aventura, " + jogador.getNome() + "!");
        System.out.println("1 - Iniciar Aventura");
        System.out.println("2 - Sair");
        int escolha = sc.nextInt();
        sc.nextLine(); // Consumir a nova linha

        if (escolha == 2) {
            System.out.println("Aventura encerrada.");
            return;
        }

        loopAventura();
    }

    public void loopAventura() {
        while (jogador.aindaPodeLutar()) {
            Criatura inimigo = gerarProximoInimigo();
            executarBatalha(inimigo);

            if (jogadorPerdeu(inimigo)) {
                System.out.println("\n💀 GAME OVER! Você chegou ate a rodada " + rodada);
                break;
            }

            if (jogadorVenceuBoss(inimigo)) {
                System.out.println("\n🏆 BOSS VENCIDO! Você chegou ate a rodada " + rodada);
                break;
            }

            processarVitoria();
        }
    }

    public Criatura gerarProximoInimigo() {
            if (inimigosDerrotados > 0 && inimigosDerrotados % 5 == 0) {
            System.out.println("\n👹 UM BOSS APARECEU! 👹");
            return new Boss(TipoElemental.TREVAS);
        } else {
            return gerarInimigo();
        }
    }

    public void executarBatalha(Criatura inimigo) {
        Jogador npc = new Jogador("Inimigo", inimigo, 0);
        batalhaService.setJogadores(jogador, npc);
        batalhaService.comecarBatalha();
    }

    public boolean jogadorPerdeu(Criatura inimigo) {
        return !jogador.aindaPodeLutar();
    }

    public boolean jogadorVenceuBoss(Criatura inimigo) {
        return inimigo instanceof Boss && !inimigo.estaVivo();
    }

    public void processarVitoria() {
        int coinsGanhas = 10 + rodada * 5;
        jogador.adicionarCoins(coinsGanhas);
        System.out.println("\n💰 Você ganhou " + coinsGanhas + " coins! Total: " + jogador.getCoins());
        inimigosDerrotados++;
        loja.abrirLoja(jogador);
        rodada++;
    }

    public Criatura gerarInimigo() {
        TipoElemental tipo = TipoElemental.values()[random.nextInt(TipoElemental.values().length)];
        int escolha = random.nextInt(4); // 4 tipos de inimigos

        switch (escolha) {
            case 0:
                return new Slime(tipo);
            case 1:
                return new Golem(tipo);
            case 2:
                return new DragonFly(tipo);
            case 3:
                return new MiniDragao(tipo);
            default:
                return new Slime(tipo);
        }
    }

    public int getInimigosDerrotados() {
        return inimigosDerrotados;
    }

    // Setter para BatalhaService para permitir reconfiguração em cada batalha, se necessário
    public void setBatalhaService(BatalhaService batalhaService) {
        this.batalhaService = batalhaService;
    }
}

