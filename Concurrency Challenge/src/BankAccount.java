import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    //FURTHER CHALLENGE SCENARIO:
    //WRITE A VERSION OF SYNCRONISED METHODS USING A REENTRANT LOCK
    //WRITE A VERSION OF SYNCRONISED METHODS USING A TIMED REENTRANT LOCK
    //NOW UPDATE THE CODE SO THAT THE STATUS VARIABLE IS THREAD SAFE (TRICK QUESTION)
    private Lock lock;
    private volatile double balance;
    private String accountNumber;

    public BankAccount(String accountNumber, double initialBalance) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        this.lock = new ReentrantLock();
    }

    public void deposit(double depositAmount) {
        //if the variable gets the lock, status sets to true
        boolean status = false;

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += depositAmount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Transaction status = " + status);

//        synchronized (this) {
//            balance += depositAmount;
//        }
    }

    public void withdraw(double withdrawAmount) {
        //if the variable gets the lock, status sets to true
        boolean status = false;

        try {
            if(lock.tryLock(1, TimeUnit.MILLISECONDS)){
                try {
                    balance -= withdrawAmount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Transaction status = " + status);
//        synchronized (this) {
//            balance -= withdrawAmount;
//        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void printAccountNumber() {
        System.out.println("Account number " + getAccountNumber());
    }
}
