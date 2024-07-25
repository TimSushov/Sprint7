package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierWithoutLoginData {

    private String password;
    private String firstName;

    public CourierWithoutLoginData (String password, String firstName){
        this.password = password;
        this.firstName = firstName;
    }

    public CourierWithoutLoginData(){
    }
}
