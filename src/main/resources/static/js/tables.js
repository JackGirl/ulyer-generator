import Api from '../api.js'
import ModifyTable from "../component/ModifyTable.js";
import ColumnApp from '../component/Columns.js'
const {ref, createApp, reactive, defineComponent, onMounted, nextTick} = Vue;
const columns = [
    {
        title: '数据源',
        dataIndex: 'dataSourceName',
        key: 'dataSourceName',
    },
    {
        title: '表名',
        dataIndex: 'tableName',
        key: 'tableName',
    },
    {
        title: '生成类名',
        dataIndex: 'className',
        key: 'className',
    },
    {
        title: '注释',
        dataIndex: 'comment',
        key: 'comment',
    },
    {
        title: '操作',
        slots: {customRender: 'action'},
    }
]

const App = defineComponent({
    setup() {
        //search and table
        let tables = ref([])
        const searchModel = reactive({tableName: '', dataSourceId: ''});
        const formRef = ref()
        const loading = ref(false);
        onMounted(() => {
            loading.value = true
            Api.queryTables({}).then(res => {
                tables.value = res.data
                loading.value = false
            })
        })
        const searchTable = () => Api.queryTables(searchModel).then(res => {
            tables.value = res.data;
        })

        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
            }
        };
        //datasource
        let dataSources = ref([])
        onMounted(() => {
            Api.queryDataSources().then(res => {
                dataSources.value = res.data
            })
        })

        //sub component form
        const modifyTableRef = ref(null)
        const modifyTableVisible = ref(false)
        const openFormVisible = (row) => {
            modifyTableVisible.value = true
            nextTick(() => {
                console.info(modifyTableRef)
                modifyTableRef.value.loadTable(row._id)
            })
        }
        const onSuccessUpdate = (tableId) => {
            searchTable()
            modifyTableVisible.value = false
        }
        //subComponent column
        const columnAppVisible = ref(false);
        const columnAppRef = ref()
        const openColumnApp = (record)=>{
            columnAppVisible.value = true
            nextTick(()=>{
                columnAppRef.value.queryColumns(record._id)
            })
        }
        //generator
        return {
            modifyTableVisible,
            modifyTableRef,
            openFormVisible,
            onSuccessUpdate,
            formRef,
            columns,
            tables,
            dataSources,
            searchTable,
            searchModel,
            rowSelection,
            loading,
            openColumnApp,
            columnAppVisible,
            columnAppRef
        }
    },
    components: {
        ModifyTable,
        ColumnApp
    }


})


var app = createApp(App)

app.use(antd).mount('#app')