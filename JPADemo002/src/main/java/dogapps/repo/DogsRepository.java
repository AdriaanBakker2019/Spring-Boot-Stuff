package dogapps.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogsRepository extends CrudRepository<Dog, Long> {
    Dog findDogByName(String name);
}