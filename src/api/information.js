import request from '@/utils/request'

export default{
    getInformationList(searchModel){
        return request({
            url: '/information/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                sName: searchModel.sName,
                sPhone: searchModel.sPhone,
                
            }
        });
    },

    addInformation(information){
        return request({
            url: '/information',
            method: 'post',
           data: information
        });
    },

    updateInformation(information){
        return request({
            url: '/information',
            method: 'put',
           data: information
        });
    },
    saveInformation(information){
        if(information.id==null && information.id==undefined){
            return this.addInformation(information);
        }
        return this.updateInformation(information);
    },

    getInformationById(id){
        return request({
            url: `/information/${id}`,
            method: 'get',
           
        });
    },

    deleteInformationById(id){
        return request({
            url: `/information/${id}`,
            method: 'delete',
           
        });
    },

} 