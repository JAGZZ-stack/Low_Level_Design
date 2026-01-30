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

Design Patterns : 
Strategy Pattern : 
The Concept Think of the Strategy Pattern as a "Switchable Brain." In your shipment application, you handle Air, Land, and Ocean shipments. Each of these has a completely different way of calculating the "Estimated Time of Delivery" (ETD).

Air: Fast, based on flight schedules.

Land: Depends on road distance and driver breaks.

Ocean: Slow, based on port wait times and sea routes.

If you put all this logic into one big "If-Else" block, your code becomes a mess. The Strategy Pattern allows you to pull these different "logics" out into their own separate classes. Your main system then just "plugs in" whichever logic it needs at that moment.

The Bad Code (The "If-Else" Mess) Imagine every time you need to calculate time, you have to check the type. If you add "Rail" shipments later, you have to break this code to add another else if.

// class ShipmentProcessor {
//     void calculateTime(String type) {
//         if (type.equals("AIR")) {
//             System.out.println("Calculating flight duration + airport clearance...");
//         } else if (type.equals("LAND")) {
//             System.out.println("Calculating driving hours + traffic...");
//         } else if (type.equals("OCEAN")) {
//             System.out.println("Calculating sea route + port docking...");
//         }
//     }
// }

The Good Code (The Strategy Way) :

1. The Rulebook (The Interface) This defines what the "brain" must be able to do.

Java
interface DeliveryStrategy {
    void calculate();
}

2. The Specific "Brains" (The Strategies) Each class focuses on only one type of logic.

class AirStrategy implements DeliveryStrategy {
    void calculate() {
        System.out.println("Logic: Fast flight path selected.");
    }
}

class LandStrategy implements DeliveryStrategy {
    void calculate() {
        System.out.println("Logic: Truck route with road tolls selected.");
    }
}

3. The Shipment Object It doesn't know how to calculate; it just holds a "Strategy" and tells it to work.
class Shipment {
    DeliveryStrategy strategy; // This is the "plug"

    void setStrategy(DeliveryStrategy s) {
        strategy = s;
    }

    void estimate() {
        strategy.calculate(); // Just calls the plug
    }
}

4. Execution
public class Main {
    public static void main(String[] args) {
        Shipment myFreight = new Shipment();

        // It's a Land shipment today
        myFreight.setStrategy(new LandStrategy());
        myFreight.estimate(); 

        // Suddenly, the customer wants to switch to Air!
        // We just swap the "brain"
        myFreight.setStrategy(new AirStrategy());
        myFreight.estimate();
    }
}
Why this helps your Brokerage App:

Cleanliness: Your Shipment class stays small. It doesn't care about flight schedules or road traffic; it just knows it has a "Strategy."

Easy Expansion: If your company starts doing Rail shipments, you just create a RailStrategy class. You don't touch the Air or Land code at all (following the Open-Closed Principle we discussed earlier!).


Observer Pattern : 

// 1. THE RULEBOOK (Interface)
// Every observer (Email, SMS, Kafka) must follow this rule.
interface Observer {
    void update(String status);
}

// 2. THE WORKERS (Concrete Observers)
// Each class handles the "How" for a specific customer or system.

class EmailNotifier implements Observer {
    String email; // We will set this manually in Main

    public void update(String status) {
        System.out.println("Email to " + email + ": " + status);
    }
}

class SMSNotifier implements Observer {
    // No extra data needed here, just the logic
    public void update(String status) {
        System.out.println("SMS Notifier " + status);
    }
}

// 3. THE SUBJECT INTERFACE
interface Subject {
    void subscribe(Observer obs);
    void unsubscribe(Observer obs);
    void updateStatus(String newStatus);
    // Optional: notify() - but updateStatus already handles notification
}


// 4. THE SHIPMENT (The Subject)
// This is the "Bulletin Board" that holds the list.

class Shipment implements Subject{
    // This is the "Mixed Bag" list of workers we talked about
    List<Observer> subscribers = new ArrayList<>();

    // Add a worker to the list
    @Override
    public void subscribe(Observer obs) {
        subscribers.add(obs);
    }
    
    // Remove a worker from the list
    @Override
    public void unsubscribe(Observer obs) {
        subscribers.remove(obs);
    }

    // This is the trigger!
    @Override
    public void updateStatus(String newStatus) {
        System.out.println("\n--- SHIPMENT UPDATE: " + newStatus + " ---");
        
        // THE LOOP: This is the exact flow you asked about.
        // It hits every worker in the list, one by one.
        for (Observer worker : subscribers) {
            worker.update(newStatus); // The "Poke"
        }
    }
}

