package view;

import java.util.Scanner;

public class View {
    public static String inputChoice() {
        System.out.print("> Insert option: ");
        String choice = new Scanner(System.in).nextLine();
        System.out.println(".".repeat(50));
        return choice;
    }
    public static void menu(){
        System.out.println("=".repeat(165));
        System.out.printf("%30s", "1. ADD NEW STUDENT");
        System.out.printf("%37s", "2. LIST ALL STUDENTS");
        System.out.printf("%52s", "3. COMMIT DATA TO FILE");
        System.out.printf("\n%33s", "4. SEARCH FOR STUDENT");
        System.out.printf("%44s", "5. UPDATE STUDENT'S INFO BY ID");
        System.out.printf("%44s", "6. DELETE STUDENT'S DATA");
        System.out.printf("\n%36s", "7. GENERATE DATA TO FILE");
        System.out.printf("%51s", "8. DELETE/CLEAR ALL DATA FROM DATA STORE");
        System.out.printf("%20s", "9. SET ROW");
        System.out.printf("\n%23s", "0, 99. EXIT");
        System.out.println();
        System.out.printf("%165s", "Copyright-CSTAD-2024");
        System.out.println();
        System.out.println("=".repeat(165));
    }
}
