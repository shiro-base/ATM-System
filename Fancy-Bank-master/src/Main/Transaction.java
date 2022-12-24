/*
It contains all information of 1 transaction
 */

package Main;

import java.time.Instant;

public class Transaction {
    private int id;
    private String date;
    private String timeStamp;
    private double sendingAmount;
    private double receivingAmount;
    private String sendingCurrency;
    private String receivingCurrency;
    private String to;
    private String from;

    public Transaction(double sendingAmount, double receivingAmount, String sendingCurrency, String receivingCurrency, String to, String from)
    {
        this.sendingAmount = sendingAmount;
        this.receivingAmount = receivingAmount;
        this.sendingCurrency = sendingCurrency;
        this.receivingCurrency = receivingCurrency;
        this.to = to;
        this.from = from;
        date = DateSystem.getToday();
        timeStamp = Instant.now().toString();
    }
    public Transaction(int id, String Date, String timeStamp, String from, String sendingCurrency,String to, double sendingAmount,   String receivingCurrency,double receivingAmount)
    {
        setId(id);
        setSendingAmount(sendingAmount);
        setReceivingAmount(receivingAmount);
        setSendingCurrency(sendingCurrency);
        setReceivingCurrency(receivingCurrency);
        setTo(to);
        setFrom(from);
        setDate(Date);
        setTimeStamp(timeStamp);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getSendingAmount() {
        return sendingAmount;
    }

    public void setSendingAmount(double sendingAmount) {
        this.sendingAmount = sendingAmount;
    }

    public double getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(double receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public String getSendingCurrency() {
        return sendingCurrency;
    }

    public void setSendingCurrency(String sendingCurrency) {
        this.sendingCurrency = sendingCurrency;
    }

    public String getReceivingCurrency() {
        return receivingCurrency;
    }

    public void setReceivingCurrency(String receivingCurrency) {
        this.receivingCurrency = receivingCurrency;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    @Override
    public String toString()
    {
        return String.join(";", Integer.toString(id), date, timeStamp,
                from, sendingCurrency,to, Double.toString(sendingAmount),
                 receivingCurrency, Double.toString(receivingAmount));
    }
}