package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_chemistry_data_permission")
public class ChemistryDataPermission {
    @TableField("Chemistry_ID")
    private Long chemistryId;
    @TableField("DEPT_ID")
    private Long deptId;
}
