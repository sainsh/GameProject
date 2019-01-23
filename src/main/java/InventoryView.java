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

public class InventoryView extends InGameWindow {

    private Map<Integer, Boolean> slots = new HashMap<>();
    private Pane root = new Pane();

    private ListChangeListener<Equipment> listerner;

    private PlayerComponent playerComponent;


    public InventoryView(PlayerComponent playerComponent, double width, double height) {
        super("Inventory", WindowDecor.MINIMIZE);

        relocate(width - 202, height - 315);

        setBackgroundColor(Color.LIGHTGREEN);
        setPrefSize(3 * 64, 4 * 64);
        setCanResize(false);
        this.playerComponent = playerComponent;

        for (int i = 0; i < 30; i++) {
            slots.put(i, true);
        }

        listerner = new ListChangeListener<Equipment>() {
            @Override
            public void onChanged(Change<? extends Equipment> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Equipment item : change.getAddedSubList()) {
                            addItem(item);
                        }
                    } else if (change.wasRemoved()) {
                        for (Equipment item : change.getRemoved()) {
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


        playerComponent.getEquipment().forEach(this::addItem);

        playerComponent.getEquipment().addListener(listerner);

        setContentPane(root);
    }

    private int getNextFreeSlot() {
        for (int i = 0; i < 30; i++) {
            if (slots.get(i))
                return i;
        }

        return -1;
    }

    private void addItem(Equipment item) {
        int index = getNextFreeSlot();
        slots.put(index, false);

        Texture view = texture(item.getName()+".png");
        view.resize(64,64);
        view.setFitHeight(64);
        view.setFitWidth(64);

        view.setUserData(new Pair<>(item, index));
        view.setTranslateX((index % 5) * 40);
        view.setTranslateY((index / 5) * 40);
        view.setOnMouseClicked(event -> {

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


        root.getChildren().add(view);
    }
}
