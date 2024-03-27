package collin.fox.Trivia.Question;

import collin.fox.Trivia.Game.GameRepository;
import collin.fox.Trivia.Logic.QuestionAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/q")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/all")
    public List<Question> allQuestions(){
        return (List<Question>) questionRepository.findAll();
    }

    @GetMapping("/game/{id}")
    public String setGameQuestions(@PathVariable("id") int id) throws IOException {
        if(!gameRepository.existsById(id)) return "Game does not exist";
        //Getting a question api object
        QuestionAPI tempApi = new QuestionAPI();
        //List of 10 questions
        ArrayList<Question> gameQuestions;
        //json string of questions
        String json = tempApi.getQuestion();
        //turning questions into Question objects
        gameQuestions = tempApi.crap(json);

        //Setting each question with the gameID
        for(Question q: gameQuestions){
            q.setGameID(id);
            questionRepository.save(q);
        }


        return "Worked";

    }

    @GetMapping("/gameByID/{id}")
    public List<Question> getGameQuestionsByID(@PathVariable("id") int id){
        List<Question> myList = questionRepository.getQuestionsByGameID(id);

        return myList;

    }
}
