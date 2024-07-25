package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CourierInSystemWithoutPasswordData {

    private String login;

    public CourierInSystemWithoutPasswordData (String login){
        this.login = login;
    }

    public CourierInSystemWithoutPasswordData(){
    }
}
