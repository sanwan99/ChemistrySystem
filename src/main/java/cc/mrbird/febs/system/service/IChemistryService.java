package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Chemistry;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;


public interface IChemistryService extends IService<Chemistry> {

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
     * @param request request
     * @param chemistry    用户对象，用于传递查询条件
     * @return IPage
     */
   // IPage<Chemistry> findChemistryDetailList(Chemistry chemistry, QueryRequest request);

    IPage<Chemistry> findChemistryDetailList(Chemistry chemistry, QueryRequest request,long id);
    /**
     * 通过用户名查找用户详细信息
     *
     * @param chemistryname 用户名
     * @return 用户信息
     */
    Chemistry findChemistryDetailList(String chemistryname);

    /**
     * 更新用户登录时间
     *
     * @param chemistryname 用户名
     */
    @Async(FebsConstant.FEBS_SHIRO_THREAD_POOL)
    void updateLoginTime(String chemistryname);

    /**
     * 新增用户
     *
     * @param chemistry chemistry
     */
    void createChemistry(Chemistry chemistry);

    /**
     * 删除用户
     *
     * @param chemistryIds 用户 id数组
     */
    void deleteChemistrys(String[] chemistryIds);

    /**
     * 修改用户
     *
     * @param chemistry chemistry
     */
    void updateChemistry(Chemistry chemistry);

//    /**
//     * 重置密码
//     *
//     * @param usernames 用户名数组
//     */
//    void resetPassword(String[] usernames);
//
//    /**
//     * 注册用户
//     *
//     * @param username 用户名
//     * @param password 密码
//     */
//    void register(String username, String password);
//
//    /**
//     * 修改密码
//     *
//     * @param username 用户名
//     * @param password 新密码
//     */
//    void updatePassword(String username, String password);
//
//    /**
//     * 更新用户头像
//     *
//     * @param username 用户名
//     * @param avatar   用户头像
//     */
//    void updateAvatar(String username, String avatar);
//
//    /**
//     * 修改用户系统配置（个性化配置）
//     *
//     * @param username 用户名称
//     * @param theme    主题风格
//     * @param isTab    是否开启 TAB
//     */
//    void updateTheme(String username, String theme, String isTab);
//
//    /**
//     * 更新个人信息
//     *
//     * @param user 个人信息
//     */
//    void updateProfile(Chemistry user);
//
//    /**
//     * 获取用户角色和权限集
//     *
//     * @param chemistry 用户
//     */
//    void doGetChemistryAuthorizationInfo(Chemistry chemistry);
}
