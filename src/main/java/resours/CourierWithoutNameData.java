package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CourierWithoutNameData {

    private String login;
    private String password;

    public CourierWithoutNameData (String login, String password){
        this.password = password;
        this.login = login;
    }

    public CourierWithoutNameData(){
    }
}
