package cc.mrbird.febs.monitor.service.impl;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.MonitorInfo;
import cc.mrbird.febs.monitor.mapper.LabMapper;
import cc.mrbird.febs.monitor.mapper.MonitorMapper;
import cc.mrbird.febs.monitor.service.ILabInfoService;
import cc.mrbird.febs.monitor.service.IMonitorInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MonitorInfoService")
public class MonitorInfoServiceImpl extends ServiceImpl<MonitorMapper, MonitorInfo> implements IMonitorInfoService {

    public List<List<MonitorInfo>> findLastSevenDaysDate(String chemistryname){

        return baseMapper.findLastSevenDaysDate(chemistryname);
    }
}
