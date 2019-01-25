package UI;

import Components.PlayerComponent;
import com.almasb.fxgl.ui.InGameWindow;
import com.almasb.fxgl.ui.Position;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BasicInfoView extends InGameWindow {

    private int tileSize;

    public BasicInfoView(PlayerComponent playerComponent, int tileSize) {
        super("Basic Info", WindowDecor.MINIMIZE);

        this.tileSize = tileSize;


        setBackgroundColor(Color.LIGHTGREEN);
        setPrefSize(5 * tileSize, 3 * tileSize);
        setCanResize(false);
        relocate(tileSize*16,tileSize*2);

        ProgressBar barHPUI = ProgressBar.makeHPBar();
        barHPUI.setTranslateX(getWidth()/10);
        barHPUI.setTranslateY(tileSize/2);
        barHPUI.setWidth(tileSize*3);
        barHPUI.setHeight(tileSize / 4);
        barHPUI.setLabelPosition(Position.RIGHT);
        barHPUI.maxValueProperty().bind(playerComponent.maxHealthPropertyProperty());
        barHPUI.currentValueProperty().bind(playerComponent.healthPropertyProperty());

        ProgressBar barMPUI = ProgressBar.makeSkillBar();
        barMPUI.setTranslateX(getWidth()/10);
        barMPUI.setTranslateY(tileSize);
        barMPUI.setWidth(tileSize*3);
        barMPUI.setHeight(tileSize/4);
        barMPUI.setLabelPosition(Position.RIGHT);
        barMPUI.maxValueProperty().bind(playerComponent.maxManaPropertyProperty());
        barMPUI.currentValueProperty().bind(playerComponent.manaPropertyProperty());

        Text exp = new Text();
        exp.textProperty().bind(playerComponent.expPropertyProperty().asString());
        exp.setTranslateX(getWidth()/10);
        exp.setTranslateY(tileSize*3/2);

        Text armorBonus = new Text();
        armorBonus.textProperty().bind(playerComponent.armorBonusPropertyProperty().asString());
        armorBonus.setTranslateX(getWidth()/10);
        armorBonus.setTranslateY(tileSize*2);

        Text attackBonus = new Text();
        attackBonus.textProperty().bind(playerComponent.attackBonusPropertyProperty().asString());
        attackBonus.setTranslateX(getWidth()/10);
        attackBonus.setTranslateY(tileSize*5/2);

        Pane uiPane = new Pane();

        uiPane.setPrefSize(5*tileSize,3*tileSize);
        uiPane.getChildren().addAll(barHPUI,barMPUI,exp,armorBonus,attackBonus);

        setContentPane(uiPane);
    }
}
