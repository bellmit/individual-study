const mutationsName = {
  SET_USER_CURRENT: {
    key: "SET_USER_CURRENT",
    domain: "user"
  },
}

const getMutationType = function (obj) {
  return obj.domain + "/" + obj.key;
}

export {
  mutationsName,
  getMutationType
}