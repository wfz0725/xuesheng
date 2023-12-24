import request from '@/utils/request'

export default{
    getCleanerList(searchModel){
        return request({
            url: '/cleaner/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                cName: searchModel.cName,
                cId: searchModel.cId,
            }
        });
    },

    addCleaner(cleaner){
        return request({
            url: '/cleaner',
            method: 'post',
           data: cleaner
        });
    },

    updateCleaner(cleaner){
        return request({
            url: '/cleaner',
            method: 'put',
           data: cleaner
        });
    },
    saveCleaner(cleaner){
        if(cleaner.id==null && cleaner.id==undefined){
            return this.addCleaner(cleaner);
        }
        return this.updateCleaner(cleaner);
    },

    getCleanerById(id){
        return request({
            url: `/cleaner/${id}`,
            method: 'get',
           
        });
    },

    deleteCleanerById(id){
        return request({
            url: `/cleaner/${id}`,
            method: 'delete',
           
        });
    },

    
}

