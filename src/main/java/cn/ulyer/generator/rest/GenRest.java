package cn.ulyer.generator.rest;

import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(GenRest.NAME)
@RequestMapping("/genRest")
public class GenRest {

    public final static String NAME = "generatorRest";

    @GetMapping("/listTableModels")
    public List<GenTables> genTables(GenTables queryModel){
        return null;
    }

    @GetMapping("/listColumnModels")
    public List<GenColumns> genColumns(GenColumns queryModel){
        return null;
    }


}
