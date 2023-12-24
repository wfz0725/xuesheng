<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchMode1.cName"
            placeholder="用户名"
            clearable
          ></el-input>
          <el-input
            v-model="searchMode1.cId"
            placeholder="电话"
            clearable
          ></el-input>
          <el-button
            @click="getCleanerList"
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
      <el-table :data="cleanerList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            {{
              (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
            }}
          </template>
    
        </el-table-column>
        <el-table-column prop="cName" label="姓名" width="170">
        </el-table-column>
        <el-table-column prop="cAge" label="性别" width="170">
        </el-table-column>
        <el-table-column prop="cId" label="联系方式" width="250">
        </el-table-column>
        <el-table-column prop="cPhone" label="身份证" width="270">
        </el-table-column>
        <el-table-column prop="status" label="用户状态" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">上班</el-tag>
            <el-tag v-else type="danger">下班</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button
              @click="openEdiuUI(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteCleaner(scope.row)"
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
      <el-form :model="cleanerForm" ref="cleanerFormRef" :rules="rules">
        <el-form-item label="姓 名:" prop="cName" :label-width="formLabelWidth">
          <el-input v-model="cleanerForm.cName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年 龄:"  prop="cAge" :label-width="formLabelWidth">
          <el-input v-model="cleanerForm.cAge" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话:"  prop="cId" :label-width="formLabelWidth">
          <el-input v-model="cleanerForm.cId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证:"  prop="cPhone" :label-width="formLabelWidth">
          <el-input v-model="cleanerForm.cPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" :label-width="formLabelWidth">
          <el-switch
            v-model="cleanerForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savecleaner"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import cleanerApi from "@/api/cleaner";
export default {
  data() {
    
    return {
      formLabelWidth: "130",
      cleanerForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchMode1: {
        pageNo: 1,
        pageSize: 10,
      },
      cleanerList: [],
      rules:{
        cName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        cAge: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "长度在 1 到 10 个字符",
            trigger: "blur",
          },
        ],
        cId: [
          { required: true, message: "请输入联系电话", trigger: "blur" },
          {
            min: 6,
            max: 11,
            message: "长度在 6 到 11 个字符",
            trigger: "blur",
          },
        ],
          cPhone: [
          { required: true, message: "请输入身份证", trigger: "blur" },
          {
            min: 6,
            max: 18,
            message: "长度在 6 到 18 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    deleteCleaner(cleaner){
      this.$confirm(`您确认删除用户 ${cleaner.cName} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          cleanerApi.deleteCleanerById(cleaner.id).then(responce =>{
            this.$message({
            type: 'success',
            message: responce.message
          });
          this.getCleanerList();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    savecleaner(){
      //触发表单验证
      this.$refs.cleanerFormRef.validate((valid) => {
        if (valid) {
          //提交后台
          cleanerApi.saveCleaner(this.cleanerForm).then((responce) => {
            //成功提示
            this.$message({
              message: responce.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新数据
            this.getCleanerList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    clearform() {
      this.cleanerForm = {};
      this.$refs.cleanerFormRef.clearValidate();
    },
    openEdiuUI(id) {
      if(id==null){
        this.title = "新增用户";
      }else{
        this.title = "修改用户";
        //根据id查询数据
        cleanerApi.getCleanerById(id).then(responce =>{
            this.cleanerForm =responce.data;
        });
      }
      
      this, (this.dialogFormVisible = true);
    },
    handleSizeChange(pageSize) {
      this.searchMode1.pageSize = pageSize;
      this.getCleanerList();
    },
    handleCurrentChange(pageNo) {
      this.searchMode1.pageNo = pageNo;
      this.getCleanerList();
    },
    getCleanerList() {
      cleanerApi.getCleanerList(this.searchMode1).then((responce) => {
        this.cleanerList = responce.data.rows;
        this.total = responce.data.total;
      });
    },
  },
  created() {
    this.getCleanerList();
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