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
            v-model="searchMode1.sPhone"
            placeholder="联系电话"
            clearable
          ></el-input>
  
          <el-button
            @click="getInformationList"
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
      <el-table :data="informationList" stripe style="width: 100%">
        <el-table-column label="#" width="50">
          <template slot-scope="scope">
            {{
              (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
            }}
          </template>
    
        </el-table-column>
        <el-table-column prop="sName" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="sId" label="学号" width="170">
        </el-table-column>
        <el-table-column prop="sSex" label="性别" width="100">
        </el-table-column>
        <el-table-column prop="sAge" label="年龄" width="100">
        </el-table-column>
        <el-table-column prop="sPhone" label="联系电话" width="180">
        </el-table-column>
        <el-table-column prop="sAddress" label="家庭住址" width="200">
        </el-table-column>
        <el-table-column prop="description" label="描述" width="170">
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
              @click="deleteInformation(scope.row)"
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
      <el-form :model="InformationForm" ref="InformationFormRef" :rules="rules">
        <el-form-item label="姓名:" prop="sName" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号:"  prop="sId" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别:"  prop="sSex" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sSex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄:"  prop="sAge" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sAge" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话:"  prop="sPhone" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址:"  prop="sAddress" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.sAddress" autocomplete="off"></el-input>
        </el-form-item>
        
        <el-form-item label="信息描述:"  prop="description" :label-width="formLabelWidth">
          <el-input v-model="InformationForm.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveInformation"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import InformationApi from "@/api/information";
export default {
  data() {
    
    return {
      formLabelWidth: "130",
      InformationForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchMode1: {
        pageNo: 1,
        pageSize: 10,
      },
      informationList: [],
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
        sAge: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的年龄",
            trigger: "blur",
          },
        ],
        sPhone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的电话",
            trigger: "blur",
          },
        ],
        sAddress: [
          { required: true, message: "请输入地址", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "请输入正确的地址",
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
    deleteInformation(information){
      this.$confirm(`您确认删除用户 ${information.sName} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          InformationApi.deleteInformationById(information.id).then(responce =>{
            this.$message({
            type: 'success',
            message: responce.message
          });
          this.getInformationList();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    saveInformation(){
      //触发表单验证
      this.$refs.InformationFormRef.validate((valid) => {
        if (valid) {
          //提交后台
          InformationApi.saveInformation(this.InformationForm).then((responce) => {
            //成功提示
            this.$message({
              message: responce.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新数据
            this.getInformationList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    clearform() {
      this.InformationForm = {};
      this.$refs.InformationFormRef.clearValidate();
    },
    openEdiuUI(id) {
      if(id==null){
        this.title = "新增用户";
      }else{
        this.title = "修改用户";
        //根据id查询数据
        InformationApi.getInformationById(id).then(responce =>{
            this.InformationForm =responce.data;
        });
      }
      
      this, (this.dialogFormVisible = true);
    },
    handleSizeChange(pageSize) {
      this.searchMode1.pageSize = pageSize;
      this.getInformationList();
    },
    handleCurrentChange(pageNo) {
      this.searchMode1.pageNo = pageNo;
      this.getInformationList();
    },
    getInformationList() {
      InformationApi.getInformationList(this.searchMode1).then((responce) => {
        this.informationList = responce.data.rows;
        this.total = responce.data.total;
      });
    },
  },
  created() {
    this.getInformationList();
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