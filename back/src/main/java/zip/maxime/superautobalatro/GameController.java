package zip.maxime.superautobalatro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping()
    public String getGameState(@RequestParam("sid") String sid, @RequestParam("uid") String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.getGameState(sid, uid);
    }

    @PostMapping("create")
    public String createGame(@RequestParam("uid") String uid, @RequestParam("deflives") Integer deflives){
        String sid = gameService.create(uid, deflives);
        return gameService.getGameState(sid, uid);
    }

    @PostMapping("start")
    public String startGame(@RequestParam("sid") String sid, @RequestParam("uid") String uid, @RequestParam("deck") String deck, @RequestParam("stake") String stake, @RequestParam("seed") String seed){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.start(sid, uid, deck, stake, seed);
    }

    @PostMapping("join")
    public String startGame(@RequestParam("sid") String sid, @RequestParam("uid") String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.playerJoin(sid, uid);
    }

    @PostMapping("kill")
    public String killGame(@RequestParam("sid") String sid, @RequestParam("uid") String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.kill(sid, uid);
    }

    @PostMapping("ante")
    public String postScore(@RequestParam("sid") String sid, @RequestParam("uid") String uid, @RequestParam("score") String score){
        if(!gameService.exist(sid)){
            return "kys";
        }
        gameService.publishScore(sid, uid, score);
        return gameService.getGameState(sid, uid);
    }
}
