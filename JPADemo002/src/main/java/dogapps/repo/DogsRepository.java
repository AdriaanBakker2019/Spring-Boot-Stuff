package dogapps.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {
    Dog findDogByName(String name);
}