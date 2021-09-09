package cc.mrbird.febs.monitor.entity;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@TableName("t_lab")
@Data
public class LabInfo implements Serializable {
    private static final long serialVersionUID = -5178504845351050670L;

    @TableId(value = "Lab_ID", type = IdType.AUTO)
    private Long labId;

    @TableField("TEMPERATURE")
    private String temperature;

    @TableField("LTEMPERATURE")
    private String ltemperature;

    @TableField("HUMIDTY")
    private String humidty;

    @TableField("LHUMIDTY")
    private String lhumidty;

    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "M月dd日HH时",timezone = "GMT+8")
    private Date createTime;
}
