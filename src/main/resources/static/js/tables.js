import Api from '../api.js'
import ModifyTable from "../component/ModifyTable.js";
import ColumnApp from '../component/Columns.js'
import ImportTable from "../component/ImportTable.js";

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
    name:'tables',
    components: {
        ModifyTable,
        ColumnApp,
        ImportTable
    },
    setup() {
        //search and table
        const tables = ref([])
        const searchModel = reactive({tableName: '', dataSourceId: ''});
        const formRef = ref()
        const loading = ref(false);
        const searchTable = () => {
            loading.value = true;
            Api.queryGenTables(searchModel).then(res => {
                tables.value = res.data;
                loading.value = false
            })
        }
        onMounted(() => {
            searchTable()
        })
        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
            }
        }
        const removeTable = (record) => {
            Api.removeTableById(record._id).then(res => {
                ant.message.success("remove success")
                searchTable()
            })
        }
        //datasource
        const dataSources = ref([])
        onMounted(() => {
            Api.queryDataSources().then(res => {
                dataSources.value = res.data
            })
        })

        //sub component edit table form
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
        const openColumnApp = (record) => {
            columnAppVisible.value = true
            nextTick(() => {
                columnAppRef.value.queryColumns(record._id)
            })
        }
        //subComponent pop importTable
        const importTableRef = ref()
        const importTableVisible = ref(false)
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
            removeTable,
            loading,
            openColumnApp,
            columnAppVisible,
            columnAppRef,
            importTableRef,
            importTableVisible,
            routerToDataSource:()=>{window.location.href = `${context}/page/dataSource`},
            routerToTemplate:()=>{window.location.href = `${context}/page/template`},
        }
    }


})


var app = createApp(App)

app.use(antd).mount('#app')