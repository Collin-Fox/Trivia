package collin.fox.Trivia.Question;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {

    @Query(value = "SELECT * from question where ?1 = gameid", nativeQuery = true)
    List<Question> getQuestionsByGameID(int GameID);

    @Query(value = "select * from question where inca = ?1 or incb = ?1 or incc = ?1 or correct = ?1", nativeQuery = true)
    Optional<Question> getQuestionByAnswer(String answer);


}
