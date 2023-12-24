<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchMode1.dNumber"
            placeholder="宿舍楼"
            clearable
          ></el-input>

          <el-input
            v-model="searchMode1.status"
            placeholder="是否打扫"
            clearable
          ></el-input>
  
          <el-button
            @click="getHealthList"
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
      <el-table :data="healthList" stripe style="width: 100%">
        <el-table-column label="#" width="150">
          <template slot-scope="scope">
            {{
              (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
            }}
          </template>
    
        </el-table-column>
        <el-table-column prop="dNumber" label="宿舍楼号" width="220">
        </el-table-column>
        <el-table-column prop="exit" label="卫生状态" width="200">
        </el-table-column>
        <el-table-column prop="status" label="是否打扫" width="230">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">已打扫</el-tag>
            <el-tag v-else type="danger">未打扫</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" width="300">
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button
              @click="openEdiuUI(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteHealth(scope.row)"
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
      <el-form :model="healthForm" ref="HealthFormRef" :rules="rules">
        <el-form-item label="宿舍楼号:" prop="dNumber" :label-width="formLabelWidth">
          <el-input v-model="healthForm.dNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="卫生状态:"  prop="exit" :label-width="formLabelWidth">
          <el-input v-model="healthForm.exit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否打扫" :label-width="formLabelWidth">
          <el-switch
            v-model="healthForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="信息描述:"  prop="description" :label-width="formLabelWidth">
          <el-input v-model="healthForm.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveHealth"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import HealthApi from "@/api/health";
export default {
  data() {
    
    return {
      formLabelWidth: "130",
      healthForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchMode1: {
        pageNo: 1,
        pageSize: 10,
      },
      healthList: [],
      rules:{
        
        exit: [
          { required: true, message: "卫生状态", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "请输入正确的卫生状态",
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
    deleteHealth(health){
      this.$confirm(`您确认删除用户 ${health.dNumber} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          HealthApi.deleteHealthById(health.id).then(responce =>{
            this.$message({
            type: 'success',
            message: responce.message
          });
          this.getHealthList();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    saveHealth(){
      //触发表单验证
      this.$refs.HealthFormRef.validate((valid) => {
        if (valid) {
          //提交后台
          HealthApi.saveHealth(this.healthForm).then((responce) => {
            //成功提示
            this.$message({
              message: responce.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新数据
            this.getHealthList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    clearform() {
      this.healthForm = {};
      this.$refs.HealthFormRef.clearValidate();
    },
    openEdiuUI(id) {
      if(id==null){
        this.title = "新增问题";
      }else{
        this.title = "修改问题";
        //根据id查询数据
        HealthApi.getHealthById(id).then(responce =>{
            this.healthForm =responce.data;
        });
      }
      
      this, (this.dialogFormVisible = true);
    },
    handleSizeChange(pageSize) {
      this.searchMode1.pageSize = pageSize;
      this.getHealthList();
    },
    handleCurrentChange(pageNo) {
      this.searchMode1.pageNo = pageNo;
      this.getHealthList();
    },
    getHealthList() {
      HealthApi.getHealthList(this.searchMode1).then((responce) => {
        this.healthList = responce.data.rows;
        this.total = responce.data.total;
      });
    },
  },
  created() {
    this.getHealthList();
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