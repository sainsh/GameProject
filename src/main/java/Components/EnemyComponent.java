package Components;

import Components.EnemyTypes.Enemy;
import com.almasb.fxgl.entity.component.Component;

public class EnemyComponent extends Component {

    private Enemy enemy;
    private int number;
    private int index;

    public EnemyComponent(Enemy enemy, int number, int index) {
        this.enemy = enemy;
        this.number = number;
        this.index = index;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
