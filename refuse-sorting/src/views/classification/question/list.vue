<template>
  <div class="app-container">
    问题列表
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="questionQuery.stem" placeholder="标题名称" />
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="questionQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="questionQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
    >

      <el-table-column
        label="序号"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="stem" label="题干" width="250" />

      <el-table-column prop="answer" label="答案" width="50" />

      <el-table-column prop="contenta" label="选项A" />

      <el-table-column prop="contentb" label="选项B" />

      <el-table-column prop="contentc" label="选项C" />

      <el-table-column prop="contentd" label="选项D" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/question/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>

<script>
import question from '@/api/classification/question'

export default {
  // 写核心代码位置
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总记录数
      questionQuery: {} // 条件封装对象
    }
  },
  created() { // 页面渲染之前执行，一般调用methods定义的方法
    // 调用
    this.getList()
    // console.log(this.$store.state.token)
  },
  methods: { // 创建具体的方法，调用article.js定义的方法
    // 列表的方法
    getList(page = 1) {
      this.page = page
      question.getQuestionListPage(this.page, this.limit, this.questionQuery)
        .then(response => { // 请求成功
          // response接口返回的数据
          // console.log(response)
          this.list = response.data.page.list
          this.total = response.data.page.totalCount
          // console.log(this.list)
          // console.log(this.total)
        })
    },
    resetData() { // 清空的方法
      // 表单输入项数据清空
      this.questionQuery = {}
      // 查询所有讲师数据
      this.getList()
    },
    // 删除文章的方法
    removeDataById(id) {
      this.$confirm('此操作将永久删除文章记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // 点击确定，执行then方法
        // 调用删除的方法
        question.deleteQuestionId(id)
          .then(response => { // 删除成功
            // 提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // 回到列表页面
            this.getList()
          })
      }) // 点击取消，执行catch方法
    }

  }
}
</script>

<style scoped>

</style>
