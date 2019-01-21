package Components;

import Components.EnemyTypes.Enemy;
import com.almasb.fxgl.entity.component.Component;

public class EnemyComponent extends Component {

    private Enemy enemy;
    private int number;

    public EnemyComponent(Enemy enemy,int number){
        this.enemy = enemy;
        this.number = number;
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
}
