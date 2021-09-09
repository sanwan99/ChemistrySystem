package cc.mrbird.febs.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@TableName("t_ask")
@Data
public class AskInfo implements Serializable {
    private static final long serialVersionUID = -5178504845341050690L;
//    @TableId(value = "ID")
//    private Long ID;

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    @TableField(value = "Chemistry_ID")
    private Long chemistryId;

    @TableField("CHEMISTRYNAME")
    private String chemistryName;

    @TableField("DESCRIPTION")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "试剂描述")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("USERNAME")
    private String username;

    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy年dd日HH时",timezone = "GMT+8")
    private Date createTime;
}
