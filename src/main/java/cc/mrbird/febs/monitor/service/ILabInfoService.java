package cc.mrbird.febs.monitor.service;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.SystemLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ILabInfoService extends IService<LabInfo> {

    List<List<LabInfo>> findLastSevenDaysDate();
}
