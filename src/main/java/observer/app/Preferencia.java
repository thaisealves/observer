package observer.app;

public enum Preferencia {
    TODAS {
        @Override
        public boolean aceita(Video video) {
            return true;
        }
    },
    DESTAQUES {
        @Override
        public boolean aceita(Video video) {
            return video.isDestaque();
        }
    },
    NENHUMA {
        @Override
        public boolean aceita(Video video) {
            return false;
        }
    };

    public abstract boolean aceita(Video video);
}
