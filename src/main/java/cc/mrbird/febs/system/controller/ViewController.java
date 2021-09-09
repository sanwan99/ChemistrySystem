package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.monitor.entity.MonitorInfo;
import cc.mrbird.febs.monitor.service.IMonitorInfoService;
import cc.mrbird.febs.system.entity.Chemistry;
import cc.mrbird.febs.system.entity.User;
//import cc.mrbird.febs.system.service.IChemistryDataPermissionService;
import cc.mrbird.febs.system.service.IChemistryService;
import cc.mrbird.febs.system.service.IUserDataPermissionService;
import cc.mrbird.febs.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller("systemView")
@RequiredArgsConstructor
public class ViewController extends BaseController {


    private final IUserService userService;
    private final IUserDataPermissionService userDataPermissionService;
    private final IMonitorInfoService iMonitorInfoService;
    private final IChemistryService chemistryService;
    //private final IChemistryDataPermissionService chemistryDataPermissionService;

    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        if (FebsUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName(FebsUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return FebsUtil.view("error/403");
    }


    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        User principal = userService.findByName(getCurrentUser().getUsername());
        userService.doGetUserAuthorizationInfo(principal);
        principal.setPassword("It's a secret");
        model.addAttribute("user", principal);
        model.addAttribute("permissions", principal.getStringPermissions());
        model.addAttribute("roles", principal.getRoles());
        return "index";
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return FebsUtil.view("layout");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return FebsUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return FebsUtil.view("system/user/userProfile");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return FebsUtil.view("system/user/avatar");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return FebsUtil.view("system/user/profileUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return FebsUtil.view("system/user/user");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return FebsUtil.view("system/user/userAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return FebsUtil.view("system/user/userDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return FebsUtil.view("system/user/userUpdate");
    }
//////////////
@GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry")
@RequiresPermissions("chemistry:view")
public String systemChemistry() {
    return FebsUtil.view("system/chemistry/chemistry");
}


    @GetMapping(FebsConstant.VIEW_PREFIX + "system/mychemistry")
    @RequiresPermissions("chemistry:view")
    public String systemmyChemistry() {
        return FebsUtil.view("system/chemistry/mychemistry");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/ask")
    //@RequiresPermissions("chemistry:view")
    public String systemAsk() {
        return FebsUtil.view("system/chemistry/ask");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/myask")
    //@RequiresPermissions("chemistry:view")
    public String systemmyAsk() {
        return FebsUtil.view("system/chemistry/myask");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/add")
    @RequiresPermissions("chemistry:add")
    public String systemChemistryAdd() {
        return FebsUtil.view("system/chemistry/chemistryAdd");
    }


    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/detail/{chemistryId}")
    @RequiresPermissions("chemistry:view")
    public String systemChemistryDetail(@PathVariable int chemistryId, Model model) {

        Chemistry chemistry = chemistryService.getById(chemistryId);
        System.out.println(chemistry);
        model.addAttribute("chemistry", chemistry);
        return FebsUtil.view("system/chemistry/chemistryDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/askfor/{chemistryId}")
    @RequiresPermissions("chemistry:ask")
    public String systemChemistryAskfor(@PathVariable int chemistryId, Model model) {

        System.out.println("----------------");
        Chemistry chemistry = chemistryService.getById(chemistryId);
        System.out.println(chemistry);
        model.addAttribute("chemistry", chemistry);
        return FebsUtil.view("system/chemistry/chemistryAskfor");
    }


    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/details/{chemistryId}")
    @RequiresPermissions("chemistry:view")
    public String systemChemistryDetailwithMonitor(@PathVariable int chemistryId, Model model) {

        Chemistry chemistry = chemistryService.getById(chemistryId);
        model.addAttribute("date1",iMonitorInfoService.findLastSevenDaysDate(chemistry.getChemistryname()));
        List<List<MonitorInfo>> lastSevenDaysDate = iMonitorInfoService.findLastSevenDaysDate(chemistry.getChemistryname());
        System.out.println(lastSevenDaysDate);
        model.addAttribute("labinfo0",lastSevenDaysDate.get(6));
        model.addAttribute("labinfo1",lastSevenDaysDate.get(5));
        model.addAttribute("labinfo2",lastSevenDaysDate.get(4));
        model.addAttribute("labinfo3",lastSevenDaysDate.get(3));
        model.addAttribute("labinfo4",lastSevenDaysDate.get(2));
        model.addAttribute("labinfo5",lastSevenDaysDate.get(1));
        model.addAttribute("labinfo6",lastSevenDaysDate.get(0));
        return FebsUtil.view("monitor/monitor");
    }
//    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/detail/{chemistryname}")
//    @RequiresPermissions("chemistry:view")
//    public String systemChemistryDetail(@PathVariable String chemistryname, Model model) {
//        resolveChemistryModel(chemistryname, model, true);
//        return FebsUtil.view("system/chemistry/chemistryDetail");
//    }

//    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/update/{chemistryname}")
//    @RequiresPermissions("chemistry:update")
//    public String systemChemistryUpdate(@PathVariable String chemistryname, Model model) {
//        System.out.println("----------------");
//        resolveChemistryModel(chemistryname, model, false);
//        return FebsUtil.view("system/chemistry/chemistryUpdate");
//    }
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/chemistry/update/{chemistryId}")
    @RequiresPermissions("chemistry:update")
    public String systemChemistryUpdate(@PathVariable int chemistryId, Model model) {
        System.out.println("----------------");
        Chemistry chemistry = chemistryService.getById(chemistryId);
        System.out.println(chemistry);
        model.addAttribute("chemistry", chemistry);
        return FebsUtil.view("system/chemistry/chemistryUpdate");
    }
    //////
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return FebsUtil.view("system/role/role");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return FebsUtil.view("system/menu/menu");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return FebsUtil.view("system/dept/dept");
    }

    @RequestMapping(FebsConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return FebsUtil.view("index");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "404")
    public String error404() {
        return FebsUtil.view("error/404");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "403")
    public String error403() {
        return FebsUtil.view("error/403");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "500")
    public String error500() {
        return FebsUtil.view("error/500");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        String deptIds = userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        user.setDeptIds(deptIds);
        model.addAttribute("user", user);
        if (transform) {
            String sex = user.getSex();
            switch (sex) {
                case User.SEX_MALE:
                    user.setSex("男");
                    break;
                case User.SEX_FEMALE:
                    user.setSex("女");
                    break;
                default:
                    user.setSex("保密");
                    break;
            }
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
    ////
    private void resolveChemistryModel(String chemistryname, Model model, Boolean transform) {
        Chemistry chemistry = chemistryService.findByName(chemistryname);
        //String deptIds = chemistryDataPermissionService.findByChemistryId(String.valueOf(chemistry.getChemistryId()));
      //  chemistry.setDeptIds(deptIds);
        model.addAttribute("chemistry", chemistry);
        if (transform) {
            String sex = chemistry.getSex();
            switch (sex) {
                case User.SEX_MALE:
                    chemistry.setSex("男");
                    break;
                case User.SEX_FEMALE:
                    chemistry.setSex("女");
                    break;
                default:
                    chemistry.setSex("保密");
                    break;
            }
        }
//        if (chemistry.getLastLoginTime() != null) {
//            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(chemistry.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
//        }
    }
}
