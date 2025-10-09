package principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.Criatura;
import model.Jogador;
import model.TipoElemental;
import model.habilidades.BolaDeFogo;
import model.habilidades.JatoDagua;
import model.habilidades.Queimar;
import model.itens.PocaoCura;
import model.itens.PocaoForca;
import model.personagens.MiniDragao;
import model.personagens.Slime;
import model.personagens.Syrene;

class ClasseDeTest {

    private Jogador jogador;
    private Criatura criaturaJogador;

    @Mock
    private Criatura mockInimigo;
    @Mock
    private PocaoCura mockPocaoCura;
    @Mock
    private PocaoForca mockPocaoForca;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criaturaJogador = new MiniDragao(TipoElemental.FOGO);
        jogador = new Jogador("Player1", criaturaJogador, 100);
    }

    @Test
    void testJogadorRecebeDano() {
        double hpInicial = jogador.getCriatura().getHp();
        jogador.getCriatura().receberDano(10);
        assertEquals(hpInicial - 10, jogador.getCriatura().getHp());
    }

    @Test
    void testJogadorUsaPocaoCura() {
        jogador.getCriatura().setHp(50);
        jogador.getCriatura().adicionarItem(mockPocaoCura);
        when(mockPocaoCura.usar(any(Criatura.class))).thenReturn(true);
        jogador.getCriatura().usarItem(mockPocaoCura, jogador.getCriatura());
        verify(mockPocaoCura, times(1)).usar(jogador.getCriatura());
    }

    @Test
    void testJogadorUsaPocaoForca() {
        double atkInicial = jogador.getCriatura().getAtk();
        jogador.getCriatura().adicionarItem(mockPocaoForca);
        when(mockPocaoForca.usar(any(Criatura.class))).thenReturn(true);
        jogador.getCriatura().usarItem(mockPocaoForca, jogador.getCriatura());
        verify(mockPocaoForca, times(1)).usar(jogador.getCriatura());
    }

    @Test
    void testHabilidadeBolaDeFogo() {
        Criatura alvo = new Slime(TipoElemental.AGUA);
        BolaDeFogo bolaDeFogo = new BolaDeFogo();
        double hpInicialAlvo = alvo.getHp();
        criaturaJogador.adicionarHabilidade(bolaDeFogo);
        criaturaJogador.usarHabilidade(bolaDeFogo, alvo);
        assertTrue(alvo.getHp() < hpInicialAlvo);
        assertFalse(alvo.getEfeitosAtivos().isEmpty());
    }

    @Test
    void testHabilidadeJatoDagua() {
        Criatura alvo = new MiniDragao(TipoElemental.FOGO);
        JatoDagua jatoDagua = new JatoDagua();
        double hpInicialAlvo = alvo.getHp();
        Criatura syrene = new Syrene(TipoElemental.AGUA);
        syrene.adicionarHabilidade(jatoDagua);
        syrene.usarHabilidade(jatoDagua, alvo);
        assertTrue(alvo.getHp() < hpInicialAlvo);
    }

    @Test
    void testHabilidadeQueimar() {
        Criatura alvo = new Slime(TipoElemental.TERRA);
        Queimar queimar = new Queimar();
        double hpInicialAlvo = alvo.getHp();
        criaturaJogador.adicionarHabilidade(queimar);
        criaturaJogador.usarHabilidade(queimar, alvo);
        assertTrue(alvo.getHp() < hpInicialAlvo);
        assertFalse(alvo.getEfeitosAtivos().isEmpty());
    }

    @Test
    void testTipoElementalVantagem() {
        assertEquals(2.0, TipoElemental.FOGO.calcularVantagem(TipoElemental.TERRA));
        assertEquals(0.5, TipoElemental.FOGO.calcularVantagem(TipoElemental.AGUA));
        assertEquals(1.0, TipoElemental.FOGO.calcularVantagem(TipoElemental.FOGO));
    }
}

