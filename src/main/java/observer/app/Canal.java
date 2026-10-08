package observer.app;

import java.util.*;

public class Canal extends Observable {
    private String nome;
    private List<Video> videos = new ArrayList<>();

    public Canal(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public Video getUltimoVideo() {
        if (videos.isEmpty()) {
            return null;
        }
        return videos.get(videos.size() - 1);
    }

    public void publicarVideo(Video video) {
        videos.add(video);
        setChanged();
        notifyObservers(video);
    }

}
