//package cc.mrbird.febs.system.service;
//
//import cc.mrbird.febs.system.entity.ChemistryRole;
//import cc.mrbird.febs.system.entity.UserRole;
//import com.baomidou.mybatisplus.extension.service.IService;
//
//import java.util.List;
//import java.util.Set;
//
//
//public interface IChemistryRoleService extends IService<ChemistryRole> {
//
//    /**
//     * 通过角色ID删除
//     *
//     * @param roleIds 角色ID
//     */
//    void deleteChemistryRolesByRoleId(List<String> roleIds);
//
//    /**
//     * 通过用户ID删除
//     *
//     * @param chemistryIds 用户ID
//     */
//    void deleteChemistryRolesByChemistryId(List<String> chemistryIds);
//
//    /**
//     * 通过角色ID查找关联的用户ID
//     *
//     * @param roleId 角色ID
//     * @return 用户ID集合
//     */
//    Set<Long> findChemistryIdByRoleId(Long roleId);
//
//    /**
//     * 通过角色ID集合查找关联的用户ID
//     *
//     * @param roleIds 角色ID集合
//     * @return 用户ID集合
//     */
//    Set<Long> findChemistryIdByRoleIds(List<String> roleIds);
//}
