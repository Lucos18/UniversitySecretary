package Users;

public class Users {
    String name;
    String surname;
    String dateB;
    String cityB;
    String sex;
    String email;
    String password;
    boolean role = false;
    public Users(String name, String surname, String dateB, String cityB, String sex, String email, String password)
    {
        this.name = name;
        this.surname = surname;
        this.dateB = dateB;
        this.cityB = cityB;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }
    public Users(String name, String surname, String email, String password, Boolean role)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

