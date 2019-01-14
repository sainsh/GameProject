import Common.Config;
import Components.EnemyTypes.Brute;
import Components.EnemyTypes.Enemy;
import Components.Equipment.Equipment;
import Components.Player;
import Components.PlayerControl;
import Components.ProfessionComponent;
import Components.Professions.Profession;
import Components.Professions.Warrior;
import Components.RaceComponent;
import Components.Races.Human;
import Components.Races.Race;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.gameplay.rpg.quest.QuestPane;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.InGamePanel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GameProjectApp extends GameApplication {


    private boolean paused = true;
    private String tempVar = "";


    private InGamePanel panel;
    private Entity playerEnt;
    private VBox playerInfo;


    private Config config;
    private int tileSize;
    private int mapHeight;
    private int mapWidth;


    private List<Enemy> enemies = new ArrayList<>();
    private ObjectProperty<Entity> selected = new SimpleObjectProperty<>();


    @Override
    protected void initSettings(GameSettings gameSettings) {


        gameSettings.setWidth(1440);
        gameSettings.setHeight(900);
        gameSettings.setTitle("New Earth");
        gameSettings.setVersion("0.1");
        // gameSettings.setMenuEnabled(true);
        // gameSettings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class)); //use for save/load later
        gameSettings.setCloseConfirmation(false);
        gameSettings.setConfigClass(Config.class);


    }


    @Override
    protected void initInput() {


    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Health", "health: 0/0");
        vars.put("ArmorBonus", "Armor: 10");
        vars.put("Race", "default");
        vars.put("Class", "default");
        vars.put("Armors", "default");
        vars.put("Weapons", "default");
        vars.put("Equipment", "default");


    }


    @Override
    protected void initGame() {


        config = new Config();
        tileSize = config.getTileSize();
        mapHeight = config.getMapHeight();
        mapWidth = config.getMapWidth();

        getGameWorld().addEntityFactory(new GameProjectFactory());

        initBackground();

        getGameScene().getViewport().setBounds(0, 0, mapWidth * tileSize, mapHeight * tileSize);


        panel = new InGamePanel();
        panel.getChildren().add(new QuestPane(300, 300));

        getGameScene().addUINode(panel);


    }


    private void initBackground() {

        Entity bg = Entities.builder().buildAndAttach(getGameWorld());

        Region region = new Region();
        region.setPrefSize(mapWidth * tileSize, mapHeight * tileSize);

        BackgroundImage bgImg = new BackgroundImage(getAssetLoader().loadTexture(
                "map of inner saisjo.jpg").getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        region.setBackground(new Background(bgImg));
        bg.getViewComponent().setView(region);

        bg.getViewComponent().setRenderLayer(new RenderLayer("BACKGROUND", 0));
    }


    @Override
    protected void initUI() {

        HBox raceBox = new HBox();
        Button humanBTN = new Button("Human \n start health: +20 \n start mana: +2 \n proficiencies: " +
                "\n light armor \n light and one-handed weapons",
                getAssetLoader().loadTexture("roma-kupriyanov-14.jpg", 200, 300));
        humanBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getGameScene().removeUINode(raceBox);
                chooseClass(new Human());
            }
        });
        humanBTN.setPrefWidth(400);

        Button elfBTN = new Button("Elf \n Comming Soon");
        Button dwarfBTN = new Button("Dwarf \n Comming Soon");

        raceBox.getChildren().add(humanBTN);
        raceBox.getChildren().add(elfBTN);
        raceBox.getChildren().add(dwarfBTN);

        raceBox.setPrefWidth(1000);
        raceBox.setTranslateX(0);
        raceBox.setTranslateY(100);

        getGameScene().addUINode(raceBox);


    }

    private void chooseClass(Race race) {

        HBox classBox = new HBox();
        Button warriorBTN = new Button("Components.Professions.Warrior \n start health: 100 \n start mana: 10 \n proficiencies: " +
                "\n light, medium and heavy armor \n all weapons \n start equipment: \n random weapon \n medium armor",
                getAssetLoader().loadTexture("tiefling soldier melee.jpg", 200, 300));
        warriorBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getGameScene().removeUINode(classBox);


                getGameWorld().setLevelFromMap("firstTestMap.json");

                PhysicsComponent physics = new PhysicsComponent();
                physics.setBodyType(BodyType.DYNAMIC);
                Profession profession = new Warrior();

                playerEnt = Entities.builder()
                        .type(GameProjectType.PLAYER)
                        .at(4 * tileSize, 4 * tileSize)
                        .viewFromNodeWithBBox(new Rectangle(64, 64, Color.BLUE))
                        .with(new PlayerControl(physics, profession, race))
                        .with(physics)
                        .with(new CollidableComponent(true))
                        .with(new ProfessionComponent(profession))
                        .with(new RaceComponent(race))
                        .with(new IrremovableComponent())
                        .buildAndAttach();


                createPlayerInfo();


                initNewInput();


                paused = false;


            }
        });
        warriorBTN.setPrefWidth(400);
        Button rogueBTN = new Button("Rogue \n Comming Soon");
        Button mageBTN = new Button("Mage \n Comming Soon");

        classBox.getChildren().add(warriorBTN);
        classBox.getChildren().add(rogueBTN);
        classBox.getChildren().add(mageBTN);
        classBox.setPrefWidth(1000);

        classBox.setTranslateX(0);
        classBox.setTranslateY(100);

        getGameScene().addUINode(classBox);


    }

    private void initNewInput() {

        Input input = getInput(); // get input service

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerControl.class).left();


                }
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerControl.class).right();

                }
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerControl.class).up();

                }
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerControl.class).down();

                }
            }
        }, KeyCode.DOWN);

        input.addAction(new UserAction("Open/Close Panel") {
            @Override
            protected void onActionEnd() {
                if (!paused) {
                    if (panel.isOpen())
                        panel.close();
                    else
                        panel.open();
                }
            }
        }, KeyCode.TAB);


    }

    @Override
    protected void onUpdate(double tdf) {
        if (playerEnt != null) {
            if (!paused) {

                updatePlayerInfo();


            }
        }
    }

    private void updatePlayerInfo() {
        getGameState().stringProperty("Health").set("Health: " + playerEnt.getComponent(PlayerControl.class).getHealth() + "/" + playerEnt.getComponent(PlayerControl.class).getMaxHealth());

        tempVar = "";
        for (Equipment n : playerEnt.getComponent(PlayerControl.class).getEquipment()) {
            tempVar += n.getName() + "\n";

        }
        getGameState().stringProperty("Equipment").set(tempVar);
        getGameState().stringProperty("ArmorBonus").set("Armor Bonus: " + playerEnt.getComponent(PlayerControl.class).getArmorBonus());
        getGameState().stringProperty("Race").set(playerEnt.getComponent(RaceComponent.class).getRace().getName());
        getGameState().stringProperty("Class").set(playerEnt.getComponent(ProfessionComponent.class).getProfession().getName());
    }


    @Override
    protected void initPhysics() {

        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        physics.addCollisionHandler(new CollisionHandler(GameProjectType.PLAYER, GameProjectType.ENEMY) {
            @Override
            protected void onHitBoxTrigger(Entity player, Entity enemy, HitBox playerBox, HitBox enemyBox) {
                System.out.println(playerBox.getName() + " X " + enemyBox.getName());
            }

            // the order of entities is determined by
            // the order of their types passed into constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                System.out.println("On Collision Begin");

                battle(enemy);
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

    private void battle(Entity enemy) {


        paused = true;



        getGameWorld().setLevelFromMap("TestBattleMap.json");



        playerEnt.getComponent(PlayerControl.class).setPosition(tileSize*11,tileSize*5);




        for (Entity enemyEnt : getGameWorld().getEntitiesByType(GameProjectType.ENEMY)) {
            enemies.add(new Brute());

        }
        enemies.forEach(o -> System.out.println(o));
        createBattleUI();


    }

    private void createBattleUI() {


        VBox playerBox = new VBox(10);
        playerBox.setTranslateX(getWidth() / 3 * 2);
        playerBox.getChildren().addAll(
                getUIFactory().newButton("Attack"),
                getUIFactory().newButton("Defend"),
                getUIFactory().newButton("Magic"),
                getUIFactory().newButton("Item")


        );
        getGameScene().addUINode(playerBox);

        playerBox.getChildren().forEach(o -> ((Button) o).setOnAction(e -> {
            playerAction(((Button) o).getText());
        }));


    }

    private void playerAction(String action) {
        System.out.println(action);

        switch (action) {

            case "Attack":
                if (getGameWorld().getSelectedEntity().isPresent()) {
                    System.out.println(getGameWorld().getSelectedEntity().toString());
                    if (getGameWorld().getSelectedEntity().get().getType() == GameProjectType.BATTLEENEMY) {
                        Entities.builder()
                                .at(getGameWorld().getSelectedEntity().get().getPosition().getX() - 64, getGameWorld().getSelectedEntity().get().getPosition().getY() - 64)
                                .viewFromAnimatedTexture("explosion.png", 48, Duration.seconds(0.5), false, true)
                                .buildAndAttach(getGameWorld());

                        updatePlayerInfo();
                    } else {
                        System.out.println("no valid target is selected");
                    }

                } else {
                    System.out.println("no target selected");
                }
                break;
            case "Defend":

                break;
            case "Magic":
                break;

            case "Item":
                break;

        }

    }

    private void createPlayerInfo() {

        playerInfo = new VBox();

        playerInfo.setTranslateX(getGameScene().getWidth() / 6 * 5);
        playerInfo.setTranslateY(getGameScene().getHeight() / 10);

        Text playerHealth = new Text();
        playerHealth.textProperty().bind(getGameState().stringProperty("Health"));
        playerInfo.getChildren().add(playerHealth);

        Text playerArmorBonus = new Text();
        playerArmorBonus.textProperty().bind(getGameState().stringProperty("ArmorBonus"));
        playerInfo.getChildren().add(playerArmorBonus);

        Text playerRace = new Text();
        playerRace.textProperty().bind(getGameState().stringProperty("Race"));
        playerInfo.getChildren().add(playerRace);

        Text playerClass = new Text();
        playerClass.textProperty().bind(getGameState().stringProperty("Class"));
        playerInfo.getChildren().add(playerClass);

        Text playerEquipment = new Text();
        playerEquipment.textProperty().bind(getGameState().stringProperty("Equipment"));
        playerInfo.getChildren().add(playerEquipment);

        getGameScene().addUINode(playerInfo);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
