package com.example.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2023-12-12
 */
@TableName("d_data")
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String sName;

    private String sId;

    private String sSex;

    private Integer dNumber;

    private String dDoor;

    private Integer status;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public Integer getdNumber() {
        return dNumber;
    }

    public void setdNumber(Integer dNumber) {
        this.dNumber = dNumber;
    }

    public String getdDoor() {
        return dDoor;
    }

    public void setdDoor(String dDoor) {
        this.dDoor = dDoor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Data{" +
            "id = " + id +
            ", sName = " + sName +
            ", sId = " + sId +
            ", sSex = " + sSex +
            ", dNumber = " + dNumber +
            ", dDoor = " + dDoor +
            ", status = " + status +
            ", description = " + description +
        "}";
    }
}
