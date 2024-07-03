package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private String id;
    private String name;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String [] classes;
    private String [] subjects;
    public static String generateId(){
        return ((new Random().nextInt(9000) + 1001) + "CSTAD");
    }
}
