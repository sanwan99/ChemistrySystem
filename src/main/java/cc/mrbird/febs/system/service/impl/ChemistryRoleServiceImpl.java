//package cc.mrbird.febs.system.service.impl;
//
//import cc.mrbird.febs.system.entity.ChemistryRole;
//import cc.mrbird.febs.system.entity.UserRole;
//import cc.mrbird.febs.system.mapper.ChemistryRoleMapper;
//import cc.mrbird.febs.system.mapper.UserRoleMapper;
//import cc.mrbird.febs.system.service.IChemistryRoleService;
//import cc.mrbird.febs.system.service.IChemistryService;
//import cc.mrbird.febs.system.service.IUserRoleService;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//
//@Service
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//public class ChemistryRoleServiceImpl extends ServiceImpl<ChemistryRoleMapper, ChemistryRole> implements IChemistryRoleService {
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteChemistryRolesByRoleId(List<String> roleIds) {
//        baseMapper.delete(new QueryWrapper<ChemistryRole>().lambda().in(ChemistryRole::getRoleId, roleIds));
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteChemistryRolesByChemistryId(List<String> chemistryIds) {
//        baseMapper.delete(new QueryWrapper<ChemistryRole>().lambda().in(ChemistryRole::getChemistryId, chemistryIds));
//    }
//
//    @Override
//    public Set<Long> findChemistryIdByRoleId(Long roleId) {
//        List<ChemistryRole> chemistryRoles = baseMapper.selectList(new QueryWrapper<ChemistryRole>().lambda()
//                .eq(ChemistryRole::getRoleId, roleId));
//        if (CollectionUtils.isNotEmpty(chemistryRoles)) {
//            return chemistryRoles.stream().map(ChemistryRole::getChemistryId).collect(Collectors.toSet());
//        }
//        return null;
//    }
//
//    @Override
//    public Set<Long> findChemistryIdByRoleIds(List<String> roleIds) {
//        List<ChemistryRole> userRoles = baseMapper.selectList(new QueryWrapper<ChemistryRole>().lambda()
//                .in(ChemistryRole::getRoleId, roleIds));
//        if (CollectionUtils.isNotEmpty(userRoles)) {
//            return userRoles.stream().map(ChemistryRole::getChemistryId).collect(Collectors.toSet());
//        }
//        return null;
//    }
//}
