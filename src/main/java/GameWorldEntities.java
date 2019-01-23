import java.util.ArrayList;
import java.util.List;

public class GameWorldEntities {

    private List[][] enemies = new List[8][8];
    private int tileSize = 0;


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


        return enemies;
    }


}
