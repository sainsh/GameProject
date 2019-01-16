package Components;

import Components.EnemyTypes.Enemy;
import com.almasb.fxgl.entity.component.Component;

public class EnemyComponent extends Component {

    private Enemy enemy;

    public EnemyComponent(Enemy enemy){
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
