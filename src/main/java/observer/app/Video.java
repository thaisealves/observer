package observer.app;

public class Video {
    private String titulo;
    private boolean destaque;

    public Video(String titulo, boolean destaque) {
        this.titulo = titulo;
        this.destaque = destaque;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDestaque() {
        return destaque;
    }

}
