/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uog.deptexammanagementsystem.room;

import com.uog.exam.entity.RoomEntity;
import com.uog.exam.room.RoomManagerRemote;
import com.uog.exam.room.RoomNotFoundException;
import com.uog.exam.room.WrongParameterException;
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
public class RoomBean {

    @EJB
    RoomManagerRemote roomManager;

    private String roomNo;

    private int roomCapacity;

    private List<RoomEntity> allRoomsList;

    private boolean globalFilterOnly;

    private Part file;

    public void addRoom(RoomEntity rooms) {

        try {

            rooms = roomManager.addRoom(roomNo, roomCapacity);

            allRoomsList.add(rooms);

            roomNo = null;

            roomCapacity = 0;

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

    public void uploadRoomFile() {

        try {

            RoomEntity roomEntity;

            Scanner scan = new Scanner(file.getInputStream());

            System.out.println("Adding room data from file..");

            while(scan.hasNextLine()) {

                String nextLine = scan.nextLine();

                String[] roomData = nextLine.split(",");

                String roomNo = roomData[0];

                int roomCapacity = Integer.parseInt(roomData[1]);

                try {

                    roomEntity = roomManager.addRoom(roomNo, roomCapacity);

                    if (roomEntity != null) {

                        System.out.println("Added room");
                        System.out.println("Room is added sucessfully : " + roomNo);

                    } else {

                        System.out.println("Unsuccessful to add new room.");
                    }
                } catch (WrongParameterException ex) {
                    Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(RoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public Part getFile() {

        return file;

    }

    public void setFile(Part file) {

        this.file = file;

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
