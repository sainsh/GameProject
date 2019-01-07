import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.AttractableComponent;
import com.almasb.fxgl.extra.entity.components.AttractorComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;


public class GameProjectApp extends GameApplication {


    @Override
    protected void initSettings(GameSettings gameSettings) {

        gameSettings.setWidth(1440);
        gameSettings.setHeight(900);
        gameSettings.setTitle("New Earth");
        gameSettings.setVersion("0.1");

    }

    private enum Type {
        PLAYER, ENEMY
    }


    @Override
    protected void initInput() {
        Input input = getInput(); // get input service

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5); // move right 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5); // move left 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5); // move up 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5); // move down 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.DOWN);


        input.addAction(new UserAction("Stop Enemy") {
            @Override
            protected void onAction() {
                player.removeComponent(AttractorComponent.class);
            }
        }, KeyCode.F);


    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);


    }

    private Entity player;
    private Entity enemy;


    @Override
    protected void initGame() {
        player = Entities.builder()
                .type(Type.PLAYER)
                .at(300, 300)
                .bbox(new HitBox("Player Body", BoundingShape.box(25, 25)))
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .with(new AttractorComponent(150, 1000))
                .build();

        enemy = Entities.builder()
                .type(Type.ENEMY)
                .at(600, 600)
                .viewFromNodeWithBBox(getAssetLoader().loadTexture("brick.png", 25, 25))
                .with(new AttractableComponent(25))
                .build();


        player.addComponent(new CollidableComponent(true));
        enemy.addComponent(new CollidableComponent(true));

        getGameWorld().addEntities(player, enemy);

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
