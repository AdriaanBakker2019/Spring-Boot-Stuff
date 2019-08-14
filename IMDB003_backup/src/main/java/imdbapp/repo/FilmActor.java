package imdbapp.repo;

public class FilmActor {
    String filmkey;
    String actorkey;

    public FilmActor(String filmkey, String actorkey) {
        this.filmkey = filmkey;
        this.actorkey = actorkey;
    }

    public String getFilmkey() {
        return filmkey;
    }

    public void setFilmkey(String filmkey) {
        this.filmkey = filmkey;
    }

    public String getActorkey() {
        return actorkey;
    }

    public void setActorkey(String actorkey) {
        this.actorkey = actorkey;
    }
}
