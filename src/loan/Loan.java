/*
the class is a single loan borrowed from bank. It contains the collateral, money borrowed, interest rate and due date of a loan
 */

package loan;

import Main.DateSystem;
import currency.Currency;
import currency.CurrencyExchange;

public class Loan<T extends Currency> {
    private T principal;
    private String currency;
    private double interest;
    private String dueDate;
    private int duration;
    private Collateral collateral;

    public Loan(T principal, String dueDate, Collateral collateral) {
        this.principal = principal;
        this.dueDate = dueDate;
        this.collateral = collateral;
        currency = principal.getClass().getSimpleName();
        duration = DateSystem.dateDifference(dueDate);
        interest = duration*0.0005;
    }

    public Loan(T principal,String curr, double interest,String dueDate, Collateral collateral) {
        this.principal = principal;
        this.dueDate = dueDate;
        this.collateral = collateral;
        this.currency = curr;
        this.duration = DateSystem.dateDifference(dueDate);
        this.interest = interest;
    }

    public T getPrincipal() {
        return principal;
    }

    public void setPrincipal(T principal) {
        this.principal = principal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Collateral getCollateral() {
        return collateral;
    }

    public void setCollateral(Collateral collateral) {
        this.collateral = collateral;
    }

    public double getInterestMoney()
    {   double interestFactor;
        if (DateSystem.dateDifference(dueDate) >= 0) {
            interestFactor = (double) (duration - DateSystem.dateDifference(dueDate)) / duration;
        }
        else {
            interestFactor = 1 + (double) (Math.abs(DateSystem.dateDifference(dueDate)) / duration) * 2;
        }
        return CurrencyExchange.round2decimal(interestFactor * interest * principal.getAmount());
    }

    public double getTotalAmount()
    {
        return principal.getAmount() + getInterestMoney();
    }

    @Override
    public String toString(){
        return String.join(";",principal.toString(),Double.toString(interest),dueDate,collateral.toString());
    }
}
