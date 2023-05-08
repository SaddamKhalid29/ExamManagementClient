/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.room;

import com.uog.exam.entity.RoomEntity;
import com.uog.exam.room.RoomManagerRemote;
import com.uog.exam.room.RoomNotFoundException;
import com.uog.exam.room.WrongParameterException;
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

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class RoomBean {

    @EJB
    RoomManagerRemote roomManager;

    private String roomNo;
    private int roomCapacity;
    private List<RoomEntity> allRoomsList;
    private boolean globalFilterOnly;

    public void addRoom(RoomEntity rooms) {
        try {
            rooms = roomManager.addRoom(roomNo, roomCapacity);
            allRoomsList.add(rooms);
            System.out.println("Room is added successfully.");
        } catch (WrongParameterException ex) {
            Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateRoom(int roomID, String roomNo, int roomCapacity) {
        try {
            roomManager.updateRoom(roomID, roomNo, roomCapacity);
            System.out.println("Room is updated successfully." + roomID);
        } catch (RoomNotFoundException ex) {
            Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String deleteRoom(int roomId, RoomEntity roomEntity) {

        try {
            roomEntity = roomManager.getRoomId(roomId);
            roomManager.deleteRoomById(roomId);
            allRoomsList.remove(roomEntity);

        } catch (RoomNotFoundException ex) {
            Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostConstruct
    public void init() {
        setGlobalFilterOnly(false);
        try {
            allRoomsList = roomManager.allRoomsList();
        } catch (RoomNotFoundException ex) {
            Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addMessage(String title, String detail) {
        FacesMessage msg = new FacesMessage(title, detail);
        FacesContext.getCurrentInstance().addMessage("RoomBean", msg);
    }

    public void submit() {
        System.out.println("************************");
        System.out.println("**** Room Data ******");
        System.out.println("************************");
        System.out.println("Room Number : " + roomNo);
        System.out.println("Room Capacity : " + roomCapacity);

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

    /**
     * @return the allRoomsList
     */
    public List<RoomEntity> getAllRoomsList() {
        return allRoomsList;
    }

    /**
     * @param allRoomsList the allRoomsList to set
     */
    public void setAllRoomsList(List<RoomEntity> allRoomsList) {
        this.allRoomsList = allRoomsList;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    /**
     * @param globalFilterOnly the globalFilterOnly to set
     */
    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

    public void onRowEdit(RowEditEvent<RoomEntity> event) {
        FacesMessage msg = new FacesMessage("Faculty Edited", String.valueOf(event.getObject().getRoomID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<RoomEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getRoomID()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

}
