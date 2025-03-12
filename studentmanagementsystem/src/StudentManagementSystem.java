import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Predicate;

public class StudentManagementSystem {
//    private List<Student> students = new ArrayList<>();
    private Map<Integer,Student> studentMap = new HashMap<>();
//    private Scanner sc = new Scanner(System.in);
    private static final Scanner sc = new Scanner(System.in);

    public static <T> T getValidInput(String prompt,Class<T> type, Predicate<T> validator,String errorMessage){
        while(true){
            System.out.println(prompt);
            String input = sc.nextLine();

            try{
                T value = convertInput(input,type);
                if(validator.test(value)){
                    return value;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(errorMessage);
        }
    }

    public boolean checkExistence(int id,Student student){
        if (studentMap.containsValue(student)){
            return  false;
        }
        return true;
    }

    private static <T> T convertInput(String input , Class<T> type) throws Exception{
        if (type== Integer.class) return type.cast(Integer.parseInt(input));
        if (type == Double.class) return type.cast(Double.parseDouble(input));
        if (type == Boolean.class) return type.cast(Boolean.parseBoolean(input));
        if (type == String.class) return type.cast(input);
        throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
    }

    public void addStudent() {
            int id = getValidInput("Enter ID: ", Integer.class, x -> x > 0, "Invalid ID! Enter a positive number.");
            String name = getValidInput("Enter Name: ", String.class, x -> !x.trim().isEmpty() && x.matches("[A-Za-z ]+"), "Name cannot be empty.");
            int age = getValidInput("Enter Age: ", Integer.class, x -> x > 0, "Invalid Age! Enter a valid number.");
            double marks = getValidInput("Enter Marks: ", Double.class, x -> x >= 0 && x <= 100, "Invalid Marks! Enter a number between 0 and 100.");

            if(checkExistence( id,new Student(id, name, age, marks))){
                studentMap.put(id,new Student(id, name, age, marks));
                System.out.println("Student added successfully ");
            }else {
                System.out.println("Student ID conflict");
            }


    }

    public void displayStudents(){
        if(studentMap.isEmpty()){
            System.out.println("No students found!!");
        }
        studentMap.values().forEach(System.out::println);
    }
    public void removeStudent(){
        int id = getValidInput("Enter ID: ", Integer.class, x -> x > 0, "Invalid ID! Enter a positive number.");
        boolean value = studentMap.remove(id) != null;
        if (value){
        System.out.println("student removed successfully!!");
        }else {
            System.out.println("Student Not found");
        }
    }

    public void sortStudentByMarks() {
        if (studentMap.isEmpty()) {
            System.out.println("No students to sort!");
            return;
        }

        List<Map.Entry<Integer, Student>> studentList = new ArrayList<>(studentMap.entrySet());
        studentList.sort(new SortByMarks()); // Sorting by marks (Descending)

        studentMap = new LinkedHashMap<>(); // Clear and maintain sorted order
        for (Map.Entry<Integer, Student> entry : studentList) {
            studentMap.put(entry.getKey(), entry.getValue());
        }

        System.out.println("Students sorted by marks in descending order!");
        displayStudents();
    }

    public void sortStudents() {
        System.out.println("Sort by: \n1. Name \n2. Age \n3. Marks (Descending)");
        int choice = getValidInput("Enter choice: ", Integer.class, x -> x > 0, "Enter a valid choice");

        if (studentMap.isEmpty()) {
            System.out.println("No students to sort!");
            return;
        }

        List<Map.Entry<Integer, Student>> studentList = new ArrayList<>(studentMap.entrySet());

        switch (choice) {
            case 1 -> studentList.sort(new SortByName());
            case 2 -> studentList.sort(new SortByAge());
            case 3 -> studentList.sort(new SortByMarks());
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        // Store sorted students in a LinkedHashMap
        studentMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Student> entry : studentList) {
            studentMap.put(entry.getKey(), entry.getValue());
        }

        System.out.println("Students sorted successfully!");
        displayStudents();
    }

    public void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        int id = getValidInput("",Integer.class,x -> x >0,"Enter valid choice" );
        Student student = studentMap.get(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found!");
        }
    }
    public void start(){
        while(true){
            System.out.println("\n-- Student Management System--\n");
            System.out.println("1. Add Student \n2. Display Students \n3. Remove Student\n4. Sort By Marks\n5. Custom Sort\n6. Search by id \n7. Exit \n");
            int choice = getValidInput("", Integer.class,x -> x > 0,"Enter valid choice");

            switch (choice){
                case 1 -> addStudent();
                case 2 -> displayStudents();
                case 3 -> removeStudent();
                case 4 -> sortStudentByMarks();
                case 5 -> sortStudents();
                case 6 -> searchStudent();
                case 7 -> {
                    System.out.println("Exiting.....");
                    return;
                }
                default -> System.out.println("Invalid choice! Try Again");
            }
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.start();
    }
}