Object Oriented Programming for Low Level Design : 

Inheritance : 
-- Child classes are created from the parent class.
-- Every Child class will posess all the data and the functions of its parent class and on top of that will have some unique data and unique functions.

// 1. Parent Class
class PaymentSystem {
    double amount = 500.0;
    
    void validate() {
        System.out.println("Validating payment");
    }

    void pay() {
        System.out.println("Paying amount");
    }
}

// 2. Child Class: Card Payment
class CardPayment extends PaymentSystem {
    String cardNumber = "1234-5678-9012";

    void swipeCard() {
        System.out.println("Card swiped");
    }
}

// 3. Child Class: UPI Payment
class UPIPayment extends PaymentSystem {
    String upiId = "user@bank";
    
    void enterUPIPin() {
        System.out.println("UPI PIN entered");
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        CardPayment card = new CardPayment();
        System.out.println(card.amount); 
        System.out.println(card.cardNumber); 
        card.validate();     // from PaymentSystem
        card.pay();          // from PaymentSystem
        card.swipeCard();    // from CardPayment

        System.out.println("------");

        UPIPayment upi = new UPIPayment();
        System.out.println(upi.amount); 
        System.out.println(upi.upiId); 
        upi.validate();      // from PaymentSystem
        upi.pay();           // from PaymentSystem
        upi.enterUPIPin();   // from UPIPayment
    }
}

Abstract Classes : 
-- Can have common/complete functions as well as abstract/unfinished functions.
-- Abstract functions : Same standard, different implementation for every subclass.
-- Every child class that inherits this abstract class needs to mandatorily implement its own version of the abstract functions.

// 1. Abstract Parent Class (The Blueprint)
abstract class PaymentSystem {
    double amount = 1000.0;

    // A regular function that everyone uses the same way
    void pay() {
        System.out.println("Processing payment of: " + amount);
    }

    // An abstract function (The "Blank" that must be filled by child classes)
    abstract void securityCheck();
}

// 2. Child Class: Card Payment
class CardPayment extends PaymentSystem {
    // Filling in the blank for Card
    void securityCheck() {
        System.out.println("Asking for OTP for Card security");
    }
}

// 3. Child Class: UPI Payment
class UPIPayment extends PaymentSystem {
    // Filling in the blank for UPI
    void securityCheck() {
        System.out.println("Scanning QR code for UPI security");
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        // PaymentSystem p = new PaymentSystem(); // This would cause an ERROR! 

        CardPayment card = new CardPayment();
        card.pay();            // Using the parent's logic
        card.securityCheck();  // Using its own "filled-in" logic

        System.out.println("------");

        UPIPayment upi = new UPIPayment();
        upi.pay();             // Using the parent's logic
        upi.securityCheck();   // Using its own "filled-in" logic
    }
}

Interfaces : 
-- Interface is a rule book.
-- It only has the method declaration
-- Any class that implements the interface will have its own implementation of the methods present in the interface.
-- Unlike abstract classes, the classes that implement the interface need not have to belong to the same identity.

// 1. The Interface (The Rulebook)
interface Receipt {
    void showReceipt(); // No code here, just a requirement
}

// 2. Parent Class
class PaymentSystem {
    double amount = 2500.0;
    
    void pay() {
        System.out.println("Payment successful!");
    }
}

// 3. Child Class implementing the Interface
class CardPayment extends PaymentSystem implements Receipt {
    // We MUST write this code because we signed the 'Receipt' contract
    public void showReceipt() {
        System.out.println("Receipt: Paid " + amount + " using Credit Card");
    }
}

// 4. Another Child Class implementing the Interface
class UPIPayment extends PaymentSystem implements Receipt {
    // We MUST write this code here too
    public void showReceipt() {
        System.out.println("Receipt: Paid " + amount + " via UPI ID");
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        CardPayment card = new CardPayment();
        card.pay();
        card.showReceipt();

        System.out.println("------");

        UPIPayment upi = new UPIPayment();
        upi.pay();
        upi.showReceipt();
    }
}

Solid Principles :
Single Responsibility Principle (SRP) :
What it means: A class should have only one job.

Why it matters: If a class does too many things, it becomes messy. If you change one part, you might accidentally break another. It’s better to have small, specialized tools than one giant tool that tries to do everything.

