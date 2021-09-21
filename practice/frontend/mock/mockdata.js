const mockData = {
  user: {
    uid: 1001,
    userName: "test01",
    userNameCn: "测试01",
    status: 0,
    createdTime: "2020-10-01"
  },
  role: {
    roleName: "admin",
    roleStatus: 0,
    createdTime: "2020-12-19",
    modifiedTime: "2020-01-01"
  },
  log: {
    sequencenum: 100,
    responsecode: 0,
    host: "127.0.0.1",
    port: "8080",
    module: "test",
    actionurl: "/api/test",
    method: "GET",
    gmtcreate: "2020-08-01"
  },
  carousel: [
    {
      "carousel_id": 1,
      "imgPath": "public/imgs/cms_1.jpg",
      "describes": "123456"
    },
    {
      "carousel_id": 2,
      "imgPath": "public/imgs/cms_2.jpg",
      "describes": "123456"
    },
    {
      "carousel_id": 3,
      "imgPath": "public/imgs/cms_3.jpg",
      "describes": "123456"
    },
    {
      "carousel_id": 4,
      "imgPath": "public/imgs/cms_4.jpg",
      "describes": "123456"
    }
  ],
  getPromoProduct: {
    "Product": [
      {
        "product_id": 1,
        "product_name": "Redmi K30",
        "category_id": 1,
        "product_title": "120Hz流速屏，全速热爱",
        "product_intro": "120Hz高帧率流速屏/ 索尼6400万前后六摄 / 6.67'小孔径全面屏 / 最高可选8GB+256GB大存储 / 高通骁龙730G处理器 / 3D四曲面玻璃机身 / 4500mAh+27W快充 / 多功能NFC",
        "product_picture": "public/imgs/phone/Redmi-k30.png",
        "product_price": 2000,
        "product_selling_price": 1599,
        "product_num": 10,
        "product_sales": 0
      },
      {
        "product_id": 2,
        "product_name": "Redmi K30 5G",
        "category_id": 1,
        "product_title": "双模5G,120Hz流速屏",
        "product_intro": "双模5G / 三路并发 / 高通骁龙765G / 7nm 5G低功耗处理器 / 120Hz高帧率流速屏 / 6.67'小孔径全面屏 / 索尼6400万前后六摄 / 最高可选8GB+256GB大存储 / 4500mAh+30W快充 / 3D四曲面玻璃机身 / 多功能NFC",
        "product_picture": "public/imgs/phone/Redmi-k30-5G.png",
        "product_price": 2599,
        "product_selling_price": 2599,
        "product_num": 10,
        "product_sales": 0
      },
      {
        "product_id": 3,
        "product_name": "小米CC9 Pro",
        "category_id": 1,
        "product_title": "1亿像素,五摄四闪",
        "product_intro": "1亿像素主摄 / 全场景五摄像头 / 四闪光灯 / 3200万自拍 / 10 倍混合光学变焦，50倍数字变焦 / 5260mAh ⼤电量 / 标配 30W疾速快充 / ⼩米⾸款超薄屏下指纹 / 德国莱茵低蓝光认证 / 多功能NFC / 红外万能遥控 / 1216超线性扬声器",
        "product_picture": "public/imgs/phone/Mi-CC9.png",
        "product_price": 2799,
        "product_selling_price": 2599,
        "product_num": 20,
        "product_sales": 0
      },
      {
        "product_id": 4,
        "product_name": "Redmi 8",
        "category_id": 1,
        "product_title": "5000mAh超长续航",
        "product_intro": "5000mAh超长续航 / 高通骁龙439八核处理器 / 4GB+64GB",
        "product_picture": "public/imgs/phone/Redmi-8.png",
        "product_price": 799,
        "product_selling_price": 699,
        "product_num": 20,
        "product_sales": 0
      },
      {
        "product_id": 5,
        "product_name": "Redmi 8A",
        "category_id": 1,
        "product_title": "5000mAh超长续航",
        "product_intro": "5000mAh超长续航 / 高通骁龙439八核处理器 / 4GB+64GB / 1200万AI后置相机",
        "product_picture": "public/imgs/phone/Redmi-8A.png",
        "product_price": 599,
        "product_selling_price": 699,
        "product_num": 20,
        "product_sales": 0
      },
      {
        "product_id": 6,
        "product_name": "Redmi Note8 Pro",
        "category_id": 1,
        "product_title": "6400万全场景四摄",
        "product_intro": "6400万四摄小金刚拍照新旗舰超强解析力，超震撼",
        "product_picture": "public/imgs/phone/Redmi-Note8-pro.png",
        "product_price": 1399,
        "product_selling_price": 1199,
        "product_num": 20,
        "product_sales": 0
      },
      {
        "product_id": 7,
        "product_name": "Redmi Note8",
        "category_id": 1,
        "product_title": "千元4800万四摄",
        "product_intro": "千元4800万四摄 | 高通骁龙665 | 小金刚品质保证",
        "product_picture": "public/imgs/phone/Redmi-Note8.png",
        "product_price": 999,
        "product_selling_price": 999,
        "product_num": 20,
        "product_sales": 0
      }
    ]
  },
  getHotProduct: {
    "Product": [
      {
        "product_id": 16,
        "product_name": "米家互联网空调C1（一级能效）",
        "category_id": 3,
        "product_title": "变频节能省电，自清洁",
        "product_intro": "一级能效 | 1.5匹 | 全直流变频 | 高效制冷/热 | 静音设计 | 自清洁 | 全屋互联",
        "product_picture": "public/imgs/appliance/AirCondition-V1C1.png",
        "product_price": 2699,
        "product_selling_price": 2599,
        "product_num": 20,
        "product_sales": 10
      },
      {
        "product_id": 17,
        "product_name": "米家空调",
        "category_id": 3,
        "product_title": "出众静音，快速制冷热",
        "product_intro": "大1匹 | 三级能效 | 静音 | 快速制冷热 | 广角送风 | 除湿功能 | 高密度过滤网 | 典雅外观",
        "product_picture": "public/imgs/appliance/AirCondition-F3W1.png",
        "product_price": 1799,
        "product_selling_price": 1699,
        "product_num": 20,
        "product_sales": 8
      },
      {
        "product_id": 18,
        "product_name": "米家互联网洗烘一体机 Pro 10kg",
        "category_id": 4,
        "product_title": "智能洗烘，省心省力",
        "product_intro": "国标双A+级洗烘能力 / 22种洗烘模式 / 智能投放洗涤剂 / 支持小爱同学语音遥控 / 支持OTA在线智能升级 / 智能空气洗 / 除菌率达99.9%+",
        "product_picture": "public/imgs/appliance/Washer-Pro-10.png",
        "product_price": 2999,
        "product_selling_price": 2999,
        "product_num": 20,
        "product_sales": 7
      },
      {
        "product_id": 9,
        "product_name": "小米电视4A 32英寸",
        "category_id": 2,
        "product_title": "人工智能系统，高清液晶屏",
        "product_intro": "小米电视4A 32英寸 | 64位四核处理器 | 1GB+4GB大内存 | 人工智能系统",
        "product_picture": "public/imgs/appliance/MiTv-4A-32.png",
        "product_price": 799,
        "product_selling_price": 799,
        "product_num": 10,
        "product_sales": 0
      },
      {
        "product_id": 10,
        "product_name": "小米全面屏电视E55A",
        "category_id": 2,
        "product_title": "全面屏设计，人工智能语音",
        "product_intro": "全面屏设计 | 内置小爱同学 | 4K + HDR | 杜比DTS | PatchWall | 海量内容 | 2GB + 8GB大存储 | 64位四核处理器",
        "product_picture": "public/imgs/appliance/MiTv-E55A.png",
        "product_price": 2099,
        "product_selling_price": 1899,
        "product_num": 10,
        "product_sales": 0
      },
      {
        "product_id": 11,
        "product_name": "小米全面屏电视E65A",
        "category_id": 2,
        "product_title": "全面屏设计，人工智能语音",
        "product_intro": "人工智能语音系统 | 海量影视内容 | 4K 超高清屏 | 杜比音效 | 64位四核处理器 | 2GB + 8GB大存储",
        "product_picture": "public/imgs/appliance/MiTv-E65A.png",
        "product_price": 3999,
        "product_selling_price": 2799,
        "product_num": 10,
        "product_sales": 0
      },
      {
        "product_id": 12,
        "product_name": "小米电视4X 43英寸",
        "category_id": 2,
        "product_title": "FHD全高清屏，人工智能语音",
        "product_intro": "人工智能语音系统 | FHD全高清屏 | 64位四核处理器 | 海量片源 | 1GB+8GB大内存 | 钢琴烤漆",
        "product_picture": "public/imgs/appliance/MiTv-4X-43.png",
        "product_price": 1499,
        "product_selling_price": 1299,
        "product_num": 10,
        "product_sales": 0
      }
    ]
  }
}

