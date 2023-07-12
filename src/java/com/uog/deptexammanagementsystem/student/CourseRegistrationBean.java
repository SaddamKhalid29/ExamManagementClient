package com.uog.deptexammanagementsystem.student;

import com.uog.exam.course.CourseManagerRemote;
import com.uog.exam.course.CourseNotFoundException;
import com.uog.exam.entity.CourseEntity;
import com.uog.exam.entity.CourseRegistrationEntity;
import com.uog.exam.entity.StudentEntity;
import com.uog.exam.student.CourseRegisterManagerRemote;
import com.uog.exam.student.DatabaseInconsistentStateException;
import com.uog.exam.student.StudentManager;
import com.uog.exam.student.StudentManagerRemote;
import com.uog.exam.student.StudentNotFoundException;
import com.uog.exam.student.WrongParameterException;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CourseRegistrationBean implements Serializable {

    @EJB
    CourseRegisterManagerRemote registerManagerRemote;

    @EJB
    StudentManagerRemote studentManagerRemote;

    @EJB
    CourseManagerRemote courseManaagerRemote;

    private StudentEntity stdID;
    private CourseEntity courseID;
    private String courseRegYear;
    private String studentId;
    private String courseId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    private boolean globalFilterOnly;
    private List<StudentEntity> studentList;
    private List<CourseEntity> courseList;
    private List<CourseRegistrationEntity> allRegisterList;

    @PostConstruct
    public void init() {
        setGlobalFilterOnly(false);
        try {
            studentList = studentManagerRemote.getAllStudents();
            courseList = courseManaagerRemote.getAllCourses();
        } catch (StudentNotFoundException | CourseNotFoundException ex) {
            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerStudentInCourse(StudentEntity student, CourseEntity course) {
        try {

            student = studentManagerRemote.getStudentByRollNo(studentId);

            course = courseManaagerRemote.getCourseByCourseCode(courseId);

            boolean alreadyRegistered = isAlreadyRegistered(student, course); // Check if student is already registered for the course

            if (alreadyRegistered == true) {

                System.out.println("Student is already registered...");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Error", "Student is already registered for this course."));

            } else {

                CourseRegistrationEntity register = registerManagerRemote.registerStudentInCourse(student, course, courseRegYear);
                System.out.println("Student roll no : " + student.getStudentRollNo());
                System.out.println("student name : " + student.getStudentName());
                System.out.println("Course Code : " + course.getCourseCode());
                System.out.println("Course Title : " + course.getCourseTitle());

                if (register == null) {
                    System.out.println("Student is not registered in course!");

                } else {
                    System.out.println("Successfully registered.");
                }
            }
        } catch (StudentNotFoundException | CourseNotFoundException | DatabaseInconsistentStateException | WrongParameterException ex) {
            System.out.println("Failed to register student in a course");
            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Helper method...
    private boolean isAlreadyRegistered(StudentEntity student, CourseEntity course) {

        try {
            List<CourseRegistrationEntity> registrations = registerManagerRemote.getAllRegisteredCourse();

            for (CourseRegistrationEntity register : registrations) {

                if (register.getCourseRegStudentID().equals(student) && register.getCourseRegCourseID().equals(course)) {

                    //System.out.println("Student is already registered in this course!");
                    return true;
                }
            }
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//    public void assignCourseToStudent() {
//        CourseRegistrationEntity courseRegistrationEntity = null;
//        try {
//            courseRegistrationEntity = registerManagerRemote.registerStudentInCourse(stdID, courseID, courseRegYear);
//
//            allRegisterList.add(courseRegistrationEntity);
//        } catch (com.uog.exam.student.WrongParameterException ex) {
//            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (courseRegistrationEntity != null) {
//            System.out.println("Course is assigned to the student successfully!");
//        } else {
//            System.out.println("An error occurred while assigning the course to the student.");
//        }
//    }
//    public void registerStudent() {
//        // Get the student and course based on the selected IDs
//        StudentEntity selectedStudent = studentList.stream()
//                .filter(s -> s.getStudentID() == studentId)
//                .findFirst()
//                .orElse(null);
//        CourseEntity selectedCourse = courseList.stream()
//                .filter(c -> c.getCourseID() == courseId)
//                .findFirst()
//                .orElse(null);
//        String year = null;
//
//        if (selectedStudent != null && selectedCourse != null) {
//            // Register the student in the course using the registration service
//            if (year != null) {
//
//                registerStudentInCourse(selectedStudent, selectedCourse, year);
//            }
//            // Clear the form fields after successful registration
//            studentId = 0;
//            courseId = 0;
//        } else {
//            // Handle error scenario (e.g., selected student or course not found)
//        }
//    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

    public StudentEntity getStdID() {
        return stdID;
    }

    public void setStdID(StudentEntity stdID) {
        this.stdID = stdID;
    }

    public CourseEntity getCourseID() {
        return courseID;
    }

    public void setCourseID(CourseEntity courseID) {
        this.courseID = courseID;
    }

    public String getCourseRegYear() {
        return courseRegYear;
    }

    public void setCourseRegYear(String courseRegYear) {
        this.courseRegYear = courseRegYear;
    }

    public List<StudentEntity> getStudentList() {
        return studentList;
    }

    public List<CourseEntity> getCourseList() {
        return courseList;
    }

    public List<CourseRegistrationEntity> getAllRegisterList() {
        return allRegisterList;
    }

}
