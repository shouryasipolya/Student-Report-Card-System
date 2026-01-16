import java.util.*;

public class Main {

    static final String TEACHER_USER = "teacher";
    static final String TEACHER_PASS = "1234";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        Student.subjects = DB.fetchSubjects();

        if (Student.subjects.isEmpty()) {
            System.out.println("No subjects found in database!");
            System.out.println("Please add subjects in DB first.");
            return;
        }

        // 🔹 Load existing students
        ArrayList<Student> allStudents = DB.fetchAllStudents();

        while (true) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> teacherLogin(sc, allStudents);
                case 2 -> studentDashboard(sc, allStudents);
                case 3 -> {
                    System.out.println("\nExiting... Have a great day!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ===================== MENU PRINT METHODS =====================
    private static void printMainMenu() {
        System.out.println("\n==============================================");
        System.out.println("          WELCOME TO REPORT CARD SYSTEM");
        System.out.println("==============================================");
        System.out.println("1️⃣  Teacher Login");
        System.out.println("2️⃣  Student Dashboard");
        System.out.println("3️⃣  Exit");
    }

    private static void printTeacherMenu() {
        System.out.println("\n==============================================");
        System.out.println("                 TEACHER MENU");
        System.out.println("==============================================");
        System.out.println("1️⃣  Add New Student");
        System.out.println("2️⃣  View All Students");
        System.out.println("3️⃣  Manage Students");
        System.out.println("4️⃣  Manage Subjects");
        System.out.println("5️⃣  Logout");
    }

    private static void printStudentDashboardMenu() {
        System.out.println("\n==============================================");
        System.out.println("               STUDENT DASHBOARD");
        System.out.println("==============================================");
        System.out.println("1️⃣  View Report by Roll Number");
        System.out.println("2️⃣  Search Student by Name");
        System.out.println("3️⃣  Class Ranking");
        System.out.println("4️⃣  Top Scorer per Subject");
        System.out.println("5️⃣  Class Average per Subject");
        System.out.println("6️⃣  Personalized Remarks");
        System.out.println("7️⃣  Back to Main Menu");
    }

    private static void printStudentManagementMenu() {
        System.out.println("\n==============================================");
        System.out.println("             STUDENT MANAGEMENT");
        System.out.println("==============================================");
        System.out.println("1️⃣  View All Students");
        System.out.println("2️⃣  Update Student Marks");
        System.out.println("3️⃣  Delete Student");
        System.out.println("4️⃣  Back");
    }

    private static void printSubjectManagementMenu() {
        System.out.println("\n==============================================");
        System.out.println("             SUBJECT MANAGEMENT");
        System.out.println("==============================================");
        System.out.println("1️⃣  View Subjects");
        System.out.println("2️⃣  Add Subject");
        System.out.println("3️⃣  Update Subject");
        System.out.println("4️⃣  Delete Subject");
        System.out.println("5️⃣  Back");
    }

    // ===================== TEACHER LOGIN & MENU =====================
    private static void teacherLogin(Scanner sc, ArrayList<Student> allStudents) {
        while (true) {
            System.out.print("Enter Username (or type 'back' to return): ");
            String user = sc.nextLine();
            if (user.equalsIgnoreCase("back")) return;

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            if (user.equals(TEACHER_USER) && pass.equals(TEACHER_PASS)) {
                System.out.println("\n✅ Login Successful!");
                boolean teacherMenu = true;

                while (teacherMenu) {
                    printTeacherMenu();
                    System.out.print("Enter your choice: ");
                    int tChoice = sc.nextInt();
                    sc.nextLine();

                    switch (tChoice) {
                        case 1 -> { // Add Student
                            Student s = new Student();
                            s.inputDetails(sc);
                            s.calculateResult();
                            allStudents.add(s);
                            DB.saveStudent(s);
                            System.out.println("✅ Student added successfully!");
                        }
                        case 2 -> { // View All Students
                            if (allStudents.isEmpty())
                                System.out.println("No students found!");
                            else for (Student st : allStudents) st.displayReport();
                        }
                        case 3 -> manageStudents(sc, allStudents);
                        case 4 -> { // Manage Subjects
                            manageSubjects(sc);
                            Student.subjects = DB.fetchSubjects(); // refresh subjects
                        }
                        case 5 -> {
                            teacherMenu = false;
                            System.out.println("✅ Teacher logged out.");
                        }
                        default -> System.out.println("Invalid choice! Try again.");
                    }

                    if (teacherMenu) {
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    }
                }
                break; // exit login loop after logout
            } else {
                System.out.println("❌ Incorrect username or password! Try again.\n");
            }
        }
    }

    // ===================== STUDENT DASHBOARD =====================
    private static void studentDashboard(Scanner sc, ArrayList<Student> allStudents) {
        boolean menu = true;
        while (menu) {
            printStudentDashboardMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> { // View by Roll Number
                    System.out.print("Enter Roll Number: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;
                    for (Student s : allStudents) {
                        if (s.rollNo == r) {
                            s.displayReport();
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("No student found with this roll number!");
                }
                case 2 -> { // Search by Name
                    System.out.print("Enter Name to search: ");
                    String nameSearch = sc.nextLine().toLowerCase();
                    boolean found = false;
                    for (Student s : allStudents) {
                        if (s.name.toLowerCase().contains(nameSearch)) {
                            s.displayReport();
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No student found with this name!");
                }
                case 3 -> { // Class Ranking
                    ArrayList<Student> ranking = new ArrayList<>(allStudents);
                    ranking.sort((a, b) -> Double.compare(b.percentage, a.percentage));
                    System.out.println("\n--- Class Ranking ---");
                    int rank = 1;
                    for (Student s : ranking) {
                        System.out.println(rank + ". " + s.name + " - " + s.percentage + "% (" + s.grade + ")");
                        rank++;
                    }
                }
                case 4 -> { // Top Scorer per Subject
                    for (int i = 0; i < Student.subjects.size(); i++) {
                        Student top = null;
                        int max = -1;
                        for (Student s : allStudents) {
                            if (s.marks[i] > max) {
                                max = s.marks[i];
                                top = s;
                            }
                        }
                        if (top != null)
                            System.out.println("Top in " + Student.subjects.get(i) + ": " + top.name + " - " + max);
                    }
                }
                case 5 -> { // Class Average per Subject
                    System.out.println("\n--- Class Average per Subject ---");
                    for (int i = 0; i < Student.subjects.size(); i++) {
                        double sum = 0;
                        for (Student s : allStudents) sum += s.marks[i];
                        System.out.println(Student.subjects.get(i) + ": " + sum / allStudents.size());
                    }
                }
                case 6 -> { // Personalized Remarks
                    for (Student s : allStudents) {
                        System.out.println("\nReport for " + s.name + ":");
                        for (int i = 0; i < Student.subjects.size(); i++) {
                            String remark;
                            if (s.marks[i] >= 90) remark = "Excellent ✅";
                            else if (s.marks[i] >= 75) remark = "Good 🙂";
                            else if (s.marks[i] >= 60) remark = "Average 😐";
                            else remark = "Needs Improvement ❌";

                            System.out.println(Student.subjects.get(i) + ": " + s.marks[i] + " (" + remark + ")");
                        }
                    }
                }
                case 7 -> menu = false; // Back to Main Menu
                default -> System.out.println("Invalid choice! Try again.");
            }

            // Pause after each action
            if (menu) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
                System.out.println("\n==============================================\n");
            }
        }
    }

    // ===================== STUDENT MANAGEMENT =====================
    private static void manageStudents(Scanner sc, ArrayList<Student> allStudents) {
        boolean studentMenu = true;
        while (studentMenu) {
            printStudentManagementMenu();
            System.out.print("Enter your choice: ");
            int sChoice = sc.nextInt();
            sc.nextLine();

            switch (sChoice) {
                case 1 -> { // View all
                    if (allStudents.isEmpty()) System.out.println("No students found!");
                    else for (Student s : allStudents) s.displayReport();
                }
                case 2 -> { // Update marks
                    System.out.print("Enter Roll Number to update: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    Student studentToUpdate = null;
                    for (Student s : allStudents) if (s.rollNo == r) studentToUpdate = s;
                    if (studentToUpdate != null) {
                        System.out.println("Enter new marks for each subject:");
                        for (int i = 0; i < Student.subjects.size(); i++) {
                            System.out.print(Student.subjects.get(i) + ": ");
                            studentToUpdate.marks[i] = sc.nextInt();
                        }
                        sc.nextLine();
                        studentToUpdate.calculateResult();
                        DB.updateStudentMarks(studentToUpdate.rollNo, studentToUpdate.marks,
                                studentToUpdate.total, studentToUpdate.percentage, studentToUpdate.grade);
                    } else System.out.println("Student not found!");
                }
                case 3 -> { // Delete student
                    System.out.print("Enter Roll Number to delete: ");
                    int delRoll = sc.nextInt();
                    sc.nextLine();
                    DB.deleteStudent(delRoll);
                    allStudents.removeIf(s -> s.rollNo == delRoll);
                    System.out.println("✅ Student deleted successfully!");
                }
                case 4 -> studentMenu = false; // Back
                default -> System.out.println("Invalid choice! Try again.");
            }

            if (studentMenu) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
        }
    }

    // ===================== SUBJECT MANAGEMENT =====================
    private static void manageSubjects(Scanner sc) {
        boolean subjectMenu = true;
        while (subjectMenu) {
            printSubjectManagementMenu();
            System.out.print("Enter your choice: ");
            int sChoice = sc.nextInt();
            sc.nextLine();

            switch (sChoice) {
                case 1 -> DB.viewSubjects();
                case 2 -> {
                    System.out.print("Enter new subject name: ");
                    String subName = sc.nextLine();
                    DB.addSubject(subName);
                }
                case 3 -> {
                    DB.viewSubjects();
                    System.out.print("Enter subject ID to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new subject name: ");
                    String newName = sc.nextLine();
                    DB.updateSubject(id, newName);
                }
                case 4 -> {
                    DB.viewSubjects();
                    System.out.print("Enter subject ID to delete: ");
                    int delId = sc.nextInt();
                    sc.nextLine();
                    DB.deleteSubject(delId);
                }
                case 5 -> subjectMenu = false; // Back
                default -> System.out.println("Invalid choice! Try again.");
            }

            if (subjectMenu) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
        }
    }
}
