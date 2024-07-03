package tableUtil;

import model.Student;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TableStudent {
    public static void print2Columns(Student stu, Table table) {
        String birthDate = stu.getBirthYear()
                + "-" + stu.getBirthMonth()
                + "-" + stu.getBirthDay();
        table.setColumnWidth(0, 35, 35);
        table.setColumnWidth(1, 35, 35);
        table.addCell("Student's Information".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Data".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(stu.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Name".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(stu.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Birth Date".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(birthDate, new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Class".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(stu.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Subject".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(stu.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Created/Updated at".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(String.valueOf(LocalDate.now()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
    }
    public static void printTableStudent(List<Student> studentList, int i, Table table) {
        Student student = studentList.get(i);
        String birthDate = student.getBirthYear()
                + "-" + student.getBirthMonth()
                + "-" + student.getBirthDay();
        table.addCell(student.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(birthDate, new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(String.valueOf(LocalDate.now()),  new CellStyle(CellStyle.HorizontalAlign.CENTER));
    }
}
