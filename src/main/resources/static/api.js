
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

const queryTables = (params)=>{
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

export default {
    queryTables,
    queryJavaTypes,
    queryDbTypes,
    queryDataSources,
    queryTableColumns,
    updateTableColumns
}