Object Oriented Concepts for Low Level Design 
// Inheritance : 
// -- Child classes are created from the parent class.
// -- Every Child class will posess all the data and the functions of its parent class and on top of that will have some unique data and unique functions.

// Real World Example : 

// PaymentSystem (Parent)
//     -validate()
//     -pay()
//     -generateReceipt()

// CardPayment, UPIPayment, WalletPayment (Child)
//     -validationProcess()
//     -paymentProcess()

// Parent Class
class PaymentSystem {
    void validate() {
        System.out.println("Basic Validation: Checking server status...");
    }

    void pay() {
        System.out.println("Common Action: Processing transaction data...");
    }

    void generateReceipt() {
        System.out.println("Receipt: Transaction successful. Thank you!\n");
    }
}

// Child Class 1
class CardPayment extends PaymentSystem {
    void validationProcess() {
        System.out.println("Card Specific: Validating CVV/Expiry...");
    }

    void paymentProcess() {
        System.out.println("Action: Authorizing via Bank Gateway...");
    }
}

// Child Class 2
class UPIPayment extends PaymentSystem {
    void validationProcess() {
        System.out.println("UPI Specific: Verifying UPI ID...");
    }

    void paymentProcess() {
        System.out.println("Action: Sending request to mobile app...");
    }
}

// Main class to run the code
public class Main {
    public static void main(String[] args) {
        // Using UPI Payment
        System.out.println("--- Starting UPI Payment ---");
        UPIPayment upi = new UPIPayment();
        upi.validate();           // Inherited from parent
        upi.validationProcess();  // Unique to UPI
        upi.pay();                // Inherited
        upi.paymentProcess();     // Unique to UPI
        upi.generateReceipt();    // Inherited

        // Using Card Payment
        System.out.println("--- Starting Card Payment ---");
        CardPayment card = new CardPayment();
        card.validate();          // Inherited
        card.validationProcess(); // Unique to Card
        card.pay();               // Inherited
        card.paymentProcess();    // Unique to Card
        card.generateReceipt();   // Inherited
    }
}

// Abstract Classes : 
// -- Can have common/complete functions as well as abstract/unfinished functions.
// -- Abstract functions : Same standard, different implementation for every subclass.
// -- Every child class that inherits this abstract class needs to mandatorily implement its own version of the abstract functions.

// 1. The Abstract Parent
abstract class PaymentSystem {
    double amount;

    // Unfinished: Every child MUST finish this
    abstract void process(); 

    // Finished: Every child uses this as-is
    void showStatus() {
        System.out.println("Payment processing...");
    }
}

// 2. Child Class 1
class CardPayment extends PaymentSystem {
    // Implementing the mandatory abstract function
    void process() {
        System.out.println("Using Credit Card.");
    }
}

// 3. Child Class 2
class UPIPayment extends PaymentSystem {
    // Implementing the mandatory abstract function
    void process() {
        System.out.println("Using UPI Scanner.");
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        CardPayment c = new CardPayment();
        c.showStatus(); // Uses common function
        c.process();    // Uses unique implementation
    }
}

// Interfaces : 
// -- Interface is a rule book.
// -- It only has the method declaration
// -- Any class that implements the interface will have its own implementation of the methods present in the interface.
// -- Unlike abstract classes, the classes that implement the interface need not have to belong to the same identity.

// 1. The Rule Book (Interface)
interface Refundable {
    void refund(); // Just a declaration, no body
}

// 2. Child Class implementing the interface
class CardPayment extends PaymentSystem implements Refundable {
    // Following the 'Refundable' rule
    public void refund() {
        System.out.println("Refunding money to Bank Account.");
    }
}

// 3. A totally different class also following the same rule
class GiftVoucher implements Refundable {
    // Even though it's not a PaymentSystem, it can be Refundable
    public void refund() {
        System.out.println("Restoring voucher balance.");
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        CardPayment c = new CardPayment();
        c.refund();

        GiftVoucher g = new GiftVoucher();
        g.refund();
    }
}

// SOLID PRINCIPLES :
// Single Responsibility Principle (SRP) :
// What it means: A class should have only one job.

// Why it matters: If a class does too many things, it becomes messy. If you change one part, you might accidentally break another. It’s better to have small, 
// specialized tools than one giant tool that tries to do everything.

// In our case: The PaymentSystem should only handle the payment logic. It should not worry about saving data to a database or printing fancy receipts. We should 
// move those extra jobs to their own classes.

// 1. The Payment Specialist (Only handles paying)
class CardPayment {
    double amount;
    
    void pay() {
        System.out.println("Paid $" + amount + " using Card.");
    }
}

// 2. The Logger Specialist (Only handles printing/logging)
class PaymentLogger {
    void printReceipt(double amount) {
        System.out.println("Receipt: You spent $" + amount);
    }
}

// 3. The Database Specialist (Only handles saving data)
class PaymentRepository {
    void saveToHistory() {
        System.out.println("Payment saved to the database.");
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        // We use three simple tools instead of one giant one
        CardPayment card = new CardPayment();
        card.amount = 200.0;
        
        PaymentLogger logger = new PaymentLogger();
        PaymentRepository repo = new PaymentRepository();
        
        card.pay();
        logger.printReceipt(card.amount);
        repo.saveToHistory();
    }
}

// Open-Closed Principle (OCP) :
// What it means: Software should be open for extension, but closed for modification.

// The easy explanation: Once you have a piece of code that works, you should not have to open it up and change the original code just to add a new feature. Instead, 
// you should be able to add new features by simply adding new code.

