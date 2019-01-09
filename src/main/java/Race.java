import java.util.List;

public interface Race {


    int getStartHealth();

    int getStartMana();

    List<String> getArmorProficiencies();

    List<String> getWeaponProficiencies();

    String getName();
}
