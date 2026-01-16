import java.util.*;

public class Student {
    int rollNo;
    String name;
    int total;
    double percentage;
    char grade;
    int[] marks;
    static ArrayList<String> subjects = new ArrayList<>(); // Dynamic subjects


    public void inputDetails(Scanner sc) {
        System.out.print("Enter Roll No: ");
        rollNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        // Initialize marks array based on number of subjects
        marks = new int[subjects.size()];

        System.out.println("\nEnter Marks for the Following Subjects (out of 100):");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.print(subjects.get(i) + ": ");
            marks[i] = sc.nextInt();
        }
        sc.nextLine();
    }

    // Calculate total, percentage and grade
    public void calculateResult() {
        total = 0;
        for (int mark : marks) total += mark;

        percentage = total / (double) marks.length;

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

        for (int i = 0; i < marks.length; i++) {
            System.out.println(subjects.get(i) + ": " + marks[i]);
        }

        System.out.println("Total: " + total);
        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Grade: " + grade);
        System.out.println("========================");
    }
}
