package cn.ulyer.generator.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ulyer
 * @date 2021.12.13
 * 统一视图
 */
@Controller
@RequestMapping("/page")
public class PageRouter {

    /**
     * table 界面 生成界面
     * @return
     */
    @GetMapping("/tables")
    public String tables(){
        return "tables";
    }

    /**
     * 列
     * @param tableId
     * @param model
     * @return
     */
    @GetMapping("/column/{tableId}")
    public String columnPage(@PathVariable String tableId, Model model){
        model.addAttribute("tableId",tableId);
        return "column";
    }

    @GetMapping("/dataSource")
    public String dataSource(){
        return "dataSource";
    }
}
