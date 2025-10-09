package model.personagens;

import model.Criatura;
import model.Habilidade;
import model.TipoElemental;
import model.habilidades.BolaDeFogo;
import model.habilidades.Queimar;

public class MiniDragao extends Criatura{
	private Habilidade hab1 = new Queimar();
	private Habilidade hab2 = new BolaDeFogo();
	
	//Esse constructor realmente precisa receber um TipoElemental?
	public MiniDragao(TipoElemental element) {
		super("Mini Dragão", 100, 80, 50, 80, element);
		super.adicionarHabilidade(hab1);
		super.adicionarHabilidade(hab2);
	}

}
