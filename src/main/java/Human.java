import java.util.ArrayList;
import java.util.List;

public class Human implements Race{

    private String name = "Human";
    private int startHealth;
    private int startMana;
    private List<String> weaponProficiencies;
    private List<String> armorProficiencies;

    public Human(){
        startHealth = 20;
        startMana = 2;
        weaponProficiencies = new ArrayList<>();
        weaponProficiencies.add("one-handed");
        weaponProficiencies.add("light");
        armorProficiencies = new ArrayList<>();
        armorProficiencies.add("light");


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

    public List<String> getWeaponProficiencies() {
        return weaponProficiencies;
    }

    public void setWeaponProficiencies(List<String> weaponProficiencies) {
        this.weaponProficiencies = weaponProficiencies;
    }

    public List<String> getArmorProficiencies() {
        return armorProficiencies;
    }

    public void setArmorProficiencies(List<String> armorProficiencies) {
        this.armorProficiencies = armorProficiencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
