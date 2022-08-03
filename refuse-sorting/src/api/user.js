import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/classification/admin/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/classification/admin/getAdminInfo',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/classification/admin/logout',
    method: 'post'
  })
}
