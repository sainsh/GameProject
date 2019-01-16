package Components.EnemyTypes;

import javafx.util.Pair;

public class Brute implements Enemy {

    private int health;
    private int maxHealth;
    private int damage;
    private int exp;
    private int armor;

    public Brute() {
        health = 20;
        maxHealth = health;
        damage = 3;
        exp = 10;
        armor = 14;
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
    public Pair<String, Integer> preferredAttack() {

        return new Pair<>("Physical", damage);
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public void setArmor(int armor) {
            this.armor=armor;
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

}
