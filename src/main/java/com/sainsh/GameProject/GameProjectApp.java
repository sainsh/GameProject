package com.sainsh.GameProject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class GameProjectApp extends GameApplication {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setIntroEnabled(false);
        gameSettings.setMenuEnabled(false);
    }
}
