import request from '@/utils/request'

export default {
  // 1.列表(条件查询分页）
  // current当前页 limit每页记录数 articleQuery条件对象
  getOrderListPage(current, limit, orderQuery) {
    return request({
      url: `/classification/sortingOrder/pageOrderCondition/${current}/${limit}`,
      method: 'post',
      // articleQuery条件对象，后端使用RequestBody获取数据
      // data表示把对象转换json进行传递到接口里面
      data: orderQuery
    })
  },
  // 添加文章
  addOrder(order) {
    return request({
      url: `/classification/sortingOrder/addOrder`,
      method: 'post',
      data: order
    })
  },
  // 根据id查询文章
  getOrderInfo(id) {
    return request({
      url: `/classification/sortingOrder/getOrder/${id}`,
      method: 'get'
    })
  },
  // 修改
  updateOrderInfo(order) {
    return request({
      url: `/classification/sortingOrder/updateOrder`,
      method: 'post',
      data: order
    })
  },
  // 接单
  receiveOrder(id) {
    return request({
      url: `/classification/sortingOrder/receiveOrder?id=` + id,
      method: 'post'
    })
  },
  completeOrder(id, factWeight) {
    return request({
      url: `/classification/sortingOrder/completeOrder?id=` + id + `&factWeight=` + factWeight,
      method: 'post'
    })
  }
}
