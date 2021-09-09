package cc.mrbird.febs.monitor.mapper;

import cc.mrbird.febs.monitor.entity.AskInfo;
import cc.mrbird.febs.monitor.entity.SystemLog;
import cc.mrbird.febs.system.entity.Chemistry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


public interface AskMapper extends BaseMapper<AskInfo> {

    <T> IPage<AskInfo> findAskDetailPage(Page<T> page, @Param("askInfo") AskInfo askInfo);

    <T> IPage<AskInfo> findmyAskDetailPage(Page<T> page, String username);

    long countAskDetail(@Param("askInfo") AskInfo askInfo);
}
