const {defineComponent, createApp, reactive, ref, onMounted, nextTick,computed} = Vue
import Api from '../api.js'
import {renderCodeMirror, resetForm} from "../lib/utils.js";

let editor = undefined;

const Template = defineComponent({
    name: 'template',
    setup() {
        const fileTypes = ref([])
        const listFileTypes = ()=>{
                Api.queryFileTypes().then(res=>{
                    res.data.forEach(value=>{value.value = value.type})
                    fileTypes.value = res.data;
                })
        }
        onMounted(()=>{ listFileTypes()})
        //module
        const moduleLoading = ref(false)
        const modules = ref([])
        const moduleSearchModel = reactive({})
        //CURD模块
        const queryModules = () => {
            moduleLoading.value = true
            Api.listModules(moduleSearchModel).then(res => {
                modules.value = res.data
            })
            moduleLoading.value = false
        }
        onMounted(() => {
            queryModules()
        })
        const moduleVisible = ref(false)
        const moduleFormRef = ref()
        const m_model = {
            moduleName: "",
            group: '',
            templates: []
        }
        const moduleFormModel = reactive(Object.assign({}, m_model))
        const selectTemplates = ref([])
        const openModuleForm = (record) => {
            Api.listTemplates().then(res => {
                selectTemplates.value = res.data
            })
            moduleVisible.value = true
            resetForm(moduleFormRef)
            if (record) {
                Object.assign(moduleFormModel, record)
            }
        }
        const removeModules = (module) => {
            antd.Modal.confirm({
                titel: '提示',
                content: '是否删除?',
                onOk: () => {
                    return new Promise(resolve => {
                        Api.removeModule(module.moduleName).then(res => {
                            antd.notification.success({message: 'deleted'})
                            queryModules()
                        })
                        resolve()
                    })
                }
            })
        }
        const saveOrUpdateModule = () => {
            moduleFormRef.value.validate().then(() => {
                    Api.saveOrUpdateModule(moduleFormModel).then(res=>{
                        antd.message.success('SUCCESS')
                        queryModules()
                        moduleVisible.value = false
                    })
                })
        }

        //template
        const templateLoading = ref(false)
        const templates = ref([])
        const t_model = {
            name:null,
            type:'',
            nameExpression:'',
            template:'',
            disabled:false,
        }
        //crud模板
        const templateSearchModel = reactive(Object.assign({},t_model))
        const templateQuery = () => {
            templateLoading.value = true
            Api.listTemplates(templateSearchModel).then(res => {
                templates.value = res.data
                templateLoading.value = false
            })
        }
        onMounted(() => {
            templateQuery()
        })
        const removeTemplate = (template) => {
            antd.Modal.confirm({
                titel: '提示',
                content: '删除前请注意修改选择了的当前模板的模块?',
                onOk: () => {
                    return new Promise(resolve => {
                        Api.removeTemplate(template.name).then(res => {
                            antd.notification.success({message: 'deleted'})
                            templateQuery()
                        })
                        resolve()
                    })
                }
            })
        }

        const templateFormRef = ref()
        const templateVisible = ref(false)
        const templateFormModel = reactive({})
        const openTemplateForm = (template)  => {
            templateVisible.value = true
            resetForm(templateFormRef)
            if (template) {
                templateFormModel.disabled = true
                Object.assign(templateFormModel,template)
            }else{
                templateFormModel.disabled = false
            }
            const defaultMode = "textile"
            function selectMode(type){
                let filter = fileTypes.value.filter(fileType=>fileType.type===type)
                return filter.length==0?defaultMode:filter[0].mode;
            }
            const mode = template?selectMode(template.type):defaultMode
            nextTick(() => {
                if (!editor) {
                    editor = renderCodeMirror(mode, "", 'code')
                }else{
                    editor.setOption('mode',mode )
                }
                editor.setValue(template?template.template:'')
                editor.setSize('100%', 700)
                //行号
               // editor.setOption("lineNumbers",true);
                setTimeout(() => editor.refresh(), 10);
            })
        }


        const saveOrUpdateTemplate = () => {
            templateFormRef.value.validate().then(()=>{
                templateFormModel.template = editor.getValue()
                Api.saveOrUpdateTemplate(templateFormModel).then(()=>{
                    antd.notification.success({message:"success"})
                    templateQuery()
                    templateVisible.value = false
                })
            })
        }

        return {
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
            selectTemplates,
            fileTypes,
            routerToTablePage: () => {
                window.location.href = `${context}/page/tables`
            }
        }
    }
})

createApp(Template).use(antd).mount('#app')