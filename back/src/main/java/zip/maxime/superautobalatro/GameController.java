package zip.maxime.superautobalatro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("{sid}")
    public String getGameState(@PathVariable String sid, @RequestBody String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.getGameState(sid, uid);
    }

    @PostMapping("create/{deflives}")
    public String createGame(@RequestBody String uid, @PathVariable Integer deflives){
        String sid = gameService.create(uid, deflives);
        return gameService.getGameState(sid, uid);
    }

    @PostMapping("{sid}/start/{deck}/{stake}/{seed}")
    public String startGame(@PathVariable String sid, @RequestBody String uid, @PathVariable String deck, @PathVariable String stake, @PathVariable String seed){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.start(sid, uid, deck, stake, seed);
    }

    @PostMapping("{sid}/join")
    public String startGame(@PathVariable String sid, @RequestBody String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.playerJoin(sid, uid);
    }

    @PostMapping("{sid}/kill")
    public String killGame(@PathVariable String sid, @RequestBody String uid){
        if(!gameService.exist(sid)){
            return "kys";
        }
        return gameService.kill(sid, uid);
    }

    @PostMapping("{sid}/ante/{score}")
    public String postScore(@PathVariable String sid, @RequestBody String uid, @PathVariable String score){
        if(!gameService.exist(sid)){
            return "kys";
        }
        gameService.publishScore(sid, uid, score);
        return gameService.getGameState(sid, uid);
    }
}
