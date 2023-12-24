import request from '@/utils/request'

export default{
    getBuilderList(searchModel){
        return request({
            url: '/builder/list',
            method: 'get',
            params:{
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                dNumber: searchModel.dNumber,
                cAunt: searchModel.cAunt,
            }
        });
    },

    addBuilder(builder){
        return request({
            url: '/builder',
            method: 'post',
           data: builder
        });
    },

    updateBuilder(builder){
        return request({
            url: '/builder',
            method: 'put',
           data: builder
        });
    },
    saveBuilder(builder){
        if(builder.id==null && builder.id==undefined){
            return this.addBuilder(builder);
        }
        return this.updateBuilder(builder);
    },

    getBuilderById(id){
        return request({
            url: `/builder/${id}`,
            method: 'get',
           
        });
    },

    deleteBuilderById(id){
        return request({
            url: `/builder/${id}`,
            method: 'delete',
           
        });
    },

}  