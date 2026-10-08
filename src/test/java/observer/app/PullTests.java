package observer.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PullTests {

    Canal canal;
    Video comum;
    Video destaque;

    @BeforeEach
    void setUp() {
        canal = new Canal("Canal A");
        comum = new Video("Vlog", false);
        destaque = new Video("Lançamento", true);
    }

    // Consulta ao canal

    @Test
    void canalSemVideosDeveRetornarUltimoVideoNulo() {
        assertNull(canal.getUltimoVideo());
    }

    @Test
    void canalDeveGuardarUltimoVideoPublicado() {
        canal.publicarVideo(comum);
        canal.publicarVideo(destaque);
        assertEquals(destaque, canal.getUltimoVideo());
        assertEquals(2, canal.getVideos().size());
    }

    @Test
    void naoInscritoDeveVerVideosDoCanal() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        canal.publicarVideo(comum);

        assertNull(joao.getUltimaNotificacao());
        assertNull(joao.abrirNotificacao());
        assertEquals(comum, canal.getUltimoVideo());
        assertEquals(1, canal.getVideos().size());
    }

    // Notificação sem o vídeo

    @Test
    void notificacaoPullNaoDeveConterOVideo() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertEquals("Novo vídeo no canal Canal A.", joao.getUltimaNotificacao());
    }

    @Test
    void deveBuscarVideoNoCanalAoAbrirNotificacao() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertEquals(comum, joao.abrirNotificacao());
    }

    @Test
    void abrirSemNotificacaoDeveRetornarNulo() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        assertNull(joao.abrirNotificacao());
    }

    @Test
    void aoAbrirDeveVerOVideoMaisRecenteDoCanal() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        canal.publicarVideo(destaque);
        assertEquals(destaque, joao.abrirNotificacao());
    }

    // Preferências

    @Test
    void naoDeveNotificarInscritoDestaquesSobreVideoComum() {
        InscritoPull joao = new InscritoPull("João", Preferencia.DESTAQUES);
        joao.inscrever(canal);
        canal.publicarVideo(comum);
        assertNull(joao.getUltimaNotificacao());
        assertNull(joao.abrirNotificacao());
    }

    @Test
    void deveNotificarInscritoDestaquesSobreDestaque() {
        InscritoPull joao = new InscritoPull("João", Preferencia.DESTAQUES);
        joao.inscrever(canal);
        canal.publicarVideo(destaque);
        assertEquals("Novo vídeo no canal Canal A.", joao.getUltimaNotificacao());
        assertEquals(destaque, joao.abrirNotificacao());
    }

    @Test
    void naoDeveNotificarInscritoNenhuma() {
        InscritoPull joao = new InscritoPull("João", Preferencia.NENHUMA);
        joao.inscrever(canal);
        canal.publicarVideo(destaque);
        assertNull(joao.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarAposDesinscrever() {
        InscritoPull joao = new InscritoPull("João", Preferencia.TODAS);
        joao.inscrever(canal);
        joao.desinscrever(canal);
        canal.publicarVideo(destaque);
        assertNull(joao.getUltimaNotificacao());
    }

}
