import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.SelectableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class GameProjectFactory implements EntityFactory {


    @Spawns("house")
    public Entity newHouse(SpawnData data) {

        return Entities.builder()
                .type(GameProjectType.HOUSE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();

    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {


        return Entities.builder()
                .type(GameProjectType.ENEMY)
                .from(data)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width") / 2, Color.RED))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();


    }

    @Spawns("battleEnemy")
    public Entity newBattleEnemy(SpawnData data) {


        return Entities.builder()
                .type(GameProjectType.BATTLEENEMY)
                .from(data)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width") / 2, Color.RED))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new SelectableComponent(true))
                .build();


    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {

        return Entities.builder()
                .type(GameProjectType.BORDER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }


}
