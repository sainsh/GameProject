package Components.Professions;

import Components.Equipment.Armor;
import Components.Equipment.Equipment;
import Components.Equipment.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Warrior implements Profession {

    private String name = "Warrior";
    private int startHealth;
    private int startMana;
    private List<String> armorProficiencies;
    private List<String> weaponProficiencies;
    private List<Equipment> startingEquipment;
    private int startAttackBonus;


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
        startingEquipment = new ArrayList<>();
        startingEquipment.add(new Weapon((int) (Math.random() * 4)));
        startingEquipment.add(new Armor("medium"));
        startAttackBonus = 2;
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

    public List<Equipment> getStartingEquipment() {
        return startingEquipment;
    }

    public void setStartingEquipment(List<Equipment> startingEquipment) {
        this.startingEquipment = startingEquipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartAttackBonus() {
        return startAttackBonus;
    }

    public void setStartAttackBonus(int startAttackBonus) {
        this.startAttackBonus = startAttackBonus;
    }
}
