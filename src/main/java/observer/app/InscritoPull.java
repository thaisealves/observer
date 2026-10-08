package observer.app;

import java.util.*;

public class InscritoPull extends Inscrito {
    private Canal canalNotificado;

    public InscritoPull(String nome, Preferencia preferencia) {
        super(nome, preferencia);
    }

    // Pull: ignora o argumento; consulta o canal só para aplicar a preferência
    @Override
    public void update(Observable o, Object arg) {
        Canal canal = (Canal) o;
        if (getPreferencia().aceita(canal.getUltimoVideo())) {
            this.canalNotificado = canal;
            setUltimaNotificacao("Novo vídeo no canal " + canal.getNome() + ".");
        }
    }

    public Video abrirNotificacao() {
        if (canalNotificado == null) {
            return null;
        }
        return canalNotificado.getUltimoVideo();
    }

}
