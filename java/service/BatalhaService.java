package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Criatura;
import model.EfeitoStatus;
import model.Habilidade;
import model.Item;
import model.Jogador;

public class BatalhaService {
    private Jogador jogador1; 
    private Jogador jogador2;
    private Scanner sc = new Scanner(System.in);
    private List<Criatura> ordemTurno;
    private Random random = new Random();

    public BatalhaService(Jogador j1, Jogador j2) {
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.ordemTurno = new ArrayList<>();
    }

    public void comecarBatalha() {
        System.out.println("\n🔥 BATALHA INICIADA 🔥");
        System.out.println(jogador1.getCriatura().getNome() + " VS " + jogador2.getCriatura().getNome());

        definirOrdemTurno();

        int turno = 1;
        while (jogador1.aindaPodeLutar() && jogador2.aindaPodeLutar()) {
            System.out.println("\n--- TURNO " + turno + " ---");

            for (Criatura criaturaAtual : ordemTurno) {
                if (!jogador1.aindaPodeLutar() || !jogador2.aindaPodeLutar()) {
                    break; // Batalha terminou
                }

                Jogador jogadorDono = (criaturaAtual == jogador1.getCriatura()) ? jogador1 : jogador2;
                Jogador oponenteDono = (criaturaAtual == jogador1.getCriatura()) ? jogador2 : jogador1;
//                Criatura criaturaOponente = oponenteDono.getCriatura();

                System.out.println("\nTurno de " + criaturaAtual.getNome() + ":");

                // Aplicar e gerenciar efeitos de status
                gerenciarEfeitosDeStatus(criaturaAtual);
                if (!criaturaAtual.estaVivo()) {
                    System.out.println(criaturaAtual.getNome() + " foi derrotado pelos efeitos de status!");
                    continue; // Próxima criatura no turno
                }

                // Verificar se a criatura está paralisada ou congelada
                boolean podeAgir = true;
                for (EfeitoStatus efeito : criaturaAtual.getEfeitosAtivos()) {
                    if (efeito.getNome().equals("Paralizar") || efeito.getNome().equals("Congelar")) {
                        System.out.println(criaturaAtual.getNome() + " está " + efeito.getNome() + " e não pode agir neste turno!");
                        podeAgir = false;
                        break;
                    }
                }

                if (podeAgir) {
                    if (jogadorDono == jogador1) { // É o jogador humano
                        executarAcaoJogador(jogadorDono, oponenteDono);
                    } else { // É o inimigo (NPC)
                        executarAcaoInimigo(jogadorDono, oponenteDono);
                    }
                }
            }
            turno++;
        }

        if (jogador1.aindaPodeLutar() && !jogador2.aindaPodeLutar()) {
            System.out.println("\n🏆 " + jogador1.getNome() + " venceu a batalha!");
        } else if (jogador2.aindaPodeLutar() && !jogador1.aindaPodeLutar()) {
            System.out.println("\n🏆 " + jogador2.getNome() + " venceu a batalha!");
        } else {
            System.out.println("\n--- FIM DA BATALHA ---");
            System.out.println("A BATALHA TERMINOU EM EMPATE!");
        }
    }

    private void definirOrdemTurno() {
        ordemTurno.add(jogador1.getCriatura());
        ordemTurno.add(jogador2.getCriatura());
        ordemTurno.sort(Comparator.comparingInt(Criatura::getVelocidade).reversed()); // dar uma analizada
        System.out.println("Ordem de turno: " + ordemTurno.get(0).getNome() + ", " + ordemTurno.get(1).getNome());
    }

