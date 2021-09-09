package cc.mrbird.febs.monitor.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.monitor.entity.AskInfo;
import cc.mrbird.febs.monitor.entity.LabInfo;
import cc.mrbird.febs.monitor.entity.SystemLog;
import cc.mrbird.febs.monitor.mapper.AskMapper;
import cc.mrbird.febs.monitor.mapper.LabMapper;
import cc.mrbird.febs.monitor.service.IAskInfoService;
import cc.mrbird.febs.monitor.service.ILabInfoService;
import cc.mrbird.febs.system.entity.Chemistry;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("AskService")
public class AskServiceImpl extends ServiceImpl<AskMapper, AskInfo> implements IAskInfoService {

    @Override
    public IPage<AskInfo> findAskDetailList(AskInfo askInfo, QueryRequest request) {

        Page<Chemistry> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        page.setTotal(baseMapper.countAskDetail(askInfo));

        SortUtil.handlePageSort(request, page, "ID", FebsConstant.ORDER_DESC, false);

        //System.out.println(baseMapper.findChemistryDetailPage(page,chemistry).getRecords());
        //System.out.println(baseMapper.findAskDetailPage(page,askInfo).getRecords());
        return baseMapper.findAskDetailPage(page,askInfo);
    }

    @Override
    public IPage<AskInfo> findmyAskDetailList(AskInfo askInfo, QueryRequest request,String username) {

        Page<Chemistry> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        page.setTotal(baseMapper.countAskDetail(askInfo));

        SortUtil.handlePageSort(request, page, "ID", FebsConstant.ORDER_DESC, false);

        //System.out.println(baseMapper.findChemistryDetailPage(page,chemistry).getRecords());
        System.out.println(baseMapper.findmyAskDetailPage(page,username).getRecords());
        return baseMapper.findmyAskDetailPage(page,username);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAsks(String[] ids) {

        List<String> list = Arrays.asList(ids);
        removeByIds(list);
    }
}
