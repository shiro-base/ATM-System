/*
a superclass for bank employees and customers. It has basic infos like name, login info, email and address of each person
 */

package Main;

public class Person {
    private String name;
    private Login login;
    private int id;
    private String address;
    private String email;

    public Person(String name, Login login,String address, String email) {
        this.name = name;
        this.login = login;
        this.address = address;
        this.email = email;
    }

    public Person(String name, Login login,int id,String address, String email) {
        this.name = name;
        this.login = login;
        this.address = address;
        this.email = email;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return String.join(";",Integer.toString(id),name, login.toString(),address,email);
    }
}
