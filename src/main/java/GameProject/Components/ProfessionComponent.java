package GameProject.Components;

import GameProject.Components.Professions.Profession;
import com.almasb.fxgl.entity.component.Component;

public class ProfessionComponent extends Component {//used as component of playerEntity to bind a Profession/class

    Profession profession;

    public ProfessionComponent(Profession profession) {
        this.profession = profession;


    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