// In our case: If we want to add a "Crypto Payment" option, we should not have to go back and edit our main PaymentSystem logic or add a bunch of "if-else" statements 
// to our existing classes. We just create a new class and plug it in.

// 1. The Parent (Open for extension)
abstract class PaymentSystem {
    double amount;
    abstract void process();
}

// 2. Existing functionality
class CardPayment extends PaymentSystem {
    void process() {
        System.out.println("Processing Card Payment of $" + amount);
    }
}

// 3. Adding a NEW feature (We didn't change the classes above!)
class CryptoPayment extends PaymentSystem {
    void process() {
        System.out.println("Processing Crypto Payment of $" + amount);
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        // We can add Crypto without ever touching the CardPayment code
        CryptoPayment crypto = new CryptoPayment();
        crypto.amount = 500.0;
        crypto.process();
    }
}

// Liskov Substitution Principle (LSP) :
// What it means: A child class should be able to replace its parent class without breaking anything.

// The easy explanation: If you have a general "Payment" tool, any specific payment (Card, UPI, etc.) should be able to do everything the general tool promised. 
// You shouldn't have a child class that "breaks the rules" or throws an error when it's asked to do something the parent is supposed to do.

// In our case: Imagine a FixedAmountPayment (like a subscription). If the parent class allows users to changeAmount(), but the child class blocks it or crashes because 
// the amount is fixed, you’ve broken the principle. The child should always be a perfect substitute for the parent.

// 1. The Parent (Promises a way to pay)
abstract class PaymentSystem {
    double amount;
    abstract void pay();
}

// 2. Good Child: CardPayment behaves exactly as expected
class CardPayment extends PaymentSystem {
    void pay() {
        System.out.println("Card payment of $" + amount + " successful.");
    }
}

// 3. Good Child: UPIPayment also behaves as expected
class UPIPayment extends PaymentSystem {
    void pay() {
        System.out.println("UPI payment of $" + amount + " successful.");
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        // We can treat any child as a PaymentSystem and it works perfectly!
        PaymentSystem p;

        p = new CardPayment();
        p.amount = 100;
        p.pay(); // Works!

        p = new UPIPayment();
        p.amount = 200;
        p.pay(); // Works!
    }
}

// Interface Segregation Principle (ISP) :
// What it means: Do not force a class to follow rules it does not need.

// The easy explanation: Imagine a remote control with 100 buttons, but your TV only needs 3 (Power, Volume, Channel). It’s annoying to have all those extra buttons in 
// your way. Instead of one giant "Super Rule Book" that covers everything, it's better to have several small, specific "Mini Rule Books." This way, a class only picks the rules it actually uses.

// In our case: Not every payment needs to be "Refundable" or needs a "Scanner." If we put refund() and scanQR() in one big list, a Card Payment would be forced to have 
// a scanQR() function even though cards don't use QR codes. We should split them up!

// 1. Specific Mini Rule Books (Small Interfaces)
interface Refundable {
    void processRefund();
}

interface QRScanner {
    void scanCode();
}

// 2. Card Payment only needs the Refund rule
class CardPayment implements Refundable {
    void processRefund() {
        System.out.println("Money sent back to card.");
    }
}

// 3. UPI Payment needs both Refund AND QR Scanning
class UPIPayment implements Refundable, QRScanner {
    void processRefund() {
        System.out.println("Money sent back to UPI ID.");
    }
    
    void scanCode() {
        System.out.println("Scanning QR Code...");
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        CardPayment card = new CardPayment();
        card.processRefund(); // Card only does what it's supposed to
        
        UPIPayment upi = new UPIPayment();
        upi.scanCode();
        upi.processRefund();
    }
}

// Dependency Inversion Principle :
// "The Big Boss shouldn't be forced to learn how every specific Worker does their job. Instead, the Worker should just follow the Boss's standard rules."

// Easy Explanation
// -- Imagine you are building a Checkout System (the Big Boss).

// -- The Wrong Way: The Checkout has to learn exactly how Credit Cards work, then learn exactly how UPI works, then learn exactly how Cash works. If you add a new payment 
// type, you have to "retrain" the Boss's brain.

// -- The Dependency Inversion Way: The Boss creates a standard rule book (like an Interface). He says, "I don't care who you are; if you want to work here, you just need 
// to know how to processPayment()."

// -- Now, the Boss stays simple, and the specific payment methods (the Workers) are the ones who have to adapt to the Boss's rules.

// 1. The Job Description (The Socket)
interface PaymentWay {
    void payNow(double amount);
}

// 2. The Workers (The Plugs) - They just follow the description
class Card implements PaymentWay {
    public void payNow(double amount) {
        System.out.println("Processing Card: $" + amount);
    }
}

class UPI implements PaymentWay {
    public void payNow(double amount) {
        System.out.println("Processing UPI: $" + amount);
    }
}

// 3. The Big Boss (The Wall Outlet)
class Checkout {
    // The boss only cares about the "Job Description"
    PaymentWay worker; 

    void completeOrder(double total) {
        worker.payNow(total); // Boss just gives the order
    }
}

// Execution
class Main {
    public static void main(String[] args) {
        Checkout shop = new Checkout();

        // We plug in the Card worker
        shop.worker = new Card();
        shop.completeOrder(100.0);

        // We "unplug" Card and "plug in" UPI - The Shop code stays the same!
        shop.worker = new UPI();
        shop.completeOrder(50.0);
    }
}

