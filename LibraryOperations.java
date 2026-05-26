/**
 * ABSTRACTION - Interface defining core library operations
 * Any class implementing this must provide concrete implementations
 */
public interface LibraryOperations {

    void issueBook(int studentId, int bookId) throws LibraryException;

    void returnBook(int studentId, int bookId) throws LibraryException;

    void addBook(Book book);

    void displayAllBooks();
}
