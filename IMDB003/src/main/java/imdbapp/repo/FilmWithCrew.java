package imdbapp.repo;

import java.util.List;

public class FilmPage {
    Film myFilm;
    List<Actor> myActorList;

    public FilmPage(Film myFilm, List<Actor> myActorList) {
        this.myFilm = myFilm;
        this.myActorList = myActorList;
    }

    public Film getMyFilm() {
        return myFilm;
    }

    public void setMyFilm(Film myFilm) {
        this.myFilm = myFilm;
    }

    public List<Actor> getMyActorList() {
        return myActorList;
    }

    public void setMyActorList(List<Actor> myActorList) {
        this.myActorList = myActorList;
    }
}
