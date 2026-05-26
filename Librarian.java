/**
 * INHERITANCE   - Librarian extends Person
 * ENCAPSULATION - Private fields with getters/setters
 * POLYMORPHISM  - Overrides displayDetails() from Person
 * CONSTRUCTOR   - Parameterized constructor
 */
public class Librarian extends Person {

    // ─── Private fields (Encapsulation) ───────────────────────────────────────
    private String employeeCode;
    private String contactNumber;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Librarian(int librarianId, String name, String employeeCode, String contactNumber) {
        super(librarianId, name);         // Call Person constructor
        this.employeeCode  = employeeCode;
        this.contactNumber = contactNumber;
    }

    // ─── Getters / Setters ────────────────────────────────────────────────────
    public String getEmployeeCode()                   { return employeeCode; }
    public String getContactNumber()                  { return contactNumber; }
    public void setContactNumber(String contactNumber){ this.contactNumber = contactNumber; }

    // ─── POLYMORPHISM: Overrides Person.displayDetails() ─────────────────────
    @Override
    public void displayDetails() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          LIBRARIAN DETAILS               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Librarian ID : %-25d║%n", getId());
        System.out.printf("║  Name         : %-25s║%n", getName());
        System.out.printf("║  Employee Code: %-25s║%n", employeeCode);
        System.out.printf("║  Contact      : %-25s║%n", contactNumber);
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
