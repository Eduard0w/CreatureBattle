package model.itens;

import model.Criatura;
import model.itens.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemTest {

    @Mock
    private Criatura mockCriatura;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUsarCura_ComSucesso() {
        Item pocaoCura = new Item("Pocao de Cura", "cura", 20);
        when(mockCriatura.getHp()).thenReturn(50.0);
        when(mockCriatura.getMaxHp()).thenReturn(100.0);

        assertTrue(pocaoCura.usar(mockCriatura));
        verify(mockCriatura, times(1)).curar(20);
    }

    @Test
    void testUsarCura_HpCheio() {
        Item pocaoCura = new Item("Pocao de Cura", "cura", 20);
        when(mockCriatura.getHp()).thenReturn(100.0);
        when(mockCriatura.getMaxHp()).thenReturn(100.0);

        assertFalse(pocaoCura.usar(mockCriatura));
        verify(mockCriatura, never()).curar(anyInt());
    }

    @Test
    void testUsarAtaque() {
        Item pocaoForca = new Item("Pocao de Forca", "ataque", 10);
        when(mockCriatura.getAtk()).thenReturn(50.0);

        assertTrue(pocaoForca.usar(mockCriatura));
        verify(mockCriatura, times(1)).setAtaque(60.0);
    }

    @Test
    void testUsarLimpar_ComEfeitos() {
        Item antidoto = new Item("Antidoto", "limpar", 0);
        java.util.List<model.efeitos.EfeitoStatus> mockList = mock(java.util.List.class);
        when(mockCriatura.getEfeitosAtivos()).thenReturn(mockList);
        when(mockCriatura.getEfeitosAtivos().isEmpty()).thenReturn(false);

        assertTrue(antidoto.usar(mockCriatura));
        verify(mockCriatura.getEfeitosAtivos(), times(1)).clear();
    }

    @Test
    void testUsarLimpar_SemEfeitos() {
        Item antidoto = new Item("Antidoto", "limpar", 0);
        java.util.List<model.efeitos.EfeitoStatus> mockList = mock(java.util.List.class);
        when(mockCriatura.getEfeitosAtivos()).thenReturn(mockList);
        when(mockCriatura.getEfeitosAtivos().isEmpty()).thenReturn(true);

        assertFalse(antidoto.usar(mockCriatura));
        verify(mockCriatura.getEfeitosAtivos(), never()).clear();
    }

    @Test
    void testUsarEfeitoNaoImplementado() {
        Item itemDesconhecido = new Item("Item Desconhecido", "desconhecido", 0);

        assertFalse(itemDesconhecido.usar(mockCriatura));
        verify(mockCriatura, never()).curar(anyInt());
        verify(mockCriatura, never()).setAtaque(anyDouble());
        verify(mockCriatura, never()).getEfeitosAtivos();
    }
}
