/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.faculty;

import com.uog.exam.entity.FacultyEntity;
import com.uog.exam.faculty.FacultyManagerRemote;
import com.uog.exam.faculty.FacultyNotFoundException;
import com.uog.exam.faculty.WrongParameterException;
import com.uog.exam.users.UserMangerRemote;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FacultyBean {

    @EJB
    FacultyManagerRemote facultyManager;
    
    @EJB
    UserMangerRemote userManger;
    
    private String facultyName;
    
    private String facultyDesignation;
    
    private String facultyEmail;
    
    private String facultyContactNo;
    
    private boolean isVisiting;
    
    private List<FacultyEntity> allFacultyList;
    
    private boolean globalFilterOnly;

    private Part file;

    public FacultyBean() {
    }

    public void addFaculty() {
    
        System.out.println("Faculty Name : " + this.facultyName);
        
        try {
        
            FacultyEntity facultyEntity = facultyManager.addNewFaculty(facultyName, facultyEmail, facultyContactNo, facultyDesignation, isVisiting);
           
            try {
        
                userManger.addUser(facultyEmail, "faculty1234", facultyEmail, "faculty");
            
            } catch (com.uog.exam.users.WrongParameterException ex) {
            
                Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            allFacultyList.add(facultyEntity);
            
            facultyName = null;
            
            facultyEmail = null;
            
            facultyContactNo = null;
            
            facultyDesignation = null;
            
            isVisiting = false;
            
            System.out.println("faculty is added..");
        
        } catch (WrongParameterException ex) {
        
            addMessage("Add Faculty", ex.getMessage());

        }
        
    }

    @PostConstruct
    public void init() {
        
        setGlobalFilterOnly(false);
        
        try {
        
            allFacultyList = facultyManager.getAllFaculty();
        
        } catch (FacultyNotFoundException ex) {
        
            Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    public void uploadFacultyFile(){
    
        try {
            
            FacultyEntity facultyEntity;
            
            Scanner scan = new Scanner(file.getInputStream());
            
            while(scan.hasNextLine()){
            
                String nextLine = scan.nextLine();
                
                String[] facultyData = nextLine.split(",");
                
                String facultyName = facultyData[0];
                
                String facultyEmail = facultyData[1];
                
                String facultyContactNo = facultyData[2];
                
                String facultyDesignation = facultyData[3];
                
                boolean isVisiting = Boolean.parseBoolean(facultyData[4]);
                
                try {
                    
                    facultyEntity = facultyManager.addNewFaculty(facultyName, facultyEmail, facultyContactNo, facultyDesignation, isVisiting);
                
                    if(facultyEntity !=null){
                    
                        System.out.println("New faculty member is added."+ facultyName);
                    }else{
                    
                        System.out.println("An error occur while adding faculty member.");
                    }
                } catch (WrongParameterException ex) {
                    Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (IOException ex) {
        
            Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void addMessage(String title, String detail) {
    
        FacesMessage msg = new FacesMessage(title, detail);
        
        FacesContext.getCurrentInstance().addMessage("FacultyBean", msg);
    }

    public String deleteFaculty(int id, FacultyEntity facultyEntity) {

        try {
            
            facultyManager.deleteFacultyById(id);
            
            allFacultyList.remove(facultyEntity);
        
        } catch (FacultyNotFoundException ex) {
            
            Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateFaculty(int facultyId, String facultyName, String faculyDesignation, String facultyEmail, String facultyContact, boolean isVisiting) {

        try {
        
            facultyManager.updateFacultyName(facultyId, facultyName, faculyDesignation, facultyEmail, facultyContact, isVisiting);
            
            System.out.println("Faculty is updated successfully." + facultyId);
        
        } catch (FacultyNotFoundException | WrongParameterException ex) {
        
            Logger.getLogger(FacultyBean.class.getName()).log(Level.SEVERE, null, ex);
        
        }
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

    public void toggleGlobalFilter() {
        
        setGlobalFilterOnly(!isGlobalFilterOnly());
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

    
    
    public Part getFile() {
        
        return file;
    
    }

    public void setFile(Part file) {
    
        this.file = file;
    
    }
    
    
    
    /**
     * @return the getFacultyList
     */
    public List<FacultyEntity> getGetFacultyList() {
    
        return allFacultyList;
    
    }

    /**
     * @param getFacultyList the getFacultyList to set
     */
    public void setGetFacultyList(List<FacultyEntity> allFacultyList) {
    
        this.allFacultyList = allFacultyList;
    
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

    public void onRowEdit(RowEditEvent<FacultyEntity> event) {
    
        FacesMessage msg = new FacesMessage("Faculty Edited", String.valueOf(event.getObject().getFacultyID()));
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    
    }

    public void onRowCancel(RowEditEvent<FacultyEntity> event) {
    
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getFacultyID()));
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    
    }

}
