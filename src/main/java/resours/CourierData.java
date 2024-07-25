package resours;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierData {

private String login;
private String password;
private String firstName;

    public CourierData(String login, String password, String firstName){
    this.password = password;
    this.login = login;
    this.firstName = firstName;
    }

    public CourierData(String login, String password){
        this.password = password;
        this.login = login;
    }

    public CourierData(){
    }


}
