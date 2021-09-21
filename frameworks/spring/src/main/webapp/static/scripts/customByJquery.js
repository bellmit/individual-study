// 全局变量
// 循环标志
var repeatFlag = 0;

/* document文档构建完成时执行
多个函数，从上到下加载
*/
$(function(){
    console.log(Slider1);
    console.log(Slider1.Enabled);
    
});
$(function(){
    
    // convertJQueryToDom1();
    // convertJQueryToDom2();
    convertDomToJQuery();
    // 折叠事件(折叠)
    // $("#basicInfo>p>ul:")
    // class时间
    bindAddEvent();
    // console.log(">>" + text);
    // 绑定2次事件，不是覆盖，而是事件链；
    // bindAlertEvent("1");
    // bindAlertEvent("2");
    // 单击事件，循环
    $("#change-btn").click(function(){
        $("body").css("background-color","green");},
        function(){
        $("body").css("background-color","red");},
        function(){
        $("body").css("background-color","yellow");}
        );

});

// jQuery 和 document 互相转换
/* jQuery 转 document
    方法一：jQuery对象是一个数组对象，通过[index]获取
*/
function convertJQueryToDom1() {
    // jQuery 对象
    var $msg = $("#msg");
    // 转换为DOM对象
    var msgDiv = $msg[0];
    // 使用DOM对象方法
    msgDiv.onclick = function() {
        alert("dom object1");
    }
}
/* jQuery 转 document
    方法二：jQuery对象是一个数组对象，通过get(index)获取
*/
function convertJQueryToDom2() {
    // jQuery 对象
    var $msg = $("#msg");
    // 转换为DOM对象
    var msgDiv = $msg.get(0);
    // 使用DOM对象方法
    msgDiv.onclick = function() {
        alert("dom object2");
    }
}
/* document 转 jQuery
    $() 包装DOM对象，获取jQuery对象
*/
function convertDomToJQuery() {
    // DOM 对象
    var msg = document.getElementById("msg");
    // 转换为jQuery对象
    var $msgDiv = $(msg);
    // 使用DOM对象方法
    $msgDiv.click(function(){
        alert("jQuery object");
    })
}
/* 不可见 */
function nonVisible(element) {
    element.style.display = "none";
}
function visible(element) {
    // 还原成默认值
    element.style.display = "";
}
// 事件绑定方法
function bindAddEvent() {
    var text = 10 / 0;
    console.log("局部变量：" + text);
    $("#selectList .item").click(function() {
        if ($(this).hasClass("item")) {
            $(this).removeClass("item");
            console.log("移除class item属性");
        } else {
            $(this).addClass("item");
            console.log("add class [item]");
        }
    });

    $("#basicInfo p").on("click", function() {
        $(this).children().removeClass(".hidden");
    });

    // toggle 事件在1.8+版本已经被删除
    $(".title").click(function() {
        console.log("qqq");
        if (repeatFlag === 0) {
            $("div.content").removeClass("hidden");
            repeatFlag = 1;
        } else {
            $("div.content").addClass("hidden");
            repeatFlag = 0;
        }
    });

    var btn = $("#btn")[0];
    var arrays = new Array();
    var items = $("[name='check']");
    $("#btn").click(function() {
        console.log(items.val());
    });
}