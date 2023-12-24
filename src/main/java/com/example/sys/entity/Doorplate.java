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
@TableName("d_doorplate")
public class Doorplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer dNumber;

    private Integer dFloor;

    private String dDoor;

    private String dStudent;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getdNumber() {
        return dNumber;
    }

    public void setdNumber(Integer dNumber) {
        this.dNumber = dNumber;
    }

    public Integer getdFloor() {
        return dFloor;
    }

    public void setdFloor(Integer dFloor) {
        this.dFloor = dFloor;
    }

    public String getdDoor() {
        return dDoor;
    }

    public void setdDoor(String dDoor) {
        this.dDoor = dDoor;
    }

    public String getdStudent() {
        return dStudent;
    }

    public void setdStudent(String dStudent) {
        this.dStudent = dStudent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Doorplate{" +
            "id = " + id +
            ", dNumber = " + dNumber +
            ", dFloor = " + dFloor +
            ", dDoor = " + dDoor +
            ", dStudent = " + dStudent +
            ", status = " + status +
        "}";
    }
}
