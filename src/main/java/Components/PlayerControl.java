/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package Components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerControl extends Component {

    private static final float SPEED_DECAY = 0.66f;

    private PhysicsComponent physics;


    private float speed = 0;

    private Vec2 velocity = new Vec2();

    public PlayerControl(PhysicsComponent physics) {
        this.physics = physics;

    }


    @Override
    public void onUpdate(double tpf) {
        speed = (float)tpf * 600;

        velocity.mulLocal(SPEED_DECAY);

        physics.setBodyLinearVelocity(velocity);

    }

    public void up() {
        velocity.set(0,speed);
    }

    public void down() {
        velocity.set(0,-speed);

    }

    public void left() {
        velocity.set(-speed,0);

    }

    public void right() {
        velocity.set(speed,0);
    }

    public void moveTowards(Entity enemy){

        //velocity.set((float) enemy.getX()*-1,(float)enemy.getY());

        //velocity.set(enemy.get)
    }
}
