import request from '@/utils/request'

export default{
    getDoorplateList(searchModel){
        return request({
            url: '/doorplate/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                sName: searchModel.sName,
                sPhone: searchModel.sPhone,
            }
        });
    },

    addDoorplate(doorplate){
        return request({
            url: '/doorplate',
            method: 'post',
           data: doorplate
        });
    },

    updateDoorplate(doorplate){
        return request({
            url: '/doorplate',
            method: 'put',
           data: doorplate
        });
    },
    saveDoorplate(doorplate){
        if(doorplate.id==null && doorplate.id==undefined){
            return this.addDoorplate(doorplate);
        }
        return this.updateDoorplate(doorplate);
    },

    getDoorplateById(id){
        return request({
            url: `/doorplate/${id}`,
            method: 'get',
           
        });
    },

    deleteDoorplateById(id){
        return request({
            url: `/doorplate/${id}`,
            method: 'delete',
           
        });
    },

} 