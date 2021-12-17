const {defineComponent,ref,reactive,onMounted,computed} = Vue
import Api from '../api.js'
import {groupArray} from "../lib/utils.js";

const template =
    `
<div>
    <a-spin :spinning="spinning">
        <a-form layout="vertical" :model="generatorParams" ref="genForm">
            <a-row :gutter="10">
                <a-col span="10">
                    <a-form-item :rules="[{required:true}]" name="genModules">
                        <div style="height:700px;overflow-y: auto">
                            <a-checkbox-group :rules="[{required:true}]" v-model:value="generatorParams.genModules">
                                <a-card :title="group" v-for="(modules,group) in groupModule" hoverable
                                        style="margin-top: 16px;width:450px">
                                    <a-row>
                                        <a-col :span="8">
                                            <a-checkbox v-for="module in modules" :value="module.moduleName"><span>{{module.moduleName}}</span>
                                            </a-checkbox>
                                        </a-col>
                                    </a-row>
                                </a-card>
                            </a-checkbox-group>
                        </div>
                    </a-form-item>
                </a-col>
                <a-col span="14">
                    <a-form-item label="包路径" name="basePackage">
                        <a-input placeholder="cn.ulyer" v-model:value="generatorParams.basePackage"></a-input>
                    </a-form-item>
                    <a-form-item label="作者" name="author">
                        <a-input v-model:value="generatorParams.author"></a-input>
                    </a-form-item>
                    <a-form-item label="扩展变量" name="basePackage">
                        <a-alert
                                message="提示"
                                description="基础变量genTable,genTemplate,basePackage,author并且不可覆盖,扩展变量需要满足json格式"
                                type="info"
                                show-icon
                        />
                        <a-textarea v-model:value="extendVariable" placeholder="扩展变量" :rows="8"/>
                    </a-form-item>
                    <a-form-item>
                        <a-button @click="start" type="primary">开始生成</a-button>
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
    </a-spin>
</div>
    `


export default defineComponent({
    template,
    name:'generator',
    setup(){
        const modules = ref([])
        const groupModule = computed(()=>{
            return groupArray(modules.value,"group")
        })
        onMounted (()=>{
            Api.listModules({}).then(res=>{
                modules.value = res.data
            })
        })
        const extendVariable = ref('{}')
        const generatorParams = reactive({
            basePackage:'',
            genModules:[],
            genTables:[],
            author:'ulyer-gen',
            extendsVariables:{}
        })
        const setTables = (tables)=>{
            generatorParams.genTables = tables
        }
        const genForm = ref()
        /**
         * 开始生成
         */
        const spinning = ref(false)
        const start = ()=>{
            spinning.value = true
            try{
                generatorParams.extendsVariables =   JSON.parse(extendVariable.value)
            }catch (e){
                spinning.value = false
                antd.message.error('扩展变量需要符合json格式')
                return
            }
            setTimeout(()=>{
                spinning.value = false
                console.info(generatorParams)
            },5000)
        }
        return{
            generatorParams,
            groupModule,
            genForm,
            setTables,
            start,
            extendVariable,spinning
        }
    }
})