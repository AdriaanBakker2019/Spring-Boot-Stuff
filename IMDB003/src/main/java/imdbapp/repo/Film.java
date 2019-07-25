package imdbapp.repo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Film {
    @Id
    String tconst;
    String primaryTitle;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }


    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }
}
