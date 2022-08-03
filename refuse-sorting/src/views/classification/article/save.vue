<template>
  <div class="app-container">
    文章表单
    <el-form label-width="120px">
      <el-form-item label="文章标题">
        <el-input v-model="article.title" />
      </el-form-item>
      <el-form-item label="显示位置">
        <el-select v-model="article.showIndex" clearable placeholder="请选择">
          <el-option :value="0" label="主页不显示" />
          <el-option :value="1" label="banner" />
          <el-option :value="2" label="通知栏" />
        </el-select>
      </el-form-item>
      <el-form-item label="文章内容">
        <el-input
          v-model="article.detail"
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 8}"
          placeholder="请输入内容"
        />
      </el-form-item>

      <!-- 图片：TODO -->
      <!-- 图片 -->
      <el-form-item label="图片">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="article.pic" />
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
import articleApi from '@/api/classification/article'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      article: {
        title: '',
        detail: '',
        showIndex: 0,
        pic: ''
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
      this.article.pic = data.url
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
        this.article = {}
        this.article.pic = 'https://online-education-lin.oss-cn-hangzhou.aliyuncs.com/article1.jpg'
      }
    },
    // 根据文章id查询的方法
    getInfo(id) {
      articleApi.getArticleInfo(id)
        .then(response => {
          this.article = response.data.article
        })
    },
    saveOrUpdate() {
      // 判断修改还是添加
      // 根据article是否有id
      if (!this.article.id) {
        // 添加
        this.saveArticle()
      } else {
        // 修改
        this.updateArticle()
      }
    },
    // 修改文章的方法
    updateArticle() {
      articleApi.updateArticleInfo(this.article)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 回到列表页面 路由跳转
          this.$router.push({ path: '/article/table' })
        })
    },
    // 添加文章的方法
    saveArticle() {
      articleApi.addArticle(this.article)
        .then(response => { // 添加成功
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 回到列表页面 路由跳转
          this.$router.push({ path: '/article/table' })
        })
    }

  }
}
</script>

<style scoped>

</style>
