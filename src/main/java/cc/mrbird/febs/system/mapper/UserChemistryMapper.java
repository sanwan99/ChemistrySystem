package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.UserChemistry;
import cc.mrbird.febs.system.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface UserChemistryMapper extends BaseMapper<UserChemistry> {

    void setStatusOk(long id);
    void setStatusNo(long id);

    void setOneStatusOk(long id,long userId);
    List<Long> getAllUserId();
    List<Long> getAllChemistryId();
}