// THE BAD CODE: Everything is jammed into one class
/*
class PaymentSystem {
    double amount = 500.0;

    // Job 1: Processing the money
    void processPayment() {
        System.out.println("Processing payment of $" + amount);
    }

    // Job 2: Saving to history (Logging)
    void saveToHistory() {
        System.out.println("History: Transaction saved to database.");
    }

    // Job 3: Sending notifications
    void sendAlert() {
        System.out.println("Email: Payment notification sent!");
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        PaymentSystem sys = new PaymentSystem();
        
        // Everything is tied to this one object
        sys.processPayment();
        sys.saveToHistory();
        sys.sendAlert();
    }
}
*/

// THE Good Code (Individual Expert for Individual Task)
// 1. The Expert in Paying
class PaymentProcessor {
    void process(double amount) {
        System.out.println("Processing payment of $" + amount);
    }
}

// 2. The Expert in Printing/Logging
class Logger {
    void logTransaction(String status) {
        System.out.println("History: Transaction was " + status);
    }
}

// 3. The Expert in Sending Alerts
class Notification {
    void sendEmail() {
        System.out.println("Email: You just spent money!");
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        // We use the experts together
        PaymentProcessor payment = new PaymentProcessor();
        Logger logger = new Logger();
        Notification notify = new Notification();

        // Step 1: Pay
        payment.process(500.0);
        
        // Step 2: Log it
        logger.logTransaction("SUCCESSFUL");
        
        // Step 3: Notify user
        notify.sendEmail();
    }
}

Open-Closed Principle (OCP) :
What it means: Open For Extension but closed for modification

The easy explanation: Once you have a piece of code that works, you should not have to open it up and change the original code just to add a new feature. Instead, you should be able to add new features by simply adding new code.

/*
// The Bad Code :
class PaymentProcessor {
    void process(String type) {
        if (type.equals("CreditCard")) {
            System.out.println("Processing Card...");
        } else if (type.equals("UPI")) {
            System.out.println("Processing UPI...");
        }
        // What if we add Apple Pay? We have to come back here and change this code!
    }
}
The Risk: By constantly changing this "Master Class," you might accidentally break the Credit Card logic while trying to add Apple Pay.
*/
    
// The Good Code :
We use an Interface (the rulebook we learned earlier). Now, the main processor does not care how you pay; it just follows the rule. To add a new payment method, you just create a new class. You never touch the old ones!
    
// 1. The Rulebook (The "Open" part)
interface PaymentMethod {
    void pay();
}

// 2. Existing method
class CardPayment implements PaymentMethod {
    public void pay() {
        System.out.println("Paid using Card");
    }
}

// 3. Adding a NEW method (We don't touch CardPayment at all!)
class GooglePay implements PaymentMethod {
    public void pay() {
        System.out.println("Paid using Google Pay");
    }
}

// 4. The Processor (The "Closed" part - it never needs to change)
class PaymentProcessor {
    void process(PaymentMethod method) {
        method.pay(); // It just works for any new method!
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        // Using Card
        CardPayment card = new CardPayment();
        processor.process(card);

        // Using Google Pay (Added without changing the Processor code!)
        GooglePay gpay = new GooglePay();
        processor.process(gpay);
    }
}

Liskov Substitution Principle (LSP) :
What it means: A child class should be able to replace its parent class without breaking anything.

The easy explanation: If you have a general "Payment" tool, any specific payment (Card, UPI, etc.) should be able to do everything the general tool promised. You should not have a child class that "breaks the rules" or throws an error when it is asked to do something the parent is supposed to do.
    
/*
The Bad Code :
class PaymentSystem {
    void processPayment() {
        System.out.println("Payment successful");
    }

    void refund() {
        System.out.println("Money sent back");
    }
}

// This child is "broken" because it can't fulfill the refund promise
class GiftCardPayment extends PaymentSystem {
    // Surprise! Gift cards can't do refunds
    void refund() {
        // This is a "surprise" error that breaks the system logic
        System.out.println("ERROR: Gift cards cannot be refunded!");
    }
}
*/
    
// The Good Code : 
// 1. Base Class for everyone
class PaymentSystem {
    void processPayment() {
        System.out.println("Processing...");
    }
}

// 2. A special group for payments that allow refunds
class RefundablePayment extends PaymentSystem {
    void refund() {
        System.out.println("Refunding money...");
    }
}

