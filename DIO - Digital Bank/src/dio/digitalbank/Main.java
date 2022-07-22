package dio.digitalbank;

public class Main {
    public static void main(String[] args) {
        Account johnAccount = new SavingsAccount("John");
        Account maryAccount = new CheckingAccount("Mary");

        johnAccount.deposit(100.0);
        johnAccount.transfer(50.0, maryAccount);

        System.out.println(johnAccount.getHistory());
        System.out.println(maryAccount.getHistory());

        johnAccount.applyInterest();
        maryAccount.applyInterest();

        System.out.println(johnAccount.getHistory());
        System.out.println(maryAccount.getHistory());
        System.out.println("John's currency: $" + johnAccount.getCurrency());
        System.out.println("Mary's currency: $" + maryAccount.getCurrency());
    }
}
