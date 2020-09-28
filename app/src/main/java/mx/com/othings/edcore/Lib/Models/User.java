package mx.com.othings.edcore.Lib.Models;

public class User {

    private String registration_tag;
    private String password;

    public User(){}

    public User(String registration_tag, String password) {
        this.registration_tag = registration_tag;
        this.password = password;
    }

    public String getRegistration_tag() {
        return registration_tag;
    }

    public void setRegistration_tag(String registration_tag) {
        this.registration_tag = registration_tag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