// 3. Card can be refunded, so it joins the Refundable group
class CardPayment extends RefundablePayment {
    // Works perfectly
}

// 4. GiftCard only joins the Basic group
class GiftCardPayment extends PaymentSystem {
    // No refund function here, so no "surprises" for the system!
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        // This part of the code ONLY accepts refundable payments
        RefundablePayment card = new CardPayment();
        card.refund(); 

        // The system won't even let you try to refund a GiftCard here, 
        // which prevents the "Break" from happening!
    }
}

Interface Segregation Principle (ISP) :
What it means: Do not force a class to follow rules it does not need.

The easy explanation: 
Child class should be able to perform all the functions of parent class.

Similarly, classes that implement any interface should be implementing their versions of all the functions in that interface.
/*
The Bad Code :
interface PaymentRules {
    void pay();
    void swipeCard();    // Specific to Cards
    void loginToBank();  // Specific to UPI/NetBanking
}

class UPIPayment implements PaymentRules {
    public void pay() { 
        System.out.println("UPI Payment Done"); 
    }

    // Problem: UPI doesn't have a card to swipe!
    public void swipeCard() {
        // We are forced to write this code even if it does nothing
        System.out.println("Error: No card for UPI");
    }

    public void loginToBank() {
        System.out.println("Logged into Bank App");
    }
}
*/

// The Good Code :
// 1. Basic Rule for everyone
interface BasePayment {
    void pay();
}

// 2. Specific Rule for Card-based payments
interface CardFunctions {
    void swipe();
}

// 3. Specific Rule for Online-based payments
interface OnlineFunctions {
    void login();
}

// Now, classes only take what they need!

class CardPayment implements BasePayment, CardFunctions {
    public void pay() { System.out.println("Card Pay"); }
    public void swipe() { System.out.println("Card Swiped"); }
    // No login() code needed here!
}

class UPIPayment implements BasePayment, OnlineFunctions {
    public void pay() { System.out.println("UPI Pay"); }
    public void login() { System.out.println("Logged into App"); }
    // No swipe() code needed here!
}

Dependency Inversion Principle :
What it means (The Easy Explanation)
The Concept:

High-level modules (the "Boss" classes that make big decisions) should not depend on Low-level modules (the "Worker" classes that do specific small tasks).

Both should depend on Abstractions (Interfaces/Rules).

Easy Explanation: Your PaymentProcessor (the Boss) shouldn't be hard-wired to a specific Bank (the Worker). Instead, the Boss should just say, "I need any Bank that follows the 'Transfer' rule."

/*
The "Bad Way" (Hard-wired) :
Here, the PaymentProcessor is stuck. It only works with HDFCBank. If you want to switch to ICICIBank, you have to "break" and rewrite the Boss class.

class HDFCBank {
    void transferMoney() {
        System.out.println("Money sent via HDFC");
    }
}

class PaymentProcessor {
    // The Boss is "dependent" on a specific worker
    HDFCBank bank = new HDFCBank(); 

    void pay() {
        bank.transferMoney();
    }
}
*/
    
// The Good Code :
// 1. The Rule (The Abstraction)
interface BankRule {
    void transfer();
}

// 2. The Workers (Low-level)
class HDFCBank implements BankRule {
    public void transfer() { System.out.println("Sent via HDFC"); }
}

class ICICIBank implements BankRule {
    public void transfer() { System.out.println("Sent via ICICI"); }
}

// 3. The Boss (High-level)
class PaymentProcessor {
    // The Boss doesn't name a specific bank anymore!
    // He just says: "I need someone who follows BankRule"
    void pay(BankRule anyBank) {
        anyBank.transfer();
    }
}
    
// Main Execution
public class Main {
    public static void main(String[] args) {
        PaymentProcessor boss = new PaymentProcessor();
        
        BankRule worker1 = new HDFCBank();
        BankRule worker2 = new ICICIBank();

        boss.pay(worker1); // Works with HDFC
        boss.pay(worker2); // Works with ICICI without changing any code!
    }
}
// Summary of the "Inversion": Instead of the Boss looking down at the Worker and saying "I need you specifically," the Boss looks at a Rule and the Worker looks at the same Rule. They are now both looking at the Interface.

