import request from '@/utils/request'
export default {
  // 1.课程分类列表
  getItemList() {
    return request({
      url: '/classification/item/getAllSubjects',
      method: 'get'
    })
  }
}
