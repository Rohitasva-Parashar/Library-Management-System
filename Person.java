/**
 * ABSTRACTION + INHERITANCE
 * Abstract parent class - cannot be instantiated directly.
 * Student and Librarian inherit from this class.
 */
public abstract class Person {

    // ─── Protected fields (accessible by subclasses) ──────────────────────────
    private int    id;
    private String name;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Person(int id, String name) {
        this.id   = id;
        this.name = name;
    }

    // ─── Getters / Setters ────────────────────────────────────────────────────
    public int getId()         { return id; }
    public String getName()    { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * POLYMORPHISM - Abstract method overridden by each subclass.
     * Each person type displays its own version of details.
     */
    public abstract void displayDetails();
}
