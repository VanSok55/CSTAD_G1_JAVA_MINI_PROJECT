package service;

import model.Student;
import org.nocrala.tools.texttablefmt.Table;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public interface Service {
    List<Student> readDataFromFileToList(String fileName, List<Student> studentList);
    void pagination(List<Student> studentList, int row);
    int compareDataFromTwoFiles(String transactionFile, String mainFile);
    void overrideEmptyToFile(String fileName);
    void writeObjectToFile(ObjectOutputStream objectOutputStream, List<Student> studentList);
    void writeFile(List<Student> students, String fileName, boolean append);
    void addStudentToDataStore(Student student, List<Student> studentList);
    void showStudentData(List<Student> studentList, int row);
    void commitDataToFile(String mainFile, String transactionFile, boolean append) throws IOException;
    void searchStudentByName(String name, List<Student> studentList, int row);
    void searchStudentById(String id, List<Student> studentList, int row);
    void updateStudentName(List<Student> studentList);
    void updateStudentBirthDate(List<Student> studentList);
    void updateArrayData(List<Student> studentList, String keyword);
    void deleteStudent(String id, List<Student> studentList);
    void generateDataToFile(int number, String transactionFile, String mainFile, List<Student> studentList);
    void clearAllDataFromList(List<Student> studentList);
    int setRow();
}
