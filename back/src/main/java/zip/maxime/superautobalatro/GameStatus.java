package zip.maxime.superautobalatro;

import lombok.Getter;

public enum GameStatus{
    LOBBY("lobby"), PLAY_FOR_ANTE("ante");

    @Getter
    private String apiName;
    GameStatus(String apiName) {
        this.apiName = apiName;
    }
}