// 4. THE EXECUTION (The Flow)
public class Main {
    public static void main(String[] args) {
        // Step 1: Create the Shipment board
        Shipment freight = new Shipment();

        // Step 2: Create specific workers for specific customers
        EmailNotifier custA = new EmailNotifier("customerA@gmail.com");
        EmailNotifier custB = new EmailNotifier("customerB@logistics.com");
        SMSNotifier custC = new SMSNotifier("4876532876");

        // Step 3: Register them (Putting them in the list)
        freight.subscribe(custA);
        freight.subscribe(custB);
        freight.subscribe(custC);

        // Step 4: The Event happens!
        // This one call will trigger 2 emails and 1 Kafka push.
        freight.updateStatus("IN-TRANSIT");

        // Step 5: Another event
        freight.updateStatus("DELIVERED");
    }
}

Factory Pattern : 

// 1. THE RULEBOOK (The Interface)
// Every object the factory builds must follow this.
interface Shipment {
    void showLocation();
}

// 2. THE PRODUCTS (The Specific Types)
// These are the classes the Factory will choose from.

class AirShipment implements Shipment {
    public void showLocation() {
        System.out.println("Tracking: Currently in the air (Flight Cargo).");
    }
}

class OceanShipment implements Shipment {
    public void showLocation() {
        System.out.println("Tracking: Currently on a ship (Sea Freight).");
    }
}

class LandShipment implements Shipment {
    public void showLocation() {
        System.out.println("Tracking: Currently in a truck (Road Freight).");
    }
}

// 3. THE FACTORY (The Specialized Warehouse Manager)
// This is where the creation logic lives.
class ShipmentFactory {
    
    // This method is the "Magic Box"
    Shipment getShipment(String mode) {
        if (mode.equals("AIR")) {
            return new AirShipment();
        } 
        else if (mode.equals("OCEAN")) {
            return new OceanShipment();
        } 
        else if (mode.equals("LAND")) {
            return new LandShipment();
        }
        return null;
    }
}

// 4. THE EXECUTION
public class Main {
    public static void main(String[] args) {
        // Step 1: Initialize the Factory manager
        ShipmentFactory manager = new ShipmentFactory();

        // Step 2: Imagine we get a Kafka message saying "OCEAN"
        // We don't say "new OceanShipment()", we just tell the manager what we want.
        Shipment s1 = manager.getShipment("OCEAN");
        s1.showLocation();

        // Step 3: Imagine the next message says "AIR"
        Shipment s2 = manager.getShipment("AIR");
        s2.showLocation();
        
        // Step 4: Imagine a "LAND" update comes in
        Shipment s3 = manager.getShipment("LAND");
        s3.showLocation();
    }
}

Chain of Responsibility :
This is a great addition to your list. In your brokerage app, this pattern is perfect for Data Validation or Approval Workflows.

// The Concept :
// Imagine a shipment request comes in. Before it gets saved, it has to pass through several "Checkpoints":

// The Validator: Is the weight positive?

// The Security Officer: Is the cargo allowed (no hazardous materials)?

// The Manager: If the shipment costs > $10,000, does it have extra approval?

// Instead of one giant class doing all these checks, you create a Chain. You pass the shipment to the first person. If they say "Clear," they pass it to the next person. If anyone finds an error, they "break the chain" and stop the process.

// The "Naked" Code (No Constructors, No this)
// 1. The Rulebook (The Interface)

interface Handler {
    void setNext(Handler next);
    void check(String cargo);
}

// 2. The Checkpoints (The Workers)

class WeightCheck implements Handler {
    Handler nextInLine;

    public void setNext(Handler next) {
        nextInLine = next;
    }

    public void check(String cargo) {
        System.out.println("Step 1: Checking weight...");
        // If everything is okay, pass it to the next person
        if (nextInLine != null) {
            nextInLine.check(cargo);
        }
    }
}

class SecurityCheck implements Handler {
    Handler nextInLine;

    public void setNext(Handler next) {
        nextInLine = next;
    }

    public void check(String cargo) {
        if (cargo.equals("Explosives")) {
            System.out.println("Step 2: REJECTED! Dangerous cargo.");
        } else {
            System.out.println("Step 2: Security cleared.");
            if (nextInLine != null) {
                nextInLine.check(cargo);
            }
        }
    }
}

// 3. The Execution (The Flow) Look how we physically link them together like a chain.

public class Main {
    public static void main(String[] args) {
        // Create the individual links
        WeightCheck weight = new WeightCheck();
        SecurityCheck security = new SecurityCheck();

        // LINK THEM: Weight -> Security
        weight.setNext(security);

        // Start the chain with a safe shipment
        System.out.println("--- Processing Apples ---");
        weight.check("Apples");

        System.out.println("\n--- Processing Explosives ---");
        // Start the chain again
        weight.check("Explosives");
    }
}
