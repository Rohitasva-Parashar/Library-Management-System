import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc      = new Scanner(System.in);
    private static Library       library;

    public static void main(String[] args) {
        printBanner();
        library = new Library("City College Central Library");
        Librarian librarian = new Librarian(1001, "Sir Rohitasva Parashar", "EMP-001", "987654321");
        library.setLibrarian(librarian);
        preloadData();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1  -> menuAddBook();
                case 2  -> menuViewAllBooks();
                case 3  -> menuAddStudent();
                case 4  -> menuIssueBook();
                case 5  -> menuReturnBook();
                case 6  -> menuViewStudentDetails();
                case 7  -> menuViewAllStudents();
                case 8  -> menuSearchBooks();
                case 9  -> menuLibrarianDetails();
                case 0  -> { running = false; printGoodbye(); }
                default -> System.out.println("  [WARN] Invalid option. Please choose 0–9.\n");
            }
        }
        sc.close();
    }
    private static void menuAddBook() {
        printSectionHeader("ADD NEW BOOK");
        int    id     = readPositiveInt("  Enter Book ID     : ");
        String name   = readNonEmpty   ("  Enter Book Name   : ");
        String author = readNonEmpty   ("  Enter Author Name : ");
        Book book = new Book(id, name, author);    // Constructor
        library.addBook(book);
        pause();
    }
    private static void menuViewAllBooks() {
        printSectionHeader("ALL BOOKS IN LIBRARY");
        library.displayAllBooks();
        pause();
    }
    private static void menuAddStudent() {
        printSectionHeader("REGISTER NEW STUDENT");
        try {
            int    id   = readPositiveInt("  Enter Student ID   : ");
            String name = readNonEmpty   ("  Enter Student Name : ");
            String dept = readNonEmpty   ("  Enter Department   : ");

            Student student = new Student(id, name, dept);  // Constructor
            library.addStudent(student);
        } catch (LibraryException e) {
            printError(e);
        }
        pause();
    }
    private static void menuIssueBook() {
        printSectionHeader("ISSUE BOOK TO STUDENT");
        try {
            int studentId = readPositiveInt("  Enter Student ID : ");
            int bookId    = readPositiveInt("  Enter Book ID    : ");
            library.issueBook(studentId, bookId);
        } catch (LibraryException e) {
            printError(e);
        }
        pause();
    }
    private static void menuReturnBook() {
        printSectionHeader("RETURN BOOK");
        try {
            int studentId = readPositiveInt("  Enter Student ID : ");
            int bookId    = readPositiveInt("  Enter Book ID    : ");
            library.returnBook(studentId, bookId);
        } catch (LibraryException e) {
            printError(e);
        }
        pause();
    }
    private static void menuViewStudentDetails() {
        printSectionHeader("STUDENT DETAILS");
        try {
            int id = readPositiveInt("  Enter Student ID : ");
            library.displayStudentDetails(id);
        } catch (LibraryException e) {
            printError(e);
        }
        pause();
    }
    private static void menuViewAllStudents() {
        printSectionHeader("ALL REGISTERED STUDENTS");
        library.displayAllStudents();
        pause();
    }
    private static void menuSearchBooks() {
        printSectionHeader("SEARCH BOOKS");
        System.out.println("  1. Search by Author");
        System.out.println("  2. Search by Title");
        int choice = readInt("  Choice: ");
        switch (choice) {
            case 1 -> {
                String author = readNonEmpty("  Enter author name: ");
                library.searchBooksByAuthor(author);
            }
            case 2 -> {
                String title = readNonEmpty("  Enter title keyword: ");
                library.searchBooksByTitle(title);
            }
            default -> System.out.println("  [WARN] Invalid option.");
        }
        pause();
    }
    private static void menuLibrarianDetails() {
        printSectionHeader("LIBRARIAN DETAILS");
        if (library.getLibrarian() != null) {
            // POLYMORPHISM: calling displayDetails() — Librarian's own version runs
            library.getLibrarian().displayDetails();
        } else {
            System.out.println("  No librarian assigned.");
        }
        pause();
    }
    private static void preloadData() {
        System.out.println("  Loading sample data...");
        library.addBook(new Book(101, "1984",     "George Orwell"));
        library.addBook(new Book(102, "Animal Farm",                   "George Orwell"));
        library.addBook(new Book(103, "Shadow over Insmouth",   "H.P. Lovecraft"));
        library.addBook(new Book(104, "Metamorphosis",              "Frans Kafka"));
        library.addBook(new Book(105, "War and Peace",              "Leo Tolstoy"));
        library.addBook(new Book(106, "Crime and Punishment",               "Fyodor Dostoevsky"));

        // Add sample students using constructors
        try {
            library.addStudent(new Student(201, "Aarav Mehta",  "Computer Science"));
            library.addStudent(new Student(202, "Priya Nair",   "Information Technology"));
            library.addStudent(new Student(203, "Rohan Verma",  "Electronics"));
        } catch (LibraryException e) {
            printError(e);
        }

        System.out.println("  Sample data loaded.\n");
    }
    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║       📚  COLLEGE LIBRARY MANAGEMENT SYSTEM  📚         ║");
        System.out.println("║                                                          ║");
        System.out.println("║   Built with: Classes · Inheritance · Polymorphism      ║");
        System.out.println("║               Encapsulation · Abstraction · Exceptions  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMainMenu() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                   │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Add Book              (Librarian)    │");
        System.out.println("│  2. View All Books                       │");
        System.out.println("│  3. Register Student                     │");
        System.out.println("│  4. Issue Book to Student                │");
        System.out.println("│  5. Return Book                          │");
        System.out.println("│  6. View Student Details                 │");
        System.out.println("│  7. View All Students                    │");
        System.out.println("│  8. Search Books                         │");
        System.out.println("│  9. Librarian Info                       │");
        System.out.println("│  0. Exit                                 │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    private static void printSectionHeader(String title) {
        System.out.println();
        System.out.println("══════════════════════════════════════════");
        System.out.println("  " + title);
        System.out.println("══════════════════════════════════════════");
    }

    private static void printError(LibraryException e) {
        System.out.println("  [ERROR][" + e.getErrorCode() + "] " + e.getMessage());
    }

    private static void printGoodbye() {
        System.out.println();
        System.out.println("  Thank you for using the Library Management System.");
        System.out.println("  Goodbye! 📚");
        System.out.println();
    }
    private static void pause() {
        System.out.println();
        System.out.print("  Press ENTER to continue...");
        sc.nextLine();
        System.out.println();
    }
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = sc.nextInt();
                sc.nextLine();  // consume newline
                return value;
            } catch (InputMismatchException e) {
                sc.nextLine();  // clear bad input
                System.out.println("  [WARN] Please enter a valid integer.");
            }
        }
    }
    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) return value;
            System.out.println("  [WARN] ID must be a positive number.");
        }
    }
    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("  [WARN] This field cannot be empty.");
        }
    }
}
