package com.uog.deptexammanagementsystem.student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
import com.uog.exam.entity.StudentEntity;
import com.uog.exam.student.StudentManagerRemote;
import com.uog.exam.student.StudentNotFoundException;
import com.uog.exam.student.WrongParameterException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class StudentBean implements Serializable{

    /**
     * Creates a new instance of studentBean
     */
    @EJB
    StudentManagerRemote studentManager;
    private String studentName;
    private String studentRollNo;
    private String studentAddress;
    private String studentSection;
    private String studentEmail;
    private int studentSemester;
    private String studentContact;
    private List<StudentEntity> allStudentsList;
    private boolean globalFilterOnly;
//    private List<StudentEntity> filterStudents;
//    private String searchTerm;

    public StudentBean() {
    }

    @PostConstruct
    public void init() {

        setGlobalFilterOnly(false);
        try {
            allStudentsList = studentManager.getAllStudents();
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addStudent() {
        System.out.println("Adding student:" + this.studentName);
        try {
            StudentEntity studentEntity = studentManager.addNewStudent(studentRollNo, studentName, studentSemester, studentSection, studentEmail, studentContact);
            allStudentsList.add(studentEntity);
            System.out.println("Student is added successfully!");
            System.out.println("Getting all of the students");
        } catch (WrongParameterException ex) {
            addMessage("Add Student", ex.getMessage());
        }
    }

    public void updateStudent(int stdId, String stdName, String stdRollNo, String stdEmail, String stdContact, int Semester, String stdSection) {
        try {
            studentManager.updateStudent(stdId, stdName, stdRollNo, stdEmail, stdContact, Semester, stdSection);
            System.out.println("Student is updated sucessfully." + stdId);
        } catch (StudentNotFoundException | WrongParameterException ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public StudentEntity search(String name) {
//        StudentEntity studentEntity = null;
//        System.out.println("Searching Students with name " + name);
//        try {
//            studentEntity = studentManager.getStudentByName(name);
//            System.out.println("Students are found" + studentEntity.getStudentRollNo());
//            System.out.println("Students are found" + studentEntity.getStudentName());
//        } catch (StudentNotFoundException | DatabaseInconsistentStateException ex) {
//            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return studentEntity;
//    }
//    public void search() {
//        setFilterStudents(allStudentsList.stream()
//                .filter(student -> student.getStudentRollNo().contains(getSearchTerm()))
//                .collect(Collectors.toList()));
//        System.out.println("Search students is ok");
//    }
//    public void searchById(int stdId){
//        try {
//           StudentEntity studentEntity = studentManager.getStudentId(stdId);
//            System.out.println("Student roll no is : "+studentEntity.getStudentRollNo());
//        } catch (StudentNotFoundException ex) {
//            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void onRowEdit(RowEditEvent<StudentEntity> event) {
        FacesMessage msg = new FacesMessage("Student Edited", String.valueOf(event.getObject().getStudentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<StudentEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getStudentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String deleteRecord(int id, StudentEntity studentEntity) {
//        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirmation", JOptionPane.YES_NO_OPTION);
//        if (result == JOptionPane.YES_OPTION) {
        try {

            allStudentsList.remove(studentEntity);
            studentManager.deleteStudentById(id);
            System.out.println("Successfully deleted");
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
//        } else {
//            System.out.println("Deletion operation canceled.");
//        }
        return null;
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

    private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
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

    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    public void submit() {
        // StudentBean studeBean=new StudentBean();
        System.out.println("*************************");
        System.out.println("***** Student data ******");
        System.out.println("*************************");
        System.out.println("Student Roll No: " + studentRollNo);
        System.out.println("Student Name : " + studentName);
        System.out.println("Student Semester : " + studentSemester);
        System.out.println("Student Section : " + studentSection);
        System.out.println("Student Emaail : " + studentEmail);
        System.out.println("Student Address : " + studentAddress);
        System.out.println("Student Contact : " + studentContact);
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

    
//    /**
//     * @return the searchTerm
//     */
//    public String getSearchTerm() {
//        return searchTerm;
//    }
//
//    /**
//     * @param searchTerm the searchTerm to set
//     */
//    public void setSearchTerm(String searchTerm) {
//        this.searchTerm = searchTerm;
//    }
//
//    /**
//     * @return the filterStudents
//     */
//    public List<StudentEntity> getFilterStudents() {
//        return filterStudents;
//    }
//
//    /**
//     * @param filterStudents the filterStudents to set
//     */
//    public void setFilterStudents(List<StudentEntity> filterStudents) {
//        this.filterStudents = filterStudents;
//    }
    
    
}
