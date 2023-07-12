/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.user;

/**
 *
 * @author User
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.uog.exam.entity.UserEntity;
import com.uog.exam.users.UserMangerRemote;
import com.uog.exam.users.UserNotFoundException;
import com.uog.exam.users.WrongParameterException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author arahman
 */
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    @EJB
    UserMangerRemote userManager;

    private String userEmail;
    private String subscriberPassword;
    private String userName;
    private String userPassword;
    private UserEntity user;
    private int serviceId;

    public void authenticateUser(UserEntity user) {
        System.out.println("Calling authenticate user");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            System.out.println("Welcome into user authenticate method....");

            try {

                user = userManager.findUserByUserName(userName);

                String password = user.getUserPassword();

                if (password.equals(userPassword)) {

                    System.out.println("Login Successfully!");

                    if ("admin".equals(user.getUserType())) {

                        try {
                            externalContext.redirect("addStudent.xhtml");

                        } catch (IOException ex) {
                            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if ("student".equals(user.getUserType()) || "faculty".equals(user.getUserType())) {

                        try {
                            externalContext.redirect("Home.xhtml");
                        } catch (IOException ex) {
                            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {

                    System.out.println("Username or password is incorrect!");
               
                    throw new WrongParameterException("Username or password is incorrect!");
                }
            } catch (UserNotFoundException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }

//            user = userManager.loginUser(userName, userPassword);
//
//            System.out.println("the user is : " + user.getUserName() + user.getUserType());
            setUser(user);
            if (user == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Wrong password."));
                System.out.println("No user is here");
            } else {
                System.out.println("The user is here" + user.getUserName());
            }
        } catch (WrongParameterException ex) {
            System.out.println("This is the exception");
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Redirecting to Dashboard");

    }

    public void addUser() throws WrongParameterException {
//        UserEntity userEntity = userManager.addUser(userName, userPassword, userEmail);
//        System.out.println("Student is added succesfully!" +userEntity.getUserName());

    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login.xhtml?faces-redirect=true";
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the subscriberPassword
     */
    public String getSubscriberPassword() {
        return subscriberPassword;
    }

    /**
     * @param subscriberPassword the subscriberPassword to set
     */
    public void setSubscriberPassword(String subscriberPassword) {
        this.subscriberPassword = subscriberPassword;
    }

    /**
     * @return the serviceId
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the userPhoneNumber
     */
    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
