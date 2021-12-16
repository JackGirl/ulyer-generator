/***
 *
 * ================================
 * types 相关
 * ===============================
 *
 */
const queryDbTypes = ()=>{
    return axios({
        url:`${context}/types/dbTypes`
    })
}

const queryJavaTypes = () => {
    return axios({
        url:`${context}/types/javaTypes`
    })
}
/***
 *
 * ================================
 * dataSource 相关
 * ===============================
 *
 */
const saveOrUpdateDataSource = (dataSource)=>{
    return axios({
        url:`${context}/dataSourceRest/saveOrUpdate`,
        data:dataSource,
        method:'post'
    })
}

const removeDataSource = (dataSourceId)=>{
    return axios({
        url:`${context}/dataSourceRest/remove/${dataSourceId}`,
        method:'delete'
    })
}

const queryDataSources = (params)=>{
    return axios({
        url:`${context}/dataSourceRest/listDataSource`,
        params
    })
}
/***
 *
 * ================================
 * table 相关
 * ===============================
 *
 */

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

/***
 *
 * ================================
 * template  相关
 * ===============================
 *
 */
const listTemplates = (params)=>{
    return axios({
        url:`${context}/templateRest/list`,
        params
    })
}

/***
 *
 * ================================
 * module  相关
 * ===============================
 *
 */

const listModules = (params)=>{
    return axios({
        url:`${context}/moduleRest/list`,
        params
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
    queryDbTables,
    saveOrUpdateDataSource,
    removeDataSource,
    listModules,
    listTemplates
}