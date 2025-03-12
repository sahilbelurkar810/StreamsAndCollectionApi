import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

class Student implements Comparable<Student> {
    private int id;
    private String name;
    private int age;
    private double marks;

    public Student(int id, String name, int age, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getMarks() { return marks; }


    @Override
    public int compareTo(Student other) {
        return Double.compare(this.marks, other.marks); // Sort by marks (ascending)
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + "', age=" + age + ", marks=" + marks + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class SortByName implements Comparator<Map.Entry<Integer, Student>> {
    @Override
    public int compare(Map.Entry<Integer, Student> s1, Map.Entry<Integer, Student> s2) {
        return s1.getValue().getName().compareToIgnoreCase(s2.getValue().getName());
    }
}

class SortByAge implements Comparator<Map.Entry<Integer, Student>> {
    @Override
    public int compare(Map.Entry<Integer, Student> s1, Map.Entry<Integer, Student> s2) {
        return Integer.compare(s1.getValue().getAge(), s2.getValue().getAge());
    }
}

class SortByMarks implements Comparator<Map.Entry<Integer, Student>> {
    @Override
    public int compare(Map.Entry<Integer, Student> s1, Map.Entry<Integer, Student> s2) {
        return Double.compare(s2.getValue().getMarks(), s1.getValue().getMarks()); // Descending order
    }
}
