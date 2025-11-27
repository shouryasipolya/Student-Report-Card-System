import java.util.*;

public class Main {

    static final String TEACHER_USER = "teacher";
    static final String TEACHER_PASS = "1234";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> allStudents = new ArrayList<>();

        System.out.println("===== Welcome to Report Card System =====");

        boolean subjectsSet = false;

        while (true) {
            System.out.println("\n1. Teacher Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // TEACHER LOGIN
            if (choice == 1) {
                System.out.print("Enter Username: ");
                String user = sc.nextLine();
                System.out.print("Enter Password: ");
                String pass = sc.nextLine();

                if (user.equals(TEACHER_USER) && pass.equals(TEACHER_PASS)) {
                    System.out.println("\nLogin Successful!");

                    // Set subjects only once
                    if (!subjectsSet) {
                        System.out.println("\nEnter Names of 5 Subjects:");
                        for (int i = 0; i < 5; i++) {
                            System.out.print("Subject " + (i + 1) + ": ");
                            Student.subjects[i] = sc.nextLine();
                        }
                        subjectsSet = true;
                    }

                    // Teacher menu loop
                    boolean teacherMenu = true;
                    while (teacherMenu) {
                        System.out.println("\n--- Teacher Menu ---");
                        System.out.println("1. Add Student");
                        System.out.println("2. View All Students");
                        System.out.println("3. Logout");
                        System.out.print("Enter choice: ");
                        int tChoice = sc.nextInt();
                        sc.nextLine();

                        if (tChoice == 1) {
                            Student s = new Student();
                            s.inputDetails();
                            s.calculateResult();
                            allStudents.add(s);
                        } else if (tChoice == 2) {
                            if (allStudents.isEmpty()) {
                                System.out.println("No students added yet!");
                            } else {
                                for (Student s : allStudents) {
                                    s.displayReport();
                                }
                            }
                        } else if (tChoice == 3) {
                            teacherMenu = false;
                            System.out.println("Teacher logged out.");
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    }

                } else {
                    System.out.println("Incorrect username or password!");
                }
            }

            // STUDENT LOGIN
            else if (choice == 2) {
                if (!subjectsSet) {
                    System.out.println("Subjects not set! Teacher must login first.");
                    continue;
                }

                boolean studentMenu = true;
                while (studentMenu) {
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
                    if (!found) {
                        System.out.println("No report found for this roll number!");
                    }

                    System.out.println("\n1. Check again");
                    System.out.println("2. Logout");
                    System.out.print("Enter choice: ");
                    int sChoice = sc.nextInt();
                    sc.nextLine();

                    if (sChoice == 2) studentMenu = false;
                }
            }

            // EXIT
            else if (choice == 3) {
                System.out.println("Exiting... Have a great day!");
                break;
            }

            // INVALID CHOICE
            else {
                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
