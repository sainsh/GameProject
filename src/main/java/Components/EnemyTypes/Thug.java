package Components.EnemyTypes;

import javafx.util.Pair;

public class Thug implements Enemy {    //enemy type thug

    private int health;
    private int maxHealth;
    private int damage;
    private int exp;
    private int armor;
    private int attackBonus;

    public Thug() {
        health = 15;
        maxHealth = health;
        damage = 2;
        exp = 10;
        armor = 12;
        attackBonus = 1;
    }

    @Override
    public boolean takeDamaged(int damage) {    //apply damage from player, returns false if dead
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
    }   //attack of enemy, and damage type

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
