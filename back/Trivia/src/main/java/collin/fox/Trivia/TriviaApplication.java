package collin.fox.Trivia;

import collin.fox.Trivia.Logic.QuestionAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.MalformedURLException;


@SpringBootApplication
public class TriviaApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TriviaApplication.class, args);
	}
}
