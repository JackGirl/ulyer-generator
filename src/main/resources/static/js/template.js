const {defineComponent,createApp,reactive,ref,onMounted,nextTick} = Vue
import Api from '../api.js'
import {renderCodeMirror} from "../lib/utils.js";
let editor = undefined;

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
        const removeModules = (module)=>{
            antd.Modal.confirm({
                titel:'提示',
                content:'是否删除?',
                onOk:()=>{
                    return new Promise(resolve => {
                        Api.removeModule(module.moduleName).then(res=>{
                            antd.notification.success({message:'deleted'})
                            queryModules()
                        })
                        resolve()
                    })
                }
            })
        }
        //添加修改模块
        const moduleVisible = ref(false)
        const moduleFormRef = ref()
        const moduleFormModel = reactive({})
        const openModuleForm = (record) =>{
            if(record){

            }else{

            }
            moduleVisible.value = true
        }
        const saveOrUpdateModule = ()=>{

        }

        //template
        const templateLoading = ref(false)
        const templates = ref([])
        const templateSearchModel = reactive({name:undefined})
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
        const removeTemplate = (template)=>{
            antd.Modal.confirm({
                titel:'提示',
                content:'删除前请注意修改选择了的当前模板的模块?',
                onOk:()=>{
                    return new Promise(resolve => {
                        Api.removeModule(template.name).then(res=>{
                            antd.notification.success({message:'deleted'})
                            templateQuery()
                        })
                        resolve()
                    })
                }
            })
        }
        //添加删除模块
        const templateFormRef = ref()
        const templateVisible = ref(false)
        const templateFormModel = reactive({})
        const openTemplateForm = (record) =>{
            if(record){

            }else{

            }
            templateVisible.value = true
            nextTick(()=> {
                if (!editor) {
                    editor = renderCodeMirror('javascript', "",'code')
                }
                editor.setOption('mode', 'javascript')
                editor.setValue('javascript')
                editor.setSize(1200,700)
                setTimeout(()=>editor.refresh(),10);
            })
        }

        const saveOrUpdateTemplate = ()=>{

        }

        return{
            moduleLoading,
            modules,
            moduleSearchModel,
            queryModules,
            removeModules,
            moduleFormRef,
            moduleFormModel,
            templateLoading,
            templates,
            templateSearchModel,
            templateQuery,
            removeTemplate,
            templateFormRef,
            templateFormModel,
            saveOrUpdateModule,
            saveOrUpdateTemplate,
            openModuleForm,
            openTemplateForm,
            moduleVisible,
            templateVisible,
            routerToTablePage:()=>{window.location.href=`${context}/page/tables`}
        }
    }
})

createApp(Template).use(antd).mount('#app')