package vote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by ashish on 3/27/2015.
 */
@Component
public interface PollsRepository extends MongoRepository<Polls, String>{

}