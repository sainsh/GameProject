/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package GameProject.Components;

import GameProject.Common.EquipPlace;
import GameProject.Components.Equipment.Armor;
import GameProject.Components.Equipment.Equipment;
import GameProject.Components.Equipment.Weapon;
import GameProject.Components.Professions.Profession;
import GameProject.Components.Races.Race;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PlayerComponent extends Component {    //used to store info about the player and functions of the player

    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int armorBonus = 10;
    private Profession profession;
    private Race race;
    private int exp;
    private List<String> armorProficiencies = new ArrayList<>();    //what armors the player can use
    private List<String> weaponProficiencies = new ArrayList<>();   //what weapons the player can use
    private ObservableList<Equipment> equipment = FXCollections.observableArrayList();  //an observable list of items the player has
    private Equipment weapon;
    private Equipment armor;
    private Equipment shield;
    private int lvl;
    private int attackBonus;
    //these properties are used as intermediate for the integer values above
    private IntegerProperty healthProperty;
    private IntegerProperty maxHealthProperty;
    private IntegerProperty manaProperty;
    private IntegerProperty maxManaProperty;
    private IntegerProperty expProperty;
    private IntegerProperty armorBonusProperty;
    private IntegerProperty attackBonusProperty;


    private static final float SPEED_DECAY = 0.66f; // makes the player movement stop after a small time, so the player doesn't just keep moving until another directional button is pressed

    private PhysicsComponent physics;


    private float speed = 0;

    private Vec2 velocity = new Vec2(); //used as movement


    public PlayerComponent(PhysicsComponent physics, Profession profession, Race race) {
        this.physics = physics;
        this.profession = profession;
        this.race = race;
        this.health = profession.getStartHealth() + race.getStartHealth();  //get starting health from profession/class and race
        this.maxHealth = this.health;
        this.mana = profession.getStartMana() + race.getStartMana();    //get starting mana from prodession/class and race
        this.maxMana = this.mana;

        for (String proficiency : profession.getArmorProficiencies()) { //adds proficiencies
            armorProficiencies.add(proficiency);

        }
        for (String proficiency : race.getArmorProficiencies()) {//adds proficiencies
            armorProficiencies.add(proficiency);

        }
        for (int i = 0; i < armorProficiencies.size(); i++) {// removed duplicates
            for (int j = 0; j < armorProficiencies.size(); j++) {

                if (j != i) {
                    if (armorProficiencies.get(i).equals(armorProficiencies.get(j))) {
                        armorProficiencies.remove(j);
                    }
                }

            }
        }

        for (String proficiency : profession.getWeaponProficiencies()) {    //adds proficiencies
            weaponProficiencies.add(proficiency);

        }
        for (String proficiency : race.getWeaponProficiencies()) {//adds proficiencies
            weaponProficiencies.add(proficiency);

        }
        for (int i = 0; i < weaponProficiencies.size(); i++) {
            for (int j = 0; j < weaponProficiencies.size(); j++) {

                if (j != i) {
                    if (weaponProficiencies.get(i).equals(weaponProficiencies.get(j))) {
                        weaponProficiencies.remove(j);  //removes duplicates
                    }
                }

            }
        }

        for (Equipment item : profession.getStartingEquipment()) {  //get starting equipment from profession
            equipment.add(item);

        }

        for (Equipment item : equipment) {  //equip armor and shield
            if (item.getClass() == Armor.class && !item.getType().equals("shield")) {
                armor = item;
            } else if (item.getClass() == Armor.class && item.getType().equals("shield")) {
                shield = item;
            }
        }
        armorBonus = 10;
        if (armor != null) {    //set armor bonus
            armorBonus += ((Armor) armor).getArmorBonus();
        }
        if (shield != null) {   //set armor bonus
            armorBonus += ((Armor) shield).getArmorBonus();
        }

        for (Equipment item : equipment) {

            if (item.getClass() == Weapon.class) {
                weapon = item;  //equip weapon
            }

        }

        lvl = 1;
        this.attackBonus = profession.getStartAttackBonus();    //set staring attack bonus
        //these set the properties as the integer values of above
        healthProperty = new SimpleIntegerProperty(health);
        maxHealthProperty = new SimpleIntegerProperty(maxHealth);
        manaProperty = new SimpleIntegerProperty(mana);
        maxManaProperty = new SimpleIntegerProperty(maxMana);
        expProperty = new SimpleIntegerProperty(exp);
        armorBonusProperty = new SimpleIntegerProperty(armorBonus);
        attackBonusProperty = new SimpleIntegerProperty(attackBonus);

    }
    //getters and setters

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

    public ObservableList<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(ObservableList<Equipment> equipment) {
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

    public Equipment getWeapon() {
        return weapon;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    @Override
    public void onUpdate(double tpf) {
        speed = (float) tpf * 600;

        velocity.mulLocal(SPEED_DECAY);

        physics.setBodyLinearVelocity(velocity);    //slows movement down until stopped,

    }
    // the next four are player movement
    public void up() {
        velocity.set(0, speed);
    }

    public void down() {
        velocity.set(0, -speed);

    }

    public void left() {
        velocity.set(-speed, 0);

    }

    public void right() {
        velocity.set(speed, 0);
    }



    public boolean getDamaged(int dmg) {    //applies damage to player, returns true if not dead
        health -= dmg;
        healthProperty.setValue(health);
        if (health <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void gainExp(int exp) {  //gain exp from killing enemies
        this.exp += exp;
        expProperty.setValue(this.exp);
        if (this.exp >= 10 + Math.pow(lvl - 1, 2) * 10) {   //lvl up,
            lvl++;
            armorBonus++;
            maxHealth += 20;
            health = maxHealth;
            mana += 2;
            maxMana +=2;
            ((Weapon) weapon).setDamage(((Weapon) weapon).getDamage() + 2);
            attackBonus++;



            armorBonusProperty.setValue(armorBonus);
            maxHealthProperty.setValue(maxHealth);
            healthProperty.setValue(health);
            maxManaProperty.setValue(maxMana);
            manaProperty.setValue(mana);
            attackBonusProperty.setValue(attackBonus);

        }
    }

    @Override
    public void onAdded() {


        velocity.set(0, 0); //stop movement when added to entity
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }


    public void setArmor(Armor item) {
    }

    public Equipment getArmor() {
        return armor;
    }

    public Equipment getShield() {
        return shield;
    }

    public void setShield(Equipment shield) {
        this.shield = shield;
    }

    public void unEquipItem(EquipPlace place) { //removes item from equipped items

        if (place == EquipPlace.BODY) {

            armor = null;

        } else if (place == EquipPlace.RIGHT) {

            weapon = null;

        } else if (place == EquipPlace.LEFT) {

            shield = null;
        }
    }

    public int getHealthProperty() {
        return healthProperty.get();
    }

    public IntegerProperty healthPropertyProperty() {
        return healthProperty;
    }

    public void setHealthProperty(int healthProperty) {
        this.healthProperty.set(healthProperty);
    }

    public int getMaxHealthProperty() {
        return maxHealthProperty.get();
    }

    public IntegerProperty maxHealthPropertyProperty() {
        return maxHealthProperty;
    }

    public void setMaxHealthProperty(int maxHealthProperty) {
        this.maxHealthProperty.set(maxHealthProperty);
    }

    public int getManaProperty() {
        return manaProperty.get();
    }

    public IntegerProperty manaPropertyProperty() {
        return manaProperty;
    }

    public void setManaProperty(int manaProperty) {
        this.manaProperty.set(manaProperty);
    }

    public int getMaxManaProperty() {
        return maxManaProperty.get();
    }

    public IntegerProperty maxManaPropertyProperty() {
        return maxManaProperty;
    }

    public void setMaxManaProperty(int maxManaProperty) {
        this.maxManaProperty.set(maxManaProperty);
    }

    public int getExpProperty() {
        return expProperty.get();
    }

    public IntegerProperty expPropertyProperty() {
        return expProperty;
    }

    public void setExpProperty(int expProperty) {
        this.expProperty.set(expProperty);
    }

    public int getArmorBonusProperty() {
        return armorBonusProperty.get();
    }

    public IntegerProperty armorBonusPropertyProperty() {
        return armorBonusProperty;
    }

    public void setArmorBonusProperty(int armorBonusProperty) {
        this.armorBonusProperty.set(armorBonusProperty);
    }

    public int getAttackBonusProperty() {
        return attackBonusProperty.get();
    }

    public IntegerProperty attackBonusPropertyProperty() {
        return attackBonusProperty;
    }

    public void setAttackBonusProperty(int attackBonusProperty) {
        this.attackBonusProperty.set(attackBonusProperty);
    }
}
