export const renderCodeMirror = (mode,value,htmlId)=>{
    return CodeMirror.fromTextArea(document.getElementById(htmlId), {
        mode: mode,
        value,
        smartIndent: true,
        autoRefresh:true,
        indentUnit: 4,
        theme: 'darcula',
        styleActiveLine: true, //设置光标所在行高亮，需要引入addon/selection/active-line.js
        extraKeys: {'Ctrl-Q': 'autocomplete'}, //设置快捷键
        readOnly:false,
    });
}

export const resetForm = (ref)=>{
    if(ref&&ref.value){
        ref.value.resetFields()
    }
}