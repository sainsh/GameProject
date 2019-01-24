package Components.EnemyTypes;

import javafx.util.Pair;

public class Cultist implements Enemy {


    private int health;
    private int maxHealth;
    private int damage;
    private int exp;
    private int armor;
    private int attackBonus;

    public Cultist() {
        health = 10;
        maxHealth = health;
        damage = 3;
        exp = 10;
        armor = 10;
        attackBonus =0;
    }

    @Override
    public boolean takeDamaged(int damage) {
        health -= damage;
        if (health > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public void setExp(int exp) {
        this.exp=exp;
    }

    @Override
    public Pair<String, Integer> preferredAttack() {
        return new Pair<>("Physical", damage);
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public int getAttackBonus() {
        return attackBonus;
    }
}
