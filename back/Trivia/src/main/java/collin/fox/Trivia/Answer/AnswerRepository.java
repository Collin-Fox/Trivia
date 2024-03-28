package collin.fox.Trivia.Answer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {

    @Query(value = "select * from answer where playerid = ?1 and gameid = ?2", nativeQuery = true)
    List<Answer> findAllByPlayerIDAndGameID(String PlayerID, int GameID);

    @Query(value = "select * from answer where playerid = ?1 and questionid = ?2", nativeQuery = true)
    Optional<Answer> findAnswerByPlayerIDAndQuestionID(String PlayerID, String QuestionID);
}
