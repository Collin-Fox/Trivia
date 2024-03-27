package collin.fox.Trivia.Player;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    private String PlayerID;
    private int GameID;
    private int Score;

    public Player() {
    }

    public Player(String playerID, int gameID, int score) {
        PlayerID = playerID;
        GameID = gameID;
        Score = score;
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

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
