package service;

import checkValidate.CheckValidData;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import tableUtil.TableStudent;

import java.io.*;
import java.util.*;
import static checkValidate.CheckValidData.inputAndCheckValidYear;

public class ServiceImp implements Service {
    public ServiceImp() {}

    @Override
    public List<Student> readDataFromFileToList(String fileName, List<Student> studentList) {
        try{ ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            while (true) {
                try {
                    Student student = (Student) objectInputStream.readObject();
                    studentList.add(student);
                } catch (IOException e) {
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException ignore) {}
        return studentList;
    }
    @Override
    public void pagination(List<Student> studentList, int row){
        int a = 0, page = 1;
        int totalPage;
        float remain = studentList.size() % row;
        if(remain > 0){
            totalPage = (studentList.size() / row) + 1;
        } else
            totalPage = studentList.size() / row;
        do {
            int data = 0;
            String input;
            System.out.println("[*] Student's data".toUpperCase());
            Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
            table.setColumnWidth(0, 22, 22);
            table.setColumnWidth(1, 25, 25);
            table.setColumnWidth(2, 25, 25);
            table.setColumnWidth(3, 30, 30);
            table.setColumnWidth(4, 30, 30);
            table.setColumnWidth(5, 25, 25);
            //Head table
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Name".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Date of Birth".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student class".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's subject".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Created/Update at".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            //Body table
            if(a+row < studentList.size()) {
                for (int i = a; i < a + row; i++) {
                    data += 1;
                    TableStudent.printTableStudent(studentList, i, table);
                }
            }
            else{
                for (int i = a; i < studentList.size(); i++) {
                    data += 1;
                    TableStudent.printTableStudent(studentList, i, table);
                }
            }
            System.out.println(table.render());
            System.out.println("-".repeat(165));
            System.out.println(" Page Number: " + page + " / " + totalPage
                    + "\t [*] Actual record: " + data
                    + "\t [*] All Records: " + studentList.size()
                    + "\t\t  [+] First - [+] Previous (P/p) - [+] Next (n/N) - [+] Back (B/b) - [+] Last");
            System.out.println("-".repeat(165));
            System.out.print("[+] Insert to Navigate [p/n]: ");
            input = new Scanner(System.in).nextLine().toLowerCase();
            if(input.equals("n") || input.equals("next")) {
                if (a + row < studentList.size()) {
                    a += row;
                    page += 1;
                } else {
                    System.out.println("[!] Last Page <<".toUpperCase());
                }
            } else if (input.equals("p") || input.equals("previous")) {
                if (a > 0) {
                    a -= row;
                    page -= 1;
                } else {
                    System.out.println("[!] First Page >>".toUpperCase());
                }
            } else if (input.equals("b") || input.equals("back")) {
                break;
            } else if (input.equals("f") || input.equals("first")) {
                a = 0;
                page = 1;
            } else if(input.equals("l") || input.equals("last")) {
                a = totalPage - row;
                page = totalPage;
            } else {
                System.out.println("Invalid input!");
            }
        }
        while(true);
    }
    @Override
    public int compareDataFromTwoFiles(String transactionFile, String mainFile) {
        List<Student> transactionData = new ArrayList<>();
        List<Student> mainData = new ArrayList<>();
        int count = 0;
        readDataFromFileToList(transactionFile, transactionData);
        readDataFromFileToList(mainFile, mainData);
        for(Student student : transactionData) {
            if(!mainData.contains(student)) {
                count ++;
            }
        }
        return count;
    }
    @Override
    public void overrideEmptyToFile(String fileName){
        try {
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName, false)));
            objectOutputStream.writeObject("");
        }catch (IOException ioException){
            System.err.println(ioException.getMessage());
        }
    }
    /*
    @Override
    public void writeObjectToFile(ObjectOutputStream objectOutputStream, List<Student> studentList) {
        try{
            for(Student student : studentList){
                objectOutputStream.writeObject(student);
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

     */
    @Override
    public void writeObjectToFile(ObjectOutputStream objectOutputStream, List<Student> studentList) {
        try {
            for (Student student : studentList) {
                objectOutputStream.writeObject(student);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void writeFile(List<Student> students, String fileName, boolean append) {
        try {
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName, append)));
            writeObjectToFile(objectOutputStream, students);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ioException) {
            System.err.println("Problem during write data to file: " + ioException.getMessage());
        }
    }
    public void addStudentToDataStore(Student student, List<Student> studentList) {
        studentList.add(student);
    }
    @Override
    public void showStudentData(List<Student> studentList, int row) {
        pagination(studentList, row);
    }
    @Override
    public void commitDataToFile(String transactionFile, String mainFile, boolean append) {
        List<Student> students = readDataFromFileToList(transactionFile, new ArrayList<>());
        if (students.isEmpty()) {
            System.out.println("[*] No data found in transaction file. Skipping commit.");
            return;
        }
        writeFile(students, mainFile, append);
        overrideEmptyToFile(transactionFile);
    }
    @Override
    public void searchStudentByName(String name, List<Student> studentList, int row){
        boolean found = false;
        List<Student> foundStudent = new ArrayList<>();
        for(Student stu: studentList){
            if(stu.getName().toLowerCase().contains(name.toLowerCase())){
                found = true;
                foundStudent.add(stu);
            }
        }
        if(found)
            showStudentData(foundStudent, row);
        else
            System.out.println("Student not found!!");
    }
    @Override
    public void searchStudentById(String id, List<Student> studentList, int row){
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        boolean found = false;
        for(Student stu: studentList){
            if(stu.getId().equalsIgnoreCase(id.trim())) {
                found = true;
                System.out.println(".".repeat(50));
                System.out.println("[*] Student's Info".toUpperCase());
                TableStudent.print2Columns(stu, table);
            }
        }
        if(!found)
            System.out.println("Student not found!!");
        System.out.println(table.render());
        System.out.println(">>> press enter to continue...");
        new Scanner(System.in).nextLine();
    }
    @Override
    public void updateStudentName( List<Student> studentList) {
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        System.out.print("[+] Insert new student's name: ");
        String name = new Scanner(System.in).nextLine();
        //Set a new name
        for(Student stu: studentList){
            stu.setName(name);
            TableStudent.print2Columns(stu, table);
        }
        System.out.println(table.render());
        System.out.println("[+] Updated successfully, press to continue...".toUpperCase());
        new Scanner(System.in).nextLine();
    }
    @Override
    public void updateStudentBirthDate(List<Student> studentList) {
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        int year = inputAndCheckValidYear();
        int month;
        do {
            System.out.print("> Month (number): ");
            month = new Scanner(System.in).nextInt();
        }while(month < 1 || month > 12);
        int day = CheckValidData.checkValidDayOfMonth(year, month);
        for (Student stu : studentList) {
            stu.setBirthYear(String.valueOf(year));
            stu.setBirthMonth(CheckValidData.formatMonthOrDay(month));
            stu.setBirthDay(CheckValidData.formatMonthOrDay(day));
            TableStudent.print2Columns(stu, table);
        }
        System.out.println(table.render());
        System.out.println("[+] Updated successfully, press to continue...".toUpperCase());
        new Scanner(System.in).nextLine();
    }
    @Override
    public void updateArrayData(List<Student> studentList, String keyword){
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        if(keyword.trim().equals("class")){
            System.out.print("[+] Insert new student's classes: ");
            String [] classes = new Scanner(System.in).nextLine().split(",");
            for (Student stu : studentList) {
                stu.setClasses(classes);
                TableStudent.print2Columns(stu, table);
            }
        }else if (keyword.equals("subject")){
            System.out.print("[+] Insert new student's subjects: ");
            String [] subjects = new Scanner(System.in).nextLine().split(",");
            for (Student stu : studentList) {
                stu.setSubjects(subjects);
                TableStudent.print2Columns(stu, table);
            }
        }
        System.out.println(table.render());
        System.out.println("[+] Updated successfully, press to continue...".toUpperCase());
        new Scanner(System.in).nextLine();
    }
    @Override
    public void deleteStudent(String id, List<Student> studentList){
        Table table = new Table(2, BorderStyle.CLASSIC_COMPATIBLE, ShownBorders.ALL);
        Student deletedStudent = null;
        for(Student student2: studentList){
            if(student2.getId().equals(id)){
                deletedStudent  = student2;
                TableStudent.print2Columns(deletedStudent, table);
            }
        }
        System.out.println(table.render());
        System.out.println("[+] Are you sure you want to delete student information[Y/N]: " + id);
        String answer = new Scanner(System.in).nextLine().trim();
        if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
            studentList.remove(deletedStudent);
            //writeObjectToFile(StudentController.fileName[0], deletedStudent, true);
            System.out.println("[+] User data has been deleted temporarily successfully!");
        }else System.out.println("[!] Failed to delete student!");
    }
    //Generate random record
    /*
    @Override
    public void generateDataToFile(int number, String transactionFile, String mainFile, List<Student> studentList){
        Student stu = new Student("1001CSTAD", "Deap Sokreaksmey",
                "2004", "04", "02",
                new String[]{"Data Analytics", "DevOps"},
                new String[]{"Java", "Networking"});
        List<Student> students = new ArrayList<>(number);
        int batchSize;
        if(number >= 1000000)
            batchSize = 1000;
        else batchSize = 0;
        long startTimeRead = System.currentTimeMillis();
        try(FileOutputStream fileOutputStream = new FileOutputStream(mainFile, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream, 65536))
        ){
            for (int i = 0; i < number; i++) {
                students.add(stu);
                studentList.add(stu);
                if (students.size() >= batchSize) {
                    writeObjectToFile(objectOutputStream, students);
                    students.clear();
                }
            }
            if(!students.isEmpty()){
                writeObjectToFile(objectOutputStream, students);
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            long endTimeRead = System.currentTimeMillis();
            double totalTime = (endTimeRead - startTimeRead) / 1000.0;
            System.out.println("[+] Spent time for writing data: ".toUpperCase() + totalTime + " s");
            System.out.println("[+] Wrote data " + number + " records into the file.");
        }catch (Exception ignore){}
    }
    */
    @Override
    public void generateDataToFile(int number, String transactionFile, String mainFile, List<Student> studentList) {
        Student stu = new Student("1001CSTAD", "Deap Sokreaksmey",
                "2004", "04", "02",
                new String[]{"Data Analytics", "DevOps"},
                new String[]{"Java", "Networking"});
        /*
        List<Student> students = new ArrayList<>(number);
        int batchSize = 1000; // Use a constant batch size or adjust based on needs
        long startTimeRead = System.currentTimeMillis();
        try (FileOutputStream fileOutputStream = new FileOutputStream(mainFile, true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream, 65536))) {
            for (int i = 0; i < number; i++) {
                students.add(stu);
                studentList.add(stu);
                if (students.size() >= batchSize) {
                    writeObjectToFile(objectOutputStream, students);
                    students.clear();
                }
            }
            if (!students.isEmpty()) {
                writeObjectToFile(objectOutputStream, students);
                students.clear();
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            long endTimeRead = System.currentTimeMillis();
            double totalTime = (endTimeRead - startTimeRead) / 1000.0;
            System.out.println("[+] Spent time for writing data: ".toUpperCase() + totalTime + " s");
            System.out.println("[+] Wrote data " + number + " records into the file.");
        } catch (IOException e) {
            System.err.println("Error writing data to file: " + e.getMessage());
        }

         */
        List<Student> students = new ArrayList<>(number);
        int batchSize = 1000;
        long startTimeRead = System.currentTimeMillis();
        try(FileOutputStream fileOutputStream = new FileOutputStream(mainFile, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream))
        ){
            for (int i = 0; i < number; i++) {
                students.add(stu);
                studentList.add(stu);
                if (students.size() >= batchSize) {
                    writeObjectToFile(objectOutputStream, students);
                    students.clear();
                }
            }
            if(!students.isEmpty()){
                writeObjectToFile(objectOutputStream, students);
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            long endTimeRead = System.currentTimeMillis();
            double totalTime = (endTimeRead - startTimeRead) / 1000.0;
            System.out.println("File Write: " + totalTime + " seconds");
            //System.out.println("List size: "+list.size());
            System.out.println("Object has been written.");
        }catch (Exception ignore){}
    }

    @Override
    public void clearAllDataFromList(List<Student> studentList){
        studentList.clear();
    }
    @Override
    public int setRow(){
        System.out.print("[+] Setting Row: ");
        return new Scanner(System.in).nextInt();
    }
}
