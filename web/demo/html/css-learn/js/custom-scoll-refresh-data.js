// 文档加载完成触发事件
$(function(){
    initData(mockData);

    $(".content").scroll(function() {
        // console.log("滚动条位置距页面顶部的距离:" + $(document).scrollTop());
        // console.log("整个页面的高度：" + $(document).height());
        // console.log("当前可见窗口的高度：" + $(window).height());
        // alert("Dom 对象：" + this);
        // alert("JQuery 对象:" + $(this));
        var $content = $(this);
        // div 高度
        console.log("div 高度：" + $(this).height());
        console.log("元素的整体高度：" + $(this)[0].scrollHeight
            + ", 距离顶部位置：" + $(this)[0].scrollTop);

        if ($(this).scrollTop() <= 0) {
           alert("滚动条到达顶部");
        }

        //滚动条位置距页面顶部的距离 = 文档的总高度 - 当前窗口的高度时，就意味着已经滚动了一个窗口的高度，及已经到当前窗口的底部
        //判断是否已经滚动到页面底部
        if (($content.height() + $content[0].scrollTop) === $content[0].scrollHeight) {
            alert("滚动到文档底部，加载新的20条数据");
            initData(mockData);
        }
            
    });

    // 回到距离顶部的位置
    $("#btn").click(function() {
        $(".content").scrollTop(5);
    });
});

// 初始化数据
function initData(mockData) {
    var num = 1000;
    for (var i = 0 ; i < 20 ; i ++) {
        temp = "<div class='data'>" 
            + renderData(num + i)
            + renderData(mockData[0].name + i)
            + renderData(mockData[0].age)
            + renderData(mockData[1].birth)
            + "</div>";
        $(".content").append(temp);
    }
}

function renderData(data) {
    return "<div class='column'>" + data + "</div>";
}

var mockData = [
    {
        "order":"20191001",
        "name":"张三",
        "age": 34,
        "birth": "1993年8月27"
    },
    {
        "order":"20191002",
        "name":"李四",
        "age": 34,
        "birth": "1993年8月27"
    }
];