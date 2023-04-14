package com.uog.deptexammanagementsystem.course;

import com.uog.exam.course.CourseManagerRemote;
import com.uog.exam.course.CourseNotFoundException;
import com.uog.exam.course.WrongParameterException;
import com.uog.exam.entity.CourseEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;


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
    private List<CourseEntity> allCoursesList;
    private boolean globalFilterOnly;
    @EJB
    CourseManagerRemote courseManager;

    public void addCourse() {
        System.out.println("Course Name : " + this.courseTitle);
        try {
           CourseEntity courseEntity =  courseManager.addCourse(courseTitle, courseCode, courseCreditHours);
            allCoursesList.add(courseEntity);
            
            System.out.println("Course is added...");
        } catch (WrongParameterException ex) {
            addMessage("Add Course", ex.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        setGlobalFilterOnly(false);
        try {
            allCoursesList = courseManager.getAllCourses();
        } catch (CourseNotFoundException ex) {
            Logger.getLogger(courseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public courseBean() {
    }

    private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("CourseBean", msg);
    }

    public void updateCourseData(int courseId, String courseTitle, String courseCode,int creditHours){
        
        try {
            courseManager.updateCourseTitle(courseId, courseTitle,courseCode,creditHours);
            System.out.println("Course Data is updated successfully." +courseId);
        } catch (CourseNotFoundException | WrongParameterException ex) {
            Logger.getLogger(courseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void deletCourse(int courseId, CourseEntity courseEntity) {
        try {
            courseManager.deleteCourse(courseId);
            allCoursesList.remove(courseEntity);
        } catch (CourseNotFoundException ex) {
            Logger.getLogger(courseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    
     public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }
     
    public void submit() {
        System.out.println("****************************");
        System.out.println("******* Courses Data *******");
        System.out.println("****************************");
        System.out.println("Course Title : " + courseTitle);
        System.out.println("Course Code  : " + courseCode);
        System.out.println("Credit Hours : " + courseCreditHours);
    }

    /**
     * @return the allCoursesList
     */
    public List<CourseEntity> getAllCoursesList() {
        return allCoursesList;
    }

    /**
     * @param allCoursesList the allCoursesList to set
     */
    public void setAllCoursesList(List<CourseEntity> allCoursesList) {
        this.allCoursesList = allCoursesList;
    }

    /**
     * @return the globalFilterOnly
     */
    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    /**
     * @param globalFilterOnly the globalFilterOnly to set
     */
    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }
    
    public void onRowEdit(RowEditEvent<CourseEntity> event) {
        FacesMessage msg = new FacesMessage("Course Edited", String.valueOf(event.getObject().getCourseID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<CourseEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getCourseID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
