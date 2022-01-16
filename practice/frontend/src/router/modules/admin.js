import AdminLayout from '@/layout/admin'

export const adminRouter = [
  {
    path: '/',
    component: AdminLayout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views-admin/dashboard/index'),
      meta: { title: 'Dashboard', icon: 's-home', auth: true }
    }]
  },
  {
    path: '/plugin',
    component: AdminLayout,
    redirect: 'plugin',
    children: [{
      path: 'plugin',
      name: 'Plugin',
      component: () => import('@/views-admin/dashboard/index'),
      meta: { title: '插件管理', icon: 'menu', auth: true }
    }]
  },
  {
    path: '/res',
    component: AdminLayout,
    redirect: 'house_info',
    children: [{
      path: 'house_info',
      name: 'houseInfo',
      component: () => import('@/views-admin/res/house-info/index'),
      meta: { title: '新房管理', icon: 'office-building', auth: true }
    }]
  },
  {
    path: '/ums',
    component: AdminLayout,
    redirect: '/ums/admin',
    name: 'ums',
    meta: { title: '权限', icon: 'setting' },
    children: [
      {
        path: 'admin',
        name: 'admin',
        component: () => import('@/views-admin/ums/admin/index'),
        meta: { title: '管理员', icon: 's-custom' }
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views-admin/ums/role/index'),
        meta: { title: '角色列表', icon: 'setting' }
      },
      // {
      //   path: 'allocMenu',
      //   name: 'allocMenu',
      //   component: () => import('@/views-admin/ums/role/allocMenu'),
      //   meta: { title: '分配菜单' },
      //   hidden: true
      // },
      // {
      //   path: 'allocResource',
      //   name: 'allocResource',
      //   component: () => import('@/views-admin/ums/role/allocResource'),
      //   meta: { title: '分配资源' },
      //   hidden: true
      // },
      // {
      //   path: 'menu',
      //   name: 'menu',
      //   component: () => import('@/views-admin/ums/menu/index'),
      //   meta: { title: '菜单列表', icon: 'setting' }
      // },
      // {
      //   path: 'addMenu',
      //   name: 'addMenu',
      //   component: () => import('@/views-admin/ums/menu/add'),
      //   meta: { title: '添加菜单' },
      //   hidden: true
      // },
      // {
      //   path: 'updateMenu',
      //   name: 'updateMenu',
      //   component: () => import('@/views-admin/ums/menu/update'),
      //   meta: { title: '修改菜单' },
      //   hidden: true
      // },
      // {
      //   path: 'resource',
      //   name: 'resource',
      //   component: () => import('@/views-admin/ums/resource/index'),
      //   meta: { title: '资源列表', icon: 'setting' }
      // },
      // {
      //   path: 'resourceCategory',
      //   name: 'resourceCategory',
      //   component: () => import('@/views-admin/ums/resource/categoryList'),
      //   meta: { title: '资源分类' },
      //   hidden: true
      // }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default adminRouter
