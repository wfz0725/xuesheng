<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchMode1.sName"
            placeholder="姓名"
            clearable
          ></el-input>
          <el-input
            v-model="searchMode1.sId"
            placeholder="学号"
            clearable
          ></el-input>
  
          <el-button
            @click="getDataList"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
        </el-col>

        <el-col :span="4" align="right">
          <el-button
            @click="openEdiuUI(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 结果列表 -->
    <el-card>
      <el-table :data="dataList" stripe style="width: 100%">
        <el-table-column label="#" width="50">
          <template slot-scope="scope">
            {{
              (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
            }}
          </template>
    
        </el-table-column>
        <el-table-column prop="sName" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="sId" label="学号" width="160">
        </el-table-column>
        <el-table-column prop="sSex" label="性别" width="100">
        </el-table-column>
        <el-table-column prop="dNumber" label="宿舍楼号" width="170">
        </el-table-column>
        <el-table-column prop="dDoor" label="宿舍号" width="170">
        </el-table-column>
        <el-table-column prop="status" label="就寝情况" width="170">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">正常</el-tag>
            <el-tag v-else type="danger">请假</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" width="180">
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              @click="openEdiuUI(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteData(scope.row)"
              type="danger"
              icon="el-icon-delete"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchMode1.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchMode1.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- 用户信息编辑对话框 -->
    <el-dialog
      @close="clearform"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="DataForm" ref="DataFormRef" :rules="rules">
        <el-form-item label="姓名:" prop="sName" :label-width="formLabelWidth">
          <el-input v-model="DataForm.sName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号:"  prop="sId" :label-width="formLabelWidth">
          <el-input v-model="DataForm.sId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别:"  prop="sSex" :label-width="formLabelWidth">
          <el-input v-model="DataForm.sSex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼号:"  prop="dNumber" :label-width="formLabelWidth">
          <el-input v-model="DataForm.dNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍号:"  prop="dDoor" :label-width="formLabelWidth">
          <el-input v-model="DataForm.dDoor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="就寝情况" :label-width="formLabelWidth">
          <el-switch
            v-model="DataForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
        
        <el-form-item label="描述:"  prop="description" :label-width="formLabelWidth">
          <el-input v-model="DataForm.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveData"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import DataApi from "@/api/data";
export default {
  data() {
    
    return {
      formLabelWidth: "130",
      DataForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchMode1: {
        pageNo: 1,
        pageSize: 10,
      },
      dataList: [],
      rules:{
        sName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的姓名",
            trigger: "blur",
          },
        ],
        sID: [
          { required: true, message: "请输入学号", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的学号",
            trigger: "blur",
          },
        ],
        sSex: [
          { required: true, message: "性别", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "请输入正确的性别",
            trigger: "blur",
          },
        ],
    
        dDoor: [
          { required: true, message: "请输入宿舍号", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的宿舍号",
            trigger: "blur",
          },
        ],
      
          descoiption: [
          { required: true, message: "请输入描述信息", trigger: "blur" },
          {
            min: 1,
            max: 100,
            message: "请输入正确的描述信息",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    deleteData(data){
      this.$confirm(`您确认删除用户 ${data.sName} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          DataApi.deleteDataById(data.id).then(responce =>{
            this.$message({
            type: 'success',
            message: responce.message
          });
          this.getDataList();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    saveData(){
      //触发表单验证
      this.$refs.DataFormRef.validate((valid) => {
        if (valid) {
          //提交后台
          DataApi.saveData(this.DataForm).then((responce) => {
            //成功提示
            this.$message({
              message: responce.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新数据
            this.getDataList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    clearform() {
      this.DataForm = {};
      this.$refs.DataFormRef.clearValidate();
    },
    openEdiuUI(id) {
      if(id==null){
        this.title = "新增用户";
      }else{
        this.title = "修改用户";
        //根据id查询数据
        DataApi.getDataById(id).then(responce =>{
            this.DataForm =responce.data;
        });
      }
      
      this, (this.dialogFormVisible = true);
    },
    handleSizeChange(pageSize) {
      this.searchMode1.pageSize = pageSize;
      this.getDataList();
    },
    handleCurrentChange(pageNo) {
      this.searchMode1.pageNo = pageNo;
      this.getDataList();
    },
    getDataList() {
      DataApi.getDataList(this.searchMode1).then((responce) => {
        this.dataList = responce.data.rows;
        this.total = responce.data.total;
      });
    },
  },
  created() {
    this.getDataList();
  },
};
</script>
<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input {
  width: 85%;
}
</style>