package cn.ulyer.generator.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageView {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
