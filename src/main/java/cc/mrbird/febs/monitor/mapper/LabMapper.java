package cc.mrbird.febs.monitor.mapper;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.LoginLog;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface LabMapper extends BaseMapper<LabInfo> {

    List<List<LabInfo>> findLastSevenDaysDate();
}
