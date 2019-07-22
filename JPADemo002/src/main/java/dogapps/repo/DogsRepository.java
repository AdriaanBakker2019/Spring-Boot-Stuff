package dogapps.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {
    Dog findDogByName(String name);

    @Query(value = "select id, name, age from dog where age > 10 order by name", nativeQuery = true)
    List<Dog> findOldDogs();
}