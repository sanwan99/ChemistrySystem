package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@TableName("t_user_chemistry")
public class UserChemistry implements Serializable {

    private static final long serialVersionUID = 2354394771812648574L;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("Chemistry_ID")
    private Long chemistryId;

    @TableField("STATUS")
    @ExcelField(value = "状态", writeConverterExp = "0=禁用,1=可用")
    private String status;

}
