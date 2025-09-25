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
    private Scanner sc = new Scanner(System.in);
    private Random random = new Random();
    private int inimigosDerrotados = 0;
    private BatalhaService batalhaService;

    public Aventura(Jogador jogador, BatalhaService batalhaService) {
        this.jogador = jogador;
        this.rodada = 1;
        this.batalhaService = batalhaService;
    }

    public Aventura(Jogador jogador) {
        this.jogador = jogador;
        this.rodada = 1;
    }

    public void iniciar() {

        while (jogador.aindaPodeLutar()) {

            Criatura inimigo = null;
            if (inimigosDerrotados > 0 && inimigosDerrotados % 5 == 0) {
                System.out.println("\n👹 UM BOSS APARECEU! 👹");
                inimigo = new Boss(TipoElemental.TREVAS);
            } else {
                inimigo = gerarInimigo();
            }

            Jogador npc = new Jogador("Inimigo", inimigo, 0);

            BatalhaService batalha = (this.batalhaService != null) ? this.batalhaService : new BatalhaService(jogador, npc);
            batalha.comecarBatalha();

            if (!jogador.aindaPodeLutar()) {
            	System.out.println("\n💀 GAME OVER! Você chegou ate a rodada " + rodada);
            	break;
            }else if(inimigo.getNome().equals("Boss") && !inimigo.estaVivo()){
            	System.out.println("\n🏆 BOSS VENCIDO! Você chegou ate a rodada " + rodada);
            	break;
            }

 
            int coinsGanhas = 10 + rodada * 5;
            jogador.adicionarCoins(coinsGanhas);
            System.out.println("\n💰 Você ganhou " + coinsGanhas + " coins! Total: " + jogador.getCoins());
            inimigosDerrotados++;
            

            Loja loja = new Loja();
            loja.abrirLoja(jogador);
            
            rodada++;
        }
        
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
    
 // Getter adicionado para fins de teste
    public int getInimigosDerrotados() {
    	System.out.println(inimigosDerrotados);
        return inimigosDerrotados;
    }
}
