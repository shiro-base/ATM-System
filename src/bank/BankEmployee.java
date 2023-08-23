/*
    concrete subclass of bank employee
 */
package bank;

import Main.Login;

public class BankEmployee extends BankEmployeeAbstract {
    public BankEmployee(String name, Login login, Bank bank) {
        super(name,login);
        setBankInCharge(bank);
        setBankEmployeeType(BankEmployeeType.EMPLOYEE);
    }
}
