package resours;

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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
