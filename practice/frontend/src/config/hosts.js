const getEnv = () => {
  /**
   * 这里写判断环境代码，
   * 最终返回对应的环境标识
   */
  return "prod";
};

const env = getEnv();

// 不同环境标识输出不同 host
export const douban = {
  prod: "",
  pre: "",
  test: ""
}[env];