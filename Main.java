import Context.Context;
import Models.*;
import Repositories.*;
import Utilities.PasswordUtils;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Context context = new Context();
        IUserRepository userRepository = new UserRepository(context);
        ICourseRepository courseRepository = new CourseRepository(context);
        IAssignmentRepository assignmentRepository = new AssignmentRepository(context);
        IEnrollmentRepository enrollmentRepository = new EnrollmentRepository(context);


        if (!userRepository.isAnyAdminExist()) {
            User admin = new User(Role.Admin,"admin", "admin", PasswordUtils.hashPassword("1234"));
            if (userRepository.addUser(admin)) {
                System.out.println("Default admin created with\n" + "username = " + admin.getUsername() + "\n" + "password = 1234");
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.teacher\n2.admin");
        int type = scanner.nextInt();
        int wantsExit = 0;
        scanner.nextLine();
        if (type == 1) {
            System.out.println("please insert your username");
            String username = scanner.nextLine();
            System.out.println("please insert your password");
            String password = scanner.nextLine();
            User teacher = userRepository.authenticateUser(Role.Teacher,username,password);
            if (teacher == null)
            {
                throw new  NoSuchElementException("teacher not found");
            }

            while (wantsExit == 0) {
                var courses = courseRepository.getTeacherCourses(teacher.getId());
                if(courses.size() > 0)
                {
                    System.out.println("please choose a course");
                    for (int i = 1 ; i <= courses.size() ; i++)
                    {
                        System.out.println(i+"."+courses.get(i-1).getTitle());
                    }
                }
                else
                {
                    System.out.println("you dont have any course to teach");
                }
                int courseIndex = scanner.nextInt();
                scanner.nextLine();
                var course = courses.get(courseIndex-1);

                System.out.println("choose your activity\n_____________________");
                System.out.println("1.add a student to a course\n2.remove a student from a course\n3.see all assignments\n4.add an assignment\n5.remove an assignment\n6.add a mark\n7.see all students\n8.exit");


                int activity = scanner.nextInt();
                scanner.nextLine();
                if (activity == 1)
                {
                    System.out.println("please enter studentUsername");
                    String studentUsername = scanner.nextLine();
                    var studentId = userRepository.getUserIdByUsername(studentUsername);
                    if (studentId == -1)
                    {
                        throw new NoSuchElementException("student not found");
                    }
                    if(enrollmentRepository.addStudentToCourse(studentId,course.getId()))
                    {
                        System.out.println("executed");
                    }
                }
                else if (activity == 2)
                {
                    System.out.println("please enter studentUsername");
                    String studentUsername = scanner.nextLine();
                    var studentId = userRepository.getUserIdByUsername(studentUsername);
                    if (studentId == -1)
                    {
                        throw new NoSuchElementException("student not found");
                    }
                    if(enrollmentRepository.removeStudentFromCourse(studentId,course.getId()))
                    {
                        System.out.println("executed");
                    }
                }
                else if (activity == 3)
                {

                    var assignments = assignmentRepository.getAllAssignmentsForCourse(course.getId());
                    if (assignments.size() > 0)
                    {
                        for (int i = 1 ; i <= assignments.size() ; i++)
                        {
                            System.out.println(i+"."+assignments.get(i-1).getTitle());
                        }
                    }
                    else
                    {
                        System.out.println("you dont have any assignment");
                    }
                }
                else if (activity == 4)
                {
                    System.out.println("please enter assignment title");
                    String title = scanner.nextLine();
                    Assignment assignment = new Assignment(course.getId(),title);
                    if(assignmentRepository.addAssignment(assignment))
                    {
                        System.out.println("executed");
                    }
                }
                else if (activity == 5)
                {
                    var assignments = assignmentRepository.getAllAssignmentsForCourse(course.getId());
                    if (assignments.size() > 0)
                    {
                        System.out.println("please choose one of your assignments");
                        for (int i = 1 ; i <= assignments.size() ; i++)
                        {
                            System.out.println(i+"."+assignments.get(i-1).getTitle());
                        }
                        int assignmentIndex = scanner.nextInt();
                        Assignment assignment = assignments.get(assignmentIndex-1);
                        assignmentRepository.removeAssignment(assignment.getId());
                    }
                    else
                    {
                        System.out.println("you dont have any assignment");
                    }

                }
                else if (activity == 6)
                {

                    var students = enrollmentRepository.getCourseStudents(course.getId());
                    if (students.size() > 0)
                    {
                        System.out.println("please choose student");
                        for (int i = 1 ; i <= students.size() ; i++)
                        {
                            System.out.println(i+"."+students.get(i-1).getFullName());
                        }
                        int studentIndex = scanner.nextInt();
                        User student = students.get(studentIndex-1);
                        System.out.println("Please enter the point");
                        float point = scanner.nextFloat();
                       if (enrollmentRepository.addPoint(student.getId(),course.getId(),point))
                       {
                           System.out.println("executed");
                       }
                    }
                    else
                    {
                        System.out.println("you dont have any students in this class");
                    }

                }
                else if (activity == 7)
                {
                     var students = enrollmentRepository.getCourseStudents(course.getId());
                    if (students.size() > 0)
                    {
                        for (int i = 1 ; i <= students.size() ; i++)
                        {
                            System.out.println(i+"."+students.get(i-1).getFullName());
                        }
                    }
                    else
                    {
                        System.out.println("you dont have any students in this class");
                    }
                }
                else if (activity == 8)
                {
                     return;
                }
                else
                {
                    System.out.println("please enter a valid value");
                }

                do {
                    System.out.println("if you want exit enter 1 and if you want continue enter 0");
                    wantsExit = scanner.nextInt();
                    scanner.nextLine();
                }
                while (wantsExit != 1 && wantsExit != 0);
            }
        }
        //admin
        else if (type == 2) {
            System.out.println("please insert your username");
            String adminUsername = scanner.nextLine();
            System.out.println("please insert your password");
            String adminPassword = scanner.nextLine();
            User admin = userRepository.authenticateUser(Role.Admin,adminUsername,adminPassword);
            if (admin == null)
            {
                throw new  NoSuchElementException("admin not found");
            }
            while (wantsExit == 0) {
                System.out.println("choose your activity\n_____________________");
                System.out.println("1.create a user\n2.create a new course\n3.remove a user\n4.remove a course\n5.exit");
                int activity = scanner.nextInt();
                scanner.nextLine();
                if (activity == 1)
                {
                    Role role = null;
                    while (role == null) {
                        System.out.println("please enter the role of new user\nAdmin\nTeacher\nStudent");
                        switch (scanner.nextLine().toLowerCase())
                        {
                            case "admin":
                                role = Role.Admin;
                                break;
                            case "teacher":
                                role = Role.Teacher;
                                break;
                            case "student":
                                role = Role.Student;
                                break;
                            default:
                                System.out.println("Please Enter a valid role");
                                break;
                        }
                    }
                    System.out.println("please enter fullName,username and password");
                    String fullName = scanner.nextLine();
                    String username = scanner.nextLine();
                    String password = scanner.nextLine();
                    String hashedPassword = PasswordUtils.hashPassword(password);
                    User user = new User(role,fullName,username,hashedPassword);
                    if(userRepository.addUser(user))
                    {
                        System.out.println("executed");
                    }
                }
                else if (activity == 2)
                {
                    System.out.println("please enter courseName,teacherUserName,capacity");
                    String courseName = scanner.nextLine();
                    String teacherUserName = scanner.nextLine();
                    int capacity = scanner.nextInt();
                    scanner.nextLine();
                    var teacherId = userRepository.getUserIdByUsername(teacherUserName);
                    if (teacherId == -1)
                    {
                        throw  new NoSuchElementException("teacher not found");
                    }
                    Course course = new Course(courseName,teacherId,capacity);
                    if(courseRepository.addCourse(course))
                    {
                        System.out.println("executed");
                    }
                }
                else if(activity == 3)
                {
                    System.out.println("Please enter username to remove");
                    String username = scanner.nextLine();
                    User user = userRepository.getUserByUsername(username);
                    if (user == null)
                    {
                        throw new SQLException("The user is not found");
                    }
                    System.out.println("role : " + user.getRole().toString() +"\nusername : " + user.getUsername() +
                            "\nfullName  : " + user.getFullName());
                    System.out.println("are you sure to remove ? yes/no");
                    if (scanner.nextLine().equalsIgnoreCase("yes"))
                    {
                        if(userRepository.removeUser(user.getId())){
                            System.out.println("executed");
                        }
                    }
                }
                else if (activity == 4)
                {
                    System.out.println("Please enter course title to remove");
                    String courseTitle = scanner.nextLine();
                    int courseId = courseRepository.getCourseIdByTitle(courseTitle);
                    if (courseId == -1)
                    {
                        throw new NoSuchElementException("course not found");
                    }
                    if(courseRepository.removeCourse(courseId))
                    {
                        System.out.println("executed");
                    }
                }
                else if (activity == 5)
                {
                    return;
                }
                else
                {
                    System.out.println("please enter a valid value");
                }

                do {
                    System.out.println("if you want exit enter 1 and if you want continue enter 0");
                    wantsExit = scanner.nextInt();
                    scanner.nextLine();
                }
                while (wantsExit != 1 && wantsExit != 0);

            }
        }
        else System.out.println("please just insert 1 or 2");

    }
}