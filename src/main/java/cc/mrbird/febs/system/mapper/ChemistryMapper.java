package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Chemistry;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChemistryMapper extends BaseMapper<Chemistry> {

    /**
     * 通过用户名查找用户
     *
     * @param chemistryname 用户名
     * @return 用户
     */
    Chemistry findByName(String chemistryname);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param chemistry 用户对象，用于传递查询条件
     * @return Ipage
     */
   // <T> IPage<Chemistry> findChemistryDetailPage(Page<T> page, @Param("chemistry") Chemistry chemistry);

    <T> IPage<Chemistry> findChemistryDetailPage(Page<T> page, @Param("chemistry") Chemistry chemistry,long id);

   // long countChemistryDetail(@Param("chemistry") Chemistry chemistry);

    long countChemistryDetail(long id);

    /**
     * 查找用户详细信息
     *
     * @param chemistry 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<Chemistry> findChemistryDetail(@Param("chemistry") Chemistry chemistry);

}