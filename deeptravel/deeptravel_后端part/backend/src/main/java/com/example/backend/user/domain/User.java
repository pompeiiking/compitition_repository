package com.example.backend.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 收入水平
     */
    private String incomeLevel;

    /**
     * 旅行方式偏好
     */
    private String travelModePreference;

    /**
     * 活动类型偏好
     */
    private String activityTypePreference;

    /**
     * 旅游限制
     */
    private String travelRestriction;

    /**
     * 其他
     */
    private String rests;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getIncomeLevel() == null ? other.getIncomeLevel() == null : this.getIncomeLevel().equals(other.getIncomeLevel()))
            && (this.getTravelModePreference() == null ? other.getTravelModePreference() == null : this.getTravelModePreference().equals(other.getTravelModePreference()))
            && (this.getActivityTypePreference() == null ? other.getActivityTypePreference() == null : this.getActivityTypePreference().equals(other.getActivityTypePreference()))
            && (this.getTravelRestriction() == null ? other.getTravelRestriction() == null : this.getTravelRestriction().equals(other.getTravelRestriction()))
            && (this.getRests() == null ? other.getRests() == null : this.getRests().equals(other.getRests()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getIncomeLevel() == null) ? 0 : getIncomeLevel().hashCode());
        result = prime * result + ((getTravelModePreference() == null) ? 0 : getTravelModePreference().hashCode());
        result = prime * result + ((getActivityTypePreference() == null) ? 0 : getActivityTypePreference().hashCode());
        result = prime * result + ((getTravelRestriction() == null) ? 0 : getTravelRestriction().hashCode());
        result = prime * result + ((getRests() == null) ? 0 : getRests().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(username);
        sb.append(", password=").append(password);
        sb.append(", gender=").append(gender);
        sb.append(", incomeLevel=").append(incomeLevel);
        sb.append(", travelModePreference=").append(travelModePreference);
        sb.append(", activityTypePreference=").append(activityTypePreference);
        sb.append(", travelRestriction=").append(travelRestriction);
        sb.append(", rests=").append(rests);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}