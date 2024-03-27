package collin.fox.Trivia.Question;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {

    @Query(value = "SELECT * from question where ?1 = gameid", nativeQuery = true)
    List<Question> getQuestionsByGameID(int GameID);
}
