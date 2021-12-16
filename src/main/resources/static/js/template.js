const {defineComponent,createApp,reactive,ref,onMounted} = Vue
import Api from '../api.js'
const Template = defineComponent({
    name:'template',
    setup(){
        //module
        const moduleLoading = ref(false)
        const modules = ref([])
        const moduleSearchModel = reactive({})
        const queryModules = ()=>{
            moduleLoading.value = true
            Api.listModules(moduleSearchModel).then(res=>{
                modules.value = res.data
            })
            moduleLoading.value = false
        }
        onMounted(()=>{
            queryModules()
        })
        const filterModule = ()=>{

        }
        //template
        const templateLoading = ref(false)
        const templates = ref([])
        const templateSearchModel = reactive({})
        const templateQuery = ()=>{
            templateLoading.value = true
            Api.listTemplates(templateSearchModel).then(res=>{
                templates.value = res.data
                templateLoading.value = false
            })
        }
        onMounted(()=>{
            templateQuery()
        })
        const filterTemplate = ()=>{

        }
        return{
            moduleLoading,
            modules,
            moduleSearchModel,
            queryModules,
            filterModule,
            templateLoading,
            templates,
            templateSearchModel,
            templateQuery,
            filterTemplate,
            routerToTablePage:()=>{window.location.href=`${context}/page/tables`}
        }
    }
})

createApp(Template).use(antd).mount('#app')