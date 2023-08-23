/*
This class records the username and the hashcode of the password of a person
 */

package Main;

public class Login {
    private String userName;
    private String pinHashCode;
    private String securityAnswer;

    public Login(String userName, String pin) {
        this.userName = userName;
        this.pinHashCode = pin;
    }

    public Login(String userName, String pin, String securityAnswer) {
        this(userName, pin);
        this.securityAnswer = securityAnswer;
    }

    public String getUserName() {
        return userName;
    }

    public String getPinHashCode() {
        return pinHashCode;
    }

    public String getSecurityAnswer() {return securityAnswer;}

    public boolean comparePin(String pin)
    {
        return pin.equals(pinHashCode);
    }
}
