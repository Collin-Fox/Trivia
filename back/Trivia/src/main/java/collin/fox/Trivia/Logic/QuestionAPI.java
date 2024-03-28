package collin.fox.Trivia.Logic;

import collin.fox.Trivia.Question.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.apache.tomcat.util.json.JSONParser;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;


public class QuestionAPI {

    public QuestionAPI() throws MalformedURLException {
    }

    public String getScript(String Question, String a, String b, String c, String d){
        String prompt = "Question: " + Question + " Answers: " + a + ", " + b + ", " + c + ", " + d;
        prompt += "\n This is a question in a trivia game im playing. Can you write a script as if you are a trivia host to ask this question and give the options" +
                "you should not reveal the correct answer";


        return prompt;
    }

    public String getQuestion() throws IOException {
        URL url = new URL("https://the-trivia-api.com/v2/questions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        int status = connection.getResponseCode();
        Question q = new Question();
        connection.disconnect();

       return content.toString();
    }


    public ArrayList<Question> crap(String entry) throws IOException {
        String jsonString = entry;
        JSONArray jArray = new JSONArray(jsonString);
        String answerString = "";
        ArrayList<Question> gameQuestions = new ArrayList<>();
        for(Object o : jArray){
            Question temp = new Question();
            JSONObject obj = (JSONObject) o;
            String category = obj.getString("category");
            String answer = obj.getString("correctAnswer");
            answer = answer.replaceAll("\"", "");
            JSONArray incArray = obj.getJSONArray("incorrectAnswers");
            JSONObject question = obj.getJSONObject("question");
            String id = obj.getString("id");
            String ques = question.getString("text");
            ques = ques.replaceAll("\"", "");
            String a = incArray.getString(0);
            String b = incArray.getString(1);
            String c = incArray.getString(2);
            a = a.replaceAll("\"", "");
            b = b.replaceAll("\"", "");
            c = c.replaceAll("\"", "");

            String script = getScript(ques, answer, a, b, c);
            //  JSONArray incorrectList = obj.getJSONArray("incorrectAnswers");

            temp.setCorrect(answer);
            temp.setQuestionID(id);
            temp.setIncA(a);
            temp.setIncB(b);
            temp.setIncC(c);
            temp.setQ(ques);
            temp.setScript("Test");
            temp.setGameID(-1);
            answerString += ques + "\n";
            answerString += "Correct: " + answer + "\n";
            answerString += a + "\n" + b + "\n" + c + "\n";
            answerString += "\n\n\n";

            gameQuestions.add(temp);
        }

        return gameQuestions;
    }
}
