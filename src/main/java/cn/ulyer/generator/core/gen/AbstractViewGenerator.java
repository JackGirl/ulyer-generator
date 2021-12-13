package cn.ulyer.generator.core.gen;

/**
 * @author ulyer
 * @date 2021.12.13
 * 前端生成抽象父类
 */
public abstract class AbstractViewGenerator {

    protected abstract byte[] generatorView();

    protected abstract byte[] generatorJsApi();

}
