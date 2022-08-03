<template>
  <div class="app-container">
    题库表单
    <el-form label-width="120px">
      <el-form-item label="文章标题">
        <el-input v-model="question.stem" />
      </el-form-item>
      <el-form-item label="答案">
        <el-input v-model="question.answer" />
      </el-form-item>
      <el-form-item label="选项A">
        <el-input v-model="question.contenta" />
      </el-form-item>
      <el-form-item label="选项B">
        <el-input v-model="question.contentb" />
      </el-form-item>
      <el-form-item label="选项C">
        <el-input v-model="question.contentc" />
      </el-form-item>
      <el-form-item label="选项D">
        <el-input v-model="question.contentd" />
      </el-form-item>

      <!-- 图片：TODO -->
      <!-- 图片 -->
      <el-form-item label="图片">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="question.img" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换图像
        </el-button>

        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调
          <input type="file" name="file"/>
        -->
        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :width="300"
          :height="300"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import questionApi from '@/api/classification/question'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      question: {
        stem: '',
        answer: '',
        contenta: '',
        contentb: '',
        contentc: '',
        contentd: '',
        img: ''
      },
      // 上传弹框组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件key值
      BASE_API: process.env.VUE_APP_BASE_API, // 获取dev.env.js里面地址
      saveBtnDisabled: false // 保存按钮是否禁用,g'
    }
  },
  watch: { // 监听
    $route(to, from) { // 路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },
  created() { // 页面渲染之前执行
    this.init()
  },
  methods: {
    close() { // 关闭上传弹框的方法
      this.imagecropperShow = false
      // 上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 上传成功方法
    cropSuccess(data) {
      this.imagecropperShow = false
      // 上传之后接口返回图片地址
      this.question.img = data.url
      this.imagecropperKey = this.imagecropperKey + 1
    },
    init() {
      // 判断路径有id值,做修改
      if (this.$route.params && this.$route.params.id) {
        // 从路径获取id值
        const id = this.$route.params.id
        // 调用根据id查询的方法
        this.getInfo(id)
      } else { // 路径没有id值，做添加
        // 清空表单
        this.question = {}
        this.question.img = ''
      }
    },
    // 根据文章id查询的方法
    getInfo(id) {
      questionApi.getQuestionInfo(id)
        .then(response => {
          this.question = response.data.question
        })
    },
    saveOrUpdate() {
      // 判断修改还是添加
      // 根据question是否有id
      if (!this.question.id) {
        // 添加
        this.saveQuestion()
      } else {
        // 修改
        this.updateQuestion()
      }
    },
    // 修改讲师的方法
    updateQuestion() {
      questionApi.updateQuestionInfo(this.question)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 回到列表页面 路由跳转
          this.$router.push({ path: '/question/table' })
        })
    },
    // 添加文章的方法
    saveQuestion() {
      questionApi.addQuestion(this.question)
        .then(response => { // 添加成功
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 回到列表页面 路由跳转
          this.$router.push({ path: '/question/table' })
        })
    }

  }
}
</script>

<style scoped>

</style>
