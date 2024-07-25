package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CourierInSystemWithoutLoginData {

    private String password;

    public CourierInSystemWithoutLoginData (String password){
        this.password = password;
    }

    public CourierInSystemWithoutLoginData(){
    }
}
