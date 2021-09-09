package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.event.UserAuthenticationUpdatedEventPublisher;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.Md5Util;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.monitor.entity.MonitorInfo;
import cc.mrbird.febs.monitor.service.IMonitorInfoService;
import cc.mrbird.febs.system.entity.*;
import cc.mrbird.febs.system.mapper.ChemistryMapper;
import cc.mrbird.febs.system.mapper.UserMapper;
import cc.mrbird.febs.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ChemistryServiceimpl extends ServiceImpl<ChemistryMapper, Chemistry> implements IChemistryService {

    private final IMenuService menuService;
    private final IRoleService roleService;
    //private final IChemistryRoleService chemistryRoleService;
    private final UserAuthenticationUpdatedEventPublisher publisher;
    private final IMonitorInfoService monitorInfoService;
    private final IUserChemistryService userChemistryService;
    private final IUserService userService;
    //private final IChemistryDataPermissionService chemistryDataPermissionService;


    @Override
    public Chemistry findByName(String chemistryname) {
        return baseMapper.findByName(chemistryname);
    }

//    @Override
//    public IPage<Chemistry> findChemistryDetailList(Chemistry chemistry, QueryRequest request) {
//        if (StringUtils.isNotBlank(chemistry.getCreateTimeFrom()) &&
//                StringUtils.equals(chemistry.getCreateTimeFrom(), chemistry.getCreateTimeTo())) {
//            chemistry.setCreateTimeFrom(chemistry.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
//            chemistry.setCreateTimeTo(chemistry.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
//        }
//        Page<Chemistry> page = new Page<>(request.getPageNum(), request.getPageSize());
//        page.setSearchCount(false);
//        page.setTotal(baseMapper.countChemistryDetail(chemistry));
//
//        SortUtil.handlePageSort(request, page, "chemistryId", FebsConstant.ORDER_ASC, false);
//
//        //System.out.println(baseMapper.findChemistryDetailPage(page,chemistry).getRecords());
//        return baseMapper.findChemistryDetailPage(page,chemistry);
//    }

    //测试id
    @Override
    public IPage<Chemistry> findChemistryDetailList(Chemistry chemistry, QueryRequest request,long id) {
        if (StringUtils.isNotBlank(chemistry.getCreateTimeFrom()) &&
                StringUtils.equals(chemistry.getCreateTimeFrom(), chemistry.getCreateTimeTo())) {
            chemistry.setCreateTimeFrom(chemistry.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            chemistry.setCreateTimeTo(chemistry.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Chemistry> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        User currentUser = FebsUtil.getCurrentUser();
        page.setTotal(baseMapper.countChemistryDetail(currentUser.getUserId()));

        SortUtil.handlePageSort(request, page, "chemistryId", FebsConstant.ORDER_ASC, false);

        //System.out.println(baseMapper.findChemistryDetailPage(page,chemistry).getRecords());
        return baseMapper.findChemistryDetailPage(page,chemistry,id);
    }

    @Override
    public Chemistry findChemistryDetailList(String chemistryname) {
        Chemistry param = new Chemistry();
        param.setChemistryname(chemistryname);
        List<Chemistry> chemistrys = baseMapper.findChemistryDetail(param);
       // System.out.println(chemistrys);
        return CollectionUtils.isNotEmpty(chemistrys) ? chemistrys.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String chemistryname) {
        Chemistry chemistry = new Chemistry();
        //chemistry.setLastLoginTime(new Date());
        baseMapper.update(chemistry, new LambdaQueryWrapper<Chemistry>().eq(Chemistry::getChemistryname, chemistryname));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createChemistry(Chemistry chemistry) {
        chemistry.setCreateTime(new Date());
        chemistry.setStatus(Chemistry.STATUS_VALID);
        chemistry.setAvatar(Chemistry.DEFAULT_AVATAR);
        //chemistry.setTheme(Chemistry.THEME_BLACK);
        //chemistry.setIsTab(Chemistry.TAB_OPEN);
        //chemistry.setPassword(Md5Util.encrypt(chemistry.getChemistryname(), Chemistry.DEFAULT_PASSWORD));
        save(chemistry);
        //保存UC
        List<Long> list=userChemistryService.getAllUserId();
        for (int i = 0; i <list.size() ; i++) {
            long userId=(long)list.get(i);
            UserChemistry userChemistry =new UserChemistry();
            userChemistry.setStatus(Chemistry.STATUS_VALID);
            userChemistry.setChemistryId(chemistry.getChemistryId());
            userChemistry.setUserId(userId);
            userChemistryService.save(userChemistry);
        }


        // 保存用户角色
//        String[] roles = chemistry.getRoleId().split(Strings.COMMA);
//        setChemistryRoles(chemistry, roles);
        // 保存用户数据权限关联关系
//        String[] deptIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(chemistry.getDeptIds(), Strings.COMMA);
//        if (ArrayUtils.isNotEmpty(deptIds)) {
//            setChemistryDataPermissions(chemistry, deptIds);
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChemistrys(String[] chemistryIds) {
        List<String> list = Arrays.asList(chemistryIds);
        // 删除试剂
        removeByIds(list);
        User currentUser = FebsUtil.getCurrentUser();
        userChemistryService.deleteUserChemistrysByChemistryId(list);
        //userChemistryService.removeById(currentUser.getUserId());
//        // 删除关联角色
//        chemistryRoleService.deleteChemistryRolesByChemistryId(list);
//        // 删除关联数据权限
//        chemistryDataPermissionService.deleteByChemistryIds(chemistryIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChemistry(Chemistry chemistry) {
        // 更新用户
        //chemistry.setPassword(null);
        //chemistry.setChemistryname(null);
        MonitorInfo monitorInfo=new MonitorInfo();
        int capacity = Integer.parseInt(chemistry.getCapacity());
        monitorInfo.setChemistryId(chemistry.getChemistryId());
        monitorInfo.setCapacity(capacity);
        monitorInfo.setChemistryName(chemistry.getChemistryname());
        monitorInfo.setHumidty(chemistry.getHumidty());
        monitorInfo.setTemperature(chemistry.getTemperature());
        monitorInfo.setCreateTime(new Date());
        monitorInfoService.save(monitorInfo);
        if(chemistry.getStatus().equals("1")) {
            User currentUser = FebsUtil.getCurrentUser();
            userChemistryService.setOneStatusOk(chemistry.getChemistryId(),currentUser.getUserId());
        }
        if(chemistry.getStatus().equals("0")) {
            userChemistryService.setStatusNo(chemistry.getChemistryId());
        }
        chemistry.setModifyTime(new Date());
        updateById(chemistry);

//        String[] chemistryId = {String.valueOf(chemistry.getChemistryId())};
//        chemistryRoleService.deleteChemistryRolesByChemistryId(Arrays.asList(chemistryId));
//        String[] roles = StringUtils.splitByWholeSeparatorPreserveAllTokens(chemistry.getRoleId(), Strings.COMMA);
//        setChemistryRoles(chemistry, roles);
//
//        chemistryDataPermissionService.deleteByChemistryIds(chemistryId);
//        String[] deptIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(chemistry.getDeptIds(), Strings.COMMA);
//        if (ArrayUtils.isNotEmpty(deptIds)) {
//            setChemistryDataPermissions(chemistry, deptIds);
//        }
               publisher.publishEvent(Sets.newHashSet(chemistry.getChemistryId()));
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void resetPassword(String[] usernames) {
//        Arrays.stream(usernames).forEach(username -> {
//            Chemistry user = new Chemistry();
//            user.setPassword(Md5Util.encrypt(username, User.DEFAULT_PASSWORD));
//            baseMapper.update(user, new LambdaQueryWrapper<Chemistry>().eq(Chemistry::getChemistryname, username));
//        });
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void register(String chemistryname, String password) {
//        Chemistry chemistry = new Chemistry();
//        chemistry.setPassword(Md5Util.encrypt(chemistryname, password));
//        chemistry.setChemistryname(chemistryname);
//        chemistry.setCreateTime(new Date());
//        chemistry.setStatus(User.STATUS_VALID);
//        chemistry.setSex(User.SEX_UNKNOW);
//        chemistry.setAvatar(User.DEFAULT_AVATAR);
//        chemistry.setTheme(User.THEME_BLACK);
//        chemistry.setIsTab(User.TAB_OPEN);
//        chemistry.setDescription("注册试剂");
//        save(chemistry);
//
//        ChemistryRole ur = new ChemistryRole();
//        ur.setChemistryId(chemistry.getChemistryId());
//        ur.setRoleId(FebsConstant.REGISTER_ROLE_ID);
//        chemistryRoleService.save(ur);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updatePassword(String username, String password) {
//        Chemistry user = new Chemistry();
//        user.setPassword(Md5Util.encrypt(username, password));
//        user.setModifyTime(new Date());
//        baseMapper.update(user, new LambdaQueryWrapper<Chemistry>().eq(Chemistry::getChemistryname, username));
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateAvatar(String username, String avatar) {
//        Chemistry user = new Chemistry();
//        user.setAvatar(avatar);
//        user.setModifyTime(new Date());
//        baseMapper.update(user, new LambdaQueryWrapper<Chemistry>().eq(Chemistry::getChemistryname, username));
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateTheme(String username, String theme, String isTab) {
//        Chemistry user = new Chemistry();
//        user.setTheme(theme);
//        user.setIsTab(isTab);
//        user.setModifyTime(new Date());
//        baseMapper.update(user, new LambdaQueryWrapper<Chemistry>().eq(Chemistry::getChemistryname, username));
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateProfile(Chemistry user) {
//        user.setChemistryname(null);
//        user.setRoleId(null);
//        user.setPassword(null);
//        if (isCurrentUser(user.getChemistryId())) {
//            updateById(user);
//        } else {
//            throw new FebsException("您无权修改别人的账号信息！");
//        }
//    }

//    @Override
//    public void doGetChemistryAuthorizationInfo(Chemistry user) {
//        // 获取用户角色集
//        List<Role> roleList = roleService.findUserRole(user.getChemistryname());
//        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
//        user.setRoles(roleSet);
//
//        // 获取用户权限集
//        List<Menu> permissionList = menuService.findUserPermissions(user.getChemistryname());
//        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
//        user.setStringPermissions(permissionSet);
//    }

//    private void setChemistryRoles(Chemistry chemistry, String[] roles) {
//        List<ChemistryRole> chemistryRoles = new ArrayList<>();
//        Arrays.stream(roles).forEach(roleId -> {
//            ChemistryRole chemistryRole = new ChemistryRole();
//            chemistryRole.setChemistryId(chemistry.getChemistryId());
//            chemistryRole.setRoleId(Long.valueOf(roleId));
//            chemistryRoles.add(chemistryRole);
//        });
//        chemistryRoleService.saveBatch(chemistryRoles);
//    }

//    private void setChemistryDataPermissions(Chemistry chemistry, String[] deptIds) {
//        List<ChemistryDataPermission> chemistryDataPermissions = new ArrayList<>();
//        Arrays.stream(deptIds).forEach(deptId -> {
//            ChemistryDataPermission permission = new ChemistryDataPermission();
//            permission.setDeptId(Long.valueOf(deptId));
//            permission.setChemistryId(chemistry.getChemistryId());
//            chemistryDataPermissions.add(permission);
//        });
//        chemistryDataPermissionService.saveBatch(chemistryDataPermissions);
//    }

    private boolean isCurrentUser(Long id) {
        User currentUser = FebsUtil.getCurrentUser();
        return currentUser.getUserId().equals(id);
    }
}
