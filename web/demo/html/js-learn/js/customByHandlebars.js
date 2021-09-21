(function ($) {

    var data = {
        "name": "guanyw",
        "age": "23岁",
        "friend": {
            "name": "wangdy",
            "age": "25岁"
        },
        "list": [
            { "name1": "张三", "age1": "31" },
            { "name1": "王五", "age1": "31" },
            { "name1": "李四", "age1": "31" }
        ],
        "person": {
            "firstName": "jogn",
            "lastName": "sms"
        }
    };

    var data1 = {
        info: ['HTML5', 'CSS3', "WebGL"],
        list: [
            { "name1": "张三", "age1": "31" },
            { "name1": "王五", "age1": "31" }
        ],
        "error": "数据取出错误"
    };

    var data2 = {
        "student": [
            {
                "name": "张三",
                "sex": "0",
                "age": 18
            },
            {
                "name": "李四",
                "sex": "0",
                "age": 22
            },
            {
                "name": "妞妞",
                "sex": "1",
                "age": 18
            }
        ]
    };

    $(function () {
        // $("#msg").handlebars($("#msg"), data);
        // $("#msg2").handlebars($("#msg2"), data1);
        // $("#msg3").handlebars($("#msg3"), data1);
        $("#msg4").handlebars($("#msg4"), data2);
    });

    // 简单使用,基本语法 {{value}}，和table 标签有bug
    function simpleUse() {
        // 获取模版
        var tpl = $("#msg").html();
        console.log("tpl" + tpl);
        // 预编译模版
        var template = Handlebars.compile(tpl);
        // 匹配json内容
        var html = template(data);
        alert(html);
        // 输入模版
        $("#msg").html(html);
    }


})(jQuery);

// 自定义helper
Handlebars.registerHelper("debug", function (optionalValue) {
    console.log("Current Context");
    console.log("====================");
    console.log(this);
    if (optionalValue) {
        console.log("Value");
        console.log("====================");
        console.log(optionalValue);
    }
});

// handlebars的jquery插件
(function ($) {
    var compiled = {};
    $.fn.handlebars = function (template, data) {
        if (template instanceof jQuery) {
            template = $(template).html();
        }
        compiled[template] = Handlebars.compile(template);
        console.log(compiled[template](data));
        this.html(compiled[template](data));
    };
})(jQuery);