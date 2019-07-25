package imdbapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {


    @Override
    List<Film> findAll();



    @Query(value = "select tconst, primaryTitle primary_title from titlebasics where tconst =  'tt0037792'", nativeQuery = true)
        public Film findFilm();

    @Query(value = "select tconst, primaryTitle primary_title from titlebasics " +
            " where tconst in ( select tconst from titleprincipals where nconst = ?1 )", nativeQuery = true)
    public List<Film>  findFilmsBy(String ncode);

}