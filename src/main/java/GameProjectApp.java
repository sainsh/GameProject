import Common.Config;
import Components.*;
import Components.Equipment.Equipment;
import Components.Equipment.Weapon;
import Components.Professions.Profession;
import Components.Professions.Warrior;
import Components.Races.Human;
import Components.Races.Race;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;
import java.util.Random;


public class GameProjectApp extends GameApplication {


    private boolean paused = true;
    private String tempVar = "";


    private InGamePanel panel;
    private Entity playerEnt;
    private VBox playerInfo;
    private Entity tempEnt;
    private Race race;
    private Profession profession;
    private PlayerComponent playerComponent;
    private PhysicsComponent physicsComponent;


    private Config config;
    private int tileSize;
    private int mapHeight;
    private int mapWidth;


    private Random dice = new Random();
    private int diceRoll;

    private PhysicsWorld physics;
    private VBox battleMenu;

    private int currentWorldX;
    private int currentworldY;
    private Point2D point;


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
        currentWorldX = 0;
        currentworldY = 0;


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
        vars.put("exp", "default");
        vars.put("playerpoint","default");


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
                chooseClass(race);
            }
        });
        humanBTN.setPrefWidth(400);

        Button elfBTN = new Button("Elf \n Comming Soon");
        Button dwarfBTN = new Button("Dwarf \n Comming Soon");

        raceBox.getChildren().addAll(humanBTN, elfBTN, dwarfBTN);

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


                getGameWorld().setLevelFromMap("world(0,0).json");

                physicsComponent = new PhysicsComponent();
                physicsComponent.setBodyType(BodyType.DYNAMIC);
                profession = new Warrior();
                playerComponent = new PlayerComponent(physicsComponent, profession, race);

                createPlayer(1 * tileSize, 1 * tileSize);

                createPlayerInfo();


                initNewInput();


                paused = false;


            }
        });
        warriorBTN.setPrefWidth(400);
        Button rogueBTN = new Button("Rogue \n Comming Soon");
        Button mageBTN = new Button("Mage \n Comming Soon");

        classBox.getChildren().addAll(warriorBTN, rogueBTN, mageBTN);

        classBox.setPrefWidth(1000);

        classBox.setTranslateX(0);
        classBox.setTranslateY(100);

        getGameScene().addUINode(classBox);


    }

    private void createPlayer(double x, double y) {
        playerEnt = Entities.builder()
                .type(GameProjectType.PLAYER)
                .at(x, y)
                .viewFromNodeWithBBox(new Rectangle(64, 64, Color.BLUE))
                .with(playerComponent)
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new ProfessionComponent(profession))
                .with(new RaceComponent(race))
                .buildAndAttach();


    }

    private void initNewInput() {

        Input input = getInput(); // get input service

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerComponent.class).left();


                }
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerComponent.class).right();


                }
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerComponent.class).up();

                }
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                if (!paused) {
                    playerEnt.getComponent(PlayerComponent.class).down();

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
                if (playerEnt.getCenter().getX() > tileSize * 15) {
                    getGameWorld().setLevelFromMap("world(" + ++currentWorldX + "," + currentworldY + ").json");
                    createPlayer(getGameWorld().getEntitiesByType(GameProjectType.WARP_W).get(0).getCenter().getX(), getGameWorld().getEntitiesByType(GameProjectType.WARP_W).get(0).getCenter().getY());
                    playerEnt.getComponent(PhysicsComponent.class).reposition(getGameWorld().getEntitiesByType(GameProjectType.WARP_W).get(0).getCenter());
                    System.out.println("warp "+getGameWorld().getEntitiesByType(GameProjectType.WARP_W).get(0).getCenter().toString());
                    System.out.println("player "+playerEnt.getPosition());

                } else if (playerEnt.getCenter().getX() < 0) {

                    getGameWorld().setLevelFromMap("world(" + --currentWorldX + "," + currentworldY + ").json");
                    createPlayer(getGameWorld().getEntitiesByType(GameProjectType.WARP_E).get(0).getCenter().getX(), getGameWorld().getEntitiesByType(GameProjectType.WARP_E).get(0).getCenter().getY());
                    playerEnt.getComponent(PhysicsComponent.class).reposition(getGameWorld().getEntitiesByType(GameProjectType.WARP_E).get(0).getCenter());
                    System.out.println("warp "+getGameWorld().getEntitiesByType(GameProjectType.WARP_E).get(0).getCenter().toString());
                    System.out.println("player "+playerEnt.getPosition());

                } else if (playerEnt.getCenter().getY() > tileSize * 10) {

                    getGameWorld().setLevelFromMap("world(" + currentWorldX + "," + ++currentworldY + ").json");

                    createPlayer(getGameWorld().getEntitiesByType(GameProjectType.WARP_N).get(0).getCenter().getX(), getGameWorld().getEntitiesByType(GameProjectType.WARP_N).get(0).getCenter().getY());
                    playerEnt.getComponent(PhysicsComponent.class).reposition(getGameWorld().getEntitiesByType(GameProjectType.WARP_N).get(0).getCenter());
                    System.out.println("warp "+getGameWorld().getEntitiesByType(GameProjectType.WARP_N).get(0).getCenter().toString());
                    System.out.println("player "+playerEnt.getPosition());

                } else if (playerEnt.getCenter().getY() < 0) {

                    getGameWorld().setLevelFromMap("world(" + currentWorldX + "," + --currentworldY + ").json");

                    createPlayer(getGameWorld().getEntitiesByType(GameProjectType.WARP_S).get(0).getCenter().getX(), getGameWorld().getEntitiesByType(GameProjectType.WARP_S).get(0).getCenter().getY());
                    playerEnt.getComponent(PhysicsComponent.class).reposition(getGameWorld().getEntitiesByType(GameProjectType.WARP_S).get(0).getCenter());
                    System.out.println("warp "+getGameWorld().getEntitiesByType(GameProjectType.WARP_S).get(0).getCenter().toString());
                    System.out.println("player "+playerEnt.getPosition());
                }

                updatePlayerInfo();


            }
        }
    }

    private void updatePlayerInfo() {
        getGameState().stringProperty("Health").set("Health: " + playerEnt.getComponent(PlayerComponent.class).getHealth() + "/" + playerEnt.getComponent(PlayerComponent.class).getMaxHealth());

        tempVar = "";
        for (Equipment n : playerEnt.getComponent(PlayerComponent.class).getEquipment()) {
            tempVar += n.getName() + "\n";

        }
        getGameState().stringProperty("Equipment").set(tempVar);
        getGameState().stringProperty("ArmorBonus").set("Armor Bonus: " + playerEnt.getComponent(PlayerComponent.class).getArmorBonus());
        getGameState().stringProperty("Race").set(playerEnt.getComponent(RaceComponent.class).getRace().getName());
        getGameState().stringProperty("Class").set(playerEnt.getComponent(ProfessionComponent.class).getProfession().getName());
        getGameState().stringProperty("exp").set("exp:" + playerEnt.getComponent(PlayerComponent.class).getExp());
        getGameState().stringProperty("playerpoint").set(""+playerEnt.getCenter());
    }


    @Override
    protected void initPhysics() {

        physics = getPhysicsWorld();
        physics.setGravity(0, 0);
        newCollisionHandler();


    }

    private void newCollisionHandler() {
        physics.addCollisionHandler(new CollisionHandler(GameProjectType.PLAYER, GameProjectType.ENEMY) {
            @Override
            protected void onHitBoxTrigger(Entity player, Entity enemy, HitBox playerBox, HitBox enemyBox) {
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
        physics.addCollisionHandler(new CollisionHandler(GameProjectType.PLAYER, GameProjectType.CHEST) {
            @Override
            protected void onCollisionBegin(Entity player, Entity chest) {
                
                openChest();
                
            }
        });
    }

    private void openChest() {
    }


    private void battle(Entity enemy) {


        paused = true;
        point = playerEnt.getPosition();

        getGameWorld().setLevelFromMap("TestBattleMap.json");
        createPlayer(11 * tileSize, 5 * tileSize);


        createBattleUI();




    }

    private void createBattleUI() {


        battleMenu = new VBox(10);
        battleMenu.setTranslateX(getWidth() / 3 * 2);
        battleMenu.getChildren().addAll(
                getUIFactory().newButton("Attack"),
                getUIFactory().newButton("Defend"),
                getUIFactory().newButton("Magic"),
                getUIFactory().newButton("Item")


        );
        getGameScene().addUINode(battleMenu);

        battleMenu.getChildren().forEach(o -> ((Button) o).setOnAction(e -> {
            playerAction(((Button) o).getText());
        }));


    }

    private void playerAction(String action ) {
        System.out.println(action);

        if (getGameWorld().getSelectedEntity().isPresent()) {

            switch (action) {

                case "Attack":

                    System.out.println(getGameWorld().getSelectedEntity().toString());
                    if (getGameWorld().getSelectedEntity().get().getType() == GameProjectType.BATTLE_ENEMY) {


                        if (rollDice(1, 20) >= getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().getArmor()) {

                            Entities.builder()
                                    .at(getGameWorld().getSelectedEntity().get().getPosition().getX() - 64, getGameWorld().getSelectedEntity().get().getPosition().getY() - 64)
                                    .viewFromAnimatedTexture("explosion.png", 48, Duration.seconds(2), false, true)
                                    .buildAndAttach(getGameWorld());

                            if (!getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().takeDamaged(((Weapon) playerEnt.getComponent(PlayerComponent.class).getWeapon()).getDamage())) {
                                playerEnt.getComponent(PlayerComponent.class).gainExp(getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().getExp());
                                getGameWorld().removeEntity(getGameWorld().getSelectedEntity().get());

                                if (getGameWorld().getEntitiesByType(GameProjectType.BATTLE_ENEMY).isEmpty()) {

                                    System.out.println("defeated all enemies");
                                    battleEnd();


                                }
                            }

                        } else {

                            System.out.println("didn't hit");
                        }

                        enemyTurn();

                    } else {
                        System.out.println("no valid target");

                    }


                    break;
                case "Defend":
                    enemyTurn();

                    break;
                case "Magic":
                    enemyTurn();
                    break;

                case "Item":

                    enemyTurn();
                    break;

            }

            updatePlayerInfo();

        } else {
            System.out.println("no target selected");
        }

    }


    private void enemyTurn() {


        for (Entity enemy : getGameWorld().getEntitiesByType(GameProjectType.BATTLE_ENEMY)) {

            if (rollDice(1, 20) >= playerEnt.getComponent(PlayerComponent.class).getArmorBonus()) {

                playerEnt.getComponent(PlayerComponent.class).getDamaged(enemy.getComponent(EnemyComponent.class).getEnemy().preferredAttack().getValue());
                System.out.println(playerEnt.getComponent(PlayerComponent.class).getHealth() + " / " + playerEnt.getComponent(PlayerComponent.class).getMaxHealth());
                Entities.builder()
                        .at(playerEnt.getX() - tileSize, playerEnt.getY() - tileSize)
                        .viewFromAnimatedTexture("explosion.png", 48, Duration.seconds(2), false, true)
                        .buildAndAttach(getGameWorld());

            } else {
                System.out.println("enemy didn't hit");
            }
        }

    }

    private void battleEnd() {


        getPhysicsWorld().clear();

        getGameScene().removeUINode(battleMenu);


        getGameWorld().setLevelFromMap("world("+currentWorldX+","+currentworldY+").json");
        getGameWorld().removeEntity(getGameWorld().getEntitiesInRange(new Rectangle2D(point.getX(),point.getY(),point.getX()+tileSize,point.getY()+tileSize)).get(0));
        createPlayer(2 * tileSize, 2 * tileSize);
        playerEnt.getComponent(PhysicsComponent.class).reposition(point);
        paused = false;
        newCollisionHandler();

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

        Text playerExp = new Text();
        playerExp.textProperty().bind(getGameState().stringProperty("exp"));
        playerInfo.getChildren().add(playerExp);

        Text playerPoint = new Text();
        playerPoint.textProperty().bind(getGameState().stringProperty("playerpoint"));
        playerInfo.getChildren().add(playerPoint);

        getGameScene().addUINode(playerInfo);


    }

    public int rollDice(int amount, int max) {

        diceRoll = 0;
        for (int i = 1; i <= amount; i++) {
            diceRoll += dice.nextInt(max) + 1;
        }

        return diceRoll;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
