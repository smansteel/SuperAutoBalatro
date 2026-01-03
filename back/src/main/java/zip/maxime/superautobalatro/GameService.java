package zip.maxime.superautobalatro;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class GameService {

    Map<String, GameState> gameStateMap = new HashMap<>();

    public boolean exist(String sid){
        return gameStateMap.containsKey(sid);
    }

    public String getGameState(String sid, String uid){
        return gameStateMap.get(sid).returnGameInfo(uid);
    }


    public String create(String uid, Integer deflives){
        String sid = UUID.randomUUID().toString();
        gameStateMap.put(sid, new GameState(sid, uid, deflives));
        return sid;
    }

    public String playerJoin(String sid, String uid){
        GameStateAndResponse g = gameStateMap.get(sid).playerJoin(uid);

        gameStateMap.replace(sid, g.gameState);
        return g.response;
    }

    public String publishScore(String sid, String uid, String score){
        GameStateAndResponse g = gameStateMap.get(sid).submitAnteResult(uid, score);
        gameStateMap.replace(sid, g.gameState);
        return g.response;
    }

    public String kill(String sid, String uid){
        if(Objects.equals(gameStateMap.get(sid).getMasterId(), uid)){
            gameStateMap.remove(sid);
        }
        return "ok";
    }

    public String start(String sid, String uid, String deck, String stake, String seed){
        GameStateAndResponse g =gameStateMap.get(sid).gameStart(uid, deck, stake, seed);
        gameStateMap.replace(sid, g.gameState);
        return g.response;
    }

}
