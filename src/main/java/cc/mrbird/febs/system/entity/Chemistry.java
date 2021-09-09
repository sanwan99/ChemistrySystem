package cc.mrbird.febs.system.entity;

import cc.mrbird.febs.common.annotation.IsMobile;
import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Data
@TableName("t_chemistry")
@Excel("试剂信息表")
public class Chemistry implements Serializable, Cloneable {

    /**
     * 用户状态：有效
     */
    public static final String STATUS_VALID = "0";
    /**
     * 用户状态：锁定
     */
    public static final String STATUS_LOCK = "0";
    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "shiji.jpg";
    /**
     * 默认密码
     */
   // public static final String DEFAULT_PASSWORD = "1234qwer";
    /**
     * 性别男
     */
    public static final String SEX_MALE = "0";
    /**
     * 性别女
     */
    public static final String SEX_FEMALE = "1";
    /**
     * 性别保密
     */
    public static final String SEX_UNKNOW = "2";
    /**
     * 黑色主题
     */
   // public static final String THEME_BLACK = "black";
    /**
     * 白色主题
     */
   // public static final String THEME_WHITE = "white";
    /**
     * TAB开启
     */
    public static final String TAB_OPEN = "1";
    /**
     * TAB关闭
     */
    public static final String TAB_CLOSE = "0";
    private static final long serialVersionUID = -4352868070794165001L;

    @TableId(value = "Chemistry_ID", type = IdType.AUTO)
    private Long chemistryId;

    @TableField("CHEMISTRYNAME")
    @Size(min = 1, max = 10, message = "{range}")
    @ExcelField(value = "试剂名")
    private String chemistryname;

    @TableField("HUMIDTY")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "湿度")
    private String humidty;

    @TableField("TEMPERATURE")
    @ExcelField(value = "温度")
    private String temperature;

    @TableField("CAPACITY")
    @ExcelField(value = "容量")
    private String capacity;
    /**
     * 状态 0锁定 1有效
     */
    @TableField("STATUS")
    @NotBlank(message = "{required}")
    @ExcelField(value = "状态", writeConverterExp = "0=禁用,1=可用")
    private String status;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 最近访问时间
     */
//    @TableField("LAST_LOGIN_TIME")
//    // @ExcelField(value = "最近访问时间", writeConverter = TimeConverter.class)
//    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
//    private Date lastLoginTime;

    /**
     * 性别 0男 1女 2 保密
     */
    @TableField("SSEX")
    //@NotBlank(message = "{required}")
    @ExcelField(value = "固液", writeConverterExp = "0=固体,1=液体,2=未知")
    private String sex;

    /**
     * 头像
     */
    @TableField("AVATAR")
    private String avatar;

    /**
     * 主题
     */
//    @TableField("THEME")
//    private String theme;

    /**
     * 是否开启 tab 0开启，1关闭
     */
//    @TableField("IS_TAB")
//    private String isTab;

    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "试剂描述")
    private String description;

    /**
     * 部门名称
     */
//    //@ExcelField(value = "部门")
//    @TableField(exist = false)
//    private String deptName;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
    /**
     * 角色 ID
     */
//    @NotBlank(message = "{required}")
//    @TableField(exist = false)
//    private String roleId;
//
//    // @ExcelField(value = "角色")
//    @TableField(exist = false)
//    private String roleName;
//
//    @TableField(exist = false)
//    private String deptIds;

//    @TableField(exist = false)
//    private Set<String> stringPermissions;
//    @TableField(exist = false)
//    private Set<String> roles;

    public String getId() {
        return StringUtils.lowerCase(chemistryname);
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }
}