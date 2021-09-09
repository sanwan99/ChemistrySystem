package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.system.entity.UserChemistry;
import cc.mrbird.febs.system.entity.UserRole;
import cc.mrbird.febs.system.mapper.UserChemistryMapper;
import cc.mrbird.febs.system.mapper.UserRoleMapper;
import cc.mrbird.febs.system.service.IUserChemistryService;
import cc.mrbird.febs.system.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserChemistryServiceImpl extends ServiceImpl<UserChemistryMapper, UserChemistry> implements IUserChemistryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserChemistrysByChemistryId(List<String> chemistryIds) {
        baseMapper.delete(new QueryWrapper<UserChemistry>().lambda().in(UserChemistry::getChemistryId, chemistryIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserChemistrysByUserId(List<String> userIds) {
        baseMapper.delete(new QueryWrapper<UserChemistry>().lambda().in(UserChemistry::getChemistryId, userIds));
    }

    @Override
    public Set<Long> findUserIdByChemistryId(Long chemistryId) {
        List<UserChemistry> userChemistries = baseMapper.selectList(new QueryWrapper<UserChemistry>().lambda()
                .eq(UserChemistry::getChemistryId, chemistryId));
        if (CollectionUtils.isNotEmpty(userChemistries)) {
            return userChemistries.stream().map(UserChemistry::getUserId).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public Set<Long> findUserIdByChemistryIds(List<String> chemistryIds) {
        List<UserChemistry> userChemistries = baseMapper.selectList(new QueryWrapper<UserChemistry>().lambda()
                .in(UserChemistry::getChemistryId, chemistryIds));
        if (CollectionUtils.isNotEmpty(userChemistries)) {
            return userChemistries.stream().map(UserChemistry::getUserId).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public void setStatusOk(long id) {
        baseMapper.setStatusOk(id);
    }

    @Override
    public void setStatusNo(long id) {
        baseMapper.setStatusNo(id);
    }

    @Override
    public void setOneStatusOk(long id, long userId) {
        baseMapper.setOneStatusOk(id,userId);
    }

    @Override
    public List<Long> getAllUserId() {
        return baseMapper.getAllUserId();
    }

    @Override
    public List<Long> getAllChemistryId() {
        return baseMapper.getAllChemistryId();
    }


}
