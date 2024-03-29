package collin.fox.Trivia.Game;

import collin.fox.Trivia.Answer.Answer;
import collin.fox.Trivia.Answer.AnswerRepository;
import collin.fox.Trivia.Player.Player;
import collin.fox.Trivia.Player.PlayerRepository;
import collin.fox.Trivia.Question.Question;
import collin.fox.Trivia.Question.QuestionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    AnswerRepository answerRepository;



    @GetMapping("/all")
    public List<Game> allGames(){
        return (List<Game>) gameRepository.findAll();
    }

    /***
     *
     * @return Sets up a new game object and generates a game id. Returns a game id to the user
     *
     */
    @GetMapping("/start")
    public int startGame(){
        Game temp = new Game();
        temp.setUrl("https://b3f8-2600-387-f-6a19-00-5.ngrok-free.app/game/login");
        gameRepository.save(temp);


        return temp.getGameID();
    }

    /***
     *
     * @return the login page for the user to create a name and enter the game id
     * TODO: Learn HTTP session to submit entries per user
     * @throws FileNotFoundException
     */
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

    /***
     *
     * @return link to the login page
     */
    @GetMapping("/link")
    public String link(){
        return "https://b3f8-2600-387-f-6a19-00-5.ngrok-free.app/game/login";
    }

    /***
     *
     * @param gameID
     * @param name
     * @return Shows the HTML form for the questions
     * @throws IOException
     */
    @GetMapping("/gameForm.html")
    public ResponseEntity<String> showForm(@RequestParam String gameID, @RequestParam String name, HttpServletResponse response) throws IOException {

        response.addCookie(new Cookie("username", name));
        response.addCookie(new Cookie("gameID", gameID));

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

    /***
     *
     * @return Handles submission
     * TODO: Need to grade each player return as a 'Scoreboard'
     */
    @GetMapping("submit.html")
    public String submitted(@RequestParam String question1, @RequestParam String question2,
                            @RequestParam String question3, @RequestParam String question4,
                            @RequestParam String question5, @RequestParam String question6,
                            @RequestParam String question7, @RequestParam String question8,
                            @RequestParam String question9, @RequestParam String question10,
                            @CookieValue("username") String username, @CookieValue("gameID") int gameID){
        String[] answerArray = {question1, question2, question3, question4, question5, question6, question7,
        question8, question9, question10};

        //for every player answer
        for(String q : answerArray){
            //Get the question
            Optional<List<Question>> tmp = questionRepository.getQuestionByAnswer(q);
            List<Question> temp = tmp.get();

            //If it found the question
            if(!temp.isEmpty()){
                Question question = temp.get(0);

                //Build the answer
                Answer answer = new Answer();
                answer.setQuestionID(question.getQuestionID());
                answer.setGameID(gameID);
                answer.setPlayerID(username);
                answer.setPlayersAnswer(q);

                //save the answer
                answerRepository.save(answer);

            }else{
                return "SOMETHING WENT WRONG LOL";
            }
        }

        String s = scoreUserGame(username, gameID);


        return s;
    }

    //Write the html form for the game questions

    /***
     *
     * @param gameQuestions
     * @return HTML FILE
     * @throws IOException
     */
    public String writeForm(List<Question> gameQuestions) throws IOException {
        FileWriter f = new FileWriter("/Users/collinfox/Desktop/trivia/front/sub.html");
        String htmlBegin = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Login</title>\n" +
                "<link rel=\"stylesheet\" href=\"test.css\">"+
                "</head>\n" +
                "<body>";
        htmlBegin += "<form id=\"submitForm\" action=\"submit.html\" method=\"get\">\n";
        int counter = 1;


        Random rand = new Random();
        List<String> givenList = new ArrayList<>();



        for(Question q : gameQuestions){
            givenList.add(q.getCorrect());
            givenList.add(q.getIncA()); givenList.add(q.getIncB()); givenList.add(q.getIncC());
            String[] rnd = new String[4];

            int numberOfElements = 4;

            for (int i = 0; i < numberOfElements; i++) {
                int randomIndex = rand.nextInt(givenList.size());
                String randomElement = givenList.get(randomIndex);
                givenList.remove(randomIndex);
                rnd[i] = randomElement;
            }
            htmlBegin += "<label for=\"question" + counter + "\">Question" + counter + ": " + ":</label><br>\n";
            htmlBegin += "<select id= \"question" + counter + "\" name = \"question" + counter + "\"><br>\n";
            htmlBegin += "<option value =\"" + rnd[0] + "\">" + rnd[0] + "</option>\n";
            htmlBegin += "<option value =\"" + rnd[1] + "\">" + rnd[1] + "</option>\n";
            htmlBegin += "<option value =\"" + rnd[2] + "\">" + rnd[2] + "</option>\n";
            htmlBegin += "<option value =\"" + rnd[3] + "\">" + rnd[3] + "</option>\n";
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


    @GetMapping("/scoreUser")
    public String scoreUserGame(@CookieValue("username") String username, @CookieValue("gameID") int gameID) {
        int score = 0;
        //Need to get the player
        String scoreBoard = "";
        if (!playerRepository.existsById(username)) return "PLAYER " + username + " DOES NOT EXIST";
        Player p = playerRepository.findById(username).get();

        //Display the users name on the scoreboard
        scoreBoard += "PLAYER: " + username + "<br>";
        //Get a list of all the questions
        ArrayList<Question> gameQuestions = (ArrayList<Question>) questionRepository.getQuestionsByGameID(gameID);
        if (gameQuestions.isEmpty()) return "THIS GAME HAS NO ANSWERS";

        for (Question q : gameQuestions) {
            //Get the players answer
            Optional<Answer> answer = answerRepository.findAnswerByPlayerIDAndQuestionID(username, q.getQuestionID());
            //If it exists...
            if (answer.isEmpty()) return "THIS QUESTION WAS NEVER ANSWERED SOMEHOW " + q.getQ();
            Answer a = answer.get();
            //Show the question plus the correct answer
            scoreBoard += "QUESTION: " + q.getQ() + "<br>" + "CORRECT ANSWER: " + q.getCorrect() + "<br>";

            //if the player was right
            if (a.getPlayersAnswer().equals(q.getCorrect())) {
                // Increase the players score
                score += 20;

            }
            scoreBoard += "YOUR ANSWER: " + a.getPlayersAnswer() + "<br>";

            scoreBoard += "<br> + <br>";


        }

        //Update the players score
        p.setScore(score);
        playerRepository.save(p);

        scoreBoard += "TOTAL SCORE: " + score + "<br>";
        return scoreBoard;
    }

    @GetMapping("/playerscore/{gameID}")
    public List<Player> getPlayersList(@PathVariable("gameID") int gameID){
        return playerRepository.getPlayersByGame(gameID);
    }

    @GetMapping("/isDone")
    public String isDone(@CookieValue("gameID") int gameID){
        List<Player> playerList = playerRepository.getPlayersByNotDone(gameID);

        String check = (playerList.isEmpty()) ? ("DONE") : ("STILL WAITING ON ");

        for(Player p: playerList){
            check += p.getPlayerID() + " ";
        }

        return check;
    }
    //Delete all questions for games that are finished

}
