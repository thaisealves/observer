package observer.app;

import java.util.*;

public class InscritoPush extends Inscrito {

    public InscritoPush(String nome, Preferencia preferencia) {
        super(nome, preferencia);
    }

    // Push: o vídeo chega no argumento e já entra na notificação
    @Override
    public void update(Observable o, Object arg) {
        Canal canal = (Canal) o;
        Video video = (Video) arg;
        if (getPreferencia().aceita(video)) {
            setUltimaNotificacao("Novo vídeo no canal " + canal.getNome()
                    + ": " + video.getTitulo() + ".");
        }
    }

}
