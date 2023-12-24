<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchMode1.dNumber"
            placeholder="宿舍楼号"
            clearable
          ></el-input>
          <el-input
            v-model="searchMode1.cAunt"
            placeholder="宿管"
            clearable
          ></el-input>
          <el-button
            @click="getBuilderList"
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
      <el-table :data="builderList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            {{
              (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
            }}
          </template>
    
        </el-table-column>
        <el-table-column prop="dName" label="宿舍名" width="190">
        </el-table-column>
        <el-table-column prop="dNumber" label="宿舍楼号" width="210">
        </el-table-column>
        <el-table-column prop="dPeople" label="承载人数" width="210">
        </el-table-column>
        <el-table-column prop="status" label="宿舍楼状态" width="210">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">开启</el-tag>
            <el-tag v-else type="danger">关闭</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="cAunt" label="宿管" width="200">
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
              @click="deleteBuilder(scope.row)"
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
      <el-form :model="builderForm" ref="BuilderFormRef" :rules="rules">
        <el-form-item label="宿舍楼名：" prop="dName" :label-width="formLabelWidth">
          <el-input v-model="builderForm.dName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼号"  prop="dNumber" :label-width="formLabelWidth">
          <el-input v-model="builderForm.dNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="核载人数:"  prop="dPeople" :label-width="formLabelWidth">
          <el-input v-model="builderForm.dPeople" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼状态状态" :label-width="formLabelWidth">
          <el-switch
            v-model="builderForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="宿管:"  prop="cAunt" :label-width="formLabelWidth">
          <el-input v-model="builderForm.cAunt" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savebuilder"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import builderApi from "@/api/builder";
export default {
  data() {
    
    return {
      formLabelWidth: "130",
      builderForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchMode1: {
        pageNo: 1,
        pageSize: 10,
      },
      builderList: [],
      rules:{
        dName: [
          { required: true, message: "请输入宿舍楼名", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        dNumber: [
          { required: true, message: "请输入宿舍楼号", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "请输入正确的宿舍楼号",
            trigger: "blur",
          },
        ],
        dPeople: [
          { required: true, message: "请输入核载人数", trigger: "blur" },
          {
            min: 1,
            max: 100,
            message: "请输入正确的人数",
            trigger: "blur",
          },
        ],
          cAunt: [
          { required: true, message: "请输入宿管信息", trigger: "blur" },
          {
            min: 1,
            max: 18,
            message: "请输入正确的宿管信息",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    deleteBuilder(builder){
      this.$confirm(`您确认删除宿舍楼号为 ${builder.dNumber} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          builderApi.deleteBuilderById(builder.id).then(responce =>{
            this.$message({
            type: 'success',
            message: responce.message
          });
          this.getBuilderList();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    savebuilder(){
      //触发表单验证
      this.$refs.BuilderFormRef.validate((valid) => {
        if (valid) {
          //提交后台
          builderApi.saveBuilder(this.builderForm).then((responce) => {
            //成功提示
            this.$message({
              message: responce.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新数据
            this.getBuilderList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    clearform() {
      this.builderForm = {};
      this.$refs.BuilderFormRef.clearValidate();
    },
    openEdiuUI(id) {
      if(id==null){
        this.title = "新增宿舍楼";
      }else{
        this.title = "修改宿舍楼";
        //根据id查询数据
        builderApi.getBuilderById(id).then(responce =>{
            this.builderForm =responce.data;
        });
      }
      
      this, (this.dialogFormVisible = true);
    },
    handleSizeChange(pageSize) {
      this.searchMode1.pageSize = pageSize;
      this.getBuilderList();
    },
    handleCurrentChange(pageNo) {
      this.searchMode1.pageNo = pageNo;
      this.getBuilderList();
    },
    getBuilderList() {
      builderApi.getBuilderList(this.searchMode1).then((responce) => {
        this.builderList = responce.data.rows;
        this.total = responce.data.total;
      });
    },
  },
  created() {
    this.getBuilderList();
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