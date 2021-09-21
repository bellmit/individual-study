import mockdata from './mockdata'

const tokens = {
  admin: {
    token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAxIn0.mfd9Q4HRQMRr36aGHlIOn4wV6934_rU24gjRMRrSnTM'
  },
  editor: {
    token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAyIn0.vjp1rJb6jwxoyg1dqArEQfUSyo9nbtttPYsxJfGYf4c'
  }
}

const users = {
  'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAxIn0.mfd9Q4HRQMRr36aGHlIOn4wV6934_rU24gjRMRrSnTM': {
    roles: ['超级管理员'],
    introduction: 'I am a super administrator',
    avatar: '',
    name: 'Super Admin',
    userName: 'Super Admin',
    role: {
      roleName: 'admin'
    }
  },
  'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAyIn0.vjp1rJb6jwxoyg1dqArEQfUSyo9nbtttPYsxJfGYf4c': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: '',
    name: 'Normal Editor',
    userName: 'Normal Editor',
    role: {
      roleName: 'normal'
    }
  }
}

export default [
  // user login
  {
    url: '/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }

      return {
        code: 0,
        data: token
      }
    }
  },

  // user page
  {
    url: '/user/page',
    type: 'get',
    response: _ => {
      let records = [];
      records.push(mockdata.user);
      records.push(mockdata.user);
      records.push(mockdata.user);

      return {
        code: 0,
        data: {
          total: records.length,
          records: records
        }
      }
    }
  },

  // get user info
  {
    url: '/user/\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users['eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAxIn0.mfd9Q4HRQMRr36aGHlIOn4wV6934_rU24gjRMRrSnTM']

      // mock error
      if (!info) {
        return {
          code: 50008,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 0,
        data: info
      }
    }
  },

  // user logout
  {
    url: '/user/logout',
    type: 'post',
    response: _ => {
      return {
        code: 0,
        data: 'success'
      }
    }
  },

  // role page
  {
    url: '/role/page',
    type: 'get',
    response: _ => {
      let records = [];
      records.push(mockdata.role);
      records.push(mockdata.role);
      records.push(mockdata.role);

      return {
        code: 0,
        data: {
          total: records.length,
          records: records
        }
      }
    }
  },

  // log page
  {
    url: '/log/page',
    type: 'get',
    response: _ => {
      let records = [];
      records.push(mockdata.log);
      records.push(mockdata.log);
      records.push(mockdata.log);

      return {
        code: 0,
        data: {
          total: records.length,
          records: records
        }
      }
    }
  }

]
