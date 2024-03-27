package collin.fox.Trivia.Question;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    private String QuestionID;
    private int GameID;
    private String Q;
    private String Correct;
    private String incA;
    private String incB;
    private String incC;
    private String script;

    public Question() {
    }
    public Question(String questionID, int gameID, String q, String correct, String incA, String incB, String incC, String script) {
        QuestionID = questionID;
        GameID = gameID;
        Q = q;
        Correct = correct;
        this.incA = incA;
        this.incB = incB;
        this.incC = incC;
        this.script = script;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String correct) {
        Correct = correct;
    }

    public String getIncA() {
        return incA;
    }

    public void setIncA(String incA) {
        this.incA = incA;
    }

    public String getIncB() {
        return incB;
    }

    public void setIncB(String incB) {
        this.incB = incB;
    }

    public String getIncC() {
        return incC;
    }

    public void setIncC(String incC) {
        this.incC = incC;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
