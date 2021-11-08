// 闭包写法
(function () {
  // 文档加载完成后执行
  window.onload = function () {
    // base();
    // 流程控制
    // processControl();
    // 内置对象
    // innerObject();
    // 自定义对象
    // customObject();

    // 定时器
    bindTimer();

    iframeControll();
  };
})();

// 一、基础
function base() {
  var iNum = 10;
  var bFlag = true;
  var aArr = [];
  var fNum = 1.5;
  var fnCalc = function () { }
  var sName = "guanyw";
  var oNull = null;
  var oUndefined = undefined;
  // 类型判断
  print(typeof iNum);
  print(typeof bFlag);
  print(typeof aArr);
  print(typeof fNum);
  print(typeof fnCalc);
  print(typeof sName);
  print(typeof oNull);
  print(typeof oUndefined);
  print((typeof oUndefined) == undefined); // false
  print((typeof oUndefined) == "undefined"); // true

  // 特殊number
  var iResult = Number.MAX_VALUE + 1;
  print(isFinite(iResult));
  // 非数字转换NaN(第一位非数字)
  print(typeof ("90a" + 1));
  print(isNaN("blue"));
  print("String -> Float :" + parseFloat("a10a"));
}

// 流程控制
function processControl() {
  /* if */

}

// 内置对象
function innerObject() {
  /* 时间对象 */
  var date = new Date();
  console.log(typeof (date));
  console.log(date);
  print("当前时间：" + DateUtil.getCurrentDateTime(new Date()));
  /* 数组对象
    常用方法：
    join() : 连接
    reverse()：数组反转
  */
  var arr = ["java", ".net", "our"];
  print(arr.join("-"));
}

// 自定义对象
function customObject() {
  //定义人对象
  function Person(name, age) { //this: 表示当前调用的对象
    //定义属性  赋值
    this.name = name;
    this.age = age;

    //定义方法
    this.say = function () {
      alert("这个对象的方法");
    }
  }

  var person = new Person("lisi", 30);
  print("属性：" + person.name + ":" + person["age"]);
  person.say();

  /*
  定义对象方式二： 无参数的构造函数
  //定义对象
  function Person(){
  }
  //创建对象
  var p = new Person();
  //追加属性
  p.name = "狗剩";
  p.age = 14;
  //追加方法
  p.say = function(){
    alert("狗剩的函数");	
		alert("狗剩的函数");	
    alert("狗剩的函数");	
		alert("狗剩的函数");	
    alert("狗剩的函数");	
  }
  */


  /*
  定义对象的方式三：使用Object对象。 Object对象可以作为任意对象的模板
  //创建对象
  var p = new Object();
  //追加属性
  p.name = "狗蛋";
  p.age = 16;
  //追加方法
  p.say = function(){
    alert("狗蛋的函数");	
		alert("狗蛋的函数");	
    alert("狗蛋的函数");	
		alert("狗蛋的函数");	
    alert("狗蛋的函数");	
  }
  */


  /*
  定义对象方式四：使用字面量的形式创建对象。 json语言（了解）
  */
  //创建人对象
  var p = {
    //定义属性（属性名：属性值）
    "name": "铁蛋",
    "age": 20,
    //定义方法
    "say": function () {
      alert("铁蛋的函数");
    }
  };
}

// 正则表达式学习
function regExpTest() {

}

// iframe 获取父类容器
function iframeControll() {
  var leftDom = window.parent.document.getElementById("left").contentWindow.document;
  var dlEle = leftDom.getElementsByTagName("dl")[0];
  console.log(dlEle.length);
  var ddEle = leftDom.createElement("dd");
  ddEle.innerHTML = "<a>测试时</a>"
  dlEle.appendChild(ddEle);
  console.log(leftDom);
}

function test1() {
  // 局部变量，方法执行完销毁
  var msg = "msg";
  document.getElementById(msg).innerHTML = "点击事件2";
}

/* let 关键字使用
区别：
let 声明的变量只在其声明的块或子块中可用
var 声明的变量作用域是整个封闭函数
 */
function varTest() {
  var x = 1;
  if (true) {
    var x = 2;  // 同样的变量!
    console.log(x);  // 2
  }
  console.log(x);  // 2
}

function letTest() {
  let x = 1;
  if (true) {
    let x = 2;  // 不同的变量
    console.log(x);  // 2
  }
  console.log(x);  // 1
}

function bindTimer() {
  window.setInterval("setCurTime()", 1000);
}
function setCurTime() {
  document.getElementById("dateTip").innerHTML = DateUtil.getCurrentDateTime();
}

/**
 * 日期工具类
 */


// console 打印
function print(val, prop) {
  if (prop) {
    console.log(prop, val);
    return;
  }
  console.log(val);
}