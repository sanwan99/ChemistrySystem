package cc.mrbird.febs.monitor.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.monitor.entity.AskInfo;
import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.SystemLog;
import cc.mrbird.febs.system.entity.Chemistry;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IAskInfoService extends IService<AskInfo> {


    IPage<AskInfo> findAskDetailList(AskInfo askInfo, QueryRequest request);
    IPage<AskInfo> findmyAskDetailList(AskInfo askInfo, QueryRequest request,String username);
    void deleteAsks(String[] ids);
}
