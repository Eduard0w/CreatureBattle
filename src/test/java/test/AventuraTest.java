package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import model.Criatura;
import model.Jogador;
import model.TipoElemental;
import model.personagens.MiniDragao;
import service.Aventura;
import service.BatalhaService;
import service.Loja;

@RunWith(MockitoJUnitRunner.class)
public class AventuraTest {

	@Mock
    private Jogador mockJogador;
    @Mock
    private BatalhaService mockBatalhaService;
    @Mock
    private Criatura mockCriaturaJogador;
    
   
    @Mock
    private Loja mockLoja;

    @InjectMocks
    private Aventura aventura;

    @BeforeEach
    public void setUp() {
    	when(mockJogador.getCriatura()).thenReturn(new MiniDragao(TipoElemental.FOGO));
        aventura = new Aventura(mockJogador, mockBatalhaService);
    }
    
    @Test
    public void jogador1DevePerder() {
    	when(mockJogador.aindaPodeLutar()).thenReturn(true, true, true, true, true, true, false);
    	
    	aventura.iniciar();
    	
    	assertEquals(3, aventura.getInimigosDerrotados());
    }
}
