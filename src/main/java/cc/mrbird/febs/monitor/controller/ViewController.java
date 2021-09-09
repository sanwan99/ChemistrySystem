package cc.mrbird.febs.monitor.controller;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.monitor.entity.*;
import cc.mrbird.febs.monitor.helper.FebsActuatorHelper;
import cc.mrbird.febs.monitor.service.ILabInfoService;
import cc.mrbird.febs.monitor.service.IMonitorInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static cc.mrbird.febs.monitor.endpoint.FebsMetricsEndpoint.FebsMetricResponse;


@Controller("monitorView")
@RequestMapping(FebsConstant.VIEW_PREFIX + "monitor")
@RequiredArgsConstructor
public class ViewController {

    private final FebsActuatorHelper actuatorHelper;
    private final ILabInfoService iLabInfoService;
    private final IMonitorInfoService iMonitorInfoService;

    @GetMapping("test")
    @RequiresPermissions("test:view")
    public String testfor01(Model model) {
        model.addAttribute("date",iLabInfoService.findLastSevenDaysDate());
        List<List<LabInfo>> lastSevenDaysDate = iLabInfoService.findLastSevenDaysDate();
        model.addAttribute("labinfo0",lastSevenDaysDate.get(6));
        model.addAttribute("labinfo1",lastSevenDaysDate.get(5));
        model.addAttribute("labinfo2",lastSevenDaysDate.get(4));
        model.addAttribute("labinfo3",lastSevenDaysDate.get(3));
        model.addAttribute("labinfo4",lastSevenDaysDate.get(2));
        model.addAttribute("labinfo5",lastSevenDaysDate.get(1));
        model.addAttribute("labinfo6",lastSevenDaysDate.get(0));
        return FebsUtil.view("monitor/temperature");
    }

    @GetMapping("/monitorInfo")
    public String Monitorfor01(Model model){
        model.addAttribute("date1",iMonitorInfoService.findLastSevenDaysDate("HCL"));
        List<List<MonitorInfo>> lastSevenDaysDate = iMonitorInfoService.findLastSevenDaysDate("HCL");
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
    @GetMapping("/monitorInfo/{chemistryname}")
    public String Monitorfor02(@NotBlank(message = "{required}") @PathVariable String chemistryname, Model model){

        model.addAttribute("date1",iMonitorInfoService.findLastSevenDaysDate(chemistryname));
        List<List<MonitorInfo>> lastSevenDaysDate = iMonitorInfoService.findLastSevenDaysDate(chemistryname);
        System.out.println(lastSevenDaysDate);
        model.addAttribute("labinfo0", lastSevenDaysDate.get(6));
        model.addAttribute("labinfo1", lastSevenDaysDate.get(5));
        model.addAttribute("labinfo2", lastSevenDaysDate.get(4));
        model.addAttribute("labinfo3", lastSevenDaysDate.get(3));
        model.addAttribute("labinfo4", lastSevenDaysDate.get(2));
        model.addAttribute("labinfo5", lastSevenDaysDate.get(1));
        model.addAttribute("labinfo6", lastSevenDaysDate.get(0));
        return FebsUtil.view("monitor/monitor");
    }

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public String online() {
        return FebsUtil.view("monitor/online");
    }

    @GetMapping("log")
    @RequiresPermissions("log:view")
    public String log() {
        return FebsUtil.view("monitor/log");
    }

    @GetMapping("loginlog")
    @RequiresPermissions("loginlog:view")
    public String loginLog() {
        return FebsUtil.view("monitor/loginLog");
    }

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public String httptrace() {
        return FebsUtil.view("monitor/httpTrace");
    }

    @GetMapping("jvm")
    @RequiresPermissions("jvm:view")
    public String jvmInfo(Model model) {
        List<FebsMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return FebsUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<FebsMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return FebsUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<FebsMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<FebsMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<FebsMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return FebsUtil.view("monitor/serverInfo");
    }

    @GetMapping("swagger")
    public String swagger() {
        return FebsUtil.view("monitor/swagger");
    }
}
