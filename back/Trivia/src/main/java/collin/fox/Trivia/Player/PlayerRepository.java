package collin.fox.Trivia.Player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {
    @Query(value = "select * from player where gameid = ?1", nativeQuery = true)
    List<Player> getPlayersByGame(int gameID);

    @Query(value = "select * from player where gameid = ?1 and is_done = false", nativeQuery = true)
    List<Player> getPlayersByNotDone(int gameID);
}
