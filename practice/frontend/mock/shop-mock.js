import mockdata from './mockdata'
import mockData from './mockdata'

export default [
  // user login
  {
    url: '/product/getPromoProduct',
    type: 'post',
    response: config => {
      return {
        code: 20000,
        data: mockData.getPromoProduct
      }
    }
  },
  {
    url: '/product/getHotProduct',
    type: 'post',
    response: config => {
      return {
        code: 20000,
        data: mockData.getHotProduct
      }
    }
  },

]