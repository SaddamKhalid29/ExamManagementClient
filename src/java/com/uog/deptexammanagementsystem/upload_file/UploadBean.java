/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.upload_file;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class UploadBean {

    private Part file;

    public void upload() {

        Scanner scan;
        try {
            System.out.println("Uploading The file data");
            scan = new Scanner(file.getInputStream());

            String fileData = scan.useDelimiter("\\A").next();
            while (scan.hasNextLine()) {
                String nextLine = scan.nextLine();
                System.out.println("The nextLine " + nextLine);
            }
            scan.close();
            System.out.println(fileData);
            System.out.println("Succeful");
        } catch (IOException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

}
