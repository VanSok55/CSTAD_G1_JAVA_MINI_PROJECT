package controller;

import dataStoreAsList.Storage;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.Service;
import service.ServiceImp;
import tableUtil.TableStudent;
import view.View;
import checkValidate.CheckValidData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    Service service = new ServiceImp();
    public static String[] fileName = {"C:\\Users\\User\\Desktop\\CSTAD-G1-Java-Mini-Project\\src\\transaction\\transaction.dat", "C:\\Users\\User\\Desktop\\CSTAD-G1-Java-Mini-Project\\src\\output\\output.dat"};
    int row = 4;
    public void getDataFromFile() {
        long startTime = System.currentTimeMillis();
        Storage.studentList = service.readDataFromFileToList(fileName[1], Storage.studentList);
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;
        System.out.println("[*] SPENT TIME FOR READING DATA: " + totalTime);
        System.out.println("[*] NUMBER OF RECORD IN DATA SOURCE FILE: " + Storage.studentList.size());
    }

    public void checkIfCommitFile() throws IOException {
        List<Student> tempStudents = new ArrayList<>();
        int count = service.compareDataFromTwoFiles(fileName[0], fileName[1]);
        service.readDataFromFileToList(fileName[0], tempStudents);
        if (!tempStudents.isEmpty()) {
            System.out.print("> Commit your " + count + " data record(s) before hand [Y/N]: ");
            String answer = new Scanner(System.in).nextLine().toLowerCase();
            if (answer.equals("y") | answer.equals("yes")) {
                long startTime = System.currentTimeMillis();
                service.writeFile(tempStudents, fileName[1], false);
                long endTime = System.currentTimeMillis();
                double totalTime = (endTime - startTime) / 1000.0;
                System.out.println("[+] Spent time for writing data: ".toUpperCase() + totalTime + " s");
                System.out.println("[+] Wrote data " + count + " records into the file.");
            }
        }
        service.overrideEmptyToFile(fileName[0]);
    }
    public void addNewStudent(){
        System.out.println("> Insert student's info".toUpperCase());
        System.out.print("[+] Insert student's name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("[+] Student date of birth".toUpperCase());
        int year = CheckValidData.inputAndCheckValidYear();
        int month;
        do {
            System.out.print("> Month (number): ");
            String monthInput = new Scanner(System.in).nextLine();
            if (monthInput.matches("^(1[0-2]|^[1-9]|0[1-9])$")) {
                month = Integer.parseInt(monthInput);
                break;
            } else {
                System.out.println("Invalid month. Please enter a number between 1 and 12.");
            }
        } while (true);
        int day = CheckValidData.checkValidDayOfMonth(year, month);
        System.out.println("[!] You can insert multi classes by splitting [,] symbol (c1,c2).".toUpperCase());
        System.out.print("[+] Student's class: ");
        String [] classes = new Scanner(System.in).nextLine().split(",");
        System.out.println("[!] You can insert multi subjects by splitting [,] symbol (s1,s2)".toUpperCase());
        System.out.print("[+] Subject studied: ");
        String[] subjects = new Scanner(System.in).nextLine().split(",");
        String birthMonth = CheckValidData.formatMonthOrDay(month);
        String birthDay = CheckValidData.formatMonthOrDay(day);
        Student student = new Student(Student.generateId(), name, String.valueOf(year), birthMonth, birthDay, classes, subjects);
        service.addStudentToDataStore(student, Storage.studentList);
        service.writeFile(Storage.studentList, fileName[0], false);
        //service.writeObjectToFile(fileName[0], student, true);
        System.out.println("[+] Student has been added successfully".toUpperCase());
        System.out.println("[!] To store data permanently, please commit it (start option 3)".toUpperCase());
    }
    public void getAllStudents(int row){
        System.out.println("[*] Student's Data");
        service.showStudentData(Storage.studentList, row);
    }
    public void commitFile() throws IOException {
        service.commitDataToFile(fileName[0], fileName[1], false);
        System.out.println("[+] Successfully committed data to the file");
    }
    public void searchStudent() {
        String choice;
        do{
            System.out.println("[+] Searching student".toUpperCase());
            System.out.println(".".repeat(50));
            System.out.println("1. Search By Name".toUpperCase());
            System.out.println("2. Search By ID".toUpperCase());
            System.out.println("- (Back/B) to back".toUpperCase());
            System.out.println(".".repeat(50));
            choice = View.inputChoice();
            choice = choice.toLowerCase(); // Convert user input to lowercase
            if(choice.equals("b") || choice.equals("back"))
                break;
            else if(choice.equals("1")) {
                System.out.println("[*] Search By Name".toUpperCase());
                System.out.print(">>> Insert student's NAME: ");
                String name = new Scanner(System.in).nextLine();
                service.searchStudentByName(name, Storage.studentList, row);
                break;
            }
            else if (choice.equals("2")) {
                System.out.println("[*] Search By ID".toUpperCase());
                System.out.print(">>> Insert student's ID: ");
                String id = new Scanner(System.in).nextLine().toUpperCase();
                service.searchStudentById(id, Storage.studentList, row);
            }

        }while(true);
    }
    public void updateStudentInfo(List<Student> studentList) {
        List<Student> updateStudentList = new ArrayList<>();
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        String choice;
        System.out.println("[*] Update student by ID".toUpperCase());
        System.out.print("> Insert student's ID: ".toUpperCase());
        String id = new Scanner(System.in).nextLine();
        System.out.println("[+] Update student's information:".toUpperCase());
        System.out.println("-".repeat(50));
        boolean found = false;
        //Show the student data that is going to be updated
        for(Student stu: studentList) {
            if (stu.getId().equalsIgnoreCase(id.trim())) {
                found = true;
                updateStudentList.add(stu);
                //service.writeObjectToFile(fileName[0], stu, true);
                TableStudent.print2Columns(stu, table);
            }
        }
        if(!found) {
            System.out.println("Student's ID does not match!");
            new Scanner(System.in).nextLine();
        }
        System.out.println(table.render());
        System.out.println("1. Update student's name".toUpperCase());
        System.out.println("2. Update student's date of birth".toUpperCase());
        System.out.println("3. Update student's class".toUpperCase());
        System.out.println("4. Update student's subject".toUpperCase());
        System.out.println(".".repeat(50));
        choice = View.inputChoice();
        choice = choice.toLowerCase();
        switch (choice) {
            case "1":{
                service.updateStudentName(updateStudentList);
                //service.writeFile(Storage.studentList, fileName[0], false);
                break;
            }
            case "2":{
                service.updateStudentBirthDate(updateStudentList);
                //service.writeFile(Storage.studentList, fileName[0], false);
                break;
            }
            case "3":{
                service.updateArrayData(updateStudentList, "class");
                //service.writeFile(Storage.studentList, fileName[0], false);

                break;
            }
            case "4": {
                service.updateArrayData(updateStudentList, "subject");
                //service.writeFile(Storage.studentList, fileName[0], true);

                break;
            }
            case "b", "back": break;
        }
        service.writeFile(Storage.studentList, fileName[0], false);
    }
    public void deleteStudentData() {
        System.out.println("[*] Delete student by ID".toUpperCase());
        System.out.print(">> Insert student's ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        service.deleteStudent(id, Storage.studentList);
        service.writeFile(Storage.studentList, fileName[0], false);
    }
    public void clearAllData(){
        System.out.print("[!] Are you sure to clear all records? [Y/N]: ".toUpperCase());
        String answer = new Scanner(System.in).nextLine().toLowerCase();
        if(answer.equals("y") || answer.equals("yes")) {
            service.clearAllDataFromList(Storage.studentList);
            service.overrideEmptyToFile(fileName[1]);
        }
        //service.writeObjectToFile(fileName[0], student,false);
    }
    public void generateRecords(){
        System.out.print("> NUMBER OF OBJECTS YOU WANT TO GENERATE [100M - 1,000,000,000]: ");
        int number = new Scanner(System.in).nextInt();
        service.generateDataToFile(number, fileName[0], fileName[1], Storage.studentList);
    }
    public void wantToExit() throws IOException {
        checkIfCommitFile();
        System.exit(0);
    }
}
