package collin.fox.Trivia.Answer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Answer {
    @Id
    private int AnswerID;
    private int QuestionID;
    private int PlayerID;
    private int GameID;
    private String PlayersAnswer;

    public Answer() {
    }

    public Answer(int answerID, int questionID, int playerID, int gameID, String playersAnswer) {
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

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(int playerID) {
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
