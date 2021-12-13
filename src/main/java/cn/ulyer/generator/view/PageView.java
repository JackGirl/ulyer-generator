package cn.ulyer.generator.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ulyer
 * @date 2021.12.13
 * 统一视图
 */
@Controller
@RequestMapping("/page")
public class PageView {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
