package GameProject.Components.Equipment;


public class Weapon implements Equipment {  //equipment type, weapon

    private String name;
    private String type;
    private int damage;
    private int range = 1;
    private String[] randomType = {"light", "one-handed", "two-handed", "ranged"};  //used to determine type of weapon
    private String[] lightWeapons = {"dagger", "light mace", "short sword", "hand axe"};    //used too determine type of light weapon
    private String[] oneHandedWeapons = {"long sword", "morningstar", "battleaxe"};//used too determine type of one-handed weapon
    private String[] twoHandedWeapons = {"great sword", "great axe", "maul"};//used too determine type of two-handed weapon
    private String[] rangedWeapons = {"bow", "crossbow", "boomerang"};//used too determine type of ranged weapon


    public Weapon(int v) {

        type = randomType[v];   //create weapon of type v
        damage = determineDamage(type); //sets damage of weapon based on type
        name = determineName(type); //determine name of weapon


    }

    private String determineName(String type) { //chooses name from type

        if (type.equals(randomType[0])) {
            return lightWeapons[(int) (Math.random() * (lightWeapons.length - 1))];
        } else if (type.equals(randomType[1])) {
            return oneHandedWeapons[(int) (Math.random() * (oneHandedWeapons.length - 1))];
        } else if (type.equals(randomType[2])) {
            return twoHandedWeapons[(int) (Math.random() * (twoHandedWeapons.length - 1))];
        } else {
            return rangedWeapons[(int) (Math.random() * (rangedWeapons.length - 1))];
        }
    }

    private int determineDamage(String type) {  //sets damage based on type

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
