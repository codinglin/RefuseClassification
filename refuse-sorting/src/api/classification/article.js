import request from '@/utils/request'

export default {
  // 1.列表(条件查询分页）
  // current当前页 limit每页记录数 articleQuery条件对象
  getArticleListPage(current, limit, articleQuery) {
    return request({
      url: `/classification/sortingArticle/pageArticleCondition/${current}/${limit}`,
      method: 'post',
      // articleQuery条件对象，后端使用RequestBody获取数据
      // data表示把对象转换json进行传递到接口里面
      data: articleQuery
    })
  },
  // 删除文章
  deleteArticleId(id) {
    return request({
      url: `/classification/sortingArticle/${id}`,
      method: 'delete'
    })
  },
  // 添加文章
  addArticle(article) {
    return request({
      url: `/classification/sortingArticle/addArticle`,
      method: 'post',
      data: article
    })
  },
  // 根据id查询文章
  getArticleInfo(id) {
    return request({
      url: `/classification/sortingArticle/getArticle/${id}`,
      method: 'get'
    })
  },
  // 修改文章
  updateArticleInfo(article) {
    return request({
      url: `/classification/sortingArticle/updateArticle`,
      method: 'post',
      data: article
    })
  }
}
