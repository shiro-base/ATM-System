/*
a collateral used to borrow loans. It has a evaluation value which determines maximum amount of loan you can borrow.
 */

package loan;

import StockMarket.Sellable;

public class Collateral implements Sellable {
    private String name;
    private double evaluation;

    public Collateral(String name, double evaluation) {
        this.name = name;
        this.evaluation = evaluation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString(){
        return String.join(";",name,Double.toString(evaluation));
    }

    @Override
    public void sell() {

    }
}
