package project.FileService;

public class Accounts {
    private String name, surname, login, password;
    private int number;
    private long telephone;

    public Accounts(String name, String surname, String login, String password, long telephone, int number) {
        setName(name);
        setSurname(surname);
        setLogin(login);
        setPassword(password);
        setTelephone(telephone);
        setNumber(number);
    }
    public Accounts(){}

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return Long.toString(telephone);
    }

    public String getNumber() {
        return Integer.toString(number);
    }
}
