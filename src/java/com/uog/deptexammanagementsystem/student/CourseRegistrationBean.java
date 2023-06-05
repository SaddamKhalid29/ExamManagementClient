/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.student;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class CourseRegistrationBean implements Serializable {

//    @EJB
//    CourseRegisterManagerRemote registerManagerRemote;
//
//    @EJB
//    StudentManagerRemote studentManagerRemote;
//
//    @EJB
//    CourseManagerRemote courseManaagerRemote;
//
//    private StudentEntity stdID;
//
//    private CourseEntity courseID;
//
//    private String courseRegYear;
//
//    private boolean globalFilterOnly;
//
//    private List<StudentEntity> studentList;
//    
//    private List<CourseEntity> courseList;
//    
//    private int selectedStudent;
//    
//    private int selectedCourse;
//    
//    private List<CourseRegistrationEntity> allRegisterList;
//
//    @PostConstruct
//    public void init() {
//
//        setGlobalFilterOnly(false);
//        try {
//            try {
//                studentList = studentManagerRemote.getAllStudents();
//                courseList = courseManaagerRemote.getAllCourses();
//            } catch (CourseNotFoundException ex) {
//                Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            allRegisterList = registerManagerRemote.getAllRegisteredCourse();
//        } catch (StudentNotFoundException ex) {
//            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void assignCourseToStudent() {
//
//        try {
//
//            CourseRegistrationEntity courseRegistrationEntity = registerManagerRemote.registerSudentInCourse(stdID, courseID, courseRegYear);
//
//            allRegisterList.add(courseRegistrationEntity);
//
//            if (courseRegistrationEntity != null) {
//
//                System.out.println("Course is assigned to student successfully!..");
//            } else {
//
//                System.out.println("While assigning course to student an error occur.");
//            }
//
//        } catch (WrongParameterException ex) {
//
//            Logger.getLogger(CourseRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public CourseRegistrationBean() {
//
//    }
//
//    public boolean isGlobalFilterOnly() {
//        return globalFilterOnly;
//    }
//
//    public void setGlobalFilterOnly(boolean globalFilterOnly) {
//        this.globalFilterOnly = globalFilterOnly;
//    }
//
//    public StudentEntity getStdID() {
//        return stdID;
//    }
//
//    public void setStdID(StudentEntity stdID) {
//        this.stdID = stdID;
//    }
//
//    public CourseEntity getCourseID() {
//        return courseID;
//    }
//
//    public void setCourseID(CourseEntity courseID) {
//        this.courseID = courseID;
//    }
//
//    public String getCourseRegYear() {
//        return courseRegYear;
//    }
//
//    public void setCourseRegYear(String courseRegYear) {
//        this.courseRegYear = courseRegYear;
//    }
//
//
//   
//    // Getters and Setters
//    public List<StudentEntity> getStudentList() {
//        return studentList;
//    }
//
//    public List<CourseEntity> getCourseList() {
//        return courseList;
//    }
//
//    public int getSelectedStudent() {
//        return selectedStudent;
//    }
//
//    public void setSelectedStudent(int selectedStudent) {
//        this.selectedStudent = selectedStudent;
//    }
//
//    public int getSelectedCourse() {
//        return selectedCourse;
//    }
//
//    public void setSelectedCourse(int selectedCourse) {
//        this.selectedCourse = selectedCourse;
//
//    }
}
