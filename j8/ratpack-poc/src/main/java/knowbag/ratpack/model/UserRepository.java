package knowbag.ratpack.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by feliperojas on 8/30/15.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);
}
