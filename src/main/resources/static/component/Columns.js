
const {defineComponent,ref,onMounted} = Vue
import Api from '../api.js'
const template =
`
<div>
    <div class="control-bar">
        <a-button type="primary" @click="saveColumns">保存</a-button>
    </div>
    <a-table :pagination="false" :loading="loading" :data-source="dataSource" :columns="columns">
        <template #uniqueId="{record}">
            <a-select v-model:value="record.uniqueId">
                <a-select-option :value="true">是</a-select-option>
                <a-select-option :value="false">否</a-select-option>
            </a-select>
        </template>
        <template #propertyName="{record}">
            <a-input v-model:value="record.propertyName"/>
        </template>
        <template #comment="{record}">
            <a-input v-model:value="record.comment"/>
        </template>
        <template #javaType="{record}">
            <a-auto-complete
                    v-model:value="record.javaType"
                    :options="javaTypes"
                    style="width: 200px"
                    placeholder="input here"
                    :filter-option="filterOption"/>
        </template>
        <template #jdbcType="{record}">
            <a-input v-model:value="record.jdbcType"/>
        </template>
        <template #nullable="{record}">
            <a-select v-model:value="record.nullable">
                <a-select-option :value="true">是</a-select-option>
                <a-select-option :value="false">否</a-select-option>
            </a-select>
        </template>
        <template #action="{record}">
            <a-button @click="removeColumns(record)" danger>删除</a-button>
        </template>
    </a-table>
</div>
`;
const columns = [
    {
        title: '列名',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: '主键',
        dataIndex: 'dataSourceName',
        key: 'dataSourceName',
        slots:{customRender:'uniqueId'}
    },
    {
        title: '生成属性名',
        dataIndex: 'propertyName',
        key: 'propertyName',
        slots:{customRender:'propertyName'}
    },
    {
        title: '字段注释',
        dataIndex: 'comment',
        key: 'comment',
        slots:{customRender:'comment'}
    },
    {
        title: 'java类型',
        dataIndex: 'javaType',
        key: 'javaType',
        slots:{customRender:'javaType'}
    },
    {
        title: 'jdbc类型',
        dataIndex: 'jdbcType',
        key: 'jdbcType',
        slots:{customRender:'jdbcType'}
    },
    {
        title: '是否为空',
        dataIndex: 'nullable',
        key: 'nullable',
        slots:{customRender:'nullable'}
    },
    {
        title: '操作',
        slots:{customRender:'action'}
    },
]
const Column = defineComponent({
    template,
    setup(){
        const loading = ref(false)
        const dataSource = ref([])
        const queryColumns = (tableId)=>{
            loading.value = true
            Api.queryTableColumns(tableId).then(res=>{
                dataSource.value = res.data
                loading.value = false
            })
        }
        const removeColumns = (row)=>{
            dataSource.value = dataSource.value.filter(record=>record._id!=row._id)
        }
        const saveColumns = () =>{
            Api.updateTableColumns(dataSource.value).then(res=>{
                antd.message.success(res.data)
            })
        }

        //javaTypes
        const javaTypes = ref([])
        onMounted(()=>{
            Api.queryJavaTypes().then(res=>{
                const arr = []
                res.data.forEach(value=>{
                    arr.push({value})
                })
                javaTypes.value = arr
            })
        })
        const filterOption = (input, option) => {
            return option.value.toUpperCase().indexOf(input.toUpperCase()) >= 0;
        }
        return{
            queryColumns,
            dataSource,
            columns,
            loading,
            javaTypes,
            saveColumns,
            filterOption,
            removeColumns
        }
    }
})

export default Column