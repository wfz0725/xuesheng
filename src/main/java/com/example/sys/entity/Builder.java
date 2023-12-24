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
@TableName("d_builder")
public class Builder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String dName;

    private Integer dNumber;

    private String dPeople;

    private Integer status;

    private String cAunt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getdNumber() {
        return dNumber;
    }

    public void setdNumber(Integer dNumber) {
        this.dNumber = dNumber;
    }

    public String getdPeople() {
        return dPeople;
    }

    public void setdPeople(String dPeople) {
        this.dPeople = dPeople;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getcAunt() {
        return cAunt;
    }

    public void setcAunt(String cAunt) {
        this.cAunt = cAunt;
    }

    @Override
    public String toString() {
        return "Builder{" +
            "id = " + id +
            ", dName = " + dName +
            ", dNumber = " + dNumber +
            ", dPeople = " + dPeople +
            ", status = " + status +
            ", cAunt = " + cAunt +
        "}";
    }
}
