public class Player {

    private int health;
    private int mana;
    private Profession profession;
    private Race race;
    private int exp;

    public Player(int health, int mana, Profession profession, Race race,int exp) {
        this.health = health;
        this.mana = mana;
        this.profession = profession;
        this.race = race;
        this.exp=exp;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
