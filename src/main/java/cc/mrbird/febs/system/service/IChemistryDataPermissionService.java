//package cc.mrbird.febs.system.service;
//
//
//import cc.mrbird.febs.system.entity.Chemistry;
//import cc.mrbird.febs.system.entity.ChemistryDataPermission;
//import cc.mrbird.febs.system.entity.UserDataPermission;
//import com.baomidou.mybatisplus.extension.service.IService;
//
//import java.util.List;
//
//
//public interface IChemistryDataPermissionService extends IService<ChemistryDataPermission> {
//
//    /**
//     * 通过部门ID删除关联关系
//     *
//     * @param deptIds 部门id
//     */
//    void deleteByDeptIds(List<String> deptIds);
//
//    /**
//     * 通过用户ID删除关联关系
//     *
//     * @param ChemistryIds 用户id
//     */
//    void deleteByChemistryIds(String[] ChemistryIds);
//
//    /**
//     * 通过用户ID查找关联关系
//     *
//     * @param ChemistryIds 用户id
//     * @return 关联关系
//     */
//    String findByChemistryId(String ChemistryIds);
//
//}
