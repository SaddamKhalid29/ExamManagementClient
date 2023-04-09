package com.uog.deptexammanagementsystem.course;


import com.uog.exam.course.CourseManagerRemote;
import com.uog.exam.course.WrongParameterException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

@ManagedBean
@ViewScoped
public class courseBean {
    
    private String courseTitle;
    private String courseCode;
    private int courseCreditHours;

    @EJB
    CourseManagerRemote courseManager;
    public void addCourse(){
        System.out.println("Course Name : "+this.courseTitle);
        try {
            courseManager.addCourse(courseTitle, courseCode, courseCreditHours);
            System.out.println("Course is added...");
        } catch (WrongParameterException ex) {
            addMessage("Add Course", ex.getMessage());
        }
    }
    public courseBean() {
    }
      private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("StudentBean", msg);
    }
    /**
     * @return the courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * @param courseTitle the courseTitle to set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * @return the courseCreditHours
     */
    public int getCourseCreditHours() {
        return courseCreditHours;
    }

    /**
     * @param courseCreditHours the courseCreditHours to set
     */
    public void setCourseCreditHours(int courseCreditHours) {
        this.courseCreditHours = courseCreditHours;
    }
    
    public void submit(){
        System.out.println("****************************");
        System.out.println("******* Courses Data *******");
        System.out.println("****************************");
        System.out.println("Course Title : "+courseTitle);
        System.out.println("Course Code  : "+courseCode);
        System.out.println("Credit Hours : "+courseCreditHours);
    }
    
}
