const {createApp,ref,defineComponent,reactive,onMounted} = Vue
import Api from '../api.js'
import {resetForm} from "../lib/utils.js";

const columns = [
    {
        title:'数据源ID',
        dataIndex:'_id',
        key:'_id'
    },
    {
        title:'数据源名称',
        dataIndex:'name',
        key:'name'
    },
    {
        title:'数据源类型',
        dataIndex:'type',
        key:'type'
    },
    {
        title:'链接属性配置',
        dataIndex:'connectionProperty',
        key:'connectionProperty'
    },
    {
        title:'操作',
        slots:{customRender:'action'}
    }
]
const DataSource = defineComponent({
    name:'dataSource',
    setup(){
        const dataSources = ref([])
        const searchModel = reactive({ name:'',type:'' })
        const loading = ref(false)
        const  queryDataSource = ()=> {
            loading.value = true
            Api.queryDataSources(searchModel).then(res=>{
                dataSources.value = res.data
                loading.value = false
            })
        }
        onMounted(()=>{
            queryDataSource()
        })
        //remove dataSource
        const removeDataSource = (record)=>{
            antd.Modal.confirm({
                title:'提示',
                content:'确定删除配置?',
                onOk:()=>{
                    return new Promise(resolve=>{
                        Api.removeDataSource(record._id).then(res=>{
                            if(res.data){
                                antd.message.info("deleted")
                            }else{
                                antd.message.error('fail')
                            }
                            queryDataSource()
                        })
                        resolve()
                    })
                }
            })
        }

        //create
        const dbTypes = ref([])
        onMounted(()=>{
            Api.queryDbTypes().then(res=>{
                dbTypes.value = res.data
            })
        })
        const createVisible = ref(false)
        const createFormRef = ref()
        const openCreate = ()=>{
            createVisible.value = true
            resetForm(createFormRef)
        }
        const fillPropertyJson = (type)=>{
            dbTypes.value.forEach(db=>{
                if(db.type===type){
                    createModel.connectionProperty = db.propertyJson
                }
            })
        }
        const createModel = reactive({name:undefined,type:undefined,connectionProperty:undefined})
        const submitCreate = ()=>{
            createFormRef.value.validate().then(()=>{
                Api.saveOrUpdateDataSource(createModel).then(res=>{
                    antd.notification.info({message:res.data})
                    queryDataSource()
                    createVisible.value = false
                })
            })
        }

        return {
            columns,
            searchModel,
            loading,
            dataSources,
            queryDataSource,
            createFormRef,
            createModel,
            createVisible,
            submitCreate,
            openCreate,
            dbTypes,
            removeDataSource,
            fillPropertyJson,
            routerToTablePage:()=>{window.location.href = `${context}/page/tables`}
        }
    }
})

const app = createApp(DataSource)
app.use(antd).mount('#app')