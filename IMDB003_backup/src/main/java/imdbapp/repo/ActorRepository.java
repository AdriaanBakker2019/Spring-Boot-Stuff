package imdbapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {


    @Query(value = "select nconst, primaryName primary_name, primaryProfession primary_profession, birthYear birth_year, knownForTitles known_for_titles"+
            " from namebasics"+
            " where primaryName = ?1 ", nativeQuery = true)
    public List<Actor> findActorsByName(String aPrimaryName);

    @Query(value = "select nconst, primaryName primary_name, primaryProfession primary_profession, birthYear birth_year, knownForTitles known_for_titles"+
            " from namebasics"+
            " where primaryName = ?1 "+
            "and birthYear is not null", nativeQuery = true)
    public List<Actor> findActorsByPrimaryNameEqualsAndBirthYearNotNull(String aPrimaryName);


    @Query(value = "select nconst, primaryName primary_name, primaryProfession primary_profession, birthYear birth_year, knownForTitles known_for_titles"+
            " from namebasics"+
            " where nconst = ?1", nativeQuery = true)
    public Actor findPersonByNconst(String aNconst);

    @Query(value = "select tp.nconst from titleprincipals tp where tp.tconst = ?1 " +
            " and category in ('actor', 'actress', 'self', 'archive_footage')", nativeQuery = true)
    public List<String> findActorKeysByTitleKey(String aTconst);


    @Query(value = "select nconst from titleprincipals where tconst = ?1", nativeQuery =  true)
    public List<String> findActorKeysBy(String tcode);

}