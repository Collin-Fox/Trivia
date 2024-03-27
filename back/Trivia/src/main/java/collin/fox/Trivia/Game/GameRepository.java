package collin.fox.Trivia.Game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>  {

}
