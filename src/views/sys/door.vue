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
              v-model="searchMode1.dDoor"
              placeholder="宿舍门牌号"
              clearable
            ></el-input>
    
            <el-button
              @click="getDoorplateList"
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
        <el-table :data="DoorplateList" stripe style="width: 100%">
          <el-table-column label="#" width="150">
            <template slot-scope="scope">
              {{
                (searchMode1.pageNo - 1) * searchMode1.pageSize + scope.$index + 1
              }}
            </template>
      
          </el-table-column>
          <el-table-column prop="dNumber" label="宿舍楼号" width="180">
          </el-table-column>
          <el-table-column prop="dFloor" label="楼层" width="180">
          </el-table-column>
          <el-table-column prop="dDoor" label="门牌号" width="180">
          </el-table-column>
          <el-table-column prop="dStudent" label="宿舍人数" width="200">
          </el-table-column>
          <el-table-column prop="status" label="状态" width="200">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == 1">正常</el-tag>
              <el-tag v-else type="danger">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="170">
            <template slot-scope="scope">
              <el-button
                @click="openEdiuUI(scope.row.id)"
                type="primary"
                icon="el-icon-edit"
                circle
              ></el-button>
              <el-button
                @click="deleteDoorplate(scope.row)"
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
        <el-form :model="DoorplateForm" ref="DoorplateFormRef" :rules="rules">
          <el-form-item label="宿舍楼号:" prop="dNumber" :label-width="formLabelWidth">
            <el-input v-model="DoorplateForm.dNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="楼层:"  prop="dFloor" :label-width="formLabelWidth">
            <el-input v-model="DoorplateForm.dFloor" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="门牌号:"  prop="dDoor" :label-width="formLabelWidth">
            <el-input v-model="DoorplateForm.dDoor" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="宿舍人数:"  prop="dStudent" :label-width="formLabelWidth">
            <el-input v-model="DoorplateForm.dStudent" autocomplete="off"></el-input>
          </el-form-item>
          
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-switch
              v-model="DoorplateForm.status"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveDoorplate"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import DoorplateApi from "@/api/door";
  export default {
    data() {
      
      return {
        formLabelWidth: "130",
        DoorplateForm: {},
        dialogFormVisible: false,
        title: "",
        total: 0,
        searchMode1: {
          pageNo: 1,
          pageSize: 10,
        },
        DoorplateList: [],
        rules:{
          sName: [
            { required: true, message: "请输入宿舍楼号", trigger: "blur" },
            {
              min: 1,
              max: 50,
              message: "请输入正确的楼号",
              trigger: "blur",
            },
          ],
          sID: [
            { required: true, message: "请输入宿舍门牌号", trigger: "blur" },
            {
              min: 1,
              max: 50,
              message: "请输入正确的门牌号",
              trigger: "blur",
            },
          ],        
        },
      };
    },
    methods: {
      deleteDoorplate(doorplate){
        this.$confirm(`您确认删除此宿舍号 ${doorplate.dDoor} ?`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            DoorplateApi.deleteDoorplateById(doorplate.id).then(responce =>{
              this.$message({
              type: 'success',
              message: responce.message
            });
            this.getDoorplateList();
            });
            
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });          
          });
      },
      saveDoorplate(){
        //触发表单验证
        this.$refs.DoorplateFormRef.validate((valid) => {
          if (valid) {
            //提交后台
            DoorplateApi.saveDoorplate(this.DoorplateForm).then((responce) => {
              //成功提示
              this.$message({
                message: responce.message,
                type: "success",
              });
              //关闭对话框
              this.dialogFormVisible = false;
              //刷新数据
              this.getDoorplateList();
            });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
  
      clearform() {
        this.DoorplateForm = {};
        this.$refs.DoorplateFormRef.clearValidate();
      },
      openEdiuUI(id) {
        if(id==null){
          this.title = "新增宿舍";
        }else{
          this.title = "修改宿舍";
          //根据id查询数据
          DoorplateApi.getDoorplateById(id).then(responce =>{
              this.DoorplateForm =responce.data;
          });
        }
        
        this, (this.dialogFormVisible = true);
      },
      handleSizeChange(pageSize) {
        this.searchMode1.pageSize = pageSize;
        this.getDoorplateList();
      },
      handleCurrentChange(pageNo) {
        this.searchMode1.pageNo = pageNo;
        this.getDoorplateList();
      },
      getDoorplateList() {
        DoorplateApi.getDoorplateList(this.searchMode1).then((responce) => {
          this.DoorplateList = responce.data.rows;
          this.total = responce.data.total;
        });
      },
    },
    created() {
      this.getDoorplateList();
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
