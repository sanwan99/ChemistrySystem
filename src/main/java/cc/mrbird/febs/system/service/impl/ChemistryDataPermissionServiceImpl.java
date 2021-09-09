//package cc.mrbird.febs.system.service.impl;
//
//import cc.mrbird.febs.common.entity.Strings;
//import cc.mrbird.febs.system.entity.ChemistryDataPermission;
//import cc.mrbird.febs.system.entity.UserDataPermission;
//import cc.mrbird.febs.system.mapper.ChemistryDataPermissionMapper;
//import cc.mrbird.febs.system.mapper.UserDataPermissionMapper;
//import cc.mrbird.febs.system.service.IChemistryDataPermissionService;
//import cc.mrbird.febs.system.service.IUserDataPermissionService;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service("chemistryDataPermissionService")
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//public class ChemistryDataPermissionServiceImpl extends ServiceImpl<ChemistryDataPermissionMapper, ChemistryDataPermission> implements IChemistryDataPermissionService {
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteByDeptIds(List<String> deptIds) {
//        baseMapper.delete(new LambdaQueryWrapper<ChemistryDataPermission>().in(ChemistryDataPermission::getDeptId, deptIds));
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteByChemistryIds(String[] chemistryIds) {
//        List<String> list = Arrays.asList(chemistryIds);
//        baseMapper.delete(new LambdaQueryWrapper<ChemistryDataPermission>().in(ChemistryDataPermission::getChemistryId, list));
//    }
//
//    @Override
//    public String findByChemistryId(String chemistryId) {
//        LambdaQueryWrapper<ChemistryDataPermission> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ChemistryDataPermission::getChemistryId, chemistryId);
//        return baseMapper.selectList(wrapper).stream().map(permission -> String.valueOf(permission.getDeptId())).collect(Collectors.joining(Strings.COMMA));
//    }
//}
