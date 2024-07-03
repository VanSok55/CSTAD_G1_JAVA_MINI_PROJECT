import controller.StudentController;
import dataStoreAsList.Storage;
import model.Student;
import service.Service;
import service.ServiceImp;
import view.View;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Service service = new ServiceImp();
        int row = 4;
        StudentController studentController = new StudentController();
        String choice;
        System.out.println("\n" +
                "                                              ██████╗███████╗████████╗ █████╗ ██████╗        ██████╗  ██╗                                                      \n" +
                "                                             ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗      ██╔════╝ ███║                                                      \n" +
                "                                             ██║     ███████╗   ██║   ███████║██║  ██║█████╗██║  ███╗╚██║                                                      \n" +
                "                                             ██║     ╚════██║   ██║   ██╔══██║██║  ██║╚════╝██║   ██║ ██║                                                      \n" +
                "                                             ╚██████╗███████║   ██║   ██║  ██║██████╔╝      ╚██████╔╝ ██║                                                      \n" +
                "                                              ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝        ╚═════╝  ╚═╝                                                      \n" +
                "                                                                                                                                                               \n" +
                "  ███████╗████████╗██╗   ██╗██████╗ ███████╗███╗   ██╗████████╗    ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗\n" +
                "  ██╔════╝╚══██╔══╝██║   ██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝    ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝\n" +
                "  ███████╗   ██║   ██║   ██║██║  ██║█████╗  ██╔██╗ ██║   ██║       ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   \n" +
                "  ╚════██║   ██║   ██║   ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║       ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   \n" +
                "  ███████║   ██║   ╚██████╔╝██████╔╝███████╗██║ ╚████║   ██║       ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   \n" +
                "  ╚══════╝   ╚═╝    ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝       ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   \n" +
                "                                                                                                                                                               \n");
        studentController.getDataFromFile();
        studentController.checkIfCommitFile();
        do {
            View.menu();
            choice = View.inputChoice();
            switch (choice) {
                case "1" -> studentController.addNewStudent();
                case "2" -> studentController.getAllStudents(row);
                case "3" -> studentController.commitFile();
                case "4" -> studentController.searchStudent();
                case "5" -> studentController.updateStudentInfo(Storage.studentList);
                case "6" -> studentController.deleteStudentData();
                case "7" -> studentController.generateRecords();
                case "8" -> studentController.clearAllData();
                case "9" -> row = service.setRow();
                case "0", "99" -> studentController.wantToExit();
                default -> System.out.println("No Option. :(");
            }
        } while (true);
    }
}