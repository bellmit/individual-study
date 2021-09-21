function getFieldListForShow(columns) {
  return columns.filter(item => item.isShow);
}

function getFieldListForAdd(columns) {
  return columns.filter(item => item.isAdd);
}

export default {
  getFieldListForShow,
  getFieldListForAdd,
}