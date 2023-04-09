/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.faculty;

import com.uog.exam.faculty.FacultyManagerRemote;
import com.uog.exam.faculty.WrongParameterException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class FacultyBean {

    @EJB
    FacultyManagerRemote facultyManager;
    
    private String facultyName;
    private String facultyDesignation;
    private String facultyEmail;
    private String facultyContactNo;
    private boolean isVisiting;

    public FacultyBean() {
    }

    public void addFaculty() {
        System.out.println("Faculty Name : " + this.facultyName);
        try {
            facultyManager.addNewFaculty(facultyName, facultyEmail, facultyContactNo, facultyDesignation, isVisiting);
            System.out.println("faculty is added..");
        } catch (WrongParameterException ex) {
            addMessage("Add Faculty", ex.getMessage());

        }
    }

    private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("StudentBean", msg);
    }

    /**
     * @return the facultyName
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * @param facultyName the facultyName to set
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * @return the facultyDesignation
     */
    public String getFacultyDesignation() {
        return facultyDesignation;
    }

    /**
     * @param facultyDesignation the facultyDesignation to set
     */
    public void setFacultyDesignation(String facultyDesignation) {
        this.facultyDesignation = facultyDesignation;
    }

    /**
     * @return the facultyEmail
     */
    public String getFacultyEmail() {
        return facultyEmail;
    }

    /**
     * @param facultyEmail the facultyEmail to set
     */
    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    /**
     * @return the facultyContactNo
     */
    public String getFacultyContactNo() {
        return facultyContactNo;
    }

    /**
     * @param facultyContactNo the facultyContactNo to set
     */
    public void setFacultyContactNo(String facultyContactNo) {
        this.facultyContactNo = facultyContactNo;
    }

    /**
     * @return the isVisiting
     */
    public boolean isIsVisiting() {
        return isVisiting;
    }

    /**
     * @param isVisiting the isVisiting to set
     */
    public void setIsVisiting(boolean isVisiting) {
        this.isVisiting = isVisiting;
    }

    public void submit() {
        System.out.println("************************");
        System.out.println("**** Faculty Data ******");
        System.out.println("************************");
        System.out.println("Faculty Name : " + facultyName);
        System.out.println("Faculty Designation : " + facultyDesignation);
        System.out.println("Faculty Email : " + facultyEmail);
        System.out.println("Faculty Contact : " + facultyContactNo);
        System.out.println("Visiting : " + isVisiting);

    }

}
