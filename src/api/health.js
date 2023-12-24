import request from '@/utils/request'

export default{
    getHealthList(searchModel){
        return request({
            url: '/health/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                dNumber: searchModel.dNumber,
                
                
            }
        });
    },

    addHealth(health){
        return request({
            url: '/health',
            method: 'post',
           data: health
        });
    },

    updateHealth(health){
        return request({
            url: '/health',
            method: 'put',
           data: health
        });
    },
    saveHealth(health){
        if(health.id==null && health.id==undefined){
            return this.addHealth(health);
        }
        return this.updateHealth(health);
    },

    getHealthById(id){
        return request({
            url: `/health/${id}`,
            method: 'get',
           
        });
    },

    deleteHealthById(id){
        return request({
            url: `/health/${id}`,
            method: 'delete',
           
        });
    },

}  