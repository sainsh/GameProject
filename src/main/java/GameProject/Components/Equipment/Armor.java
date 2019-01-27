package GameProject.Components.Equipment;

public class Armor implements Equipment {   //equipment type, armor

    private String type;
    private int hitpoints;
    private int hardness;
    private int armorBonus;


    public Armor(String type) { //creates an armor based on type
        this.type = type;

        if (type.equals("light")) {
            hitpoints = 20;
            hardness = 10;
            armorBonus = 2;
        } else if (type.equals("medium")) {
            hitpoints = 40;
            hardness = 15;
            armorBonus = 4;
        } else if(type.equals("heavy")){
            hitpoints = 80;
            hardness = 20;
            armorBonus = 8;
        } else if(type.equals("shield")){
            hitpoints = 20;
            hardness = 10;
            armorBonus = 1;
        }


    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public int getArmorBonus() {
        return armorBonus;
    }

    public void setArmorBonus(int armorBonus) {
        this.armorBonus = armorBonus;
    }

    @Override
    public String getName() {
        return getType()+" armor";
    }
}
