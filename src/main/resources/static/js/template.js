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
        }
        onMounted(()=>{
            queryModules()
        })
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
        return{
            moduleLoading,
            modules,
            moduleSearchModel,
            queryModules,
            templateLoading,
            templates,
            templateSearchModel,
            templateQuery
        }
    }
})

createApp(Template).use(antd).mount('#app')