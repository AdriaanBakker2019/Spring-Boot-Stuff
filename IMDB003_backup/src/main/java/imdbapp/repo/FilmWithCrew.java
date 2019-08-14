package imdbapp.repo;

import java.util.ArrayList;
import java.util.List;

public class FilmWithCrew {
    Film film;
    String Crewstring = "";

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    List<Actor> Crew;

    public FilmWithCrew(Film aFilm) {
        film = aFilm;
        Crew = new ArrayList<>();
    }

    public String getCrewstring() {
        return Crewstring;
    }

    public void setCrewstring(String crewstring) {
        Crewstring = crewstring;
    }

    public List<Actor> getCrew() {
        return Crew;
    }

    public void setCrew(List<Actor> crew) {
        Crew = crew;
    }

    public void addActor(Actor actor) {
        if (Crewstring.length() == 0) {
            Crewstring += actor.getPrimaryName();
        } else {
            Crewstring += "," + actor.getPrimaryName();
        }


    }
}
