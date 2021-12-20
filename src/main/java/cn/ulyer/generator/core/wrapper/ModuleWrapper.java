package cn.ulyer.generator.core.wrapper;

import cn.ulyer.generator.model.GenModule;
import cn.ulyer.generator.model.GenTemplate;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class ModuleWrapper {

    private GenModule module;

    private List<GenTemplate> templates;

    private ModuleWrapper(){}

    public static ModuleWrapper of(GenModule module,List<GenTemplate> templates){
        ModuleWrapper wrapper = new ModuleWrapper();
        wrapper.module = module;
        wrapper.templates = templates;
        return wrapper;
    }

    @Override
    public String toString() {
        return module==null?"null":module.toString();
    }
}
