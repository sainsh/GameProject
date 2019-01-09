/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package Common;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */

public class PlayerControl extends Component {


    private PhysicsComponent physics;


    private double speed = 0;

    public PlayerControl(PhysicsComponent physics) {
        this.physics = physics;

    }


    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;

    }

    public void up() {
        physics.setLinearVelocity(0, -100);


    }

    public void down() {
        physics.setLinearVelocity(0, 100);
    }

    public void left() {
        physics.setLinearVelocity(-100, 0);
    }

    public void right() {
        physics.setLinearVelocity(100, 0);
    }


}
