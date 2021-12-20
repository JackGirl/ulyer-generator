package cn.ulyer.generator.core.wrapper;

import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@ToString
public class TableWrapper {

    private GenTable genTable;

    private List<GenColumn> columns;

    private String idType = "String";

    private String idProp = "id";

    private TableWrapper(){}

    public static TableWrapper of(GenTable table,List<GenColumn> columns){
        TableWrapper wrapper = new TableWrapper();
        wrapper.genTable = table;
        wrapper.columns = columns;
        if(!CollectionUtils.isEmpty(columns)){
            columns.forEach(column->{
                if(column.isUniqueId()){
                    wrapper.idType = column.getJavaType().substring(column.getJavaType().lastIndexOf(".")+1);
                    wrapper.idProp = column.getPropertyName();
                    return;
                }
            });
        }
        return wrapper;
    }

}
