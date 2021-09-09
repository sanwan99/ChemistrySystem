package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("t_chemistry_role")
public class ChemistryRole implements Serializable {

    private static final long serialVersionUID = 2354394771912648574L;
    /**
     * 用户ID
     */
    @TableField("Chemistry_ID")
    private Long chemistryId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;


}
