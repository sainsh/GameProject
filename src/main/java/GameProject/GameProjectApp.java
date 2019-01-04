package GameProject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;


public class GameProjectApp extends GameApplication {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    protected void initSettings(GameSettings gameSettings) {

        gameSettings.setIntroEnabled(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setTitle("RPG");
        gameSettings.setVersion("0.1");
        gameSettings.setHeight(900);
        gameSettings.setWidth(1440);

    }
}
