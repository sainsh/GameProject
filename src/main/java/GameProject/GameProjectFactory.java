package GameProject;

import GameProject.Common.GameProjectType;
import GameProject.Components.EnemyComponent;
import GameProject.Components.EnemyTypes.Brute;
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
    public Entity newHouse(SpawnData data) {    //creates a house entity which is impassable

        return Entities.builder()
                .type(GameProjectType.HOUSE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();

    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {    //creates an enemy. was previously used when enemies where a part of the map files


        return Entities.builder()
                .type(GameProjectType.ENEMY)
                .from(data)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width") / 2, Color.RED))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();


    }

    @Spawns("battleEnemy")
    public Entity newBattleEnemy(SpawnData data) {  //creates a battle enemy, was previously used when enemies where a part of the map files


        return Entities.builder()
                .type(GameProjectType.BATTLE_ENEMY)
                .from(data)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width") / 2, Color.RED))
                .with(new PhysicsComponent())
                .with(new SelectableComponent(true))
                .with(new EnemyComponent(new Brute(),1,0))
                .build();


    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {   //creates an impassable border, used for water, big bushes, trees and fences

        return Entities.builder()
                .type(GameProjectType.BORDER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("warp")
    public Entity newWarp(SpawnData data,GameProjectType type) {    //creates warp points



        return Entities.builder()
                .type(type)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .build();
    }
        //these next warps are used to differentiate the different directions
    @Spawns("warpN")
    public Entity newWarpN(SpawnData data){

        return newWarp(data,GameProjectType.WARP_N);
    }
    @Spawns("warpS")
    public Entity newWarpS(SpawnData data){

        return newWarp(data,GameProjectType.WARP_S);
    }
    @Spawns("warpE")
    public Entity newWarpE(SpawnData data){

        return newWarp(data,GameProjectType.WARP_E);
    }
    @Spawns("warpW")
    public Entity newWarpW(SpawnData data){

        return newWarp(data,GameProjectType.WARP_W);
    }

    @Spawns("chest")
    public Entity newChest(SpawnData data){     //creates a chest

        return Entities.builder()
                .type(GameProjectType.CHEST)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }


}
