package model.personagens;

import model.Criatura;
import model.Habilidade;
import model.TipoElemental;
import model.habilidades.Prender;
import model.habilidades.Quicksand;

public class Golem extends Criatura{
	private Habilidade hab1 = new Quicksand();
	private Habilidade hab2 = new Prender();
	
	public Golem(TipoElemental element) {
		super("Golem", 100, 80, 120, 50, element);
		super.adicionarHabilidade(hab1);
		super.adicionarHabilidade(hab2);
	}

}
