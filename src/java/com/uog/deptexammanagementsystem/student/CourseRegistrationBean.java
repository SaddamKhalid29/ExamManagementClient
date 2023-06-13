package com.uog.deptexammanagementsystem.student;

import com.uog.exam.course.CourseManagerRemote;
import com.uog.exam.course.CourseNotFoundException;
import com.uog.exam.entity.CourseEntity;
import com.uog.exam.entity.CourseRegistrationEntity;
import com.uog.exam.entity.StudentEntity;
import com.uog.exam.student.CourseRegisterManagerRemote;
import com.uog.exam.student.StudentManagerRemote;
import com.uog.exam.student.StudentNotFoundException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class CourseRegistrationBean implements Serializable {

    @EJB
    private CourseRegisterManagerRemote registerManagerRemote;

    @EJB
    private StudentManagerRemote studentManagerRemote;

    @EJB
    private CourseManagerRemote courseManaagerRemote;



        // ...
        private StudentEntity studentID;
        private CourseEntity courseID;
        private String courseRegYear;

        // ...
        public void assignCourseToStudent() {
            // Create a new CourseRegistrationEntity and set the student, course, and courseRegYear
            CourseRegistrationEntity courseRegistrationEntity = new CourseRegistrationEntity();
            courseRegistrationEntity.setCourseRegStudentID(studentID);
            courseRegistrationEntity.setCourseRegCourseID(courseID);
            courseRegistrationEntity.setCourseRegYear(courseRegYear);

            // Call the register method in your registerManagerRemote to store the course registration
            try {
                registerManagerRemote.registerSudentInCourse(studentID, courseID, courseRegYear);

                System.out.println("Course assigned to student successfully!");

            } catch (com.uog.exam.student.WrongParameterException ex) {
                System.out.println("Course assigned to student is unsuccessful.");
                Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Getters and Setters
        public StudentEntity getStudent() {
            return studentID;
        }

        public void setStudent(StudentEntity student) {
            this.studentID = student;
        }

        public CourseEntity getCourse() {
            return courseID;
        }

        public void setCourse(CourseEntity course) {
            this.courseID = course;
        }

        public String getCourseRegYear() {
            return courseRegYear;
        }

        public void setCourseRegYear(String courseRegYear) {
            this.courseRegYear = courseRegYear;
        }


    private List<StudentEntity> studentList;
    private List<CourseEntity> courseList;

    @PostConstruct
    public void init() {
        try {
            studentList = studentManagerRemote.getAllStudents();
            courseList = courseManaagerRemote.getAllCourses();
        } catch (StudentNotFoundException | CourseNotFoundException ex) {
            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void assignCourseToStudent() {
//        System.out.println("Course is being offering to students");
//        CourseRegistrationEntity courseRegistrationEntity = null;
//        try {
//            courseRegistrationEntity = registerManagerRemote.registerSudentInCourse(studentEntity, courseEntity, courseRegYear);
//        } catch (com.uog.exam.student.WrongParameterException ex) {
//            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (courseRegistrationEntity != null) {
//            System.out.println("Course is assigned to student successfully!");
//            // Reset the input fields
//            
//            courseRegYear = null;
//        } else {
//            System.out.println("An error occurred while assigning the course to the student.");
//        }
//    }
    // Getters and Setters
//    public StudentEntity getStdID() {
//        return studentEntity;
//    }
//
//    public void setStdID(StudentEntity stdID) {
//        this.studentEntity = stdID;
//    }
//
//    public CourseEntity getCourseID() {
//        return courseEntity;
//    }
//
//    public void setCourseID(CourseEntity courseID) {
//        this.courseEntity = courseID;
//    }
//
//    public String getCourseRegYear() {
//        return courseRegYear;
//    }
//
//    public void setCourseRegYear(String courseRegYear) {
//        this.courseRegYear = courseRegYear;
//    }
    public List<StudentEntity> getStudentList() {
        return studentList;
    }

    public List<CourseEntity> getCourseList() {
        return courseList;
    }
}
