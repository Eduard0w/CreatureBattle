package model.personagens;

import model.Criatura;
import model.Habilidade;
import model.TipoElemental;
import model.habilidades.Congelar;
import model.habilidades.JatoDagua;

public class Syrene extends Criatura{
	private Habilidade hab1 = new JatoDagua();
	private Habilidade hab2 = new Congelar();

	public Syrene(TipoElemental element) {
		super("Syrene", 100, 50, 60, 80, element);
		super.adicionarHabilidade(hab1);
		super.adicionarHabilidade(hab2);
	}

}
