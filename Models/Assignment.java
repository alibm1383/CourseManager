package Models;

public class Assignment {

    private int id;
    private String title;
    private int courseId;

    public Assignment( int courseId, String title) {
        this.title = title;
        this.courseId = courseId;
    }

    public Assignment (int id , int courseId , String title)
    {
        this(courseId,title);
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getId() {
        return id;
    }
}
