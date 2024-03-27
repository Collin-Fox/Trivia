package collin.fox.Trivia.Game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.annotation.processing.Generated;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int GameID;
    private boolean isDone;
    private String url;

    public Game() {
        isDone = false;
    }

    public Game(boolean isDone) {
        this.isDone = isDone;
    }

    public Game(int gameID, boolean isDone) {
        GameID = gameID;
        this.isDone = isDone;
    }


    public Game(int gameID, boolean isDone, String url) {
        GameID = gameID;
        this.isDone = isDone;
        this.url = url;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
