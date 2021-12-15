const {defineComponent,ref} = Vue
import Api from '../api.js'
const template =
`
<div>
    <div>
        <a-space>
            <a-select style="width:200px" v-model:value="dataSourceId">
                <a-select-option v-for="dataSource in dataSources" :key="dataSource._id">{{dataSource.name}}</a-select-option>
            </a-select>
            <a-button @click="queryTables">查询</a-button>
            <a-button @click="doImport">开始导入</a-button>
        </a-space>
    </div>
    <a-table :pagination="false" row-key="(record)=>record.tableName" :row-selection="rowSelection" :data-source="tables" :columns="columns" :row-key="">
    </a-table>
</div>

`
const columns = [
    {
        title:'表名',
        dataIndex: 'tableName',
        key:'tableName'
    }
]
export default defineComponent({
    name:"ImportTable",
    template,
    props:{
        dataSources:{
            type:Array
        }
    },
    setup(props,ctx){
        const dataSourceId = ref("")
        const loading = ref()
        const tables = ref([])
        const selectedKey = ref([])
        const queryTables = ()=>{
            if(!dataSourceId.value){
                antd.message.error('没有指定配置的数据源')
                return
            }
            loading.value = true
            Api.queryDbTables(dataSourceId.value).then(res=>{
                tables.value = res.data.map(value=>{
                    return {"tableName":value}
                })
                loading.value = false
            })
        }
        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                selectedKey.value = selectedRows.map(record=>record.tableName)
            }
        }
        const doImport = ()=>{
            if(!dataSourceId.value){
                antd.message.error('没有指定配置的数据源')
                return
            }
            antd.Modal.confirm({
                title:'提示',
                content:'确定导入?',
                onOk:()=>{
                    return new Promise(resolve => {
                        importTable(dataSourceId.value,selectedKey.value).then(res=>{
                            antd.notification.info({message:res.data})
                            ctx.emit('success',true)
                        })
                        setTimeout(resolve,1000)
                    })
                }
            })

        }
        return{
            dataSourceId,
            loading,
            tables,
            queryTables,
            columns,
            rowSelection,
            doImport
        }
    }
})


const importTable  = (dataSourceId,tableNames)=>{
    return axios({
        url:`${context}/genRest/import`,
        method:'post',
        params:{
            id:dataSourceId,
            tableNames:tableNames+''
        }
    })
}