package Components;

import Components.Races.Race;
import com.almasb.fxgl.entity.component.Component;

public class RaceComponent extends Component {

    Race race;

    public RaceComponent(Race race) {

        this.race = race;

    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
