package Components.Professions;

import Components.Equipment.Equipment;

import java.util.List;

public interface Profession {

    int getStartHealth();

    int getStartMana();

    List<String> getArmorProficiencies();

    List<String> getWeaponProficiencies();

    List<Equipment> getStartingEquipment();

    String getName();

    int getStartAttackBonus();
}
