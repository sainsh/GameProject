import Common.Config;
import Common.PlayerControl;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.gameplay.rpg.quest.QuestPane;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.InGamePanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Map;


public class GameProjectApp extends GameApplication {


    private boolean paused = true;
    private String tempVar = "";


    private InGamePanel panel;
    private Entity playerEnt;
    private Entity enemy;
    private VBox playerInfo;

    private Config config;
    private int tileSize;
    private int mapHeight;
    private int mapWidth;


    private Player player;
    private Race race;


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
        vars.put("Player Health", "health: 0/0");
        vars.put("Player ArmorBonus", "Armor: 10");
        vars.put("Player Race", "default");
        vars.put("Player Class", "default");
        vars.put("Player Armors", "default");
        vars.put("Player Weapons", "default");
        vars.put("Player Equipment", "default");


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
                race = new Human();
                chooseClass();
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

    private void chooseClass() {

        HBox classBox = new HBox();
        Button warriorBTN = new Button("Warrior \n start health: 100 \n start mana: 10 \n proficiencies: " +
                "\n light, medium and heavy armor \n all weapons \n start equipment: \n random weapon \n medium armor",
                getAssetLoader().loadTexture("tiefling soldier melee.jpg", 200, 300));
        warriorBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getGameScene().removeUINode(classBox);

                player = new Player(new Warrior(), race);

                createPlayerInfo();


                getGameWorld().setLevelFromMap("firstTestMap.json");

                playerEnt = getGameWorld().spawn("player", 50, 50);

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

        getInput().addAction(new UserAction("Open/Close Panel") {
            @Override
            protected void onActionEnd() {
                if (paused) {
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
        if (player != null) {
            getGameState().stringProperty("Player Health").set("Health: " + player.getHealth() + "/" + player.getMaxHealth());
            tempVar = "";
            for (Equipment n : player.getEquipment()) {
                tempVar += n.getName() + "\n";

            }
            getGameState().stringProperty("Player Equipment").set(tempVar);
            getGameState().stringProperty("Player ArmorBonus").set("Armor Bonus: " + player.getArmorBonus());
            getGameState().stringProperty("Player Race").set(player.getRace().getName());
            getGameState().stringProperty("Player Class").set(player.getProfession().getName());
        }
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

        getGameScene().removeUINode(playerInfo);
        getGameWorld().setLevelFromMap("TestBattleMap.json");

    }

    private void createPlayerInfo(){

        playerInfo = new VBox();

        playerInfo.setTranslateX(getGameScene().getWidth() / 6 * 5);
        playerInfo.setTranslateY(getGameScene().getHeight() / 10);

        Text playerHealth = new Text();
        playerHealth.textProperty().bind(getGameState().stringProperty("Player Health"));
        playerInfo.getChildren().add(playerHealth);

        Text playerArmorBonus = new Text();
        playerArmorBonus.textProperty().bind(getGameState().stringProperty("Player ArmorBonus"));
        playerInfo.getChildren().add(playerArmorBonus);

        Text playerRace = new Text();
        playerRace.textProperty().bind(getGameState().stringProperty("Player Race"));
        playerInfo.getChildren().add(playerRace);

        Text playerClass = new Text();
        playerClass.textProperty().bind(getGameState().stringProperty("Player Class"));
        playerInfo.getChildren().add(playerClass);

        Text playerEquipment = new Text();
        playerEquipment.textProperty().bind(getGameState().stringProperty("Player Equipment"));
        playerInfo.getChildren().add(playerEquipment);

        getGameScene().addUINode(playerInfo);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
