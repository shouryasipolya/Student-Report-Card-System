import java.util.*;

public class Student {
    int rollNo;
    String name;
    int total;
    double percentage;
    char grade;
    int[] marks = new int[5];
    static String[] subjects = new String[5]; // Subject names decided by teacher

    // Input student details
    public void inputDetails() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Roll No: ");
        rollNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.println("\nEnter Marks for the Following Subjects (out of 100):");
        for (int i = 0; i < 5; i++) {
            System.out.print(subjects[i] + ": ");
            marks[i] = sc.nextInt();
        }
    }

    // Calculate total, percentage and grade
    public void calculateResult() {
        total = 0;
        for (int mark : marks) total += mark;

        percentage = total / 5.0;

        if (percentage >= 90) grade = 'A';
        else if (percentage >= 80) grade = 'B';
        else if (percentage >= 70) grade = 'C';
        else if (percentage >= 60) grade = 'D';
        else grade = 'F';
    }

    // Display report card
    public void displayReport() {
        System.out.println("\n===== Report Card =====");
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("-----------------------");

        for (int i = 0; i < 5; i++) {
            System.out.println(subjects[i] + ": " + marks[i]);
        }

        System.out.println("Total: " + total);
        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Grade: " + grade);
        System.out.println("========================");
    }
}
