package model.personagens;

import model.Criatura;
import model.TipoElemental;

public class AirSpirit extends Criatura{

	public AirSpirit(TipoElemental element) {
		super("Espirito", 100, 50, 60, 200, element);
	}

}
