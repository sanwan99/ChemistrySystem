package cc.mrbird.febs.monitor.mapper;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.MonitorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MonitorMapper extends BaseMapper<MonitorInfo> {

    List<List<MonitorInfo>> findLastSevenDaysDate(String chemistryname);
}
