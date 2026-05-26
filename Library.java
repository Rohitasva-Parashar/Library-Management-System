import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ABSTRACTION   - Implements the LibraryOperations interface
 * ENCAPSULATION - Private collections accessed through public methods
 * This is the central class that wires together Books, Students, Librarian
 */
public class Library implements LibraryOperations {

    // ─── Fine configuration ───────────────────────────────────────────────────
    private static final int    LOAN_PERIOD_DAYS = 14;   // 2-week loan period
    private static final double FINE_PER_DAY     = 2.0;  // ₹2 per day overdue

    // ─── Private collections (Encapsulation) ──────────────────────────────────
    private final String       libraryName;
    private final List<Book>    books;
    private final List<Student> students;
    private Librarian           librarian;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.books       = new ArrayList<>();
        this.students    = new ArrayList<>();
    }

    // ─── Librarian Management ─────────────────────────────────────────────────
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    // =========================================================================
    //  LibraryOperations INTERFACE IMPLEMENTATION
    // =========================================================================

    /**
     * Add a book to the library catalog.
     * Throws unchecked duplicate exception (wrapped in runtime).
     */
    @Override
    public void addBook(Book book) {
        // Check for duplicate book ID
        if (findBookById(book.getBookId()).isPresent()) {
            System.out.println("  [ERROR] " + "Book ID " + book.getBookId() + " already exists.");
            return;
        }
        books.add(book);
        System.out.println("  [SUCCESS] Book \"" + book.getBookName() + "\" added successfully.");
    }

    /**
     * Display all books in the library catalog.
     */
    @Override
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("  No books in the library catalog yet.");
            return;
        }
        System.out.println("┌───────┬──────────────────────────────┬──────────────────────┬────────────┐");
        System.out.println("│ ID    │ Title                        │ Author               │ Status     │");
        System.out.println("├───────┼──────────────────────────────┼──────────────────────┼────────────┤");
        for (Book b : books) {
            b.displaySummary();
        }
        System.out.println("└───────┴──────────────────────────────┴──────────────────────┴────────────┘");
        System.out.printf("  Total: %d book(s)  |  Available: %d  |  Issued: %d%n",
            books.size(),
            books.stream().filter(Book::isAvailable).count(),
            books.stream().filter(b -> !b.isAvailable()).count()
        );
    }

    /**
     * Issue a book to a student.
     * EXCEPTION HANDLING for: book not found, already issued, borrow limit
     */
    @Override
    public void issueBook(int studentId, int bookId) throws LibraryException {
        // 1. Find student
        Student student = findStudentById(studentId)
            .orElseThrow(() -> LibraryException.studentNotFound(studentId));

        // 2. Find book
        Book book = findBookById(bookId)
            .orElseThrow(() -> LibraryException.bookNotFound(bookId));

        // 3. Check if book is available
        if (!book.isAvailable()) {
            throw LibraryException.bookAlreadyIssued(bookId);
        }

        // 4. Check student borrow limit
        if (!student.canBorrow()) {
            throw LibraryException.borrowLimitExceeded(studentId);
        }

        // 5. Issue the book
        book.setAvailability(false);
        student.borrowBook(book);

        System.out.println("  [SUCCESS] Book \"" + book.getBookName()
            + "\" issued to " + student.getName() + ".");
        System.out.println("  Due date: " + LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
    }

    /**
     * Accept a book return from a student.
     * Also calculates and displays fine if overdue.
     * EXCEPTION HANDLING for: student/book not found, not borrowed by this student
     */
    @Override
    public void returnBook(int studentId, int bookId) throws LibraryException {
        // 1. Find student
        Student student = findStudentById(studentId)
            .orElseThrow(() -> LibraryException.studentNotFound(studentId));

        // 2. Find book
        Book book = findBookById(bookId)
            .orElseThrow(() -> LibraryException.bookNotFound(bookId));

        // 3. Verify student actually borrowed this book
        if (!student.hasBorrowed(bookId)) {
            throw LibraryException.bookNotBorrowedByStudent(studentId, bookId);
        }

        // 4. Calculate fine (optional feature)
        double fine = calculateFine(book);

        // 5. Return the book
        student.returnBook(book);
        book.setAvailability(true);

        System.out.println("  [SUCCESS] Book \"" + book.getBookName()
            + "\" returned by " + student.getName() + ".");

        if (fine > 0) {
            System.out.printf("  [FINE] Overdue fine: ₹%.2f (please pay at the counter).%n", fine);
        } else {
            System.out.println("  [INFO] Returned on time. No fine.");
        }
    }

    // =========================================================================
    //  STUDENT MANAGEMENT
    // =========================================================================

    /**
     * Register a new student in the system.
     */
    public void addStudent(Student student) throws LibraryException {
        if (findStudentById(student.getId()).isPresent()) {
            throw LibraryException.duplicateStudentId(student.getId());
        }
        students.add(student);
        System.out.println("  [SUCCESS] Student \"" + student.getName() + "\" registered.");
    }

    /**
     * Display all registered students.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("  No students registered yet.");
            return;
        }
        System.out.println("┌────────┬──────────────────────┬─────────────────┬───────┐");
        System.out.println("│ ID     │ Name                 │ Department      │ Books │");
        System.out.println("├────────┼──────────────────────┼─────────────────┼───────┤");
        for (Student s : students) {
            s.displaySummary();
        }
        System.out.println("└────────┴──────────────────────┴─────────────────┴───────┘");
    }

    /**
     * Display a specific student's full details.
     */
    public void displayStudentDetails(int studentId) throws LibraryException {
        Student student = findStudentById(studentId)
            .orElseThrow(() -> LibraryException.studentNotFound(studentId));
        student.displayDetails();
    }

    // =========================================================================
    //  SEARCH FEATURES (Optional)
    // =========================================================================

    /**
     * Search books by author name (case-insensitive partial match).
     */
    public void searchBooksByAuthor(String authorName) {
        List<Book> results = books.stream()
            .filter(b -> b.getAuthorName().toLowerCase()
                          .contains(authorName.toLowerCase()))
            .toList();

        if (results.isEmpty()) {
            System.out.println("  No books found by author: " + authorName);
            return;
        }
        System.out.println("  Search results for author \"" + authorName + "\":");
        System.out.println("┌───────┬──────────────────────────────┬──────────────────────┬────────────┐");
        System.out.println("│ ID    │ Title                        │ Author               │ Status     │");
        System.out.println("├───────┼──────────────────────────────┼──────────────────────┼────────────┤");
        results.forEach(Book::displaySummary);
        System.out.println("└───────┴──────────────────────────────┴──────────────────────┴────────────┘");
    }

    /**
     * Search book by title (case-insensitive partial match).
     */
    public void searchBooksByTitle(String title) {
        List<Book> results = books.stream()
            .filter(b -> b.getBookName().toLowerCase().contains(title.toLowerCase()))
            .toList();

        if (results.isEmpty()) {
            System.out.println("  No books found with title containing: " + title);
            return;
        }
        System.out.println("  Search results for title \"" + title + "\":");
        System.out.println("┌───────┬──────────────────────────────┬──────────────────────┬────────────┐");
        System.out.println("│ ID    │ Title                        │ Author               │ Status     │");
        System.out.println("├───────┼──────────────────────────────┼──────────────────────┼────────────┤");
        results.forEach(Book::displaySummary);
        System.out.println("└───────┴──────────────────────────────┴──────────────────────┴────────────┘");
    }

    // =========================================================================
    //  INTERNAL HELPERS (Private - Encapsulation)
    // =========================================================================

    private Optional<Book> findBookById(int bookId) {
        return books.stream()
                    .filter(b -> b.getBookId() == bookId)
                    .findFirst();
    }

    private Optional<Student> findStudentById(int studentId) {
        return students.stream()
                       .filter(s -> s.getId() == studentId)
                       .findFirst();
    }

    /**
     * OPTIONAL FEATURE: Fine calculation based on overdue days.
     * Fine = overdue days × FINE_PER_DAY (₹2/day)
     */
    private double calculateFine(Book book) {
        if (book.getIssuedDate() == null) return 0;
        long daysBorrowed = ChronoUnit.DAYS.between(book.getIssuedDate(), LocalDate.now());
        long overdueDays  = daysBorrowed - LOAN_PERIOD_DAYS;
        return overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public String getLibraryName()    { return libraryName; }
    public int getTotalBooks()        { return books.size(); }
    public int getTotalStudents()     { return students.size(); }
}