    private void executarAcaoJogador(Jogador jogador, Jogador oponente) {
        Criatura atacante = jogador.getCriatura();
        Criatura defensor = oponente.getCriatura();

        System.out.println("\n👉 " + jogador.getNome() + ", escolha sua ação:");
        System.out.println("1 - Atacar");
        System.out.println("2 - Usar Habilidade");
        System.out.println("3 - Usar Item");
        System.out.println("4 - Fugir");
        int escolha = sc.nextInt();

        switch (escolha) {
            case 1:
                realizarAtaqueBasico(atacante, defensor);
                break;
            case 2:
                if (atacante.getHabilidades().isEmpty()) {
                    System.out.println("Nenhuma habilidade disponível! Atacando...");
                    realizarAtaqueBasico(atacante, defensor);
                } else {
                    System.out.println("Escolha uma habilidade:");
                    for (int i = 0; i < atacante.getHabilidades().size(); i++) {
                        System.out.println((i + 1) + " - " + atacante.getHabilidades().get(i).nomeHabilidade());
                    }
                    int escolhaHab = sc.nextInt() - 1;
                    if (escolhaHab >= 0 && escolhaHab < atacante.getHabilidades().size()) {
                        Habilidade h = atacante.getHabilidades().get(escolhaHab);
                        h.atacarHabilidade(atacante, defensor);
                    } else {
                        System.out.println("Escolha inválida! Atacando...");
                        realizarAtaqueBasico(atacante, defensor);
                    }
                }
                break;
            case 3:
                if (jogador.getInventario().isEmpty()) {
                    System.out.println("Nenhum item disponível! Atacando...");
                    realizarAtaqueBasico(atacante, defensor);
                } else {
                    System.out.println("Escolha um item:");
                    for (int i = 0; i < jogador.getInventario().size(); i++) {
//                    	if(jogador.getInventario().get(i) == jogador.getInventario().get(i+1)) {
//                    		System.out.println((i + 1) + " - " + jogador.getInventario().get(i).getNome() + (i++));
//                    	}
                        System.out.println((i + 1) + " - " + jogador.getInventario().get(i).getNome());
                    }
                    int escolhaItem = sc.nextInt() - 1;
                    if (escolhaItem >= 0 && escolhaItem < jogador.getInventario().size()) {
                        Item item = jogador.getInventario().get(escolhaItem);
                        if(item.usar(atacante)) {
                        	jogador.getInventario().remove(item);
                        	System.out.println(atacante.getNome() + " usou " + item.getNome());
                        }
                        //Quando o HP estiver no maximo, ele não deve usar e deve dar a opção do jogador escolher o que fazer de novo.
                    } else {
                        System.out.println("Escolha inválida! Atacando...");
                        realizarAtaqueBasico(atacante, defensor);
                    }
                }
                break;
            case 4:
                System.out.println(jogador.getNome() + " fugiu da batalha!");
                atacante.setHp(0); // Força a derrota do jogador que fugiu
                break;
            default:
                System.out.println("Ação inválida! Atacando...");
                realizarAtaqueBasico(atacante, defensor);
        }
    }

    private void executarAcaoInimigo(Jogador inimigo, Jogador jogador) {
        Criatura atacante = inimigo.getCriatura();
        Criatura defensor = jogador.getCriatura();

        System.out.println("\nTurno do inimigo: " + atacante.getNome());

        // Lógica simples de IA para o inimigo: 70% chance de atacar, 30% de usar habilidade (se tiver)
        if (random.nextDouble() < 0.7 || atacante.getHabilidades().isEmpty()) {
            realizarAtaqueBasico(atacante, defensor);
        } else {
            // Escolhe uma habilidade aleatoriamente
            Habilidade habilidadeEscolhida = atacante.getHabilidades().get(random.nextInt(atacante.getHabilidades().size()));
            habilidadeEscolhida.atacarHabilidade(atacante, defensor);
        }
    }

    private void realizarAtaqueBasico(Criatura atacante, Criatura defensor) {
        double danoBase = atacante.getAtk();
        double multiplicadorElemental = atacante.getElement().calcularVantagem(defensor.getElement());
        // O dano final deve ser o dano base multiplicado pelo fator elemental, e a defesa deve reduzir esse valor.
        // Se a defesa for maior que o dano, o dano mínimo é 0.
        double danoCalculado = danoBase * multiplicadorElemental;
        double danoFinal = Math.max(0, danoCalculado - defensor.getDef());

        System.out.println(atacante.getNome() + " ataca " + defensor.getNome() + ". Multiplicador elemental: " + String.format("%.1f", multiplicadorElemental) + ". Dano calculado antes da defesa: " + String.format("%.2f", danoCalculado));
        defensor.receberDano(danoFinal);
    }

    private void gerenciarEfeitosDeStatus(Criatura criatura) {
        List<EfeitoStatus> efeitosParaRemover = new ArrayList<>();
        for (EfeitoStatus efeito : criatura.getEfeitosAtivos()) {
            if (efeito.estaAtivo()) {
                if (efeito.getValorEfeito() != 0) { // Se o efeito causa dano/cura
                    System.out.println(criatura.getNome() + " sofre efeito de " + efeito.getNome() + ". Dano/Cura: " + String.format("%.2f", efeito.getValorEfeito()));
                    criatura.receberDano(efeito.getValorEfeito()); // Assumindo que valorEfeito pode ser negativo para cura
                }
                efeito.decrementarDuracao();
            } else {
                efeitosParaRemover.add(efeito);
            }
        }
        for (EfeitoStatus efeito : efeitosParaRemover) {
            criatura.removerEfeitoStatus(efeito);
            System.out.println(criatura.getNome() + " não está mais sob efeito de " + efeito.getNome() + ".");
        }
    }
}