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

    @Query(value = "select tconst, primaryTitle primary_title, startYear start_year, genres from titlebasics " +
            " where tconst in ( select tconst from titleprincipals where nconst = ?1 )" +
            " and titleType in ('short','movie','tvShort', 'tvMovie')" +
            " order by start_year desc, primary_title", nativeQuery = true)
    public List<Film>  findFilmsBy(String ncode);

    @Query(value = "select tconst from titleprincipals where nconst = ?1" +
                   " and tcode in (select tcode from titleprincipals, titlebasics where nconst = ?2 " +
                   " and titleType in ('short','movie','tvShort', 'tvMovie'))",
                   nativeQuery = true)
    public List<Film>  findTitlesInCommon(String ncode1, String ncode2);
}