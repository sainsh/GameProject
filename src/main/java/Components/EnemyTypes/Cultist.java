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
        health -= (health - damage);
        if (health > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public void setHealth(int health) {

    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    public void setMaxHealth(int maxHealth) {

    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void setDamage(int damage) {

    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public void setExp(int exp) {

    }

    @Override
    public Pair<String, Integer> preferredAttack() {
        return new Pair<>("magical", damage);
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
