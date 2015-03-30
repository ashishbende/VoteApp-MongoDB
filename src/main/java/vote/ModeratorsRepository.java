package vote;

/**
 * Created by ashish on 3/27/2015.
 */

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ModeratorsRepository extends MongoRepository<Moderators,String>{

}