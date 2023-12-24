import request from '@/utils/request'

export default{
    getDataList(searchModel){
        return request({
            url: '/data/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                sName: searchModel.sName,
                sId: searchModel.sId,
                
            }
        });
    },

    addData(data){
        return request({
            url: '/data',
            method: 'post',
           data: data
        });
    },

    updateData(data){
        return request({
            url: '/data',
            method: 'put',
           data: data
        });
    },
    saveData(data){
        if(data.id==null && data.id==undefined){
            return this.addData(data);
        }
        return this.updateData(data);
    },

    getDataById(id){
        return request({
            url: `/data/${id}`,
            method: 'get',
           
        });
    },

    deleteDataById(id){
        return request({
            url: `/data/${id}`,
            method: 'delete',
           
        });
    },

} 