export default mockData;

/*
api/resources/carousel
{"code":"001","carousel":[{"carousel_id":1,"imgPath":"public/imgs/cms_1.jpg","describes":"123456"},{"carousel_id":2,"imgPath":"public/imgs/cms_2.jpg","describes":"123456"},{"carousel_id":3,"imgPath":"public/imgs/cms_3.jpg","describes":"123456"},{"carousel_id":4,"imgPath":"public/imgs/cms_4.jpg","describes":"123456"}]}

/product/getPromoProduct
{"code":"001","Product":[{"product_id":1,"product_name":"Redmi K30","category_id":1,"product_title":"120Hz流速屏，全速热爱","product_intro":"120Hz高帧率流速屏/ 索尼6400万前后六摄 / 6.67'小孔径全面屏 / 最高可选8GB+256GB大存储 / 高通骁龙730G处理器 / 3D四曲面玻璃机身 / 4500mAh+27W快充 / 多功能NFC","product_picture":"public/imgs/phone/Redmi-k30.png","product_price":2000,"product_selling_price":1599,"product_num":10,"product_sales":0},{"product_id":2,"product_name":"Redmi K30 5G","category_id":1,"product_title":"双模5G,120Hz流速屏","product_intro":"双模5G / 三路并发 / 高通骁龙765G / 7nm 5G低功耗处理器 / 120Hz高帧率流速屏 / 6.67'小孔径全面屏 / 索尼6400万前后六摄 / 最高可选8GB+256GB大存储 / 4500mAh+30W快充 / 3D四曲面玻璃机身 / 多功能NFC","product_picture":"public/imgs/phone/Redmi-k30-5G.png","product_price":2599,"product_selling_price":2599,"product_num":10,"product_sales":0},{"product_id":3,"product_name":"小米CC9 Pro","category_id":1,"product_title":"1亿像素,五摄四闪","product_intro":"1亿像素主摄 / 全场景五摄像头 / 四闪光灯 / 3200万自拍 / 10 倍混合光学变焦，50倍数字变焦 / 5260mAh ⼤电量 / 标配 30W疾速快充 / ⼩米⾸款超薄屏下指纹 / 德国莱茵低蓝光认证 / 多功能NFC / 红外万能遥控 / 1216超线性扬声器","product_picture":"public/imgs/phone/Mi-CC9.png","product_price":2799,"product_selling_price":2599,"product_num":20,"product_sales":0},{"product_id":4,"product_name":"Redmi 8","category_id":1,"product_title":"5000mAh超长续航","product_intro":"5000mAh超长续航 / 高通骁龙439八核处理器 / 4GB+64GB","product_picture":"public/imgs/phone/Redmi-8.png","product_price":799,"product_selling_price":699,"product_num":20,"product_sales":0},{"product_id":5,"product_name":"Redmi 8A","category_id":1,"product_title":"5000mAh超长续航","product_intro":"5000mAh超长续航 / 高通骁龙439八核处理器 / 4GB+64GB / 1200万AI后置相机","product_picture":"public/imgs/phone/Redmi-8A.png","product_price":599,"product_selling_price":699,"product_num":20,"product_sales":0},{"product_id":6,"product_name":"Redmi Note8 Pro","category_id":1,"product_title":"6400万全场景四摄","product_intro":"6400万四摄小金刚拍照新旗舰超强解析力，超震撼","product_picture":"public/imgs/phone/Redmi-Note8-pro.png","product_price":1399,"product_selling_price":1199,"product_num":20,"product_sales":0},{"product_id":7,"product_name":"Redmi Note8","category_id":1,"product_title":"千元4800万四摄","product_intro":"千元4800万四摄 | 高通骁龙665 | 小金刚品质保证","product_picture":"public/imgs/phone/Redmi-Note8.png","product_price":999,"product_selling_price":999,"product_num":20,"product_sales":0}]}

/product/getHotProduct
{"code":"001","Product":[{"product_id":16,"product_name":"米家互联网空调C1（一级能效）","category_id":3,"product_title":"变频节能省电，自清洁","product_intro":"一级能效 | 1.5匹 | 全直流变频 | 高效制冷/热 | 静音设计 | 自清洁 | 全屋互联","product_picture":"public/imgs/appliance/AirCondition-V1C1.png","product_price":2699,"product_selling_price":2599,"product_num":20,"product_sales":10},{"product_id":17,"product_name":"米家空调","category_id":3,"product_title":"出众静音，快速制冷热","product_intro":"大1匹 | 三级能效 | 静音 | 快速制冷热 | 广角送风 | 除湿功能 | 高密度过滤网 | 典雅外观","product_picture":"public/imgs/appliance/AirCondition-F3W1.png","product_price":1799,"product_selling_price":1699,"product_num":20,"product_sales":8},{"product_id":18,"product_name":"米家互联网洗烘一体机 Pro 10kg","category_id":4,"product_title":"智能洗烘，省心省力","product_intro":"国标双A+级洗烘能力 / 22种洗烘模式 / 智能投放洗涤剂 / 支持小爱同学语音遥控 / 支持OTA在线智能升级 / 智能空气洗 / 除菌率达99.9%+","product_picture":"public/imgs/appliance/Washer-Pro-10.png","product_price":2999,"product_selling_price":2999,"product_num":20,"product_sales":7},{"product_id":9,"product_name":"小米电视4A 32英寸","category_id":2,"product_title":"人工智能系统，高清液晶屏","product_intro":"小米电视4A 32英寸 | 64位四核处理器 | 1GB+4GB大内存 | 人工智能系统","product_picture":"public/imgs/appliance/MiTv-4A-32.png","product_price":799,"product_selling_price":799,"product_num":10,"product_sales":0},{"product_id":10,"product_name":"小米全面屏电视E55A","category_id":2,"product_title":"全面屏设计，人工智能语音","product_intro":"全面屏设计 | 内置小爱同学 | 4K + HDR | 杜比DTS | PatchWall | 海量内容 | 2GB + 8GB大存储 | 64位四核处理器","product_picture":"public/imgs/appliance/MiTv-E55A.png","product_price":2099,"product_selling_price":1899,"product_num":10,"product_sales":0},{"product_id":11,"product_name":"小米全面屏电视E65A","category_id":2,"product_title":"全面屏设计，人工智能语音","product_intro":"人工智能语音系统 | 海量影视内容 | 4K 超高清屏 | 杜比音效 | 64位四核处理器 | 2GB + 8GB大存储","product_picture":"public/imgs/appliance/MiTv-E65A.png","product_price":3999,"product_selling_price":2799,"product_num":10,"product_sales":0},{"product_id":12,"product_name":"小米电视4X 43英寸","category_id":2,"product_title":"FHD全高清屏，人工智能语音","product_intro":"人工智能语音系统 | FHD全高清屏 | 64位四核处理器 | 海量片源 | 1GB+8GB大内存 | 钢琴烤漆","product_picture":"public/imgs/appliance/MiTv-4X-43.png","product_price":1499,"product_selling_price":1299,"product_num":10,"product_sales":0}]}


*/