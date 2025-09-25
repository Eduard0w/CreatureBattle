package model.personagens;

import model.Criatura;
import model.Habilidade;
import model.TipoElemental;
import model.habilidades.Congelar;

public class DragonFly extends Criatura{
	private Habilidade hab1 = new Congelar();

	public DragonFly(TipoElemental element) {
		super("DragonFly", 100, 50, 60, 200, element);
		super.adicionarHabilidade(hab1);
	}

}
