/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.room;

import com.uog.exam.room.RoomManagerRemote;
import com.uog.exam.room.WrongParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class roomBean {
    
    @EJB
    RoomManagerRemote roomManager;
    
    private String roomNo;
    private int roomCapacity;

    public roomBean() {
    }
    public void addRoom(){
        System.out.println("Adding a room!");
        try {
            
            roomManager.addRoom(roomNo, roomCapacity);
            System.out.println("Successfully Room Added");
        } catch (WrongParameterException ex) {
            addMessage("Room Add", ex.getMessage());
        }
    }
    
    public void addMessage(String title, String detail){
        FacesMessage msg= new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("RoomBean", msg);
    }

    /**
     * @return the roomNo
     */
    public String getRoomNo() {
        return roomNo;
    }

    /**
     * @param roomNo the roomNo to set
     */
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    /**
     * @return the roomCapacity
     */
    public int getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * @param roomCapacity the roomCapacity to set
     */
    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
    
    public void submit(){
        System.out.println("**************************");
        System.out.println("******* Room Data ********");
        System.out.println("**************************");
        System.out.println("Room No : "+roomNo);
        System.out.println("Room Capacity : "+roomCapacity);
    }
    
}
