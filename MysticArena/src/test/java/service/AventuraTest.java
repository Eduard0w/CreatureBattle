package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import model.Criatura;
import model.Jogador;
import model.TipoElemental;
import model.personagens.Boss;
import model.personagens.Slime;
import model.personagens.Golem;
import model.personagens.DragonFly;
import model.personagens.MiniDragao;

import java.util.Random;
import java.util.Scanner;

class AventuraTest {

    @Mock
    private Jogador mockJogador;
    @Mock
    private BatalhaService mockBatalhaService;
    @Mock
    private Scanner mockScanner;
    @Mock
    private Random mockRandom;
    @Mock
    private Loja mockLoja;

    private Aventura aventura;
    private Aventura spyAventura;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aventura = new Aventura(mockJogador, mockBatalhaService, mockScanner, mockRandom, mockLoja);
        spyAventura = spy(aventura);

        when(mockJogador.getNome()).thenReturn("Herói");
    }

    // Helper para definir o campo inimigosDerrotados via reflexão
    private void setInimigosDerrotados(int value) {
        try {
            java.lang.reflect.Field field = Aventura.class.getDeclaredField("inimigosDerrotados");
            field.setAccessible(true);
            field.set(spyAventura, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Não foi possível acessar o campo inimigosDerrotados: " + e.getMessage());
        }
    }

    @Test
    void testIniciar_EscolheIniciar() {
        when(mockScanner.nextInt()).thenReturn(1);
        when(mockScanner.nextLine()).thenReturn("\n");

        doNothing().when(spyAventura).loopAventura();

        spyAventura.iniciar();

        verify(mockScanner, times(1)).nextInt();
        verify(spyAventura, times(1)).loopAventura();
    }

    @Test
    void testIniciar_EscolheSair() {
        when(mockScanner.nextInt()).thenReturn(2);
        when(mockScanner.nextLine()).thenReturn("\n");

        spyAventura.iniciar();

        verify(mockScanner, times(1)).nextInt();
        verify(spyAventura, never()).loopAventura();
    }

    @Test
    void testLoopAventura_JogadorVenceUmaBatalha() {
        when(mockJogador.aindaPodeLutar()).thenReturn(true, false); // Jogador luta uma vez e para

        Criatura mockInimigo = mock(Slime.class);
        when(mockInimigo.estaVivo()).thenReturn(false); // Inimigo é derrotado

        doReturn(mockInimigo).when(spyAventura).gerarProximoInimigo();
        doNothing().when(spyAventura).executarBatalha(any(Criatura.class));
        doReturn(false).when(spyAventura).jogadorPerdeu(any(Criatura.class));
        doReturn(false).when(spyAventura).jogadorVenceuBoss(any(Criatura.class));
        
        // Usar doAnswer para simular o incremento de inimigosDerrotados
        doAnswer(invocation -> {
            setInimigosDerrotados(spyAventura.getInimigosDerrotados() + 1);
            return null;
        }).when(spyAventura).processarVitoria();

        spyAventura.loopAventura();

        verify(spyAventura, times(1)).gerarProximoInimigo();
        verify(spyAventura, times(1)).executarBatalha(mockInimigo);
        verify(spyAventura, times(1)).jogadorPerdeu(mockInimigo);
        verify(spyAventura, times(1)).jogadorVenceuBoss(mockInimigo);
        verify(spyAventura, times(1)).processarVitoria();
        assertEquals(1, spyAventura.getInimigosDerrotados());
    }

    @Test
    void testLoopAventura_JogadorPerdeUmaBatalha() {
        when(mockJogador.aindaPodeLutar()).thenReturn(true, false); // Jogador luta uma vez e para

        Criatura mockInimigo = mock(Slime.class);
        when(mockInimigo.estaVivo()).thenReturn(true); // Inimigo não é derrotado (jogador perde)

        doReturn(mockInimigo).when(spyAventura).gerarProximoInimigo();
        doNothing().when(spyAventura).executarBatalha(any(Criatura.class));
        doReturn(true).when(spyAventura).jogadorPerdeu(any(Criatura.class)); // Jogador perde
        doReturn(false).when(spyAventura).jogadorVenceuBoss(any(Criatura.class));
        doNothing().when(spyAventura).processarVitoria();

        spyAventura.loopAventura();

        verify(spyAventura, times(1)).gerarProximoInimigo();
        verify(spyAventura, times(1)).executarBatalha(mockInimigo);
        verify(spyAventura, times(1)).jogadorPerdeu(mockInimigo);
        verify(spyAventura, never()).jogadorVenceuBoss(any(Criatura.class));
        verify(spyAventura, never()).processarVitoria();
        assertEquals(0, spyAventura.getInimigosDerrotados());
    }

    @Test
    void testLoopAventura_JogadorVenceBoss() {
        when(mockJogador.aindaPodeLutar()).thenReturn(true, true, true, true, true, false); // 5 batalhas, depois para

        Criatura mockInimigoNormal = mock(Slime.class);
        when(mockInimigoNormal.estaVivo()).thenReturn(false);
        Criatura mockBoss = mock(Boss.class);
        when(mockBoss.estaVivo()).thenReturn(false); // Boss é derrotado

        doReturn(mockInimigoNormal, mockInimigoNormal, mockInimigoNormal, mockInimigoNormal, mockBoss)
                .when(spyAventura).gerarProximoInimigo();
        doNothing().when(spyAventura).executarBatalha(any(Criatura.class));
        doReturn(false).when(spyAventura).jogadorPerdeu(any(Criatura.class));
        doReturn(false, false, false, false, true).when(spyAventura).jogadorVenceuBoss(any(Criatura.class)); // Vence o boss na 5ª
        
        // Usar doAnswer para simular o incremento de inimigosDerrotados
        doAnswer(invocation -> {
            setInimigosDerrotados(spyAventura.getInimigosDerrotados() + 1);
            return null;
        }).when(spyAventura).processarVitoria();

        spyAventura.loopAventura();

        verify(spyAventura, times(5)).gerarProximoInimigo();
        verify(spyAventura, times(5)).executarBatalha(any(Criatura.class));
        verify(spyAventura, times(5)).jogadorPerdeu(any(Criatura.class));
        verify(spyAventura, times(5)).jogadorVenceuBoss(any(Criatura.class));
        verify(spyAventura, times(4)).processarVitoria(); // 4 vitórias normais, boss não chama processarVitoria
        assertEquals(4, spyAventura.getInimigosDerrotados()); // 4 inimigos normais derrotados
    }

    @Test
    void testLoopAventura_JogadorPerdeBoss() {
        when(mockJogador.aindaPodeLutar()).thenReturn(true, true, true, true, true, false); // 5 batalhas, depois para

        Criatura mockInimigoNormal = mock(Slime.class);
        when(mockInimigoNormal.estaVivo()).thenReturn(false);
        Criatura mockBoss = mock(Boss.class);
        when(mockBoss.estaVivo()).thenReturn(true); // Boss não é derrotado (jogador perde)

        doReturn(mockInimigoNormal, mockInimigoNormal, mockInimigoNormal, mockInimigoNormal, mockBoss)
                .when(spyAventura).gerarProximoInimigo();
        doNothing().when(spyAventura).executarBatalha(any(Criatura.class));
        doReturn(false, false, false, false, true).when(spyAventura).jogadorPerdeu(any(Criatura.class)); // Jogador perde na 5ª
        doReturn(false).when(spyAventura).jogadorVenceuBoss(any(Criatura.class));
        
        // Usar doAnswer para simular o incremento de inimigosDerrotados
        doAnswer(invocation -> {
            setInimigosDerrotados(spyAventura.getInimigosDerrotados() + 1);
            return null;
        }).when(spyAventura).processarVitoria();

        spyAventura.loopAventura();

        verify(spyAventura, times(5)).gerarProximoInimigo();
        verify(spyAventura, times(5)).executarBatalha(any(Criatura.class));
        verify(spyAventura, times(5)).jogadorPerdeu(any(Criatura.class));
        verify(spyAventura, times(4)).jogadorVenceuBoss(any(Criatura.class)); // Boss não é vencido, então não é verificado na 5ª
        verify(spyAventura, times(4)).processarVitoria(); // 4 vitórias normais
        assertEquals(4, spyAventura.getInimigosDerrotados()); // 4 inimigos normais derrotados
    }

    @Test
    void testGerarProximoInimigo_NormalCaso() {
        setInimigosDerrotados(0);

        Criatura mockSlime = mock(Slime.class);
        doReturn(mockSlime).when(spyAventura).gerarInimigo();

        Criatura inimigo = spyAventura.gerarProximoInimigo();

        assertNotNull(inimigo);
        assertFalse(inimigo instanceof Boss);
        verify(spyAventura, times(1)).gerarInimigo();
    }

    @Test
    void testGerarProximoInimigo_BossCaso() {
        setInimigosDerrotados(5);

        Criatura inimigo = spyAventura.gerarProximoInimigo();

        assertNotNull(inimigo);
        assertTrue(inimigo instanceof Boss);
        verify(spyAventura, never()).gerarInimigo(); // Não deve chamar gerarInimigo() se for boss
    }

    @Test
    void testExecutarBatalha() {
        Criatura mockInimigo = mock(Slime.class);
        spyAventura.executarBatalha(mockInimigo);

        ArgumentCaptor<Jogador> jogadorCaptor = ArgumentCaptor.forClass(Jogador.class);
        ArgumentCaptor<Jogador> npcCaptor = ArgumentCaptor.forClass(Jogador.class);

        verify(mockBatalhaService, times(1)).setJogadores(jogadorCaptor.capture(), npcCaptor.capture());
        verify(mockBatalhaService, times(1)).comecarBatalha();

        assertEquals(mockJogador, jogadorCaptor.getValue());
        assertEquals(mockInimigo, npcCaptor.getValue().getCriatura());
    }

    @Test
    void testJogadorPerdeu() {
        when(mockJogador.aindaPodeLutar()).thenReturn(false);
        assertTrue(spyAventura.jogadorPerdeu(mock(Criatura.class)));

        when(mockJogador.aindaPodeLutar()).thenReturn(true);
        assertFalse(spyAventura.jogadorPerdeu(mock(Criatura.class)));
    }

    @Test
    void testJogadorVenceuBoss() {
        Criatura mockBoss = mock(Boss.class);
        when(mockBoss.estaVivo()).thenReturn(false);
        assertTrue(spyAventura.jogadorVenceuBoss(mockBoss));

        when(mockBoss.estaVivo()).thenReturn(true);
        assertFalse(spyAventura.jogadorVenceuBoss(mockBoss));

        Criatura mockSlime = mock(Slime.class);
        assertFalse(spyAventura.jogadorVenceuBoss(mockSlime));
    }

    @Test
    void testProcessarVitoria() {
        spyAventura.processarVitoria();

        verify(mockJogador, times(1)).adicionarCoins(anyInt());
        verify(mockLoja, times(1)).abrirLoja(mockJogador);
        assertEquals(1, spyAventura.getInimigosDerrotados());
    }

    @Test
    void testGerarInimigo() {
        when(mockRandom.nextInt(TipoElemental.values().length)).thenReturn(0); // Tipo elemental fixo

        when(mockRandom.nextInt(4)).thenReturn(0); // Slime
        assertTrue(spyAventura.gerarInimigo() instanceof Slime);

        when(mockRandom.nextInt(4)).thenReturn(1); // Golem
        assertTrue(spyAventura.gerarInimigo() instanceof Golem);

        when(mockRandom.nextInt(4)).thenReturn(2); // DragonFly
        assertTrue(spyAventura.gerarInimigo() instanceof DragonFly);

        when(mockRandom.nextInt(4)).thenReturn(3); // MiniDragao
        assertTrue(spyAventura.gerarInimigo() instanceof MiniDragao);
    }
}

