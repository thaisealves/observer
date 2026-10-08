package observer.app;

import java.util.*;

public abstract class Inscrito implements Observer {
    private String nome;
    private Preferencia preferencia;
    private String ultimaNotificacao;

    public Inscrito(String nome, Preferencia preferencia) {
        this.nome = nome;
        this.preferencia = preferencia;
    }

    public String getNome() {
        return nome;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(Preferencia preferencia) {
        this.preferencia = preferencia;
    }

    public void inscrever(Canal canal) {
        canal.addObserver(this);
    }

    public void desinscrever(Canal canal) {
        canal.deleteObserver(this);
    }

    public String getUltimaNotificacao() {
        return ultimaNotificacao;
    }

    protected void setUltimaNotificacao(String ultimaNotificacao) {
        this.ultimaNotificacao = ultimaNotificacao;
    }

}
