/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package Components;

import Common.EquipPlace;
import Components.Equipment.Armor;
import Components.Equipment.Equipment;
import Components.Equipment.Weapon;
import Components.Professions.Profession;
import Components.Races.Race;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PlayerComponent extends Component {

    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int armorBonus = 10;
    private Profession profession;
    private Race race;
    private int exp;
    private List<String> armorProficiencies = new ArrayList<>();
    private List<String> weaponProficiencies = new ArrayList<>();
    private ObservableList<Equipment> equipment = FXCollections.observableArrayList();
    private Equipment weapon;
    private Equipment armor;
    private Equipment shield;
    private int lvl;
    private int attackBonus;

    private IntegerProperty healthProperty;
    private IntegerProperty maxHealthProperty;
    private IntegerProperty manaProperty;
    private IntegerProperty maxManaProperty;
    private IntegerProperty expProperty;
    private IntegerProperty armorBonusProperty;
    private IntegerProperty attackBonusProperty;


    private static final float SPEED_DECAY = 0.66f;

    private PhysicsComponent physics;


    private float speed = 0;

    private Vec2 velocity = new Vec2();


    public PlayerComponent(PhysicsComponent physics, Profession profession, Race race) {
        this.physics = physics;
        this.profession = profession;
        this.race = race;
        this.health = profession.getStartHealth() + race.getStartHealth();
        this.maxHealth = this.health;
        this.mana = profession.getStartMana() + race.getStartMana();
        this.maxMana = this.mana;

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
            if (item.getClass() == Armor.class && !item.getType().equals("shield")) {
                armor = item;
            } else if (item.getClass() == Armor.class && item.getType().equals("shield")) {
                shield = item;
            }
        }
        armorBonus = 10;
        if (armor != null) {
            armorBonus += ((Armor) armor).getArmorBonus();
        }
        if (shield != null) {
            armorBonus += ((Armor) shield).getArmorBonus();
        }

        for (Equipment item : equipment) {

            if (item.getClass() == Weapon.class) {
                weapon = item;
            }

        }

        lvl = 1;
        this.attackBonus = profession.getStartAttackBonus();

        healthProperty = new SimpleIntegerProperty(health);
        maxHealthProperty = new SimpleIntegerProperty(maxHealth);
        manaProperty = new SimpleIntegerProperty(mana);
        maxManaProperty = new SimpleIntegerProperty(maxMana);
        expProperty = new SimpleIntegerProperty(exp);
        armorBonusProperty = new SimpleIntegerProperty(armorBonus);
        attackBonusProperty = new SimpleIntegerProperty(attackBonus);

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

        physics.setBodyLinearVelocity(velocity);

    }

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


    public void setPosition(PhysicsComponent physics, float x, float y) {

        Point2D point = new Point2D(x, y);
        getEntity().removeComponent(PhysicsComponent.class);
        getEntity().setPosition(point);
        physics.setBodyType(BodyType.DYNAMIC);
        getEntity().addComponent(physics);

    }

    public boolean getDamaged(int dmg) {
        health -= dmg;
        healthProperty.setValue(health);
        if (health <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void gainExp(int exp) {
        this.exp += exp;
        expProperty.setValue(this.exp);
        if (this.exp >= 10 + Math.pow(lvl - 1, 2) * 10) {
            lvl++;
            armorBonus++;
            maxHealth += 20;
            health = maxHealth;
            mana += 2;
            maxMana +=2;
            ((Weapon) weapon).setDamage(((Weapon) weapon).getDamage() + 2);
            attackBonus++;
            System.out.println("gained a lvl");


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


        velocity.set(0, 0);
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

    public void unEquipItem(EquipPlace place) {

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
