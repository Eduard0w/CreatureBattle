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
		// Pega a primeira habilidade do Golem, que é Paralizar.
				Habilidade habilidadeGolem = p1.getCriatura().getHabilidades().get(0);
				
				// Usa a habilidade no inimigo
				p1.getCriatura().usarHabilidade(habilidadeGolem, p2.getCriatura());
				
				List<EfeitoStatus> efeitosInimigo = p2.getCriatura().getEfeitosAtivos();
				
				// Verifica se a lista de efeitos ativos não está vazia e se o nome é "Paralizar"
				assertFalse(efeitosInimigo.isEmpty());
				assertEquals("Paralizar", efeitosInimigo.get(0).getNome());
	}
	
	@Test
	public void testDefesa() {
		//TODO: testar ataque
	}

}


//package test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import model.Criatura;
//import model.EfeitoStatus;
//import model.Habilidade;
//import model.Jogador;
//import model.TipoElemental;
//import model.habilidades.BolaDeFogo;
//import model.personagens.Golem;
//import model.personagens.MiniDragao;
//import model.personagens.Slime;
//
//public class ClasseDeTest {
//    Jogador p1;
//    Jogador p2;
//
//    @BeforeEach
//    public void setup() {
//        p1 = new Jogador("nomeTest", new MiniDragao(TipoElemental.FOGO), 20);
//        p2 = new Jogador("Inimigo", new Slime(TipoElemental.AGUA), 20);
//    }
//    
//    @Test
//    public void testCriacaoJogador() {
//        String ConfirmarNome = p1.getNome();
//        assertEquals("nomeTest", ConfirmarNome);
//    }
//    
//    @Test
//    public void testAtaque() {
//        double hpInicialInimigo = p2.getCriatura().getHp();
//        
//        // Pega a BolaDeFogo do MiniDragao
//        Habilidade habilidade = p1.getCriatura().getHabilidades().get(1);
//        
//        // Usa a habilidade para atacar
//        p1.getCriatura().usarHabilidade(habilidade, p2.getCriatura());
//        
//        double hpFinalInimigo = p2.getCriatura().getHp();
//        
//        // Verifica se o HP do inimigo foi reduzido após o ataque
//        assertNotEquals(hpInicialInimigo, hpFinalInimigo);
//        assertTrue(hpFinalInimigo < hpInicialInimigo);
//    }
//    
//    @Test
//    public void testDefesa() {
//    	// Inicia as criaturas com HP conhecido para facilitar o cálculo
//        p1 = new Jogador("nomeTest", new MiniDragao(TipoElemental.FOGO), 20);
//        p2 = new Jogador("Inimigo", new Golem(TipoElemental.TERRA), 20);
//
//    	double hpInicialInimigo = p2.getCriatura().getHp(); // HP inicial do Golem: 100
//        
//        // Pega a BolaDeFogo do MiniDragao
//        Habilidade habilidade = p1.getCriatura().getHabilidades().get(1);
//        
//        // Usa a habilidade para atacar o Golem
//        p1.getCriatura().usarHabilidade(habilidade, p2.getCriatura());
//        
//        double hpFinalInimigo = p2.getCriatura().getHp();
//
//        // O dano da Bola de Fogo + Ataque do Mini Dragão é 7 + 80 = 87
//        // O dano final é reduzido pela defesa do Golem (120)
//        // Dano final = Math.max(0, 87 - 120) = 0
//        assertEquals(hpInicialInimigo, hpFinalInimigo);
//    }
//    
//    @Test
//    public void testStatus() {
//        // Pega a primeira habilidade do Golem, que é Paralizar.
//        Habilidade habilidadeGolem = p1.getCriatura().getHabilidades().get(0);
//        
//        // Usa a habilidade no inimigo
//        p1.getCriatura().usarHabilidade(habilidadeGolem, p2.getCriatura());
//        
//        List<EfeitoStatus> efeitosInimigo = p2.getCriatura().getEfeitosAtivos();
//        
//        // Verifica se a lista de efeitos ativos não está vazia e se o nome é "Paralizar"
//        assertFalse(efeitosInimigo.isEmpty());
//        assertEquals("Paralizar", efeitosInimigo.get(0).getNome());
//    }
//}