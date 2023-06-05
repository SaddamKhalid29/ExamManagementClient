/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.department;

import com.uog.exam.department.DepartmentManagerRemote;
import com.uog.exam.department.DepartmentNotFoundException;
import com.uog.exam.department.WrongParameterException;
import com.uog.exam.entity.DepartmentEntity;
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
public class DepartmentBean {

    private String departmentName;
    private List<DepartmentEntity> allDepartmentsList;
    private boolean globalFilterOnly;
    private Part file;


    @EJB
    DepartmentManagerRemote departmentManager;

    public void addDepartment() {
        System.out.println("Department Name : " + this.departmentName);
        try {
            DepartmentEntity departmentEntity = departmentManager.addDepartment(departmentName);
            System.out.println("Department is added..." + departmentEntity.getDepartmentName());
            departmentName = null;
        } catch (WrongParameterException ex) {
            addMessage("Add Department", ex.getMessage());
        }
    }
    
    

//    public void upload() {
//        if (file != null) {
//            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
//    }
//
//    public void handleFileUpload(FileUploadEvent event) {
//        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//    }
//    public void upload() {
//        try {
//
//            DepartmentEntity departmentEntity;
//            InputStream inputStream = file.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            List<String[]> data = new ArrayList<>();
//            inputStream = file.getInputStream();
//            Scanner scan = new Scanner(inputStream);
//            while (scan.hasNextLine()) {
//                String nextLine = scan.nextLine();
//                System.out.println("The nextLine " + nextLine);
//                System.out.println(" ");
//                String[] deptData = nextLine.split(",");
//                String deptName = deptData[0];
//                try {
//                    departmentEntity = departmentManager.addDepartment(deptName);
//                    System.out.println("Course is added..." + departmentEntity.getDepartmentName());
//                } catch (WrongParameterException ex) {
//                    Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//            // Optionally, display a success message or perform other actions
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the exception and display an error message
//        }
//    }

    @PostConstruct
    public void init() {
        setGlobalFilterOnly(false);
        try {
            allDepartmentsList = departmentManager.getAllDepartments();
        } catch (DepartmentNotFoundException ex) {
            Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void uploadDepartmentFile(){
        
        try {
            Scanner scan = new Scanner(file.getInputStream());
            System.out.println("Department Data");
            DepartmentEntity  departmentEntity;
            while(scan.hasNextLine()){
            String nextLine = scan.nextLine();
                System.out.println(" The Data is ");
                System.out.println(nextLine);
                System.out.println("");
                String[] deptData = nextLine.split(",");
                String deptName = deptData[0];
                try {
                    System.out.println("Adding new department to database");
                    departmentEntity = departmentManager.addDepartment(deptName);
                    if(departmentEntity!=null){
                        System.out.println("Department is added successfully");
                    }else{
                        System.out.println("Department is not added");
                    }
                } catch (WrongParameterException ex) {
                    Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Sucessfuly Loaded Department Data");
            scan.close();
            
        } catch (IOException ex) {
            Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//    public void uploadFile(FileUploadEvent event) {
//        UploadedFile uploadedFile = event.getFile();
//        InputStream inputStream = null;
//
//        DepartmentEntity departmentEntity;
//
//        try {
//            inputStream = uploadedFile.getInputStream();
//            Scanner scan = new Scanner(inputStream);
//            while (scan.hasNextLine()) {
//                String nextLine = scan.nextLine();
//                System.out.println("The nextLine " + nextLine);
//                System.out.println(" ");
//                String[] deptData = nextLine.split(",");
//                String deptName = deptData[0];
//                try {
//                    departmentEntity = departmentManager.addDepartment(deptName);
//                    System.out.println("Course is added..." + departmentEntity.getDepartmentName());
//                } catch (WrongParameterException ex) {
//                    Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if(inputStream!=null){
//            inputStream.close();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("DepartmentBean", msg);
    }

    public void updateDepartmentData(int deptID, String departmentName) {

        try {
            departmentManager.updateDepartmentName(deptID, departmentName);
            System.out.println("Department Data is updated successfully." + deptID);
        } catch (WrongParameterException ex) {
            Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteDepartment(int deptID, DepartmentEntity departmentEntity) {
        try {
            departmentManager.deleteDepartmentById(deptID);
            allDepartmentsList.remove(departmentEntity);
        } catch (DepartmentNotFoundException ex) {
            Logger.getLogger(DepartmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void submit() {
        System.out.println("****************************");
        System.out.println("******* Department Data *******");
        System.out.println("****************************");
        System.out.println("Department Name : " + departmentName);
    }

    public void onRowEdit(RowEditEvent<DepartmentEntity> event) {
        FacesMessage msg = new FacesMessage("Department Edited", String.valueOf(event.getObject().getDepartmentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<DepartmentEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getDepartmentID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
  
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<DepartmentEntity> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<DepartmentEntity> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

}
