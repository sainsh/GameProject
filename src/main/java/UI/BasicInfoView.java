package UI;

import Components.PlayerComponent;
import com.almasb.fxgl.ui.InGameWindow;
import com.almasb.fxgl.ui.Position;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BasicInfoView extends InGameWindow {   //creates a window showing basic info on the player

    private int tileSize;

    public BasicInfoView(PlayerComponent playerComponent, int tileSize) {
        super("Basic Info", WindowDecor.MINIMIZE);

        this.tileSize = tileSize;


        setBackgroundColor(Color.LIGHTGREEN);       //sets colour
        setPrefSize(5 * tileSize, 3 * tileSize); //sets size
        setCanResize(false);    //player cant change the size of window
        relocate(tileSize*16,tileSize*2);   //sets position

        ProgressBar barHPUI = ProgressBar.makeHPBar();  // creates a progressbar for health
        barHPUI.setTranslateX(getWidth()/10);
        barHPUI.setTranslateY(tileSize/2);
        barHPUI.setWidth(tileSize*3);
        barHPUI.setHeight(tileSize / 4);
        barHPUI.setLabelPosition(Position.RIGHT);
        barHPUI.maxValueProperty().bind(playerComponent.maxHealthPropertyProperty());  //binds max to maxHealthProperty from playerComponent
        barHPUI.currentValueProperty().bind(playerComponent.healthPropertyProperty());// binds current to HealthProperty from playerComponent

        ProgressBar barMPUI = ProgressBar.makeSkillBar();   //creates a progressbar for mana
        barMPUI.setTranslateX(getWidth()/10);
        barMPUI.setTranslateY(tileSize);
        barMPUI.setWidth(tileSize*3);
        barMPUI.setHeight(tileSize/4);
        barMPUI.setLabelPosition(Position.RIGHT);
        barMPUI.maxValueProperty().bind(playerComponent.maxManaPropertyProperty());//binds max to maxManaProperty from playerComponent
        barMPUI.currentValueProperty().bind(playerComponent.manaPropertyProperty());// binds current to manaProperty from playerComponent

        Text exp = new Text();
        exp.textProperty().bind(playerComponent.expPropertyProperty().asString());  //adds exp value from playerComponent
        exp.setTranslateX(getWidth()/10);
        exp.setTranslateY(tileSize*3/2);

        Text armorBonus = new Text();
        armorBonus.textProperty().bind(playerComponent.armorBonusPropertyProperty().asString()); //adds armor from playerComponent
        armorBonus.setTranslateX(getWidth()/10);
        armorBonus.setTranslateY(tileSize*2);

        Text attackBonus = new Text();
        attackBonus.textProperty().bind(playerComponent.attackBonusPropertyProperty().asString()); //adds attack bonus from playerComponent
        attackBonus.setTranslateX(getWidth()/10);
        attackBonus.setTranslateY(tileSize*5/2);

        Pane uiPane = new Pane();

        uiPane.setPrefSize(5*tileSize,3*tileSize);
        uiPane.getChildren().addAll(barHPUI,barMPUI,exp,armorBonus,attackBonus);    //adds the bars and texts to pane

        setContentPane(uiPane); //adds pane to game
    }
}
