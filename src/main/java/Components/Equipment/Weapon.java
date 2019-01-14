package Components.Equipment;

import Components.Equipment.Equipment;

public class Weapon implements Equipment {

    private String name;
    private String type;
    private int damage;
    private int range = 1;
    private String[] randomType = {"light", "one-handed", "two-handed", "ranged"};
    private String[] lightWeapons = {"dagger", "light mace", "short sword", "hand axe"};
    private String[] oneHandedWeapons = {"long sword", "morningstar", "battleaxe", "light flail"};
    private String[] twoHandedWeapons = {"great sword", "great axe", "maul", "lance"};
    private String[] rangedWeapons = {"bow", "crossbow", "javelin", "boomerang"};


    public Weapon(int v) {

        type = randomType[v];
        damage = determineDamage(type);
        name = determineName(type);


    }

    private String determineName(String type) {

        if (type.equals(randomType[0])) {
            return lightWeapons[(int) (Math.random() * (lightWeapons.length-1))];
        } else if (type.equals(randomType[1])) {
            return oneHandedWeapons[(int)(Math.random()*(oneHandedWeapons.length-1))];
        } else if (type.equals(randomType[2])){
            return twoHandedWeapons[(int)(Math.random()*(twoHandedWeapons.length-1))];
        }else{
            return rangedWeapons[(int)(Math.random()*(rangedWeapons.length-1))];
        }
    }

    private int determineDamage(String type) {

        if (type.equals(randomType[0])) {
            return 1;
        } else if (type.equals(randomType[1])) {
            return 2;
        } else if (type.equals(randomType[2])) {
            return 3;
        } else if (type.equals(randomType[3])) {
            this.range = 5;
            return 2;
        } else {
            return 0;
        }

    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
