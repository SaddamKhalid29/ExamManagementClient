package com.uog.deptexammanagementsystem.student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */

import com.uog.exam.entity.StudentEntity;
import com.uog.exam.student.StudentManagerRemote;
import com.uog.exam.student.StudentNotFoundException;
import com.uog.exam.student.WrongParameterException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
public class StudentBean {

    /**
     * Creates a new instance of studentBean
     */
    @EJB
    StudentManagerRemote studentManager;
    private String studentName ;
    private String studentRollNo;
    private String studentAddress;
    private String  studentSection;
    private String studentEmail;
    private int studentSemester;
    private String studentContact;
    private List<StudentEntity> allStudentsList;
    
    public StudentBean() {
    }
    public void addStudent(){
        System.out.println("Adding student:"+this.studentName);
        try {
            studentManager.addNewStudent(studentRollNo, studentName, studentSemester, studentSection, studentEmail, studentContact);
            System.out.println("Student is added successfully!");
            System.out.println("Getting all of the students");
        } catch (WrongParameterException ex) {
            addMessage("Add Student",ex.getMessage());
        }
    }
    @PostConstruct
    public void init(){
        try {
            allStudentsList=studentManager.getAllStudents();
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName the studentName to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return the studentRollNo
     */
    public String getStudentRollNo() {
        return studentRollNo;
    }
    private void addMessage(String title,String detail){
        FacesMessage msg=new FacesMessage(title,detail);
        FacesContext.getCurrentInstance().addMessage("StudentBean", msg);
    }
    /**
     * @param studentRollNo the studentRollNo to set
     */
    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    /**
     * @return the studentAddress
     */
    public String getStudentAddress() {
        return studentAddress;
    }

    /**
     * @param studentAddress the studentAddress to set
     */
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    /**
     * @return the studentContact
     */
    public String getStudentContact() {
        return studentContact;
    }

    /**
     * @param studentContact the studentContact to set
     */
    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    /**
     * @return the studentSection
     */
    public String getStudentSection() {
        return studentSection;
    }

    /**
     * @param studentSection the studentSection to set
     */
    public void setStudentSection(String studentSection) {
        this.studentSection = studentSection;
    }

    /**
     * @return the studentEmail
     */
    public String getStudentEmail() {
        return studentEmail;
    }

    /**
     * @param studentEmail the studentEmail to set
     */
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    /**
     * @return the studentSemester
     */
    public int getStudentSemester() {
        return studentSemester;
    }

    /**
     * @param studentSemester the studentSemester to set
     */
    public void setStudentSemester(int studentSemester) {
        this.studentSemester = studentSemester;
    }
    
    public void submit(){
       // StudentBean studeBean=new StudentBean();
        System.out.println("*************************");
        System.out.println("***** Student data ******");
        System.out.println("*************************");
        System.out.println("Student Roll No: "+studentRollNo);
        System.out.println("Student Name : "+studentName);
        System.out.println("Student Semester : "+studentSemester);
        System.out.println("Student Section : "+studentSection);
        System.out.println("Student Emaail : "+studentEmail);
        System.out.println("Student Address : "+studentAddress);
        System.out.println("Student Contact : "+studentContact);
    }

    /**
     * @return the allStudentsList
     */
    public List<StudentEntity> getAllStudentsList() {
        return allStudentsList;
    }

    /**
     * @param allStudentsList the allStudentsList to set
     */
    public void setAllStudentsList(List<StudentEntity> allStudentsList) {
        this.allStudentsList = allStudentsList;
    }
}
