package GameProject;

import java.util.ArrayList;
import java.util.List;

public class GameWorldEntities {    //used to add enemies to map

    private List[][] enemies = new List[8][8];  // an array of lists the size of the game world



    public GameWorldEntities() {


    }

    public List[][] loadEnemies() {


        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies.length; j++) {      //creates a list for each part of the array
                enemies[i][j] = new ArrayList();
            }
        }
        //format: type, number, x, y
        enemies[0][0].add("brute,1,4,4");   //creates each enemy with type, number and coordinates to the list for each map of the game world
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
        enemies[1][1].add("brute,2,7,5");
        enemies[1][1].add("cultist,1,8,6");
        enemies[2][1].add("brute,2,8,4");
        enemies[3][1].add("cultist,4,12,7");
        enemies[4][1].add("cultist,2,7,4");
        enemies[6][1].add("thug,4,7,6");

        enemies[0][2].add("brute,4,9,2");
        enemies[1][2].add("thug,4,7,6");
        enemies[2][2].add("thug,2,8,5");
        enemies[2][2].add("cultist,4,9,6");
        enemies[3][2].add("brute,4,6,6");
        enemies[4][2].add("thug,3,6,5");
        enemies[6][2].add("thug,3,8,6");
        enemies[7][2].add("thug,5,7,6");

        enemies[0][3].add("cultist,2,8,4");
        enemies[1][3].add("thug,2,6,7");
        enemies[1][3].add("thug,2,7,7");
        enemies[2][3].add("cultist,4,9,3");
        enemies[3][3].add("brute,2,10,7");
        enemies[5][3].add("brute,4,10,4");
        enemies[6][3].add("cultist,5,4,4");

        enemies[0][4].add("thug,4,7,4");
        enemies[2][4].add("brute,2,7,7");
        enemies[3][4].add("cultist,3,10,6");
        enemies[5][4].add("thug,3,4,5");
        enemies[6][4].add("boss,1,6,8");

        enemies[1][5].add("brute,2,7,4");
        enemies[1][5].add("thug,3,6,8");
        enemies[2][5].add("cultist,2,4,6");
        enemies[2][5].add("thug,2,10,3");
        enemies[4][5].add("cultist,4,8,5");
        enemies[4][5].add("cultist,4,9,5");
        enemies[5][5].add("thug,2,7,7");

        enemies[0][6].add("brute,2,9,7");
        enemies[1][6].add("brute,4,14,7");
        enemies[2][6].add("thug,4,2,8");
        enemies[4][6].add("cultist,2,13,4");
        enemies[5][6].add("cultist,1,11,3");
        enemies[5][6].add("cultist,2,11,4");
        enemies[5][6].add("cultist,3,10,4");
        enemies[6][6].add("brute,2,6,8");

        enemies[0][7].add("brute,4,6,6");
        enemies[0][7].add("brute,4,9,6");
        enemies[1][7].add("cultist,3,13,4");
        enemies[2][7].add("cultist,2,4,5");
        enemies[2][7].add("brute,2,4,6");
        enemies[3][7].add("brute,2,6,7");
        enemies[4][7].add("brute,2,4,4");
        enemies[4][7].add("cultist,3,7,4");
        enemies[4][7].add("thug,4,12,5");
        enemies[5][7].add("thug,5,10,4");
        enemies[5][7].add("brute,2,10,6");
        enemies[7][7].add("brute,4,10,9");
        enemies[7][7].add("brute,5,14,5");

        return enemies; //returns the array of lists
    }


}
