
const queryDbTypes = ()=>{
    return axios({
        url:`${context}/types/dbTyes`
    })
}

const queryJavaTypes = () => {
    return axios({
        url:`${context}/types/javaTypes`
    })
}

const queryDataSources = ()=>{
    return axios({
        url:`${context}/dataSourceRest/listDataSource`
    })
}
/**
 * 查询真实数据库表
 * @param dataSourceId
 */
const queryDbTables = (dataSourceId) => {
    return axios({
        url:`${context}/dataSourceRest/datasourceTables/${dataSourceId}`
    })
}
/**
 * 查询配置的生成表
 * @param params
 * @returns {*}
 */
const queryGenTables = (params)=>{
    return axios({
        url:`${context}/tableRest/listTableModels`,
        method:'get',
        params
    })
}

const queryTableColumns = (tableId)=> {
    return axios({
        url:`${context}/tableRest/listColumnModels?tableId=${tableId}`
    })
}

const updateTableColumns = (columns)=> {
    return axios({
        url:`${context}/tableRest/updateColumns`,
        method:'post',
        data:{
            columns
        }
    })
}

const removeTableById = (tableId)=> {
    return axios({
        url:`${context}/tableRest/remove/${tableId}`,
        method:'delete'
    })
}

export default {
    queryGenTables,
    queryJavaTypes,
    queryDbTypes,
    queryDataSources,
    queryTableColumns,
    updateTableColumns,
    removeTableById,
    queryDbTables
}