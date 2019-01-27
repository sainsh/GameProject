package UI;

import Components.Equipment.Armor;
import Components.Equipment.Equipment;
import Components.Equipment.Weapon;
import Components.PlayerComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.InGameWindow;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.almasb.fxgl.app.DSLKt.texture;

public class InventoryView extends InGameWindow {   //shows the inventory of the player

    private Map<Integer, Boolean> slots = new HashMap<>();
    private Pane root = new Pane();

    private ListChangeListener<Equipment> listerner;

    private PlayerComponent playerComponent;


    public InventoryView(PlayerComponent playerComponent, double width, double height) {
        super("Inventory", WindowDecor.MINIMIZE);

        relocate(width - 202, height - 315);    //sets position of the window

        setBackgroundColor(Color.LIGHTGREEN);   //set colour
        setPrefSize(3 * 64, 4 * 64);    //sets size
        setCanResize(false);    //player cant change the size of window
        this.playerComponent = playerComponent; //playerComponent to see inventory

        for (int i = 0; i < 30; i++) {  //creates 30 inventory slots
            slots.put(i, true);
        }

        listerner = new ListChangeListener<Equipment>() {   //creates listerner to listen to changes in inventory, like new items and removed items
            @Override
            public void onChanged(Change<? extends Equipment> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Equipment item : change.getAddedSubList()) {       //adds items to inventoryview
                            addItem(item);
                        }
                    } else if (change.wasRemoved()) {
                        for (Equipment item : change.getRemoved()) { //removes items from inventoryview
                            for (Iterator<Node> it = root.getChildren().iterator(); it.hasNext(); ) {
                                Node node = it.next();

                                if (node.getUserData() != null) {
                                    Pair<Equipment, Integer> data = (Pair<Equipment, Integer>) node.getUserData();

                                    if (data.getKey() == item) {
                                        slots.put(data.getValue(), true);
                                        it.remove();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };


        playerComponent.getEquipment().forEach(this::addItem);  //runs through list of items the player has

        playerComponent.getEquipment().addListener(listerner);  // adds the listener

        setContentPane(root);   //adds the inventoryview to pane
    }

    private int getNextFreeSlot() { //finds the next free slot, used when adding new items, returns -1 if inventory is full
        for (int i = 0; i < 30; i++) {
            if (slots.get(i))
                return i;
        }

        return -1;
    }

    private void addItem(Equipment item) {
        int index = getNextFreeSlot();
        slots.put(index, false);    //set slot full

        Texture view = texture(item.getName()+".png");  //gets the texture from assets corresponding to the item name
        view.resize(64,64);
        view.setFitHeight(64);
        view.setFitWidth(64);   //sets size

        view.setUserData(new Pair<>(item, index));
        view.setTranslateX((index % 5) * 40);
        view.setTranslateY((index / 5) * 40);
        view.setOnMouseClicked(event -> {   //change equipment to clicked items.

            if (event.getButton() == MouseButton.PRIMARY) {
                if (item instanceof Weapon) {
                    playerComponent.setWeapon((Weapon) item);
                } else if (item instanceof Armor && !((Armor) item).getType().equals("shield")) {
                    playerComponent.setArmor((Armor) item);
                } else if (item instanceof Armor && ((Armor) item).getType().equals("shield")) {
                    playerComponent.setShield((Armor) item);
                }
            }
        });
        view.setCursor(Cursor.HAND);


        root.getChildren().add(view); //adds the item to the pane
    }
}
