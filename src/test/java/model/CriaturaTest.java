package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.efeitos.EfeitoStatus;
import model.itens.Item;

class CriaturaTest {

    private Criatura criatura;

    @Mock
    private Habilidade mockHabilidadeFogo;
    @Mock
    private Habilidade mockHabilidadeAgua;
    @Mock
    private EfeitoStatus mockEfeitoStatus;
    @Mock
    private Item mockItem;
    @Mock
    private Criatura mockAlvo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criatura = new Criatura("Teste", 100, 10, 5, 3, TipoElemental.FOGO);

        when(mockHabilidadeFogo.getElemento()).thenReturn(TipoElemental.FOGO);
        when(mockHabilidadeFogo.nomeHabilidade()).thenReturn("Bola de Fogo");
        when(mockHabilidadeAgua.getElemento()).thenReturn(TipoElemental.AGUA);
        when(mockHabilidadeAgua.nomeHabilidade()).thenReturn("Jato D'água");
        when(mockItem.getNome()).thenReturn("Poção de Cura");
    }

    @Test
    void testCriaturaConstructorAndGetters() {
        assertEquals("Teste", criatura.getNome());
        assertEquals(100, criatura.getHp());
        assertEquals(10, criatura.getAtk());
        assertEquals(5, criatura.getDef());
        assertEquals(3, criatura.getVelocidade());
        assertEquals(TipoElemental.FOGO, criatura.getElement());
        assertNotNull(criatura.getHabilidades());
        assertTrue(criatura.getHabilidades().isEmpty());
        assertNotNull(criatura.getEfeitosAtivos());
        assertTrue(criatura.getEfeitosAtivos().isEmpty());
        assertNotNull(criatura.getInventario());
        assertTrue(criatura.getInventario().isEmpty());
    }

    @Test
    void testSetters() {
        criatura.setNome("Novo Nome");
        assertEquals("Novo Nome", criatura.getNome());
        criatura.setHp(50);
        assertEquals(50, criatura.getHp());
        criatura.setAtk(20);
        assertEquals(20, criatura.getAtk());
        criatura.setDef(10);
        assertEquals(10, criatura.getDef());
        criatura.setVelocidade(5);
        assertEquals(5, criatura.getVelocidade());
        criatura.setElement(TipoElemental.AGUA);
        assertEquals(TipoElemental.AGUA, criatura.getElement());
    }

    @Test
    void testAdicionarHabilidade_TipoCorreto() {
        criatura.adicionarHabilidade(mockHabilidadeFogo);
        assertFalse(criatura.getHabilidades().isEmpty());
        assertEquals(1, criatura.getHabilidades().size());
        assertEquals(mockHabilidadeFogo, criatura.getHabilidades().get(0));
    }

    @Test
    void testAdicionarHabilidade_TipoIncorreto() {
        criatura.adicionarHabilidade(mockHabilidadeAgua);
        assertTrue(criatura.getHabilidades().isEmpty());
    }

    @Test
    void testAdicionarEfeitoStatus() {
        criatura.adicionarEfeitoStatus(mockEfeitoStatus);
        assertFalse(criatura.getEfeitosAtivos().isEmpty());
        assertEquals(1, criatura.getEfeitosAtivos().size());
        assertEquals(mockEfeitoStatus, criatura.getEfeitosAtivos().get(0));
    }

    @Test
    void testRemoverEfeitoStatus() {
        criatura.adicionarEfeitoStatus(mockEfeitoStatus);
        criatura.removerEfeitoStatus(mockEfeitoStatus);
        assertTrue(criatura.getEfeitosAtivos().isEmpty());
    }

    @Test
    void testAdicionarItem() {
        criatura.adicionarItem(mockItem);
        assertFalse(criatura.getInventario().isEmpty());
        assertEquals(1, criatura.getInventario().size());
        assertEquals(mockItem, criatura.getInventario().get(0));
    }

    @Test
    void testUsarItem_ItemPresente() {
        criatura.adicionarItem(mockItem);
        criatura.usarItem(mockItem, mockAlvo);
        verify(mockItem, times(1)).usar(mockAlvo);
        assertTrue(criatura.getInventario().isEmpty());
    }

    @Test
    void testUsarItem_ItemAusente() {
        criatura.usarItem(mockItem, mockAlvo);
        verify(mockItem, never()).usar(mockAlvo);
        assertTrue(criatura.getInventario().isEmpty());
    }

    @Test
    void testAtacar() {
        criatura.atacar(mockAlvo);
    }

    @Test
    void testReceberDano_VidaPositiva() {
        criatura.receberDano(10);
        assertEquals(90, criatura.getHp());
    }

    @Test
    void testReceberDano_VidaZero() {
        criatura.receberDano(150);
        assertEquals(0, criatura.getHp());
    }

    @Test
    void testReceberDano_JaMorto() {
        criatura.setHp(0);
        criatura.receberDano(10);
        assertEquals(0, criatura.getHp());
    }

    @Test
    void testSetAtaque() {
        criatura.setAtaque(25);
        assertEquals(25, criatura.getAtk());
    }

    @Test
    void testCurar_VidaNaoCheia() {
        criatura.setHp(50);
        criatura.curar(20);
        assertEquals(70, criatura.getHp());
    }

    @Test
    void testCurar_VidaCheia() {
        criatura.setHp(100);
        criatura.curar(20);
        assertEquals(100, criatura.getHp());
    }

    @Test
    void testUsarHabilidade() {
        criatura.adicionarHabilidade(mockHabilidadeFogo);
        criatura.usarHabilidade(mockHabilidadeFogo, mockAlvo);
        verify(mockHabilidadeFogo, times(1)).atacarHabilidade(criatura, mockAlvo);
    }

    @Test
    void testEstaVivo_True() {
        criatura.setHp(1);
        assertTrue(criatura.estaVivo());
    }

    @Test
    void testEstaVivo_False() {
        criatura.setHp(0);
        assertFalse(criatura.estaVivo());
    }

    @Test
    void testEstaVivo_Negativo() {
        criatura.setHp(-10);
        assertFalse(criatura.estaVivo());
    }
}

