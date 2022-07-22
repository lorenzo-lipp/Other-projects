package dio.digitalbank;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private static int LAST_ACC_NUMBER = 1;

    protected int number;

    protected int agency;

    protected double currency;

    protected List<String> history;

    public String owner;

    Account(String owner) {
        this.owner = owner;
        this.agency = 200;
        this.currency = 0d;
        this.number = LAST_ACC_NUMBER++;
        this.history = new ArrayList<>();
    }

    public double getCurrency() {
        return currency;
    }

    public int getAgency() {
        return agency;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getHistory() {
        return history;
    }

    public boolean deposit(Double value) {
        this.currency += value;
        history.add("DEPOSIT - $" + value);
        return true;
    }

    public boolean deposit(Double value, String from) {
        this.currency += value;
        history.add("TRANSFER - $" + value + " FROM " + from);
        return true;
    }

    public boolean withdraw(Double value) {
        if (value > this.currency) {
            return false;
        }
        this.currency -= value;
        history.add("WITHDRAW - $" + value);
        return true;
    }

    public boolean withdraw(Double value, String to) {
        if (value > this.currency) {
            return false;
        }
        this.currency -= value;
        history.add("TRANSFER - $" + value + " TO " + to);
        return true;
    }

    public boolean transfer(Double value, Account acc) {
        Boolean success = this.withdraw(value, acc.owner);
        if (!success) {
            return false;
        }
        acc.deposit(value, this.owner);
        return true;
    }

    public void logInterest(Double value) {
        history.add("INTEREST - $" + value);
    }

    public void applyInterest() {

    }
}
