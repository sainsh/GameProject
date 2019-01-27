package GameProject.Components.Professions;

import GameProject.Components.Equipment.Equipment;

import java.util.List;

public interface Profession {   //interface between playerEntity/playerComponent and profession/class of player

    int getStartHealth();

    int getStartMana();

    List<String> getArmorProficiencies();

    List<String> getWeaponProficiencies();

    List<Equipment> getStartingEquipment();

    String getName();

    int getStartAttackBonus();
}
