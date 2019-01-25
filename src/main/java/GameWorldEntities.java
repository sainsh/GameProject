import java.util.ArrayList;
import java.util.List;

public class GameWorldEntities {

    private List[][] enemies = new List[8][8];



    public GameWorldEntities() {


    }

    public List[][] loadEnemies() {


        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies.length; j++) {
                enemies[i][j] = new ArrayList();
            }
        }
        //format: type, number, x, y
        enemies[0][0].add("brute,1,4,4");
        enemies[0][0].add("brute,1,4,6");
        enemies[1][0].add("thug,1,7,5");
        enemies[1][0].add("thug,1,7,6");
        enemies[2][0].add("cultist,3,4,4");
        enemies[3][0].add("brute,2,5,6");
        enemies[4][0].add("thug,3,4,6");
        enemies[4][0].add("thug,2,6,6");
        enemies[5][0].add("brute,4,10,3");
        enemies[5][0].add("cultist,2,4,6");
        enemies[6][0].add("thug,2,6,6");
        enemies[7][0].add("thug 2,5,5");
        enemies[7][0].add("thug,3,10,7");

        enemies[0][1].add("cultist,4,7,5");
        enemies[1][1].add();



        return enemies;
    }


}
