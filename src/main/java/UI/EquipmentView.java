package UI;

import Common.EquipPlace;
import Components.Equipment.Equipment;
import Components.PlayerComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.InGameWindow;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.app.DSLKt.texture;


public class EquipmentView extends InGameWindow {


    private Map<EquipPlace, Group> groups = new HashMap<EquipPlace, Group>();

    private PlayerComponent playerComponent;
    private int tileSize;


    public EquipmentView(PlayerComponent playerComponent, double width, double height, int tileSize) {
        super("Equipment", WindowDecor.MINIMIZE);
        this.tileSize = tileSize;

        relocate(width - 6 * tileSize, height - 5 * tileSize);

        setBackgroundColor(Color.LIGHTGREEN);
        setPrefSize(3 * tileSize, 5 * tileSize);
        setCanResize(false);

        this.playerComponent = playerComponent;

        groups.put(EquipPlace.BODY, createGroup(tileSize * 2, tileSize));
        groups.put(EquipPlace.LEFT, createGroup(tileSize * 3, tileSize));
        groups.put(EquipPlace.RIGHT, createGroup(tileSize, tileSize));


            setItem(EquipPlace.BODY, playerComponent.getArmor());
            setItem(EquipPlace.RIGHT,playerComponent.getWeapon());
            setItem(EquipPlace.LEFT,playerComponent.getShield());



        Pane pane = new Pane();
        pane.getChildren().addAll(groups.values());
        setContentPane(pane);
    }

    private Group createGroup(int x, int y) {
        Group group = new Group();
        group.setTranslateX(x);
        group.setTranslateY(y);
        Tooltip tooltip = new Tooltip();

        Text text = new Text();
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(200);

        tooltip.setGraphic(text);
        Tooltip.install(group, tooltip);

        group.setUserData(text);
        return group;
    }

    private void setItem(EquipPlace place, Equipment item) {

        if(item != null){
            Texture view = texture(item.getName()+".png");
            view.setOnMouseClicked(event -> playerComponent.unEquipItem(place));
            view.setCursor(Cursor.HAND);
            view.resize(tileSize,tileSize);
            view.setFitHeight(tileSize);
            view.setFitWidth(tileSize);

            Group group = groups.get(place);
            group.getChildren().setAll(view);
        }



    }
}
