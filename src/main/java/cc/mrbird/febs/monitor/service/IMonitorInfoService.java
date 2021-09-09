package cc.mrbird.febs.monitor.service;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.MonitorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMonitorInfoService extends IService<MonitorInfo> {

    List<List<MonitorInfo>> findLastSevenDaysDate(String chemistryname);
}
