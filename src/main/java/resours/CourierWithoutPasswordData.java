package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierWithoutPasswordData {

    private String login;
    private String firstName;

    public CourierWithoutPasswordData (String login, String firstName){
        this.login = login;
        this.firstName = firstName;
    }

    public CourierWithoutPasswordData(){
    }
}
