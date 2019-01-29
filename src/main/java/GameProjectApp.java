import GameProject.Common.Config;
import GameProject.Common.GameProjectType;
import GameProject.Components.EnemyComponent;
import GameProject.Components.EnemyTypes.Boss;
import GameProject.Components.EnemyTypes.Brute;
import GameProject.Components.EnemyTypes.Cultist;
import GameProject.Components.EnemyTypes.Thug;
import GameProject.Components.Equipment.Weapon;
import GameProject.Components.PlayerComponent;
import GameProject.Components.ProfessionComponent;
import GameProject.Components.Professions.Profession;
import GameProject.Components.Professions.Warrior;
import GameProject.Components.RaceComponent;
import GameProject.Components.Races.Human;
import GameProject.Components.Races.Race;
import GameProject.GameProjectFactory;
import GameProject.GameWorldEntities;
import GameProject.UI.BasicInfoView;
import GameProject.UI.EquipmentView;
import GameProject.UI.InventoryView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.SelectableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.settings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.Random;


public class GameProjectApp extends GameApplication {


    private boolean paused = true;


    private Entity playerEnt;


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
    private int currentWorldY;
    private Point2D point;

    private List[][] enemies = new List[8][8];
    private GameWorldEntities gameWorldEntities;

    private int currentEnemyIndex;
    private EnemyComponent enemyComponent;
    private boolean defend = false;


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
        currentWorldX = 0;      //x coordinate of the world map which is set up as world(x,y).json
        currentWorldY = 0;      //y coordinate


    }


    @Override
    protected void initInput() {

        //no initial input
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        //no gameVars
    }


    @Override
    protected void initGame() {


        config = new Config();      //set config
        tileSize = config.getTileSize();    //tileSize is 64 px
        mapHeight = config.getMapHeight();  //map is 10 tiles high
        mapWidth = config.getMapWidth();    //map is 15 tiles long

        getGameWorld().addEntityFactory(new GameProjectFactory());  //add factory

        initBackground();
        gameWorldEntities = new GameWorldEntities();    //get gameWorldEntities, enemies, and 1 boss

        getGameScene().getViewport().setBounds(0, 0, mapWidth * tileSize, mapHeight * tileSize);


    }


    private void initBackground() {

        Entity bg = Entities.builder().buildAndAttach(getGameWorld());

        Region region = new Region();
        region.setPrefSize(mapWidth * tileSize, mapHeight * tileSize);

        BackgroundImage bgImg = new BackgroundImage(getAssetLoader().loadTexture(
                "mapofinnersaisjo.jpg").getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);     //sets a non repeating background for start menu

        region.setBackground(new Background(bgImg));
        bg.getViewComponent().setView(region);

        bg.getViewComponent().setRenderLayer(new RenderLayer("BACKGROUND", 0));
    }


    @Override
    protected void initUI() {       //sets 3 buttons giving the player 3 choices of races, only 1 is implemented so far

        HBox raceBox = new HBox();
        Button humanBTN = new Button("Human \n start health: +20 \n start mana: +2 \n proficiencies: " +
                "\n light armor \n light and one-handed weapons",
                getAssetLoader().loadTexture("roma-kupriyanov-14.jpg", 200, 300));
        humanBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getGameScene().removeUINode(raceBox);   //removed the 3 buttons
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

    private void chooseClass(Race race) {       //after choosing race, set 3 new buttons to choose profession/class only 1 is implemented

        HBox classBox = new HBox();
        Button warriorBTN = new Button("GameProject.Components.Professions.Warrior \n start health: 100 \n start mana: 10 \n proficiencies: " +
                "\n light, medium and heavy armor \n all weapons \n start equipment: \n random weapon \n medium armor",
                getAssetLoader().loadTexture("tieflingsoldiermelee.jpg", 200, 300));
        warriorBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                getGameScene().removeUINode(classBox);  //removes the 3 buttons


                getGameWorld().setLevelFromMap("world(0,0).json");  //sets map to first map

                physicsComponent = new PhysicsComponent();
                physicsComponent.setBodyType(BodyType.DYNAMIC); // set bodytype so entity can be interacted with
                profession = new Warrior();
                playerComponent = new PlayerComponent(physicsComponent, profession, race);  //sends physics profession and race to new playerComponent which acts as a player

                createPlayer(1 * tileSize, 1 * tileSize);   //set a playerEntity at tile 1,1


                initNewInput(); // sets input for player

                enemies = gameWorldEntities.loadEnemies();      //creates array with enemies
                getGameScene().addUINode(new InventoryView(playerComponent, getWidth(), getHeight()));  //adds inventory window
                getGameScene().addUINode(new EquipmentView(playerComponent, getWidth(), getHeight(), tileSize));    //adds equipment window, though does the same as inventory for now
                getGameScene().addUINode(new BasicInfoView(playerComponent, tileSize)); //adds info window, with HP mana, exp, armor bonus, and attack bonus


                paused = false; //enables input


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

    private void createPlayer(double x, double y) {     //creates a player entity with player,physics,profession,race and collidable components
        playerEnt = Entities.builder()
                .type(GameProjectType.PLAYER)
                .at(x, y)
                .viewFromNodeWithBBox(new Rectangle(tileSize / 2, tileSize / 2, Color.BLUE))
                .with(playerComponent)
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .with(new ProfessionComponent(profession))
                .with(new RaceComponent(race))
                .buildAndAttach();


    }

    private void initNewInput() {       //works only if not paused

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


    }

    @Override
    protected void onUpdate(double tdf) {   //used to transition to new map
        if (playerEnt != null) {
            if (!paused) {
                if (playerEnt.getCenter().getX() > tileSize * 15) {

                    worldTransition("world(" + ++currentWorldX + "," + currentWorldY + ").json", GameProjectType.WARP_W);   //when going right


                } else if (playerEnt.getCenter().getX() < 0) {

                    worldTransition("world(" + --currentWorldX + "," + currentWorldY + ").json", GameProjectType.WARP_E);   //when going left


                } else if (playerEnt.getCenter().getY() > tileSize * 10) {

                    worldTransition("world(" + currentWorldX + "," + ++currentWorldY + ").json", GameProjectType.WARP_N);   //when going down


                } else if (playerEnt.getCenter().getY() < 0) {

                    worldTransition("world(" + currentWorldX + "," + --currentWorldY + ").json", GameProjectType.WARP_S);   //when going up

                }


            }
        }
    }

    public void worldTransition(String map, GameProjectType type) {

        getGameWorld().setLevelFromMap(map);    //sets new map
        createPlayer(getGameWorld().getEntitiesByType(type).get(0).getCenter().getX(), getGameWorld().getEntitiesByType(type).get(0).getCenter().getY());   //creates a player entity at the warp point
        playerEnt.getComponent(PhysicsComponent.class).reposition(getGameWorld().getEntitiesByType(type).get(0).getCenter());   //relocates the player entity to warp point

        enemies[currentWorldX][currentWorldY].forEach(
                e -> createEnemy(e.toString(), enemies[currentWorldX][currentWorldY].indexOf(e))    //creates all enemies on current map
        );

        newCollisionHandler();  //reinstates the collision handler


    }

    public void worldTransition(String map, Point2D point) {    // same as above but used when returning from battle

        getGameWorld().setLevelFromMap(map);
        createPlayer(point.getX(), point.getY());
        playerEnt.getComponent(PhysicsComponent.class).reposition(point);

        enemies[currentWorldX][currentWorldY].forEach(
                e -> createEnemy(e.toString(), enemies[currentWorldX][currentWorldY].indexOf(e))
        );

        newCollisionHandler();
    }

    private void createEnemy(String enemy, int i) {     //looks up the array at the current world for enemy types

        String[] formatEnemy = enemy.split(",");    //format of enemis: type,number of enemies,x,y
        for (String s : formatEnemy) {

        }

        if (formatEnemy[0].equals("brute")) {
            createBrute(formatEnemy, i);


        } else if (formatEnemy[0].equals("thug")) {
            createThug(formatEnemy, i);

        } else if (formatEnemy[0].equals("cultist")) {
            createCultist(formatEnemy, i);

        } else if (formatEnemy[0].equals("boss")) {
            createBoss(formatEnemy, i);
        }
    }

    private void createBoss(String[] formatEnemy, int i) {      //creates boss at x,y
        Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(Integer.parseInt(formatEnemy[2]) * tileSize, Integer.parseInt(formatEnemy[3]) * tileSize)
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.BLACK))
                .with(new EnemyComponent(new Boss(), Integer.parseInt(formatEnemy[1]), i))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }

    private void createCultist(String[] formatEnemy, int i) {//creates cultist at x,y
        Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(Integer.parseInt(formatEnemy[2]) * tileSize, Integer.parseInt(formatEnemy[3]) * tileSize)
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .with(new EnemyComponent(new Cultist(), Integer.parseInt(formatEnemy[1]), i))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }

    private void createThug(String[] formatEnemy, int i) {  //creates thug at x,y
        Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(Integer.parseInt(formatEnemy[2]) * tileSize, Integer.parseInt(formatEnemy[3]) * tileSize)
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .with(new EnemyComponent(new Thug(), Integer.parseInt(formatEnemy[1]), i))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }

    private void createBrute(String[] formatEnemy, int i) { //creates brute at x,y

        Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(Integer.parseInt(formatEnemy[2]) * tileSize, Integer.parseInt(formatEnemy[3]) * tileSize)
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .with(new EnemyComponent(new Brute(), Integer.parseInt(formatEnemy[1]), i))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

    }


    @Override
    protected void initPhysics() {

        physics = getPhysicsWorld();    //retrieves physics world
        physics.setGravity(0, 0);   //set gravity to 0, since this is a top down view, and entities would fall off the map, if not set to 0
        newCollisionHandler();  //add collisionhandler


    }

    private void newCollisionHandler() {
        physics.addCollisionHandler(new CollisionHandler(GameProjectType.PLAYER, GameProjectType.ENEMY) {   //add collision to player and enemy
            @Override
            protected void onHitBoxTrigger(Entity player, Entity enemy, HitBox playerBox, HitBox enemyBox) {
            }

            // the order of entities is determined by
            // the order of their types passed into constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                currentEnemyIndex = enemy.getComponent(EnemyComponent.class).getIndex();    //gets the index of the colliding enemy to remove it later from the array so it doesn't spawn again
                enemyComponent = enemy.getComponent(EnemyComponent.class);
                battle(enemy);  //starts battle
            }

            @Override
            protected void onCollision(Entity player, Entity enemy) {

            }

            @Override
            protected void onCollisionEnd(Entity player, Entity enemy) {


            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameProjectType.PLAYER, GameProjectType.CHEST) {
            @Override
            protected void onCollisionBegin(Entity player, Entity chest) {

                openChest();    //colliding with chest isn't implemented yet

            }
        });
    }

    private void openChest() {
    }


    private void battle(Entity enemy) {


        paused = true;      //pause player input commands.
        point = playerEnt.getPosition();    //return point for player when battle ends

        getGameWorld().setLevelFromMap("battleMap.json");   // set map to battle map

        createPlayer(11 * tileSize, 5 * tileSize);  //adds player entity

        for (int i = 0; i < enemyComponent.getNumber(); i++) {      //creates every enemy in collided enemy
            Entities.builder()
                    .type(GameProjectType.BATTLE_ENEMY)
                    .at(point.getX() + i * tileSize, point.getY() + (tileSize * i + tileSize))
                    .with(enemyComponent)
                    .with(new SelectableComponent(true))
                    .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                    .buildAndAttach(getGameWorld());
        }


        createBattleUI();   // add battle commands


    }

    private void createBattleUI() {


        battleMenu = new VBox(10);
        battleMenu.setTranslateX(getWidth() / 3 * 2);
        battleMenu.getChildren().addAll(
                getUIFactory().newButton("Attack"),
                getUIFactory().newButton("Defend")//,
                // getUIFactory().newButton("Magic") //added when magic is implemented for now only physical attack and defend


        );
        getGameScene().addUINode(battleMenu);

        battleMenu.getChildren().forEach(o -> ((Button) o).setOnAction(e -> {
            playerAction(((Button) o).getText());   //adds action to buttons calling plaerAction with choice
        }));


    }

    private void playerAction(String action) {

        defend = false;

        if (getGameWorld().getSelectedEntity().isPresent()) {   //player needs to select an enemy to do anything in combat

            switch (action) {

                case "Attack":  //if the attack button is clicked


                    if (getGameWorld().getSelectedEntity().get().getType() == GameProjectType.BATTLE_ENEMY) {   //if selected is an enemy


                        if (rollDice(1, 20, playerEnt.getComponent(PlayerComponent.class).getAttackBonus()) >= getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().getArmor()) {
                            //checks the result of rollDice with the enemy's armor to see if attack hits the enemy
                            Entities.builder()
                                    .at(getGameWorld().getSelectedEntity().get().getPosition().getX() - 64, getGameWorld().getSelectedEntity().get().getPosition().getY() - 64)
                                    .viewFromAnimatedTexture("explosion.png", 48, Duration.seconds(2), false, true)
                                    .buildAndAttach(getGameWorld());    //creates an explosion at the enemy if it's hit

                            if (!getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().takeDamaged(((Weapon) playerEnt.getComponent(PlayerComponent.class).getWeapon()).getDamage())) {    //applies damage to enemy and checks if the enemy is dead
                                playerEnt.getComponent(PlayerComponent.class).gainExp(getGameWorld().getSelectedEntity().get().getComponent(EnemyComponent.class).getEnemy().getExp()); //gives the player exp from the enemy
                                getGameWorld().removeEntity(getGameWorld().getSelectedEntity().get());  //removes the dead enemy

                                if (getGameWorld().getEntitiesByType(GameProjectType.BATTLE_ENEMY).isEmpty()) { //checks if there are any enemies left


                                    battleEnd();    //ends the battle


                                }
                            }

                        } else {


                        }

                        enemyTurn();    //enemy's turn in combat

                    } else {


                    }


                    break;
                case "Defend":
                    defend = true;  // sets defend true, which halves the damage of the next enemy attack
                    enemyTurn();

                    break;
                case "Magic":   //cant be reached since there is no magic button in battleUI
                    enemyTurn();
                    break;


            }


        } else {

        }

    }


    private void enemyTurn() {


        for (Entity enemy : getGameWorld().getEntitiesByType(GameProjectType.BATTLE_ENEMY)) {   //every enemy attacks the player

            if (rollDice(1, 20, enemy.getComponent(EnemyComponent.class).getEnemy().getAttackBonus()) >= playerEnt.getComponent(PlayerComponent.class).getArmorBonus()) {   //rolls to see if it hits

                if (defend) {
                    playerEnt.getComponent(PlayerComponent.class).getDamaged(enemy.getComponent(EnemyComponent.class).getEnemy().preferredAttack().getValue() / 2); //halves damage if player defended
                } else {
                    playerEnt.getComponent(PlayerComponent.class).getDamaged(enemy.getComponent(EnemyComponent.class).getEnemy().preferredAttack().getValue()); //applies damage to player
                }


                Entities.builder()
                        .at(playerEnt.getX() - tileSize, playerEnt.getY() - tileSize)
                        .viewFromAnimatedTexture("explosion.png", 48, Duration.seconds(2), false, true)
                        .buildAndAttach(getGameWorld());    //adds explosion to player if hit

            } else {

            }
        }

    }

    private void battleEnd() {


        getGameScene().removeUINode(battleMenu);    //removed battleUI buttons

        enemies[currentWorldX][currentWorldY].remove(currentEnemyIndex);    //removed the map enemy the player just fought


        worldTransition("world(" + currentWorldX + "," + currentWorldY + ").json", point); //sets the map back


        paused = false; //unpauses the player inputs


    }


    public int rollDice(int amount, int max, int attackBonus) {     //simulates rolling d20 dice, 20 sided die

        diceRoll = 0;
        for (int i = 1; i <= amount; i++) { //rolls a number of d20's
            diceRoll += dice.nextInt(max) + 1 + attackBonus;    //adds attack bonus to result
        }

        return diceRoll;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
