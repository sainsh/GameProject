/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package Common;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class EnemyControl extends Component {

    private Point2D target;

    public EnemyControl(Point2D target) {
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D position = entity.getComponent(PositionComponent.class).getValue();

        entity.getComponent(PositionComponent.class).translate(target.subtract(position).normalize().multiply(60 * tpf));
    }
}