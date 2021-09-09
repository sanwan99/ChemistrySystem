package cc.mrbird.febs.monitor.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.Md5Util;
import cc.mrbird.febs.monitor.entity.AskInfo;
import cc.mrbird.febs.monitor.service.IAskInfoService;
import cc.mrbird.febs.system.entity.Chemistry;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IChemistryService;
import cc.mrbird.febs.system.service.IUserChemistryService;
import cc.mrbird.febs.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("Ask")
public class   AskController extends BaseController {

    private final IUserService userService;
    private final IChemistryService chemistryService;
    private final IAskInfoService askInfoService;
    private final IUserChemistryService userChemistryService;

    @PostMapping("ask")
    @RequiresPermissions("chemistry:ask")
    @ControllerEndpoint(operation = "发起申请", exceptionMessage = "发起申请失败")
    public FebsResponse updateChemistryAsk(@Valid Chemistry chemistry) {
        if (chemistry.getChemistryId() == null) {
            throw new FebsException("试剂ID为空");
        }
        AskInfo askInfo = new AskInfo();
        askInfo.setChemistryId(chemistry.getChemistryId());
        askInfo.setChemistryName(chemistry.getChemistryname());
        askInfo.setDescription(chemistry.getDescription());
        askInfo.setStatus(chemistry.getStatus());
        askInfo.setUsername(chemistry.getHumidty());
        askInfo.setCreateTime(new Date());
        System.out.println(askInfo);
        askInfoService.save(askInfo);
        return new FebsResponse().success();
    }
    @GetMapping("asklist")
    @RequiresPermissions("chemistry:view")
    public FebsResponse AskList(AskInfo askInfo, QueryRequest request) {

        return new FebsResponse().success()
                .data(getDataTable(askInfoService.findAskDetailList(askInfo, request)));
    }

    @GetMapping("myasklist")
    @RequiresPermissions("chemistry:view")
    public FebsResponse myAskList(AskInfo askInfo, QueryRequest request) {
        User currentUser = FebsUtil.getCurrentUser();
        System.out.println(currentUser.getUsername());
        String username=currentUser.getUsername();
        return new FebsResponse().success()
                .data(getDataTable(askInfoService.findmyAskDetailList(askInfo,request,username)));

    }

    @GetMapping("deleteask/{ids}")
    @ControllerEndpoint(operation = "删除申请", exceptionMessage = "删除申请失败")
    public FebsResponse deleteAsks(@NotBlank(message = "{required}") @PathVariable String ids) {
        askInfoService.deleteAsks(StringUtils.split(ids, Strings.COMMA));
        return new FebsResponse().success();
    }

    @GetMapping("ok/{ids}")
    @ControllerEndpoint(operation = "通过申请", exceptionMessage = "通过申请失败")
    public FebsResponse okAsks(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] okIds =StringUtils.split(ids, Strings.COMMA);
        List<String> list = Arrays.asList(okIds);
        for (int i = 0; i <list.size() ; i++) {
            String id =list.get(i);
            AskInfo askInfo =askInfoService.getById(id);
            askInfo.setStatus("2");
            askInfoService.saveOrUpdate(askInfo);
            User user =userService.findByName(askInfo.getUsername());
            userChemistryService.setOneStatusOk(askInfo.getChemistryId(),user.getUserId());

            Chemistry chemistry =chemistryService.getById(askInfo.getChemistryId());
            chemistry.setStatus("1");
            chemistryService.saveOrUpdate(chemistry);
        }
        return new FebsResponse().success();
    }
    @GetMapping("refuse/{ids}")
    @ControllerEndpoint(operation = "拒绝申请", exceptionMessage = "拒绝申请失败")
    public FebsResponse refuseAsks(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] okIds =StringUtils.split(ids, Strings.COMMA);
        List<String> list = Arrays.asList(okIds);
        for (int i = 0; i <list.size() ; i++) {
            String id =list.get(i);
            AskInfo askInfo =askInfoService.getById(id);
            askInfo.setStatus("0");
            askInfoService.saveOrUpdate(askInfo);
        }
        return new FebsResponse().success();
    }


}
