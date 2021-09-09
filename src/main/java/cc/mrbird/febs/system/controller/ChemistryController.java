package cc.mrbird.febs.system.controller;

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
import java.util.Date;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("chemistry")
public class   ChemistryController extends BaseController {

    private final IChemistryService chemistryService;
    private final IAskInfoService askInfoService;

    @GetMapping("{chemistryname}")
    public Chemistry getChemistry(@NotBlank(message = "{required}") @PathVariable String chemistryname) {

        return chemistryService.findChemistryDetailList(chemistryname);
    }

    @GetMapping("check/{chemistryname}")
    public boolean checkChemistryName(@NotBlank(message = "{required}") @PathVariable String chemistryname, String chemistryrId) {
       // System.out.println(chemistryService.findByName(chemistryname));
        return chemistryService.findByName(chemistryname) == null || StringUtils.isNotBlank(chemistryrId);
    }

//    @GetMapping("list")
//    @RequiresPermissions("chemistry:view")
//    public FebsResponse chemistryList(Chemistry chemistry, QueryRequest request) {
//        System.out.println(chemistry);
//        return new FebsResponse().success()
//                .data(getDataTable(chemistryService.findChemistryDetailList(chemistry, request)));
//    }

//测试
@GetMapping("list")
@RequiresPermissions("chemistry:view")
public FebsResponse chemistryList(Chemistry chemistry, QueryRequest request) {
    System.out.println(chemistry);
    User currentUser = FebsUtil.getCurrentUser();
    return new FebsResponse().success()
            .data(getDataTable(chemistryService.findChemistryDetailList(chemistry, request,currentUser.getUserId())));
}

    @PostMapping
    @RequiresPermissions("chemistry:add")
    @ControllerEndpoint(operation = "新增试剂", exceptionMessage = "新增试剂失败")
    public FebsResponse addChemistry(@Valid Chemistry chemistry) {
        chemistryService.createChemistry(chemistry);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{chemistryIds}")
    @RequiresPermissions("chemistry:delete")
    @ControllerEndpoint(operation = "删除试剂", exceptionMessage = "删除试剂失败")
    public FebsResponse deleteChemistrys(@NotBlank(message = "{required}") @PathVariable String chemistryIds) {
        chemistryService.deleteChemistrys(StringUtils.split(chemistryIds, Strings.COMMA));
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("chemistry:update")
    @ControllerEndpoint(operation = "修改试剂", exceptionMessage = "修改试剂失败")
    public FebsResponse updateChemistry(@Valid Chemistry chemistry) {
        if (chemistry.getChemistryId() == null) {
            throw new FebsException("试剂ID为空");
        }
        chemistryService.updateChemistry(chemistry);
        return new FebsResponse().success();
    }


    @PostMapping("ask")
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
        askInfoService.save(askInfo);
        return new FebsResponse().success();
    }
    @GetMapping("asklist")
    @RequiresPermissions("chemistry:view")
    public FebsResponse AskList(AskInfo askInfo, QueryRequest request) {
        return new FebsResponse().success()
                .data(getDataTable(askInfoService.findAskDetailList(askInfo, request)));
    }

    @GetMapping("excel")
    @RequiresPermissions("user:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Chemistry chemistry, HttpServletResponse response) {
        User currentUser = FebsUtil.getCurrentUser();
        ExcelKit.$Export(Chemistry.class, response)
                .downXlsx(chemistryService.findChemistryDetailList(chemistry, queryRequest,currentUser.getUserId()).getRecords(), false);
    }


}
