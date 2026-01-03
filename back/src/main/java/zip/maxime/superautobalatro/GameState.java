package zip.maxime.superautobalatro;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;


public class GameState {
    private final int LIVES_LOST = 1;
    private record GameStatusAndAnte(GameStatus status, int ante){  }


    @Getter
    private String sessionId;

    @Getter
    private String masterId;

    @Getter
    private int defLives = -1;

    @Getter
    private GameStatusAndAnte current = new GameStatusAndAnte(GameStatus.LOBBY, 0);

    @Getter
    private String stake;

    @Getter
    private String deck;

    @Getter
    private String seed;

    @Getter
    private Map<String, Integer> playerList;

    @Getter
    private Map<String, BigDecimal> currentAnteScores;

    private String getPlayerAndLives(){
        return String.join(",", playerList.entrySet().stream().map(p -> p.getKey() +";"+p.getValue()).toList());
    }

    private String formatCurrent(){
        return getCurrent().status.getApiName() + ";"+ getCurrent().ante;
    }

    public GameState(String sessionId, String masterId, int defLives) {
        this.sessionId = sessionId;
        this.masterId = masterId;
        this.defLives = defLives;
        this.playerList = new HashMap<>();
        this.playerList.put(masterId, defLives);

    }

    public String returnGameInfo(String userId){
        return String.format("""
%s
%s
%b
%s
%s
%s
%s
""", sessionId,
                formatCurrent(),
                Objects.equals(userId, masterId),
                getPlayerAndLives(),
                stake,
                deck,
                seed
        );
    }

    public GameStateAndResponse playerJoin(String userId){
        if(Objects.equals(userId, masterId)){
            return new GameStateAndResponse(this, "kys");
        }
        if(playerList.entrySet().stream().noneMatch(p -> Objects.equals(p.getKey(), userId))){
            playerList.put(userId, defLives);
            return new GameStateAndResponse(this, returnGameInfo(userId));
        }
        return new GameStateAndResponse(this, "kys");
    }

    public GameStateAndResponse gameStart(String userId, String deckId, String stakeId, String seedSrc){
        if(!Objects.equals(userId, masterId)){
            return new GameStateAndResponse(this, "kys");
        }
        this.current = new GameStatusAndAnte(GameStatus.PLAY_FOR_ANTE, 1);
        deck = deckId;
        stake = stakeId;
        seed = seedSrc;
        currentAnteScores = new HashMap<>();
        return new GameStateAndResponse(this,  returnGameInfo(userId));
    }

    public GameStateAndResponse submitAnteResult(String userId, String score){
        currentAnteScores.put(userId, new BigDecimal(score));
        if(currentAnteScores.size() != playerList.size()){
            return new GameStateAndResponse(this,  returnGameInfo(userId));
        }
        Map.Entry<String, BigDecimal> lowest = null;
        for (Map.Entry<String, BigDecimal> playerScore : currentAnteScores.entrySet()){
            if(lowest == null || lowest.getValue().compareTo(playerScore.getValue())< 0 ){
                lowest = playerScore;
            }
        }
        playerList.replace(lowest.getKey(), playerList.get(lowest.getKey())-LIVES_LOST);
        this.current = new GameStatusAndAnte(GameStatus.PLAY_FOR_ANTE, current.ante+1);
        return new GameStateAndResponse(this,  returnGameInfo(userId));
    }


}
