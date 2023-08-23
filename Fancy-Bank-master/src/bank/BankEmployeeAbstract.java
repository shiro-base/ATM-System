/*
    abstract bank employee class, hold attributes and functions that should be shared by all employees
 */
package bank;

import Main.Login;
import Main.Person;

public abstract class BankEmployeeAbstract extends Person {

    private Bank bankInCharge;
    private BankEmployeeType bankEmployeeType;

    public BankEmployeeAbstract(String name, Login login){
        super(name,login,"","");
    }

    public Bank getBankInCharge() {
        return bankInCharge;
    }

    public BankEmployeeType getBankEmployeeType() {
        return bankEmployeeType;
    }

    public void setBankInCharge(Bank bankInCharge) {
        this.bankInCharge = bankInCharge;
    }


    public void setBankEmployeeType(BankEmployeeType bankEmployeeType) {
        this.bankEmployeeType = bankEmployeeType;
    }
}
