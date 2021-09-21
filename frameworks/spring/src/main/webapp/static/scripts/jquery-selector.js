(function ($) {

    $(function () {
        hideOtherElement();
        // levelSelector();
        // filterSelector();
        // formFilterSelector();
        // websiteBrandDemo();
        // domOperate();
        // alinkPrompt();
        // eventTest();
        // animateTest();
        // filmShow();
        formAndTable();
    });
    // 表单和表格
    function formAndTable() {
        // 输入框
        $(".formAndTable :input:not(:checkbox)").focus(function() {
            $(this).addClass("focus");
        }).blur(function() {
            $(this).removeClass("focus");
        });

        // 多行文本框
        var $comment = $("#comment");
        $("span.bigger").click(function() {
            if (!$comment.is(":animated")) {
                if ($comment.height() < 500) {
                    // 重新设置高度，在原来的基础上加50
                    // $comment.height($comment.height() + 50);
                    // $comment.animate({height:"+=50"}, 400);
                    $comment.animate({scrollTop:"+=50"}, 400);
                }
            }
        });
        $("span.smaller").click(function() {
            if (!$comment.is(":animated")) {
                if ($comment.height() > 50) {
                    // 重新设置高度，在原来的基础上-50
                    // $comment.height($comment.height() - 50);
                    // $comment.animate({height:"-=50"}, 400);
                    $comment.animate({scrollTop:"-=50"}, 400);
                }
            }
        });

        // 复选框，全选和全不选
        $("#checkAll").click(function() {
            $("[name='items']:checkbox").attr("checked", true);
        });
        $("#checkNo").click(function() {
            $("[name='items']:checkbox").attr("checked", false);
        });
        $("#checkRev").click(function() { // 反选
            $("[name='items']:checkbox").each(function() {
                // console.log($(this).attr("checked"));
                // $(this).attr("checked", !$(this).attr("checked"));
                this.checked = !this.checked;
            });
        });
        $("#send").click(function() {
            var str = "你选中的是：\r\n";
            $("[name='items']:checkbox:checked").each(function() {
                str += $(this).val() + "\r\n";
            });
            alert(str);
        });
        $("#checkAll2").click(function() {
            // if (this.checked) {
            //     $("[name='items']:checkbox").attr("checked", true);
            // } else {
            //     $("[name='items']:checkbox").attr("checked", false);
            // }
            $("[name='items']:checkbox").attr("checked", this.checked);
        });
        // 复选框 关联 全选、全不选
        $("[name='items']:checkbox").click(function() {
            var flag = true;
            $("[name='items']:checkbox").each(function() {
                if (!this.checked) {
                    flag = false;
                }
                $("#checkAll2").attr("checked", flag);
                // $("#checkAll2").prop("checked", flag);
            });
        });
    }

    // 视频展示效果
    function filmShow() {
        var page = 1; // 第一个版面
        var i = 4; // 每版放4个图片，如果8个，2个版面；如果9个，3个版面
        $("div.change-btn span.next").click(function() {
            var $parent = $(this).parents("div.v-show");
            var $vshow = $parent.find("div.v-content-list");
            var $vcontent = $parent.find("div.v-content");
            var vWidth = $vcontent.width(); // 获取区域内容宽度
            console.log(vWidth);
            var len = $vshow.find("li").length; // 总的视频数
            var pageCount = Math.ceil(len / i); // 向上取整

            if (!$vshow.is(":animated")) { // 判断是否已经在动画里了
                if (page == pageCount) { // 到达最后一个版面，再往后，就到第一个版面
                    $vshow.animate({left:"0px"}, "nomal"); // 通过改变left值，跳转到第1版面
                    page = 1;
                } else {
                    $vshow.animate({left:"-=" + vWidth}, "nomal"); // 通过改变left值，达到每次换一个版面
                    page ++;
                }
    
                $parent.find("span").eq((page - 1)).addClass("current")
                    .siblings().removeClass("current");
            }
        });
    }

    // 动画
    function animateTest() {
        var i = 0;
        $("div.animate button").on("click", function() {
            if (i == 0) {
                // $(this).next("div").hide();
                // $(this).next("div").fadeOut();
                $(this).next("div").slideUp();
                i ++;
            } else {
                // $(this).next("div").show();
                // $(this).next("div").fadeIn();
                $(this).next("div").slideDown();
                i --;
            }
        });

        // $("div.animate #pannel").animate({left:"500px"}, 3000);
        // $("div.animate #pannel").animate({left:"+=500px"}, 300);
        $("div.animate #pannel").animate({left:"500px"}, 3000)
            .animate({height:"200px"}, 300);
        $("div.animate #pannel").on("click", function() {
            $(this).animate({left:"400px",height:"200px",opacity:"1"}, 3000)
                .animate({top:"200px",width:"200px"}, 3000, function() {
                    $(this).css("boder", "5px solid blue");
                });
        });
    }

    // 事件
    function eventTest() {
        // 连续点击事件
        var i = 0;
        $("#toggle-btn").on("click", function(event) {
            var $prevNode = $(this).prev();
            if (i == 0) {
                $prevNode.hide();
                $prevNode.removeClass("highlight");
                i ++;
            } else {
                $prevNode.show();
                $prevNode.addClass("highlight");
                i --;
            }
            // $(this).prev().toggle();
        });
        // 事件冒泡
        $("div.eventBubble .outer").on("click", function(event) {
            alert("outer div");
        });
        $("div.eventBubble .inner").on("click", function(event) {
            alert("inner div");
            event.stopPropagation(); // 阻止事件冒泡
        });
        $("#event-bubble-btn").on("click", function(event) {
            alert("event-bubble-btn button");
            console.log(event.type);
            console.log(event.which);

            event.stopPropagation(); // 阻止事件冒泡
            event.preventDefault(); // 阻止默认行为（表单提交，超链接）
        });
        // 获取键盘按键，Ascii 码值
        $("div.inner .input-text").keyup(function(event) {
            alert(event.which);
        });
        // 模拟事件自动发生
        $("#event-bubble-btn").trigger("click");
        // 触发自定义事件
        $("#event-bubble-btn").on("customClick", function() {
            console.log("customClick");
        });
        $("#event-bubble-btn").trigger("customClick");

        // 事件命名空间
        $(".namespace .outer").on("click.plugin", function() {
            $(this).append("<p>click事件</p>");
        });
        $(".namespace .outer").on("dbclick.plugin", function() {
            $(this).append("<p>dbclick事件</p>");
        });
        $(".namespace .outer").on("mouseover.plugin", function() {
            $(this).append("<p>mouseover事件</p>");
        });

        $(".namespace #namespace-btn").on("click", function() {
            $("div.namespace").append("<p>click事件</p>");
        });
        $(".namespace #namespace-btn").on("click.plugin", function() {
            $("div.namespace").trigger("click!");
        });

    }

    // 超链接提示效果
    function alinkPrompt() {
        var div = document.createElement("div");
        $("a.tooltip").on("mouseover", function (event) {
            this.myTitle = this.title;
            this.title = "";
            $(div).html(this.myTitle)
                .attr("id", "tooltip")
                .appendTo($("div.link-prompt")); // 追加到文档中
            $("#tooltip").css({
                "top": (event.pageY + 20) + "px",
                "left": (event.pageX + 20) + "px"
            }).show("fast"); // 设置x、y坐标并显示
        }).on("mouseout", function (event) {
            $("#tooltip").remove();
            this.title = this.myTitle;
        }).on("mousemove", function(event) {
            console.log("x :" + event.pageX + " y: " + event.pageY);
            $("#tooltip").css({
                "top": (event.pageY + 20) + "px",
                "left": (event.pageX + 20) + "px"
            });
        });
    }

    // DOM 操作
    function domOperate() {
        var $li = $("div.jquery-dom ul>li");
        // findNode();
        // createNode();
        // removeNode();
        // copyNode($li);
        // wrapNode();
        // fetchContent();
        // foreachSub();
        cssAttr();
    }
    // 查找节点
    function findNode() {
        $(".jquery-dom ul>li:eq(1)").css("background", "#bbffaa");
        console.log($(".jquery-dom").attr("title"));
    }
    // 创建节点
    function createNode() {
        var $li1 = $("<li></li>");
        var $li2 = $("<li></li>");
        var $li3 = $("<li>非相机</li>");
        $("div.jquery-dom ul").append($li1);
        $("div.jquery-dom ul").append($li2);
        $li3.prependTo($("div.jquery-dom ul"));
        var $li4 = $("<li>----------</li>");
        $("div.jquery-dom ul>li").after($li4);
        var $li5 = $("<li>******</li>");
        $("div.jquery-dom ul>li:contains('松下')").before($li5);
    }
    // 删除节点
    function removeNode() {
        var $removeLis = $("div.jquery-dom ul>li:even").remove();
        $("div.jquery-dom ul>li").remove("li:contains('松下')");
        $("div.jquery-dom ul>li:first").empty();
    }
    // 复制节点
    function copyNode($li) {
        console.log($li);
        $li.on("click", function (event) {
            $(this).clone(true).appendTo($(this).parent("ul"));
        });
    }
    // 包裹节点
    function wrapNode($li) {
        // $("div.jquery-dom ul>li:first").wrap("<b></b>");
        // $("div.jquery-dom ul>li:not(:first)").wrapAll("<i></i>");
        $("div.jquery-dom ul>li").wrapInner("<b></b>");
    }
    // 获取文本值
    function fetchContent() {
        var val;
        $(".jquery-dom #email").on("focus", function () {
            val = $(this).val();
            if ("请输入邮箱" == val) {
                $(this).val("");
            }
        }).on("blur", function () {
            $(this).val(val);
        });

    }
    // 遍历节点子集合
    function foreachSub() {
        var $lis = $(".jquery-dom ul").children("li");
        console.log($lis.length);

        console.log($(".jquery-dom ul").next());
        console.log($(".jquery-dom form").prev());
        $(document).on("click", function (event) {
            $(event.target).closest("li").css("color", "red");
        });
    }
    // CSS 相关dom
    function cssAttr() {
        var $dom = $(".jquery-dom");
        console.log($dom.height());
        console.log($dom.width());
        console.log($dom.offset());
    }

    // 层次选择器
    function levelSelector() {
        // $("body div").css("background", "#bbffaa");
        // $("body>div").css("background", "#bbffaa");
        // $("#one+div").css("background", "#bbffaa");
        // $("#one~div").css("background", "#bbffaa"); // 后面的同辈
        // $("#one").siblings().css("background", "#bbffaa"); // 所有同辈
    }

    // 过滤选择器
    function filterSelector() {
        // $("div:first").css("background", "#bbffaa");
        // $("div:not([title='test'])").css("background", "#bbffaa");
        $(":animated").css("background", "#bbffaa");
        $(":focus").css("background", "#bbffaa");
        // $("div:contains(di)").css("background", "#bbffaa");
        // $("div:empty").css("background", "#bbffaa");
        // $("div:has('.mini')").css("background", "#bbffaa");
        // $("div:visible").css("background", "#ff6500");
        $("div:hidden").show(3000);
    }
    // 表单过滤器/选择器
    function formFilterSelector() {
        console.log($("input:checked").length);
        console.log($("select:selected").text());
        var items = $(":checkbox:checked");
        $("select+div").html("checkbox 个数：" + items.length);
    }

    // 网站品牌展示特效
    function websiteBrandDemo() {
        // 从第三个开始隐藏，除了最后一个
        var $category = $("ul li:gt(2):not(:last)");
        $category.hide();

        $("div.showmore>a").on("click", function () {
            if ($category.is(":visible")) {
                $category.hide();
                $(this).find("span").text("显示全部品牌列表");
                $("ul li").removeClass("highlight");
            } else {
                $category.show();
                $(this).find("span").text("精简品牌列表");

                $("ul li").filter(":contains('三星'),:contains('松下')")
                    .addClass("highlight");
            }
            return false; // 取消默认行为
        });
    }

    // 隐藏第一个div后面的所有元素
    function hideOtherElement() {
        // $("div:first~*").hide();
        // $("h2:not(:first)~div").hide();
        $("h2~div").hide();
        $("h2").on("click", function() {
            $(this).next("div").show()
                .end()
                .siblings("h2").next("div").hide();
        });
    }

})(jQuery);