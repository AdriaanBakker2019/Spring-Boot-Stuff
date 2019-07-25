package imdbapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {


    @Override
    List<Actor> findAll();

    @Query(value = "select nconst, primaryName primary_name, primaryProfession primary_profession, birthYear birth_year, knownForTitles known_for_titles"+
            " from namebasics"+
            " where primaryName = ?1 "+
            " and birthYear is not null" +
            " and primaryProfession like '%actor%'", nativeQuery = true)
    public List<Actor> findActorsByName(String aName);
}