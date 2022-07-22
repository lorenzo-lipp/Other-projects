package dio.digitalbank;

public class SavingsAccount extends Account {
    SavingsAccount(String owner) {
        super(owner);
    }

    @Override
    public void applyInterest() {
        double interest = this.currency * 0.02;
        this.currency += interest;
        this.logInterest(interest);
    }

    @Override
    public boolean withdraw(Double value) {
        if (value > 1000) {
            return false;
        }
        return super.withdraw(value);
    }
}
