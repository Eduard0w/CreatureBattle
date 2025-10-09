package principal;

import java.util.Random;
import java.util.Scanner;

import model.Jogador;
import model.TipoElemental;
import model.personagens.Golem;
import model.personagens.MiniDragao;
import model.personagens.Slime;
import model.personagens.Syrene;
import service.Aventura;
import service.BatalhaService;
import service.Loja;

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
		
		// Instanciando as dependências para Aventura
		Random random = new Random();
		Loja loja = new Loja(sc);
		// BatalhaService precisa de dois jogadores no construtor, mas para a inicialização do Main, podemos usar um placeholder
		// O Aventura irá chamar setJogadores no BatalhaService com os jogadores corretos.
		BatalhaService batalhaService = new BatalhaService(p1, new Jogador("InimigoPlaceholder", new Slime(TipoElemental.AR), 0), sc, random);

		Aventura comecar = new Aventura(p1, batalhaService, sc, random, loja);
		comecar.iniciar();
		
		sc.close();
	}
}

