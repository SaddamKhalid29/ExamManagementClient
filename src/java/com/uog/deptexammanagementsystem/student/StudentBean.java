package com.uog.deptexammanagementsystem.student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
import com.uog.exam.entity.StudentEntity;
import com.uog.exam.entity.UserEntity;
import com.uog.exam.student.StudentManagerRemote;
import com.uog.exam.student.StudentNotFoundException;
import com.uog.exam.student.WrongParameterException;
import com.uog.exam.users.UserMangerRemote;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class StudentBean implements Serializable {

    /**
     * Creates a new instance of studentBean
     */
    @EJB
    StudentManagerRemote studentManager;
    @EJB
    UserMangerRemote userManager;
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

    private Part file;

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

            //To add the student in the database
            StudentEntity studentEntity = studentManager.addNewStudent(studentRollNo, studentName, studentSemester, studentSection, studentEmail, studentContact);
            allStudentsList.add(studentEntity);
            try {
                //To add the new user in the database
                UserEntity user = userManager.addUser(studentRollNo, "student12345", studentEmail, "student");
                System.out.println("New user is added : " + user.getUserName());

                //To rest the values in the frontend
                studentName = null;
                studentRollNo = null;
                studentEmail = null;
                studentSection = null;
                studentSemester = 0;
                studentContact = null;

            } catch (com.uog.exam.users.WrongParameterException ex) {
                Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Student is added successfully!");
            System.out.println("Getting all of the students");
        } catch (WrongParameterException ex) {
            addMessage("Add Student", ex.getMessage());
        }
    }

    //To upload the file data
    public void uploadStudentFile() {
        try {
            StudentEntity studentEntity;
            Scanner scan = new Scanner(file.getInputStream());
            System.out.println("Student Data from file");

            while (scan.hasNextLine()) {

                String nextLine = scan.nextLine();
                System.out.println(nextLine);
                String[] stdData = nextLine.split(",");
                String stdID = stdData[0];
                String stdName = stdData[1];
                String stdRollNo = stdData[2];
                String stdContact = stdData[3];
                String section = stdData[4];
                int semester = Integer.parseInt(stdData[5]);
                String email = stdData[6];
                try {
                    studentEntity = studentManager.addNewStudent(stdRollNo, stdName, semester, section, email, stdContact);
                    if(studentEntity!= null){
                        System.out.println("New students from file is successfully inserted!"+ stdID + stdName);
                    }else{
                        System.out.println("Unsuccessful to insert new student from file..");
                    }
                } catch (WrongParameterException ex) {
                    Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //To update the student student
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
    //This two methods will allow us to edit the row 
    //If we click yes then  this method will be called
    public void onRowEdit(RowEditEvent<StudentEntity> event) {
        FacesMessage msg = new FacesMessage("Student Edited", String.valueOf(event.getObject().getStudentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //If we cancel editing then this message will be called
    public void onRowCancel(RowEditEvent<StudentEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getStudentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //To remove the data from the database as well as from frontend
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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
