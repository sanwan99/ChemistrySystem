package cc.mrbird.febs.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_monitor")
@Data
public class MonitorInfo implements Serializable {
    private static final long serialVersionUID = -5178504845351050690L;
//    @TableId(value = "ID")
//    private Long ID;

    @TableId(value = "Chemistry_ID")
    private Long chemistryId;

    @TableField("TEMPERATURE")
    private String temperature;

    @TableField("HUMIDTY")
    private String humidty;

    @TableField("CAPACITY")
    private int capacity;

    @TableField("CHEMISTRYNAME")
    private String chemistryName;

    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "dd日HH时",timezone = "GMT+8")
    private Date createTime;
}
