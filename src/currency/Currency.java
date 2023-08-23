/*
This class represnet a general currency. It should be inherited by a specific currency.
 */

package currency;

public abstract class Currency implements Convertable{
    private double amount;

    public Currency() {this(0);}

    public Currency(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract <T extends Currency> double addAmount(T income);

    public abstract <T extends Currency> T deductAmount(double amount);

    @Override
    public  String toString(){
        return amount + ";" + getClass().getSimpleName();
    }
}
