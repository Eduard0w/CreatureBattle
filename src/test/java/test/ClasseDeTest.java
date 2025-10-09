package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import model.EfeitoStatus;
import model.Habilidade;
import model.Jogador;
import model.TipoElemental;
import model.personagens.Golem;
import model.personagens.Slime;

public class ClasseDeTest {
	private TipoElemental tp;
	Jogador p1;
	Jogador p2;
	
	@BeforeEach
	public void setup() {
		p1 = new Jogador("nomeTest", new Golem(TipoElemental.TERRA), 20);
		p2 = new Jogador("Inimigo", new Slime(TipoElemental.TERRA), 20);
	}
	
	@Test
	public void testCriacaoJogador() {	
		setup();
		String ConfirmarNome = p1.getNome();
		assertEquals("nomeTest", ConfirmarNome);
	}
	
	@Test
	public void testAtaque() {
		setup();
		double ConfirmarAtaque = p2.getCriatura().getHp();
		
		assertNotEquals(100, ConfirmarAtaque);
	}
	
	@Test
	public void testStatus() {
		setup();
				Habilidade habilidadeGolem = p1.getCriatura().getHabilidades().get(0);
				
				p1.getCriatura().usarHabilidade(habilidadeGolem, p2.getCriatura());
				
				List<EfeitoStatus> efeitosInimigo = p2.getCriatura().getEfeitosAtivos();
				
				assertFalse(efeitosInimigo.isEmpty());
				assertEquals("Paralizar", efeitosInimigo.get(0).getNome());
	}
	
	@Test
	public void testDefesa() {
		//TODO: testar ataque
	}

}
