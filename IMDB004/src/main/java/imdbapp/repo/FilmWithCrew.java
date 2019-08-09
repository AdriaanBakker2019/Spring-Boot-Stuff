package imdbapp.repo;

public class FilmWithCrew {
    Film film;
    String crew = "";


    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }



    public FilmWithCrew(Film aFilm) {
        film = aFilm;
    }

    public String getCrew() {
        return crew;
    }



    public void addActor(Actor actor) {
        if (crew.length() == 0) {
            crew += actor.getPrimaryName();
        } else {
            crew += "," + actor.getPrimaryName();
        }


    }
}
