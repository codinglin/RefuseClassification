import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/article',
    component: Layout,
    redirect: '/article/table',
    name: '文章管理',
    meta: { title: '文章管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'table',
        name: '文章列表',
        component: () => import('@/views/classification/article/list'),
        meta: { title: '文章列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加文章',
        component: () => import('@/views/classification/article/save'),
        meta: { title: '添加文章', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: 'EduArticleEdit',
        component: () => import('@/views/classification/article/save'),
        meta: { title: '编辑文章', noCache: true },
        hidden: true
      }
    ]
  },

  {
    path: '/question',
    component: Layout,
    redirect: '/question/table',
    name: '题库管理',
    meta: { title: '题库管理', icon: 'form' },
    children: [
      {
        path: 'table',
        name: '题库列表',
        component: () => import('@/views/classification/question/list'),
        meta: { title: '题库列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加题库',
        component: () => import('@/views/classification/question/save'),
        meta: { title: '添加题库', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: 'EduQuestionEdit',
        component: () => import('@/views/classification/question/save'),
        meta: { title: '编辑题库', noCache: true },
        hidden: true
      }
    ]
  },

  {
    path: '/order',
    component: Layout,
    redirect: '/order/table',
    name: '订单管理',
    meta: { title: '订单管理', icon: 'link' },
    children: [
      {
        path: 'table',
        name: '订单列表',
        component: () => import('@/views/classification/order/list'),
        meta: { title: '订单列表', icon: 'table' }
      }
    ]
  },

  {
    path: '/item',
    component: Layout,
    redirect: '/item/table',
    name: '物品分类',
    meta: { title: '物品类别', icon: 'form' },
    children: [
      {
        path: 'table',
        name: '类别列表',
        component: () => import('@/views/classification/item/list'),
        meta: { title: '类别列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加类别',
        component: () => import('@/views/classification/item/save'),
        meta: { title: '添加类别', icon: 'tree' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
