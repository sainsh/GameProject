package Components.EnemyTypes;

import Components.EnemyTypes.Enemy;

public class Brute implements Enemy {
    
    private int health;
    private int maxHealth;
    private int damage;
    private int exp;

    public Brute() {
        health = 20;
        maxHealth = health;
        damage = 3;
        exp = 10;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String getDamaged(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() > 0) {
            return "" + getHealth();
        } else{
            return "dead";
        }

    }

}
