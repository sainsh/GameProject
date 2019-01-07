import Common.EnemyControl;
import Common.PlayerControl;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.AttractableComponent;
import com.almasb.fxgl.extra.entity.components.AttractorComponent;
import com.almasb.fxgl.gameplay.rpg.quest.Quest;
import com.almasb.fxgl.gameplay.rpg.quest.QuestObjective;
import com.almasb.fxgl.gameplay.rpg.quest.QuestPane;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.scene.Viewport;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.settings.MenuItem;
import com.almasb.fxgl.ui.InGamePanel;
import com.almasb.fxgl.ui.InGameWindow;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;


public class GameProjectApp extends GameApplication {

    private enum Type {
        PLAYER, ENEMY
    }


    private InGamePanel panel;
    private Entity player;
    private Entity enemy;
    private PlayerControl playerControl;
    private EnemyControl enemyControl;


    @Override
    protected void initSettings(GameSettings gameSettings) {

        gameSettings.setWidth(1440);
        gameSettings.setHeight(900);
        gameSettings.setTitle("New Earth");
        gameSettings.setVersion("0.1");
        gameSettings.setMenuEnabled(true);
       // gameSettings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class)); //use for save/load later



    }


    @Override
    protected void initInput() {
        Input input = getInput(); // get input service

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerControl.left();
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerControl.right();
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerControl.up();
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerControl.down();
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.DOWN);


        input.addAction(new UserAction("Stop Enemy") {
            @Override
            protected void onActionEnd() {
                if (player.hasComponent(AttractorComponent.class)) {

                    player.removeComponent(AttractorComponent.class);
                } else {
                    player.addComponent(new AttractorComponent(150, 1000));
                }
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Open/Close Panel") {
            @Override
            protected void onActionEnd() {
                if (panel.isOpen())
                    panel.close();
                else
                    panel.open();
            }
        }, KeyCode.TAB);

        input.addAction(new UserAction("Open Window") {
            @Override
            protected void onActionEnd() {
                openWindow();
            }
        }, KeyCode.E);


    }


    public void openWindow() {
        // 1. create in-game window
        InGameWindow window = new InGameWindow("Window Title");

        // 2. set properties
        window.setPrefSize(300, 200);
        window.setPosition(400, 300);
        window.setBackgroundColor(Color.LIGHTBLUE);

        // 3. attach to game scene as UI node
        getGameScene().addUINode(window);
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);


    }


    @Override
    protected void initGame() {


        playerControl = new PlayerControl();


        player = Entities.builder()
                .type(Type.PLAYER)
                .at(200, 200)
                .bbox(new HitBox("Player_Body", BoundingShape.box(25, 25)))
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .with(new AttractorComponent(50, 1000))
                .with(playerControl)
                .with(new CollidableComponent(true))
                .build();

        enemyControl = new EnemyControl(player.getPosition());

        enemy = Entities.builder()
                .type(Type.ENEMY)
                .at(600, 600)
                .viewFromNodeWithBBox(getAssetLoader().loadTexture("brick.png", 25, 25))
                .with(new AttractableComponent(25))
                .with(enemyControl)
                .with(new CollidableComponent(true))
                .build();


        getGameWorld().addEntities(player, enemy);


        panel = new InGamePanel();

        Text text = getUIFactory().newText("Hello from Panel");
        text.setTranslateX(50);
        text.setTranslateY(50);
        panel.getChildren().add(text);
        panel.getChildren().add(new QuestPane(300, 300));

        getGameScene().addUINode(panel);

        getGameplay().getQuestManager().addQuest(new Quest("TestQuest", Arrays.asList(
                new QuestObjective("TestObjective", new SimpleIntegerProperty(0), 3)
        )));

        Viewport viewPort = getGameScene().getViewport();
        viewPort.bindToEntity(player,0,0);
        viewPort.setBounds(200,100,1000,700);

    }


    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph




    }


    @Override
    protected void initPhysics() {
        // 3. get physics world and register a collision handler
        // between Type.PLAYER and Type.ENEMY

        PhysicsWorld physics = getPhysicsWorld();

        physics.addCollisionHandler(new CollisionHandler(Type.PLAYER, Type.ENEMY) {
            @Override
            protected void onHitBoxTrigger(Entity player, Entity enemy, HitBox playerBox, HitBox enemyBox) {
                System.out.println(playerBox.getName() + " X " + enemyBox.getName());
            }

            // the order of entities is determined by
            // the order of their types passed into constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                System.out.println("On Collision Begin");
            }

            @Override
            protected void onCollision(Entity player, Entity enemy) {
                System.out.println("On Collision");
                player.translateTowards(new Point2D(enemy.getX()+100,enemy.getY()+100),10);
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity enemy) {
                System.out.println("On Collision End");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
