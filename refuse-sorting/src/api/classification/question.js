import request from '@/utils/request'

export default {
  // 1.列表(条件查询分页）
  // current当前页 limit每页记录数 teacherQuery条件对象
  getQuestionListPage(current, limit, questionQuery) {
    return request({
      url: `/classification/sorting-question/pageQuestionCondition/${current}/${limit}`,
      method: 'post',
      // articleQuery条件对象，后端使用RequestBody获取数据
      // data表示把对象转换json进行传递到接口里面
      data: questionQuery
    })
  },
  // 删除问题
  deleteQuestionId(id) {
    return request({
      url: `/classification/sorting-question/${id}`,
      method: 'delete'
    })
  },
  // 添加问题
  addQuestion(question) {
    return request({
      url: `/classification/sorting-question/addQuestion`,
      method: 'post',
      data: question
    })
  },
  // 根据id查询问题
  getQuestionInfo(id) {
    return request({
      url: `/classification/sorting-question/getQuestion/${id}`,
      method: 'get'
    })
  },
  // 修改问题
  updateQuestionInfo(question) {
    return request({
      url: `/classification/sorting-question/updateQuestion`,
      method: 'post',
      data: question
    })
  }
}
