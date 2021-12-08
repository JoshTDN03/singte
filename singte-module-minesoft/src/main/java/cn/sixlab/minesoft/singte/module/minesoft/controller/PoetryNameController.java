package cn.sixlab.minesoft.singte.module.minesoft.controller;

import cn.sixlab.minesoft.singte.core.common.config.BaseController;
import cn.sixlab.minesoft.singte.core.common.vo.ModelResp;
import cn.sixlab.minesoft.singte.core.mapper.StePoemAtomMapper;
import cn.sixlab.minesoft.singte.core.mapper.StePoemMapper;
import cn.sixlab.minesoft.singte.core.mapper.StePoemNameMapper;
import cn.sixlab.minesoft.singte.module.minesoft.models.StePoem;
import cn.sixlab.minesoft.singte.module.minesoft.models.StePoemAtom;
import cn.sixlab.minesoft.singte.module.minesoft.models.StePoemName;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/poetry/name")
public class PoetryNameController extends BaseController {

    @Autowired
    private StePoemMapper poemMapper;

    @Autowired
    private StePoemAtomMapper poemAtomMapper;

    @Autowired
    private StePoemNameMapper poemNameMapper;

    @GetMapping(value = "/poems")
    public String poems(ModelMap modelMap) {

        List<StePoem> poemList = poemMapper.selectPoems(false);

        modelMap.put("poemList", poemList);

        return "poetry/name/poems";
    }

    @ResponseBody
    @PostMapping(value = "/addPoem")
    public ModelResp addPoem(StePoem poem) {
        poem.setCreateTime(new Date());
        poemMapper.insert(poem);

        String[] atoms = split(poem.getPoemContent(), "[\\.\\!\\?\\r\\n\\s。！？]");
        if (ArrayUtils.isNotEmpty(atoms)) {
            for (int i = 0; i < atoms.length; i++) {
                StePoemAtom atom = new StePoemAtom();
                atom.setPoemId(poem.getId());
                atom.setPoemName(poem.getPoemName());
                atom.setAtomContent(atoms[i]);
                atom.setAtomOrder(i);
                atom.setCreateTime(new Date());

                poemAtomMapper.insert(atom);
            }
        }

        return ModelResp.success();
    }

    @GetMapping(value = "/names")
    public String names(ModelMap modelMap) {

        return poemNames(modelMap, 1);
    }

    @GetMapping(value = "/names/{page}")
    public String names(ModelMap modelMap, @PathVariable Integer page) {

        return poemNames(modelMap, page);
    }

    private String poemNames(ModelMap modelMap, Integer page) {
        PageHelper.startPage(page, 100);

        List<StePoemName> poemNameList = poemNameMapper.selectPoemNames();

        PageInfo<StePoemName> pageInfo = new PageInfo<>(poemNameList);

        modelMap.put("pageInfo", pageInfo);

        return "poetry/name/names";
    }

    @GetMapping(value = "/keywords")
    public String keywords(ModelMap modelMap, String keywords) {
        String[] keywordList = StringUtils.split(keywords, " ");

        List<StePoemAtom> poemAtomList = poemAtomMapper.selectByKeywords(keywordList);

        modelMap.put("poemAtomList", poemAtomList);

        return "poetry/name/keywords";
    }

    public String[] split(String str, String regex) {
        String[] splits = str.split(regex);
        List<String> result = new ArrayList<>();
        for (String split : splits) {
            if (StringUtils.isNotEmpty(split)) {
                result.add(split);
            }
        }
        System.out.println("\n\n");
        System.out.println(result);
        System.out.println("\n\n");
        return result.toArray(new String[]{});
    }
}