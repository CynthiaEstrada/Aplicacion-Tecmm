package mx.com.othings.edcore.Activities.BlocDeNotas.pojos;

import java.io.Serializable;

/*
 *Clase POJO de la tabla USER de la BDD
 */
public class User implements Serializable {

    private Integer userId;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String email, String password) {
        this.userId = userId;
    }

    public User(String email, String password) {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                '}';
    }
}
