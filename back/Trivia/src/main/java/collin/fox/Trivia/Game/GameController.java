package collin.fox.Trivia.Game;

import collin.fox.Trivia.Player.Player;
import collin.fox.Trivia.Player.PlayerRepository;
import collin.fox.Trivia.Question.Question;
import collin.fox.Trivia.Question.QuestionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    PlayerRepository playerRepository;
    @GetMapping("/all")
    public List<Game> allGames(){
        return (List<Game>) gameRepository.findAll();
    }

    @GetMapping("/start")
    public int startGame(){
        Game temp = new Game();
        temp.setUrl("http://localhost:8080/game/login");
        gameRepository.save(temp);


        return temp.getGameID();
    }

    @GetMapping("/login")
    public String login() throws FileNotFoundException {
        FileReader f = new FileReader("/Users/collinfox/Desktop/trivia/front/login.html");
        Scanner sc = new Scanner(f);
        String htmlString = "";
        while(sc.hasNext()){
            htmlString += sc.nextLine();
        }

        return htmlString;
    }

    @GetMapping("/gameForm.html")
    public ResponseEntity<String> showForm(@RequestParam String gameID, @RequestParam String name) throws IOException {

        List<Question> gameQuestions = questionRepository.getQuestionsByGameID(Integer.valueOf(gameID));

        if(gameQuestions.isEmpty()) return new ResponseEntity<>("No game Questions", HttpStatus.NOT_FOUND);
        if(!gameRepository.existsById(Integer.valueOf(gameID))) return new ResponseEntity<>("This game does not exist", HttpStatus.NOT_FOUND);

        //Add a new player to the game
        Player player = new Player(name, Integer.valueOf(gameID), 0);
        playerRepository.save(player);


        String test = "";

        String p = writeForm(gameQuestions);

        return new ResponseEntity<>(p, HttpStatus.OK);

    }

    @GetMapping("submit.html")
    public String submitted(){
        return "Worked";
    }

    //Write the html form
    public String writeForm(List<Question> gameQuestions) throws IOException {
        FileWriter f = new FileWriter("/Users/collinfox/Desktop/trivia/front/sub.html");
        String htmlBegin = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Login</title>\n" +
                "</head>\n" +
                "<body>";
        htmlBegin += "<form id=\"submitForm\" action=\"submit.html\" method=\"get\">\n";
        int counter = 1;
        for(Question q : gameQuestions){
            htmlBegin += "<label for=\"question" + counter + "\">Question" + counter + ": " + q.getQ() + ":</label><br>\n";
            htmlBegin += "<select id= \"question" + counter + "\" name = \"question" + counter + "\"><br>\n";
            htmlBegin += "<option value =\"" + q.getCorrect() + "\">" + q.getCorrect() + "</option>\n";
            htmlBegin += "<option value =\"" + q.getIncA() + "\">" + q.getIncA() + "</option>\n";
            htmlBegin += "<option value =\"" + q.getIncB() + "\">" + q.getIncB() + "</option>\n";
            htmlBegin += "<option value =\"" + q.getIncC() + "\">" + q.getIncC() + "</option>\n";
            htmlBegin += "</select><br>\n";
            counter++;
        }

        htmlBegin += "<input type =\"submit\">\n";
        htmlBegin += "</form>";
        htmlBegin += "</body>\n" +
                "</html>";

        f.write(htmlBegin);
        f.close();
        return htmlBegin;
    }

    //Delete all questions for games that are finished

}
