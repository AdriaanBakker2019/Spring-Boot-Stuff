package imdbapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {


    @Override
    List<Film> findAll();



   @Query(value = "select tconst, primaryTitle primary_title, genres genres, startYear start_year, titleType title_type " +
            " from titlebasics where tconst =  ?1 ", nativeQuery = true)
   public Film findFilmByTconst(String tconst);



    @Query(value = "select tconst from titleprincipals where nconst =  ?1 ", nativeQuery = true)
    public List<String> findFilmKeysByActorKey(String nconst);


    @Query(value = "select tconst, primaryTitle primary_title, startYear start_year, genres, titleType title_type from titlebasics " +
            " where tconst in ( select tconst from titleprincipals where nconst = ?1 )" +
            " and titleType in ('short','movie','tvShort', 'tvMovie')" +
            " order by start_year desc, primary_title", nativeQuery = true)
    public List<Film> findFilmsByNconst(String ncode);


    @Query(value = "select ncode from titleprincipals where nconst = ?1ß", nativeQuery =  true)
    public List<String> findActorKeysBy(String tcode);

    @Query(value = "select tconst, primaryTitle primary_title, startYear start_year, genres, titleType title_type from titlebasics " +
            " where tconst in ( select tconst from titleprincipals where primaryTitle = ?1 )" +
            " and titleType in ('short','movie','tvShort', 'tvMovie')" +
            " order by start_year desc, primary_title", nativeQuery = true)
    List<Film> findFilmsByPrimaryTitle(String primaryTitle);



}