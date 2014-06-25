package de.haw.mps.transport.entity;

import com.google.api.client.util.Key;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "Transport")
@Table(appliesTo = "Transport")
public class TransportEntity implements Serializable {

    @Id
    @GeneratedValue
    @Key
    @GenericGenerator(name = "idGen", strategy = "increment")
    public int id;

    @Column
    private int remoteId;

    @Column
    @Key
    public String countryCode;

    @Column
    @Key
    public String city;

    @Column
    @Key
    public String postal;

    @Column
    @Key
    public String address;

    @Column
    @Key
    public boolean send;

    public int getId() {
        return id;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}