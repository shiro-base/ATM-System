/*
This class manages the currency exchange rate and calculate the currency exchange between currencies.
 */

package currency;

import static Utils.Constants.*;

public class CurrencyExchange {
    private static final double USDtoCNY = 7;
    private static final double USDtoINR = 80;

    public static double convertUSDtoCNY(double USD)
    {
        return USD * USDtoCNY;
    }

    public static double convertUSDtoINR(double USD)
    {
        return USD * USDtoINR;
    }

    public static double convertCNYtoUSD(double CNY)
    {
        return  round2decimal(CNY / USDtoCNY);
    }

    public static double convertINRtoUSD(double INR)
    {
        return round2decimal(INR / USDtoINR);
    }

    public static double convertINRtoCNY(double INR)
    {
        return convertUSDtoCNY(convertINRtoUSD(INR));
    }

    public static double convertCNYtoINR(double CNY)
    {
        return convertUSDtoINR(convertCNYtoUSD(CNY));
    }

    public static double convert(String currency1, String currency2, double amount)
    {
        if (currency1.equals(USD)) {
            if (currency2.equals(INR)) {
                return convertUSDtoINR(amount);
            } else if (currency2.equals(CNY)) {
                return convertUSDtoCNY(amount);
            } else {
                return amount;
            }
        } else if (currency1.equals(CNY)) {
            if (currency2.equals(USD)) {
                return convertCNYtoUSD(amount);
            } else if (currency2.equals(INR)) {
                return convertCNYtoINR(amount);
            } else {
                return amount;
            }
        } else {
            if (currency2.equals(USD)) {
                return convertINRtoUSD(amount);
            } else if (currency2.equals(CNY)) {
                return convertINRtoCNY(amount);
            } else {
                return amount;
            }
        }
    }

    public static double round2decimal(double num)
    {
        return (double) Math.round(num * 100.0) / 100;
    }
}
