public class Main {

    //CHALLENGE SCENARIO:
    //2 PEOPLE ARE USING A JOINT BANK ACCOUNT.
    //CREATE AND START TWO THREADS THAT USE THE SAME BANK ACCOUNT INSTANCE AND AN INITIAL BALANCE OF £1000
    //ONE WILL DEPOSIT £300.00 AND THEN WITHDRAW £50
    //THE OTHER WILL DEPOSIT £203.75 AND WITHDRAW £100
    //MAKE THE BANK ACCOUNT CLASS THREAD-SAFE
    public static void main(String[] args) {

        BankAccount sharedAccount = new BankAccount("12345-678", 1000.00);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
               sharedAccount.deposit(300);
               sharedAccount.withdraw(50);
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sharedAccount.deposit(203.75 );
                sharedAccount.withdraw(100);
            }
        });

        thread1.start();
        thread2.start();

    }
}
