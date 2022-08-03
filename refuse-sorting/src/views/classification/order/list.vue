<template>
  <div class="app-container">
    订单列表
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderQuery.userName" placeholder="用户姓名" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="orderQuery.companyId" type="hidden" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="orderQuery.type" clearable placeholder="订单类型">
          <el-option :value="1" label="网络订单" />
          <el-option :value="2" label="实体订单" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="orderQuery.status" clearable placeholder="订单状态">
          <el-option :value="1" label="等待上门" />
          <el-option :value="2" label="等待评价" />
          <el-option :value="3" label="订单结束" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-input v-model="orderQuery.address" placeholder="订单地区" />
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="orderQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="orderQuery.end"
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

      <el-table-column prop="userName" label="客户名称" width="100" />
      <el-table-column prop="tel" label="电话" width="140" />
      <el-table-column prop="address" label="地址" width="160" />

      <el-table-column label="订单类型" width="80">
        <template slot-scope="scope">
          {{ scope.row.type === 1 ? '网络订单':'实体订单' }}
        </template>
      </el-table-column>

      <el-table-column label="订单状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? '等待上门':scope.row.status === 2 ? '正在上门' : '订单结束' }}
        </template>
      </el-table-column>

      <el-table-column prop="anticipationTime" label="预计上门时间" width="100" />

      <el-table-column prop="evaluateWeight" label="预计重量(单位kg)" width="80" />

      <el-table-column prop="itemDetail" label="回收物品" />

      <el-table-column prop="factWeight" label="实际重量(单位kg)" width="80" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--          <router-link :to="'/order/edit/'+scope.row.id">-->
          <!--            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>-->
          <!--          </router-link>-->
          <el-button v-if="scope.row.status===1" type="primary" size="mini" icon="el-icon-edit" @click="receiveOrderById(scope.row.id)">接单
          </el-button>
          <el-button v-if="scope.row.status===2" type="primary" size="mini" icon="el-icon-edit" @click="completeOrderById(scope.row.id)">完成
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
import order from '@/api/classification/order'

export default {
  // 写核心代码位置
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 5, // 每页记录数
      total: 0, // 总记录数
      orderQuery: {
        companyId: '1'
      }, // 条件封装对象
      companyId: '',
      visiblePublish: ''
    }
  },
  created() { // 页面渲染之前执行，一般调用methods定义的方法
    this.handleLogin()
    // 调用
    this.getList()
  },
  // beforeUpdate() {
  //   this.getList()
  // },
  methods: { // 创建具体的方法，调用article.js定义的方法
    // 文章列表的方法
    getList(page = 1) {
      this.page = page
      // this.orderQuery.companyId = this.companyId
      console.log(this.orderQuery)
      order.getOrderListPage(this.page, this.limit, this.orderQuery)
        .then(response => { // 请求成功
          // response接口返回的数据
          // console.log(response)
          this.list = response.data.page.records
          this.total = response.data.page.total
          // console.log(this.list)
          // console.log(this.total)
        })
    },
    handleLogin() {
      this.$store.dispatch('user/getInfo').then((response) => {
        // console.log(response)
        // this.orderQuery.companyId = response.companyId
        this.orderQuery = {
          companyId: response.companyId
        }
        this.companyId = response.companyId
      }).catch((res) => {
        console.log(res)
      })
    },
    resetData() { // 清空的方法
      // 表单输入项数据清空
      this.orderQuery = {
        companyId: this.companyId
      }
      // 查询所有数据
      this.getList()
    },
    receiveOrderById(id) {
      console.log(id)
      order.receiveOrder(id)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '接单成功!'
          })
          this.getList()
        })
    },
    completeOrderById(id) {
      this.$prompt('请输入实际重量', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^\d{1,}$/,
        inputErrorMessage: '请输入数字'
      }).then(({ value }) => {
        order.completeOrder(id, value)
          .then(response => {
            this.$message({
              type: 'success',
              message: '完成订单!'
            })
            this.getList()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    }
    // // 删除文章的方法
    // removeDataById(id) {
    //   this.$confirm('此操作将永久删除文章记录, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => { // 点击确定，执行then方法
    //     // 调用删除的方法
    //     order.deleteArticleId(id)
    //       .then(response => { // 删除成功
    //         // 提示信息
    //         this.$message({
    //           type: 'success',
    //           message: '删除成功!'
    //         })
    //         // 回到列表页面
    //         this.getList()
    //       })
    //   }) // 点击取消，执行catch方法
    // }

  }
}
</script>

<style scoped>

</style>
