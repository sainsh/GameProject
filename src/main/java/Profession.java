import java.util.List;

public interface Profession {

    int getStartHealth();

    int getStartMana();

    List<String> getArmorProficiencies();

    List<String> getWeaponProficiencies();

    List<Equipment> getStartingEquipment();
}
