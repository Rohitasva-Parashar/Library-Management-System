import java.time.LocalDate;

/**
 * ENCAPSULATION - All fields are private, accessed via getters/setters
 * CONSTRUCTOR   - Parameterized constructor to initialize book objects
 */
public class Book {

    // ─── Private fields (Encapsulation) ───────────────────────────────────────
    private int    bookId;
    private String bookName;
    private String authorName;
    private boolean isAvailable;
    private LocalDate issuedDate;   // Optional: for fine calculation

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Book(int bookId, String bookName, String authorName) {
        this.bookId     = bookId;
        this.bookName   = bookName;
        this.authorName = authorName;
        this.isAvailable = true;    // New book is always available
        this.issuedDate  = null;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public int getBookId()       { return bookId; }
    public String getBookName()  { return bookName; }
    public String getAuthorName(){ return authorName; }
    public boolean isAvailable() { return isAvailable; }
    public LocalDate getIssuedDate() { return issuedDate; }

    // ─── Setters ──────────────────────────────────────────────────────────────
    public void setBookName(String bookName)   { this.bookName = bookName; }
    public void setAuthorName(String authorName){ this.authorName = authorName; }

    /**
     * Changes availability status and tracks issued date for fine calculation
     */
    public void setAvailability(boolean available) {
        this.isAvailable = available;
        this.issuedDate  = available ? null : LocalDate.now();
    }

    // ─── Display ──────────────────────────────────────────────────────────────
    public void displayDetails() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.printf("│  Book ID     : %-26d│%n", bookId);
        System.out.printf("│  Title       : %-26s│%n", truncate(bookName, 26));
        System.out.printf("│  Author      : %-26s│%n", truncate(authorName, 26));
        System.out.printf("│  Status      : %-26s│%n", isAvailable ? "✔ Available" : "✘ Issued");
        if (issuedDate != null) {
            System.out.printf("│  Issued On   : %-26s│%n", issuedDate);
        }
        System.out.println("└─────────────────────────────────────────┘");
    }

    /** One-line summary used in table views */
    public void displaySummary() {
        System.out.printf("│ %-5d │ %-28s │ %-20s │ %-10s │%n",
            bookId,
            truncate(bookName, 28),
            truncate(authorName, 20),
            isAvailable ? "Available" : "Issued"
        );
    }

    @Override
    public String toString() {
        return String.format("Book[ID=%d, Name=%s, Author=%s, Available=%b]",
            bookId, bookName, authorName, isAvailable);
    }

    // ─── Utility ──────────────────────────────────────────────────────────────
    private String truncate(String s, int max) {
        return (s != null && s.length() > max) ? s.substring(0, max - 1) + "…" : s;
    }
}
