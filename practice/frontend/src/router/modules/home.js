import HomeLayout from '@/layout/home'

const homeRouter = [
  {
    path: '/user',
    component: HomeLayout,
    redirect: '/user/info',
    name: 'HomeUser',
    children: [
      {
        path: 'info',
        component: () => import('@/views/user/user-info'),
        name: 'UserInfo',
        meta: { title: '用户详情', icon: 'user', auth: false }
      }
    ]
  },
  // {
  //   path: '/chat',
  //   component: HomeLayout,
  //   redirect: '/chat/room',
  //   name: 'Chat',
  //   children: [
  //     {
  //       path: 'room',
  //       component: () => import('@/views/chat/chat-room'),
  //       name: 'ChatRoom',
  //       meta: { title: '聊天室', icon: 'user', auth: false }
  //     }
  //   ]
  // },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    name: 'Login',
    meta: {
      title: '登录',
      icon: 'login'
    }
  },
  {
    path: '/register',
    component: HomeLayout,
    redirect: '/register/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/register/index'),
        name: 'Register',
        meta: { title: '注册', icon: 'home', affix: true }
      }
    ]
  }
]
export default homeRouter