import Common.PlayerControl;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class GameProjectFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameProjectType.PLAYER)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(64, 64, Color.BLUE))
                .with(new PlayerControl(physics))
                .with(physics)
                .with(new CollidableComponent(true))
                .build();
    }

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
}
