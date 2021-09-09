package cc.mrbird.febs.monitor.service.impl;

import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.mapper.LabMapper;
import cc.mrbird.febs.monitor.service.ILabInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LabInfoService")
public class LabInfoServiceImpl extends ServiceImpl<LabMapper, LabInfo> implements ILabInfoService {

    public List<List<LabInfo>> findLastSevenDaysDate(){

        return baseMapper.findLastSevenDaysDate();
    }
}
