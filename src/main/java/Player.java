import java.util.ArrayList;
import java.util.List;

public class Player {

    private int health;
    private int mana;
    private Profession profession;
    private Race race;
    private int exp;
    private List<String> armorProficiencies = new ArrayList<>();
    private List<String> weaponProficiencies = new ArrayList<>();
    private List<Equipment> equipment = new ArrayList<>();

    public Player(Profession profession, Race race) {

        this.profession = profession;
        this.race = race;
        this.health = profession.getStartHealth() + race.getStartHealth();
        this.mana = profession.getStartMana() + race.getStartMana();
        for (String proficiency : profession.getArmorProficiencies()) {
            armorProficiencies.add(proficiency);

        }
        for (String proficiency : race.getArmorProficiencies()) {
            armorProficiencies.add(proficiency);

        }
        for (int i = 0; i < armorProficiencies.size(); i++) {
            for (int j = 0; j < armorProficiencies.size(); j++) {

                if (j != i) {
                    if (armorProficiencies.get(i).equals(armorProficiencies.get(j))) {
                        armorProficiencies.remove(j);
                    }
                }

            }
        }

        for (String proficiency : profession.getWeaponProficiencies()) {
            weaponProficiencies.add(proficiency);

        }
        for (String proficiency : race.getWeaponProficiencies()) {
            weaponProficiencies.add(proficiency);

        }
        for (int i = 0; i < weaponProficiencies.size(); i++) {
            for (int j = 0; j < weaponProficiencies.size(); j++) {

                if (j != i) {
                    if (weaponProficiencies.get(i).equals(weaponProficiencies.get(j))) {
                        weaponProficiencies.remove(j);
                    }
                }

            }
        }

        for (Equipment item : profession.getStartingEquipment()) {
            equipment.add(item);

        }


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
