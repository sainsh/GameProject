package Components.EnemyTypes;

import javafx.util.Pair;

public interface Enemy {


    boolean takeDamaged(int damage);

    public int getHealth();

    public void setHealth(int health);

    public int getMaxHealth();

    public void setMaxHealth(int maxHealth);

    public int getDamage();

    public void setDamage(int damage);

    public int getExp();

    public void setExp(int exp);

    public Pair<String, Integer> preferredAttack();

    public int getArmor();

    public void setArmor(int armor);

    int getAttackBonus();

}
