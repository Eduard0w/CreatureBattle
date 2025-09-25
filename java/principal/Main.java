package principal;

import java.util.Scanner;

import model.Jogador;
import model.TipoElemental;
import model.personagens.Golem;
import model.personagens.MiniDragao;
import model.personagens.Slime;
import model.personagens.Syrene;
import service.Aventura;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Jogador p1 = null;
		
		System.out.println("Bem vindo!");
		System.out.println("Treinador, digite seu nome: ");
		String nome = sc.nextLine();
		System.out.println("Escolha sua criatura para se aventurar nas batalhas!!");
		System.out.println("1- MiniDragão(tipo FOGO)"
				+ "\n2- Golem(tipo TERRA)"
				+ "\n3- syrene(tipo ÁGUA)");
		int escolha = sc.nextInt();
		if(escolha > 3 || escolha <= 0) {
			System.out.println("Vou escolher um para você, aleatoriamente");
			p1 = new Jogador(nome, new Slime(TipoElemental.AR), 20);
		}
		switch(escolha) {
			case 1: 
				p1 = new Jogador(nome, new MiniDragao(TipoElemental.FOGO), 20);
				break;
			case 2: 
				p1 = new Jogador(nome, new Golem(TipoElemental.TERRA), 20);
				break;
			case 3: 
				p1 = new Jogador(nome, new Syrene(TipoElemental.AGUA), 20);
				break;
		}
		
		Aventura comecar = new Aventura(p1);
		comecar.iniciar();
		
		sc.close();
		
	}

	//O que falta (que eu lembro agora):
	/* Definir habilidade para os personagens que podem ser escohidos pelo usuario.
	 * Deixar o usuario escolher qual criatura vai utilizar.
	 * Definir que criaturas só podem ter habilidades que condizem com seu elemento.
	 * Fazer a batalha ser mais equilibrada, fazendo inimigos que ataquem habilidades e causem dano.
	 * Fazer os efeitos funcionarem.
	 * Fazer quando houver mais de um item igual no iventario ele ser incrementado um no outro (EX.: 1- poção de cura(3)).
	 * Fazer os outros itens funcionarem.
	 * Fazer uma contagem de inimigo, quando 5 forem mortos aparece o boss final
	 * Definir um nivel maximo de turnos que o usuario ira lutar para batalhar contra o boss final.
	 * Dar a possibilidade do usuario comprar mais de um item na loja, dando a opção dele sair da loja. (mas tem que comprar no minimo um item, amenos que não tenha dinheiro suficiente)
	 * 
	 * ----------
	 * 
	 * 
	 * Fazer testes com Junit e mockito
	 */
}