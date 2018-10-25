package com.danielshimon.android_project.model.entities;

import com.danielshimon.android_project.model.entities.Drivingstatus;

import java.sql.Time;

public class Travel {
    Drivingstatus drivingStatus;
    Position current;
    Position destination;
    Time          stratDrving;
    Time          endDriving;
    String        clientName;
    String        clientNumber;
    String        clientEmail;
   //region getter and setter
    public Drivingstatus getDrivingStatus() {
        return drivingStatus;
    }

    public void setDrivingStatus(Drivingstatus drivingStatus) {
        this.drivingStatus = drivingStatus;
    }

    public Position getCurrent() {
        return current;
    }

    public void setCurrent(Position current) {
        this.current = current;
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public Time getStratDrving() {
        return stratDrving;
    }

    public void setStratDrving(Time stratDrving) {
        this.stratDrving = stratDrving;
    }

    public Time getEndDriving() {
        return endDriving;
    }

    public void setEndDriving(Time endDriving) {
        this.endDriving = endDriving;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    //endregion
}
