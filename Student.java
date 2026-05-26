import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    // ─── Constants ────────────────────────────────────────────────────────────
    public static final int MAX_BORROW_LIMIT = 3;

    // ─── Private fields (Encapsulation) ───────────────────────────────────────
    private String     department;
    private List<Book> borrowedBooks;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Student(int studentId, String name, String department) {
        super(studentId, name);           // Call Person constructor
        this.department   = department;
        this.borrowedBooks = new ArrayList<>();
    }

    // ─── Getters / Setters ────────────────────────────────────────────────────
    public String getDepartment()          { return department; }
    public void setDepartment(String dept) { this.department = dept; }
    public List<Book> getBorrowedBooks()   { return borrowedBooks; }

    // ─── Business Logic ───────────────────────────────────────────────────────

    /** Returns true if the student can borrow more books */
    public boolean canBorrow() {
        return borrowedBooks.size() < MAX_BORROW_LIMIT;
    }

    /** Returns the number of books currently borrowed */
    public int getBorrowCount() {
        return borrowedBooks.size();
    }

    /** Adds a book to the student's borrowed list */
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    /** Removes a book from the student's borrowed list */
    public boolean returnBook(Book book) {
        return borrowedBooks.removeIf(b -> b.getBookId() == book.getBookId());
    }

    /** Checks if this student has borrowed a specific book */
    public boolean hasBorrowed(int bookId) {
        return borrowedBooks.stream()
                            .anyMatch(b -> b.getBookId() == bookId);
    }

    // ─── POLYMORPHISM: Overrides Person.displayDetails() ─────────────────────
    @Override
    public void displayDetails() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           STUDENT DETAILS                ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Student ID  : %-26d║%n", getId());
        System.out.printf("║  Name        : %-26s║%n", getName());
        System.out.printf("║  Department  : %-26s║%n", department);
        System.out.printf("║  Books Held  : %d / %-22d║%n", borrowedBooks.size(), MAX_BORROW_LIMIT);
        System.out.println("╠══════════════════════════════════════════╣");

        if (borrowedBooks.isEmpty()) {
            System.out.println("║  No books currently borrowed.            ║");
        } else {
            System.out.println("║  Borrowed Books:                         ║");
            for (Book b : borrowedBooks) {
                String line = String.format("  [%d] %s", b.getBookId(), b.getBookName());
                System.out.printf("║  %-40s║%n",
                    line.length() > 40 ? line.substring(0, 39) + "…" : line);
            }
        }
        System.out.println("╚══════════════════════════════════════════╝");
    }

    /** Compact one-line summary */
    public void displaySummary() {
        System.out.printf("│ %-6d │ %-20s │ %-15s │ %d/%-2d │%n",
            getId(), getName(), department, borrowedBooks.size(), MAX_BORROW_LIMIT);
    }
}
