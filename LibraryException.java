/**
 * EXCEPTION HANDLING - Custom exception for all library-related errors
 */
public class LibraryException extends Exception {

    private final String errorCode;

    public LibraryException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    // Static factory methods for common errors
    public static LibraryException bookNotFound(int bookId) {
        return new LibraryException(
            "Book with ID " + bookId + " was not found in the library.",
            "BOOK_NOT_FOUND"
        );
    }

    public static LibraryException bookAlreadyIssued(int bookId) {
        return new LibraryException(
            "Book with ID " + bookId + " is already issued to another student.",
            "BOOK_ALREADY_ISSUED"
        );
    }

    public static LibraryException studentNotFound(int studentId) {
        return new LibraryException(
            "Student with ID " + studentId + " was not found.",
            "STUDENT_NOT_FOUND"
        );
    }

    public static LibraryException borrowLimitExceeded(int studentId) {
        return new LibraryException(
            "Student ID " + studentId + " has already borrowed the maximum of 3 books.",
            "BORROW_LIMIT_EXCEEDED"
        );
    }

    public static LibraryException bookNotBorrowedByStudent(int studentId, int bookId) {
        return new LibraryException(
            "Book ID " + bookId + " was not borrowed by Student ID " + studentId + ".",
            "BOOK_NOT_BORROWED"
        );
    }

    public static LibraryException invalidId(String field) {
        return new LibraryException(
            "Invalid " + field + " entered. ID must be a positive integer.",
            "INVALID_ID"
        );
    }

    public static LibraryException duplicateBookId(int bookId) {
        return new LibraryException(
            "A book with ID " + bookId + " already exists in the library.",
            "DUPLICATE_BOOK_ID"
        );
    }

    public static LibraryException duplicateStudentId(int studentId) {
        return new LibraryException(
            "A student with ID " + studentId + " is already registered.",
            "DUPLICATE_STUDENT_ID"
        );
    }
}
