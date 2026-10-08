package observer.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PushTests {

    Canal canal;
    Video comum;
    Video destaque;

    @BeforeEach
    void setUp() {
        canal = new Canal("Canal A");
        comum = new Video("Vlog", false);
        destaque = new Video("Lançamento", true);
    }

    // Preferência TODAS

    @Test
    void deveNotificarInscritoTodasSobreVideoComum() {
        InscritoPush joao = new InscritoPush("João", Preferencia.TODAS);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertEquals("Novo vídeo no canal Canal A: Vlog.", joao.getUltimaNotificacao());
    }

    @Test
    void deveNotificarInscritoTodasSobreDestaque() {
        InscritoPush joao = new InscritoPush("João", Preferencia.TODAS);
        joao.inscrever(canal);
        canal.publicarVideo(destaque);
        assertEquals("Novo vídeo no canal Canal A: Lançamento.", joao.getUltimaNotificacao());
    }

    // Preferência DESTAQUES

    @Test
    void naoDeveNotificarInscritoDestaquesSobreVideoComum() {
        InscritoPush joao = new InscritoPush("João", Preferencia.DESTAQUES);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertNull(joao.getUltimaNotificacao());
    }

    @Test
    void deveNotificarInscritoDestaquesSobreDestaque() {
        InscritoPush joao = new InscritoPush("João", Preferencia.DESTAQUES);
        joao.inscrever(canal);
        canal.publicarVideo(destaque);
        assertEquals("Novo vídeo no canal Canal A: Lançamento.", joao.getUltimaNotificacao());
    }

    @Test
    void inscritoDestaquesDeveManterUltimoDestaqueAposVideoComum() {
        InscritoPush joao = new InscritoPush("João", Preferencia.DESTAQUES);
        joao.inscrever(canal);
        canal.publicarVideo(destaque);
        canal.publicarVideo(comum);
        assertEquals("Novo vídeo no canal Canal A: Lançamento.", joao.getUltimaNotificacao());
    }

    // Preferência NENHUMA

    @Test
    void naoDeveNotificarInscritoNenhuma() {
        InscritoPush joao = new InscritoPush("João", Preferencia.NENHUMA);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        canal.publicarVideo(destaque);
        assertNull(joao.getUltimaNotificacao());
    }

    @Test
    void deveRespeitarMudancaDePreferencia() {
        InscritoPush joao = new InscritoPush("João", Preferencia.NENHUMA);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertNull(joao.getUltimaNotificacao());

        joao.setPreferencia(Preferencia.TODAS);
        canal.publicarVideo(comum);
        assertEquals("Novo vídeo no canal Canal A: Vlog.", joao.getUltimaNotificacao());
    }

    // Registro de observers

    @Test
    void naoDeveNotificarQuemNaoEstaInscrito() {
        InscritoPush joao = new InscritoPush("João", Preferencia.TODAS);
        canal.publicarVideo(destaque);
        assertNull(joao.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarAposDesinscrever() {
        InscritoPush joao = new InscritoPush("João", Preferencia.TODAS);
        joao.inscrever(canal);
        assertEquals(1, canal.countObservers());

        joao.desinscrever(canal);
        assertEquals(0, canal.countObservers());
        canal.publicarVideo(destaque);
        assertNull(joao.getUltimaNotificacao());
    }

    @Test
    void deveNotificarCadaInscritoConformeSuaPreferencia() {
        InscritoPush todas = new InscritoPush("Ana", Preferencia.TODAS);
        InscritoPush destaques = new InscritoPush("Bia", Preferencia.DESTAQUES);
        InscritoPush nenhuma = new InscritoPush("Caio", Preferencia.NENHUMA);
        todas.inscrever(canal);
        destaques.inscrever(canal);
        nenhuma.inscrever(canal);

        canal.publicarVideo(comum);

        assertEquals("Novo vídeo no canal Canal A: Vlog.", todas.getUltimaNotificacao());
        assertNull(destaques.getUltimaNotificacao());
        assertNull(nenhuma.getUltimaNotificacao());
    }

    @Test
    void deveNotificarSomenteInscritosDoCanalQuePublicou() {
        Canal canalB = new Canal("Canal B");
        InscritoPush joao = new InscritoPush("João", Preferencia.TODAS);
        InscritoPush maria = new InscritoPush("Maria", Preferencia.TODAS);
        joao.inscrever(canal);
        maria.inscrever(canalB);

        canal.publicarVideo(comum);

        assertEquals("Novo vídeo no canal Canal A: Vlog.", joao.getUltimaNotificacao());
        assertNull(maria.getUltimaNotificacao());
    }

}
