/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package Components;

import Components.Equipment.Armor;
import Components.Equipment.Equipment;
import Components.Professions.Profession;
import Components.Races.Race;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

import java.util.ArrayList;
import java.util.List;

public class PlayerControl extends Component {

    private int health;
    private int maxHealth;
    private int mana;
    private int armorBonus = 10;
    private Profession profession;
    private Race race;
    private int exp;
    private List<String> armorProficiencies = new ArrayList<>();
    private List<String> weaponProficiencies = new ArrayList<>();
    private List<Equipment> equipment = new ArrayList<>();

    private static final float SPEED_DECAY = 0.66f;

    private PhysicsComponent physics;


    private float speed = 0;

    private Vec2 velocity = new Vec2();

    public PlayerControl(PhysicsComponent physics, Profession profession, Race race) {
        this.physics = physics;
        this.profession=profession;
        this.race = race;
        this.health = profession.getStartHealth() + race.getStartHealth();
        this.maxHealth = this.health;
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

        for (Equipment item : equipment) {
            if (item.getClass() == Armor.class) {
                armorBonus += ((Armor) item).getArmorBonus();
            }
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

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getArmorBonus() {
        return armorBonus;
    }

    public void setArmorBonus(int armorBonus) {
        this.armorBonus = armorBonus;
    }



    @Override
    public void onUpdate(double tpf) {
        speed = (float)tpf * 600;

        velocity.mulLocal(SPEED_DECAY);

        physics.setBodyLinearVelocity(velocity);

    }

    public void up() {
        velocity.set(0,speed);
    }

    public void down() {
        velocity.set(0,-speed);

    }

    public void left() {
        velocity.set(-speed,0);

    }

    public void right() {
        velocity.set(speed,0);
    }

    public void moveTowards(Entity enemy){

        //velocity.set((float) enemy.getX()*-1,(float)enemy.getY());

        //velocity.set(enemy.get)
    }
}
