package cn.sixlab.minesoft.singte.core.controller;

import cn.sixlab.minesoft.singte.core.common.config.BaseController;
import cn.sixlab.minesoft.singte.core.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/date")
public class DateController extends BaseController {

    @Autowired
    private ArticleService service;

    @GetMapping(value = "/{date}")
    public String date(ModelMap modelMap, @PathVariable String date) {
        service.listParam(modelMap, 1, 10, "date", "/date/" + date);
        modelMap.put("date", date);
        return "list";
    }

    @GetMapping(value = "/{date}/{pageNum}")
    public String date(ModelMap modelMap, @PathVariable String date, @PathVariable Integer pageNum) {
        service.listParam(modelMap, pageNum, 10, "date", "/date/" + date);
        modelMap.put("date", date);
        return "list";
    }

    @GetMapping(value = "/{date}/{pageNum}/{pageSize}")
    public String date(ModelMap modelMap, @PathVariable String date, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        service.listParam(modelMap, pageNum, pageSize, "date", "/date/" + date);
        modelMap.put("date", date);
        return "list";
    }

}