package Models;



public class Course {

    private int id;
    private int teacherId;
    private String title;
    private int capacity;
    private User teacher;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getCapacity() {
        return capacity;
    }
    public Course(String title, int teacherId, int capacity) {
        this.title = title;
        this.teacherId = teacherId;
        this.capacity = capacity;
    }

      public Course(int id ,String title, int teacherId, int capacity) {
        this(title,teacherId,capacity);
       this.id = id;
    }
}
