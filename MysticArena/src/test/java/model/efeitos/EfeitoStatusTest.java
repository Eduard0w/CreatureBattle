package model.efeitos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EfeitoStatusTest {

    private EfeitoStatus efeitoStatus;

    @BeforeEach
    void setUp() {
        efeitoStatus = new EfeitoStatus("Queimadura", 3, 10.0);
    }

    @Test
    void testGetNome() {
        assertEquals("Queimadura", efeitoStatus.getNome());
    }

    @Test
    void testGetDuracao() {
        assertEquals(3, efeitoStatus.getDuracao());
    }

    @Test
    void testDecrementarDuracao() {
        efeitoStatus.decrementarDuracao();
        assertEquals(2, efeitoStatus.getDuracao());
    }

    @Test
    void testGetValorEfeito() {
        assertEquals(10.0, efeitoStatus.getValorEfeito());
    }

    @Test
    void testEstaAtivo() {
        assertTrue(efeitoStatus.estaAtivo());
        efeitoStatus.decrementarDuracao(); // Duracao = 2
        assertTrue(efeitoStatus.estaAtivo());
        efeitoStatus.decrementarDuracao(); // Duracao = 1
        assertTrue(efeitoStatus.estaAtivo());
        efeitoStatus.decrementarDuracao(); // Duracao = 0
        assertFalse(efeitoStatus.estaAtivo());
    }

    @Test
    void testEfeitoStatusComDuracaoZero() {
        EfeitoStatus efeitoZero = new EfeitoStatus("Veneno", 0, 5.0);
        assertFalse(efeitoZero.estaAtivo());
        assertEquals(0, efeitoZero.getDuracao());
    }
}
