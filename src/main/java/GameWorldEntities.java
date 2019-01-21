import Components.EnemyComponent;
import Components.EnemyTypes.Brute;
import Components.EnemyTypes.Cultist;
import Components.EnemyTypes.Thug;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class GameWorldEntities {

    private List[][] enemies = new List[8][8];
    private int tileSize = 0;


    public GameWorldEntities(int tileSize) {
        this.tileSize = tileSize;

    }

    public List[][] loadEnemies() {


        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies.length; j++) {
                enemies[i][j] = new ArrayList();
            }
        }

        enemies[0][0].add(newBrute(tileSize * 4, tileSize * 7, 1));
        enemies[0][0].add(newBrute(tileSize * 4, tileSize * 5, 1));
        enemies[1][0].add(newThug(tileSize * 7, tileSize * 6, 1));
        enemies[1][0].add(newThug(tileSize * 7, tileSize * 5, 1));


        return enemies;
    }

    private Entity newBrute(int x, int y, int number) {

        return Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(x, y)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(new Brute(), number))

                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .build();
    }

    private Entity newCultist(int x, int y, int number) {

        return Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(x, y)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(new Cultist(), number))
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .build();
    }

    private Entity newThug(int x, int y, int number) {

        return Entities.builder()
                .type(GameProjectType.ENEMY)
                .at(x, y)
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(new Thug(), number))
                .viewFromNodeWithBBox(new Circle(tileSize / 2, Color.RED))
                .build();
    }
}
