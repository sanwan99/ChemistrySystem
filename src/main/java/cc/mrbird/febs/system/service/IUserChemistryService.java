package cc.mrbird.febs.system.service;

import cc.mrbird.febs.system.entity.UserChemistry;
import cc.mrbird.febs.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;


public interface IUserChemistryService extends IService<UserChemistry> {

    /**
     * 通过角色ID删除
     *
     */
    void deleteUserChemistrysByChemistryId(List<String> chemistryIds);


    /**
     * 通过用户ID删除
     *
     * @param userIds 用户ID
     */
    void deleteUserChemistrysByUserId(List<String> userIds);

    /**
     * 通过角色ID查找关联的用户ID
     *
     * @return 用户ID集合
     */
    Set<Long> findUserIdByChemistryId(Long chemistryId);

    /**
     * 通过角色ID集合查找关联的用户ID
     *
     * @return 用户ID集合
     */
    Set<Long> findUserIdByChemistryIds(List<String> chemistryIds);

    void setStatusOk(long id);

    void setStatusNo(long id);

    void setOneStatusOk(long id,long userId);
    List<Long> getAllUserId();
    List<Long> getAllChemistryId();

}
