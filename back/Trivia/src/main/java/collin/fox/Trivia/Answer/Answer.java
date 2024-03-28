package collin.fox.Trivia.Answer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int AnswerID;
    private String QuestionID;
    private String PlayerID;
    private int GameID;
    private String PlayersAnswer;

    public Answer() {
    }

    public Answer(int answerID, String questionID, String playerID, int gameID, String playersAnswer) {
        AnswerID = answerID;
        QuestionID = questionID;
        PlayerID = playerID;
        GameID = gameID;
        PlayersAnswer = playersAnswer;
    }

    public int getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(int answerID) {
        AnswerID = answerID;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public String getPlayersAnswer() {
        return PlayersAnswer;
    }

    public void setPlayersAnswer(String playersAnswer) {
        PlayersAnswer = playersAnswer;
    }
}
