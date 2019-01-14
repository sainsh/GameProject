package Components;

import Components.Professions.Profession;
import com.almasb.fxgl.entity.component.Component;

public class ProfessionComponent extends Component {

    Profession profession;

    public ProfessionComponent(Profession profession) {
        this.profession = profession;


    }
}
