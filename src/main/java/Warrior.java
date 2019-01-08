import java.util.ArrayList;
import java.util.List;

public class Warrior implements Profession{

    private int startHealth;
    private int startMana;
    private List<String> armorProficiencies;
    private List<String> weaponProficiencies;
    private List<Equipment> startingEquiptment;


    public Warrior() {
        startHealth = 100;
        startMana = 10;
        armorProficiencies = new ArrayList<>();
        armorProficiencies.add("light");
        armorProficiencies.add("medium");
        armorProficiencies.add("heavy");
        armorProficiencies.add("shield");
        weaponProficiencies = new ArrayList<>();
        weaponProficiencies.add("light");
        weaponProficiencies.add("one-handend");
        weaponProficiencies.add("two-handed");
        weaponProficiencies.add("ranged");
        startingEquiptment = new ArrayList<>();
        startingEquiptment.add(new Weapon((int)(Math.random()*4)));
        startingEquiptment.add(new Armor("medium"));
    }

    public int getStartHealth() {
        return startHealth;
    }

    public void setStartHealth(int startHealth) {
        this.startHealth = startHealth;
    }

    public int getStartMana() {
        return startMana;
    }

    public void setStartMana(int startMana) {
        this.startMana = startMana;
    }

    public List<String> getArmorProficiencies() {
        return armorProficiencies;
    }

    public void setArmorProficiencies(List<String> armorProficiencies) {
        this.armorProficiencies = armorProficiencies;
    }

    public List<String> getWeaponProficiencies() {
        return weaponProficiencies;
    }

    public void setWeaponProficiencies(List<String> weaponProficiencies) {
        this.weaponProficiencies = weaponProficiencies;
    }

    public List<Equipment> getStartingEquiptment() {
        return startingEquiptment;
    }

    public void setStartingEquiptment(List<Equipment> startingEquiptment) {
        this.startingEquiptment = startingEquiptment;
    }
}
