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


public class EquipmentView extends InGameWindow {   //creates a window showing what items are equipped


    private Map<EquipPlace, Group> groups = new HashMap<EquipPlace, Group>();

    private PlayerComponent playerComponent;
    private int tileSize;


    public EquipmentView(PlayerComponent playerComponent, double width, double height, int tileSize) {
        super("Equipment", WindowDecor.MINIMIZE);
        this.tileSize = tileSize;

        relocate(width - 6 * tileSize, height - 5 * tileSize);  //sets position of window

        setBackgroundColor(Color.LIGHTGREEN);   //set colour
        setPrefSize(3 * tileSize, 5 * tileSize);
        setCanResize(false);    //player cant change the size

        this.playerComponent = playerComponent; // used to get equipped items

        groups.put(EquipPlace.BODY, createGroup(tileSize * 2, tileSize));   //creates the equipment slots
        groups.put(EquipPlace.LEFT, createGroup(tileSize * 3, tileSize));
        groups.put(EquipPlace.RIGHT, createGroup(tileSize, tileSize));


            setItem(EquipPlace.BODY, playerComponent.getArmor());       //set the equipped items into slots
            setItem(EquipPlace.RIGHT,playerComponent.getWeapon());
            setItem(EquipPlace.LEFT,playerComponent.getShield());



        Pane pane = new Pane();
        pane.getChildren().addAll(groups.values()); //set slots to pane
        setContentPane(pane);   //adds pane to view
    }

    private Group createGroup(int x, int y) {   //create slots and adds tooltip
        Group group = new Group();
        group.setTranslateX(x);
        group.setTranslateY(y);
        Tooltip tooltip = new Tooltip();    //there are no current tool tips for items

        Text text = new Text();
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(200);

        tooltip.setGraphic(text);
        Tooltip.install(group, tooltip);

        group.setUserData(text);
        return group;
    }

    private void setItem(EquipPlace place, Equipment item) {    //used to set textures to items

        if(item != null){
            Texture view = texture(item.getName()+".png"); // get the corresponding texture by item name
            view.setOnMouseClicked(event -> playerComponent.unEquipItem(place));    //unequips the times
            view.setCursor(Cursor.HAND);
            view.resize(tileSize,tileSize);
            view.setFitHeight(tileSize);
            view.setFitWidth(tileSize);

            Group group = groups.get(place);
            group.getChildren().setAll(view); //adds the texture to group
        }



    }
}
