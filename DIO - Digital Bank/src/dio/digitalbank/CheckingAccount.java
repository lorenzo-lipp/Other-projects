package dio.digitalbank;

public class CheckingAccount extends Account {
    CheckingAccount(String owner) {
        super(owner);
    }

    @Override
    public void applyInterest() {
        double interest = this.currency * 0.01;
        this.currency += interest;
        this.logInterest(interest);
    }
}
