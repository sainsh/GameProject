public class Cultist implements Enemy {


    private int health;
    private int maxHealth;
    private int damage;
    private int exp;

    @Override
    public String getDamaged(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() > 0) {
            return "" + getHealth();
        } else{
            return "dead";
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
}
