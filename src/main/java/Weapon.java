public class Weapon implements Equipment {

    private String type;
    private int damage;
    private int range=1;
    private String[] randomType = {"light", "one-handed", "two-handed", "ranged"};


    public Weapon(int v) {

        type = randomType[v];
        damage = determineDamage(type);


    }

    private int determineDamage(String type) {

        if (type.equals(randomType[0])) {
            return 1;
        } else if (type.equals(randomType[1])) {
            return 2;
        }else if(type.equals(randomType[2])){
            return 3;
        }else if(type.equals(randomType[3])){
            this.range = 5;
            return 2;
        }else{
            return 0;
        }

    }


}
