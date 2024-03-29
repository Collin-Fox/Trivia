package collin.fox.Trivia.Logic;

import collin.fox.Trivia.Player.Player;
import collin.fox.Trivia.Player.PlayerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Logic {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/try")
    public List<Player> getAllPlayers(){
        return (List<Player>) playerRepository.findAll();
    }


    @GetMapping(value = "/login/{name}/game/{game}")
    public String login(HttpServletResponse response, @PathVariable("name") String name, @PathVariable("game") int game){

        //SAVE THE COOKIES
        response.addCookie(new Cookie("username", name));
        response.addCookie(new Cookie("gameID", String.valueOf(game)));

        Player p = new Player();
        p.setScore(0);
        p.setGameID(game);
        p.setPlayerID(name);

        //SAVE THE PLAYER
        playerRepository.save(p);





        return "OK";
    }

    @PostMapping(value = "/killMe", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void help(@RequestBody final Player p, HttpServletResponse response){

        //SAVE THE COOKIES
        //response.addCookie(new Cookie("username", p.getPlayerID()));
       // response.addCookie(new Cookie("gameID", String.valueOf(p.getGameID())));
        playerRepository.save(p);
    }
}
