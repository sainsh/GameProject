package GameProject.Components.Races;

import java.util.List;

public interface Race { //interface between playerEntity/playerComponent and race of player


    int getStartHealth();

    int getStartMana();

    List<String> getArmorProficiencies();

    List<String> getWeaponProficiencies();

    String getName();
}
