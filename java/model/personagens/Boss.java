package model.personagens;

import model.Criatura;
import model.TipoElemental;

public class Boss extends Criatura{

	public Boss(TipoElemental element) {
		super("Boss", 150, 20, 150, 40, element);
	}

}
