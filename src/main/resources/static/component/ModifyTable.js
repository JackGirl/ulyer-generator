
const {defineComponent, ref,reactive} = Vue

const template =
    `
 <div>
    <a-form 
    ref="formRef"
     :model="table"
    :rules="rules"> 
        <a-form-item label="表名">
            <a-input disabled v-model:value="table.tableName"/>
        </a-form-item>
         <a-form-item label="类名">
            <a-input v-model:value="table.className"/>
        </a-form-item>
        <a-form-item label="注释">
            <a-input v-model:value="table.comment"/>
        </a-form-item>
        <a-form-item>
            <a-button type="primary" @click="onSubmit" >提交</a-button>
        </a-form-item>
    </a-form>
</div>
    `;



export default defineComponent({
    template,
    setup(props,ctx) {
        const formRef = ref()
        const table = reactive({
            tableName: '',
            _id: '',
            comment: '',
            className: ''
        })
        const rules = {
            comment: [{required: true, trigger: 'change'}],
            className: [{required: true, trigger: 'change'}]
        }
        const loadTable = (tableId) =>{
            loadTableById(tableId).then(res=>{
                console.info(res.data)
                Object.assign(table,res.data)
            })
        }
        const onSubmit = ()=>{
            formRef.value
                .validate()
                .then(() => {
                    updateTable(table).then(res=>{
                        if(res.data){
                            antd.message.success("已修改")
                            ctx.emit('success',table._id)
                        }else{
                            antd.message.success("error")
                        }
                    })

                })
        }
        return {
            formRef,
            table,
            rules,
            loadTable,
            onSubmit
        }
    }
})


const loadTableById = (tableId)=>{
    return axios({
        url:`${context}/tableRest/details/${tableId}`
    })
}
const updateTable = (table)=>{
    return axios({
        url:`${context}/tableRest/update`,
        method:'post',
        data:table
    })
}