/** 实用的JavaScript代码段 */
(function ($) {

    // 文档加载完成
    $(function () {
        initPageStyle();
        bindInitEvent();

		f1(f2);
	});
	
	function f1(fn) {
		fn();
	}

	function f2() {
		console.log("1111")
	}

    // 去除字符串左右两边的空格
    function onePointOne(val) {
        var result = val.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g, ""); // 正则替换
        showOutput(result);
    }

    // 验证用户是否输入
    function onePointTwo(val) {
        var isNull = !val.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g, "");
        if (isNull) {
            showOutput("空字符串");
        } else {
            showOutput("非空字符串");
        }
    }

    function onePointThree() {
        $("#input-content").focus(function () {
            $(this).blur();// 控制失去焦点
        });
    }

    function onePointFour() {
        // 创建节点数组
        var arr = [document.getElementById("banInputMethod")],
            self = this;
        for (var i = 0, arrLen = arr.length; i < arrLen; i++) {
            var data = arr[i];
            data.onfocus = function () {
                this.style.imeMode = "disabled";
            }

            var banInputMethod = data.getAttribute("banInputMethod");
            if (banInputMethod) {
                var clearChiness = function (_this) {
                    var val = _this.value;
                    _this.value = val.replace(/[\u4e00-\u9fa5]/g, "");
                }
                data.onkeyup = function () {
                    clearChiness(this);
                }
                data.onblur = function () {
                    clearChiness(this);
                }
            }
        }
    }

    function onePointFive() {
        var banCopyPaste = $("#banCopyPaste")[0];
        console.log(banCopyPaste)
        banCopyPaste.oncopy = function () {
            return false;
        };
        banCopyPaste.onpaste = function () {
            return false;
        };
    }

    function onePointSix() {
        var $banNumber = $("#banNumber"),
            clearNoNumber = function (tThis) {
                var val = tThis.value;
                tThis.value = val.replace(/\D/g, "");
            };
        $banNumber.focus(function () {
            clearNoNumber(this);
        });
        $banNumber.keyup(function () {
            clearNoNumber(this);
        });
        $banNumber.blur(function () {
            clearNoNumber(this);
        });
    }

    function onePointSeven() {
        var $chinessStr = $("#chinessStr"),
            clearNoNumber = function (tThis) {
                var val = tThis.value;
                tThis.value = val.replace(/[^\u4e00-\u9fa5]/g, "");
            };
        $chinessStr.focus(function () {
            clearNoNumber(this);
        });
        $chinessStr.keyup(function () {
            clearNoNumber(this);
        });
        $chinessStr.blur(function () {
            clearNoNumber(this);
        });
    }

    function onePointEight() {
        var $limitLength = $("#limitLength"),
            clearNoNumber = function (tThis) {
                var val = tThis.value,
                    vLen = val.length,
                    dataLength = tThis.getAttribute("data-length"),
                    dataModel = tThis.getAttribute("data-model"),
                    subLen = dataLength;

                if (vLen > dataLength) {
                    tThis.value = val.substr(0, subLen);
                }
            };
        $limitLength.focus(function () {
            clearNoNumber(this);
        });
        $limitLength.keyup(function () {
            clearNoNumber(this);
        });
        $limitLength.blur(function () {
            clearNoNumber(this);
        });
    }

    function onePointNine() {
        var forElementArr = function (_elementArr, callBack) {
            var arr = _elementArr, // 所有节点对象
                self = this; // 外城环境
            if (!(_elementArr instanceof Array)) {
                arr = [_elementArr];
            }
            for (var i = 0, arrLen = arr.length; i < arrLen; i++) {
                var arrI = arr[i];
                if (typeof arrI == "string") {
                    arrI = $(arrI)[0];
                }
                callBack && callBack(i, arrI); // 如果存在回调则执行回调
            }
        },
            showRemainingCharacters = function (_nums, _remainingCharacters) {
                if (_remainingCharacters.search(",") != -1) {
                    _remainingCharacters = _remainingCharacters.split(",");
                }
                forElementArr(_remainingCharacters, function (_index, _this) {
                    $(_this).html((_nums && _nums.toString()) || "0");
                });
            },
            $remainingCharacters = $("#remainingCharacters"),
            clearNoNumber = function (tThis) {
                var val = tThis.value,
                    vLen = val.length,
                    dataLength = tThis.getAttribute("data-length"),
                    dataModel = tThis.getAttribute("data-model"),
                    subLen = dataLength,
                    remainingCharacters = tThis.getAttribute("data-remainingCharacters");

                if (dataModel == "Ch") {
                    vLen = strLen(val, dataModel);
                    var vv = val.match(/[\u4e00-\u9fa5]/g);
                    subLen = dataLength - (!vv ? 0 : vv.length);
                }
                if (vLen > dataLength) {
                    tThis.value = val.substr(0, subLen);
                }
                if (remainingCharacters) {
                    showRemainingCharacters(!vLen ? dataLength : (vLen > dataLength ? 0 : dataLength - vLen), remainingCharacters);
                }
            };
        $remainingCharacters.focus(function () {
            clearNoNumber(this);
        });
        $remainingCharacters.keyup(function () {
            clearNoNumber(this);
        });
        $remainingCharacters.blur(function () {
            clearNoNumber(this);
        });
    }

    function onePointEleven() {
        // 1.placeholder 属性
        // 2.双元素单显示
        var setCss = function (_this, cssOption) {
            if (!_this || _this.nodeType === 3 || _this.nodeType === 8 || !_this.style) {
                console.log("_this");
                return;
            }
            for (var cs in cssOption) {
                _this.style[cs] = cssOption[cs];
            }
            return _this;
        },
            hintInput = $("#hintInput")[0],
            _span = document.createElement("span"),
            dataHint = hintInput.getAttribute("data-hint");
        _span.innerText = dataHint;
        setCss(_span, {
            "position": "absolute",
            "letf": hintInput.offsetLeft + 2,
            "top": hintInput.offsetTop,
            "zIndex": 2
        });
        _span.className = "hintInput";
        hintInput.value = "";
        _span.setAttribute("id", "hint0");
        hintInput.parentNode.insertBefore(_span, hintInput);

        var onhint = function (e) {
            setCss(_span, {
                "display": "none"
            });
            hintInput.focus();
        }

        hintInput.onblur = function (e) {
            // 正则过滤字符判断文本是否为空
            if (!hintInput.value.replace(/^(\s|\u00A0)+|(\s|u00A0)+$/g, "")) {
                setCss(_span, {
                    "distplay": "block"
                })
            }
        }

        _span.onclick = hintInput.onfocus = onhint;
    }

    function onePointTwelve() {
        var rollContent = document.getElementById("rollContent"),
            _div = rollContent.innerHTML,
            setCss = function (_this, cssOption) {
                if (!_this || _this.nodeType === 3 || _this.nodeType === 8 || !_this.style) {
                    console.log("_this");
                    return;
                }
                for (var cs in cssOption) {
                    _this.style[cs] = cssOption[cs];
                }
                return _this;
            };
        rollContent.innerHTML = "<div id='rollContent_roll'>" + _div + "</div>";
        setCss(rollContent, {
            "position": "relative",
            "overflow": "hidden",
            "wordWrap": "break-word",
            "wordBreak": "break-all",
            "width": rollContent.getAttribute("data-rwidth"),
            "height": rollContent.getAttribute("data-rheight")
        });
        var timeRoll = document.getElementById("rollContent_roll"),
            _h = timeRoll.offsetHeight,
            timeoutRoll = function () {
                var _h = timeRoll.offsetHeight,
                    _t = parseInt(timeRoll.style.top, 10),
                    _tt = _h > Math.abs(_t) || _t >= 0 ? _t - 10 : (_h || 0);
                setCss(timeRoll, {
                    "top": _tt + "px"
                });
                setTimeout(timeoutRoll, 200); // 定时调动，模拟动画
            };
        if (_h > rollContent.getAttribute("data-rheight")) {
            timeoutRoll();
            setCss(timeRoll, {
                "position": "relative",
                "top": "0px"
            });
        }
    }

    function onePointThirteen() {
        function setCss(_this, cssOption) {
            // 判断节点类型（节点不存在，或者是文本节点、注释节点，节点样式不存在）
            if (!_this || _this.nodeType === 3 || _this.nodeType === 8 || !_this.style) {
                return;
            }
            for (var cs in cssOption) {
                _this.style[cs] = cssOption[cs];
            }
            return _this;
        }

        function trim(chars) { // 去除字符串左右空格
            return (chars || "").replace(/^(\s|\u00A0)+|(\s|u00A0)+$/g, "");
        }

        function passwordStrength(passwordStrength, showStrength) {
            var self = this;
            /* 字符权重：
            数字1,字母2，其他字符3
            当密码长度小于6时不符合标准；
            长度>=6,强度小于10，强度弱；
            长度>=6,强度>=10 且 < 15，强度中
            长度>=6,强度>=15,强
            */
            passwordStrength.onkeyup = function () {
                var _color = ["red", "yellow", "orange", "green"],
                    msgs = ["密码太短", "弱", "中", "强"],
                    _strength = 0,
                    _v = trim(passwordStrength.value),
                    _vLen = _v.length,
                    i = 0;
                console.log("len :" + _vLen);

                // 计算单个字符强度
                var charStrength = function (char) {
                    if (char >= 48 && char <= 57) {
                        return 1;
                    }
                    if (char >= 97 && char <= 122) {
                        return 2;
                    } else { // 特殊字符
                        return 3;
                    }
                }

                if (_vLen < 6) {
                    showStrength.innerText = msgs[0];
                    setCss(showStrength, {
                        "color": _color[0]
                    });
                } else {
                    for (; i < _vLen; i++) { // 遍历字符
                        _strength += charStrength(_v.toLocaleLowerCase().charCodeAt(i));
                    }
                    if (_strength < 10) {
                        showStrength.innerText = msgs[1];
                        setCss(showStrength, {
                            "color": _color[1]
                        });
                    }
                    if (_strength >= 10 && _strength < 15) {
                        showStrength.innerText = msgs[2];
                        setCss(showStrength, {
                            "color": _color[2]
                        });
                    }
                    if (_strength > 15) {
                        showStrength.innerText = msgs[3];
                        setCss(showStrength, {
                            "color": _color[3]
                        });
                    }
                }
            }
        }

        passwordStrength(document.getElementById("passwordStrength"),
            document.getElementById("showStrength"));
    }

    function onePointFourteen() {
        document.getElementById("enterSubmit").onkeyup = function (e) {
            console.log("event :" + e);
            // 获取事件对象
            e = e || window.event;
            // 获取键码
            var keycode = e.keyCode || e.which || e.charCode;
            if (keycode === 13) {
                alert("回车提交成功")
            }
        }
    }

    function onePointSeventeen() {
        document.getElementById("autoSelected").select();
    }

    function onePointEighteen() {
        var strToJson = function (str) { // 字符串转JSON对象
            return typeof JSON == "object" ? JSON.parse(str) :
                (new Function("return " + str))();
        },
            setCss = function (_this, cssOption) {
                // 判断节点类型（节点不存在，或者是文本节点、注释节点，节点样式不存在）
                if (!_this || _this.nodeType === 3 || _this.nodeType === 8 || !_this.style) {
                    return;
                }
                for (var cs in cssOption) {
                    _this.style[cs] = cssOption[cs];
                }
                return _this;
            },
            autoUpdateCss = document.getElementById("autoUpdateCss"),
            fCss = autoUpdateCss.getAttribute("data-fCss"),
            fClass = autoUpdateCss.getAttribute("data-fClass"),
            bClass = autoUpdateCss.getAttribute("data-bClass"),
            bCss = autoUpdateCss.getAttribute("data-bCss");

        autoUpdateCss.onfocus = function () {
            fCss && setCss(this, strToJson(fCss));
            fClass && (this.className = fClass);
        }

        autoUpdateCss.onblur = function () {
            bCss && setCss(this, strToJson(bCss));
            bClass && (this.className = bClass);
        }
    }

    function onePointNighteen() {
        var getRegular = function (rstr) {
            var regData = {}; // 正则表达式存储域
            regData.rtrim = /^(\s|\u00A0)+|(\s|\u00A0)+$/g; //去除空格的正则
            regData.Chiness = /[\u4e00-\u9fa5]/g; // 中文
            regData.nonumber = /\D/g; // 数字
            regData.nochiness = /[^\u4e00-\u9fa5]/g; // 非中文
            regData.email = /^\s*[a-zA-Z0-9]+(([\._\-]?)[a-zA-Z0-9]+)*@[a-zA-Z0-9]+([_\-][a-zA-Z0-9]+)*(\.[a-zA-Z0-9]+([_\-][a-zA-Z0-9]+)*)+\s*$/; // 邮件
            regData.phone = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,})){0,}$/; // 电话
            regData.decimalNumber = /^\d+(\.\d+)+$/; // 带小数位的数字
            regData.htmlTags = /<[\/\!]*[^<>]*>/ig; // html
            return regData[rstr];
        },
            forElementArr = function (_elementArr, callBack) {
                var arr = _elementArr,
                    self = this;
                if (!(_elementArr instanceof Array)) {
                    arr = [_elementArr];
                };
                for (var i = 0, arrLen = arr.length; i < arrLen; i++) {
                    var arrI = arr[i];
                    if (typeof arrI == "string") {
                        arrI = document.getElementById(arrI);
                    }
                    callBack && callBack(i, arrI); // 如果存在回调
                }
            },
            verification = function (str, reg) {
                return getRegular(reg).test(str);
            },
            setCss = function (_this, cssOption) {
                // 判断节点类型（节点不存在，或者是文本节点、注释节点，节点样式不存在）
                if (!_this || _this.nodeType === 3 || _this.nodeType === 8 || !_this.style) {
                    return;
                }
                for (var cs in cssOption) {
                    _this.style[cs] = cssOption[cs];
                }
                return _this;
            };
        forElementArr([
            document.getElementById("regUser"),
            document.getElementById("regEmail"),
            document.getElementById("regPhone"),
            document.getElementById("regNumber")
        ], function (index, _this) {
            _this.onkeyup = function () {
                // 获取被处理的元素值
                var _v = this.value.replace(getRegular("rtrim"), ""),
                    _reg = this.getAttribute("data-reg"),
                    _regArr = _reg.indexOf(",") > 0 ? _reg.split(",") : [_reg],
                    _regLen = _regArr.length,
                    _emsg = this.getAttribute("data-emsg"),
                    _smsg = this.getAttribute("data-smsg"),
                    // 获取显示信息的元素
                    _target = document.getElementById(this.getAttribute("data-tmsg")),
                    i = 0;
                for (; i < _regLen; i++) {
                    if (!verification(_v, _regArr[i])) {
                        _target.innerHTML = _emsg;
                        setCss(_target, {
                            "color": "red"
                        });
                        return;
                    }
                }
                _target.innerHTML = _smsg;
                setCss(_target, {
                    "color": "green"
                });
            }
        });
    }

    function onePointTwenty() {
        var _keyWordFiltering = $("#keyWordsFiltering")[0];
        _keyWordFiltering.onclick = function () {
            var keyWordsLibs = [
                "JavaScript",
                "美女",
                /[外]{1}.{0,3}[挂]{1}/
            ],
                keyWordsLibsLen = keyWordsLibs.length;
            for (var i = 0; i < keyWordsLibsLen; i++) {
                _keyWordFiltering.value = _keyWordFiltering.value.replace(keyWordsLibs[i], "***");
            }
        };
    }

    function onePointTwentyOne() {
        var autoRow = $("#autoRow")[0];
        autoRow.style.overflowY = "hidden"; // Y 轴是否超过隐藏
        autoRow.onkeyup = function () {
            $("#autoRow").height(autoRow.scrollHeight);
        };
    }

    function onePointTwentyTwo() {
        var $addOptions = $("#addOptions"),
            $addOption = $("#addOption"),
            addOptions = function (target, options) {
                var _option = null,
                    ol = options.length,
                    i = 0,
                    _v = "",
                    _t = "";
                for (; i < ol; i++) {
                    _v = options[i].value;
                    _t = options[i].text;
                    _option = document.createElement("option");
                    _option.value = _v;
                    _option.text = _t;
                    target.options.add(_option);
                }
            };

        $addOptions.click(function () {
            addOptions($addOption[0], [{
                "value": "新元素",
                "text": "新元素",
            }])
        });
    }

    function onePointTwentyThree() {
        var linkDatas = {
            provinces: [
                {
                    "code": "0",
                    "name": "请选择"
                }
                ,
                {
                    "code": "1",
                    "name": "北京"
                }
            ],
            citys: {
                0: [
                    "请选择"
                ],
                1: [
                    "朝阳区",
                    "海淀区",
                    "东城区",
                    "西城区",
                    "其他"
                ],
                2: [
                    "天津"
                ]
            }
        };
        function addOptions(target, options) {
            var _option = null,
                ol = options.length,
                i = 0,
                _v = "",
                _t = "";
            for (; i < ol; i++) {
                _v = options[i].value;
                _t = options[i].text;
                _option = document.createElement("option");
                _option.value = _v;
                _option.text = _t;
                target.options.add(_option);
            }
        };

        function linkage(parents, childs) {
            var _linkDatas = linkDatas,
                _parents = linkDatas.provinces,
                _childs = linkDatas.citys,
                _initCity = _childs[0],
                _p = [];

            for (var i in _parents) {
                _p.push({
                    "text": _parents[i].name,
                    "value": _parents[i].code
                });
            }

            addOptions(_parents, _p);

            addOptions(childs, [
                {
                    "value": _initCity,
                    "text": _initCity
                }
            ]);

            parents.onchange = function () {
                var __childs = _childs[this.value],
                    __childsLen = __childs.length,
                    j = 0,
                    __p = [];
                childs.innerHTML = "";
                for (; j < __childsLen; j++) {
                    __p.push({
                        "value": __childs[j],
                        "text": __childs[j]
                    });
                }
                addOptions(childs, __p);
            }
        }

        linkage(
            document.getElementById("provinces"),
            document.getElementById("citys")
        );
    }

    /**
     * 控制台的使用方式
     */
    function consoleUsage() {
        console.time("times");
        console.profile();

        console.log("1");
        console.log(4, 3, { 1: "one" });
        console.warn("warn");
        console.error("error");
        console.clear();
        console.trace();
        console.dir({ test: 1, test2: 2 });
        /* keys({ test: 1, test2: 2 });
        values({ test: 1, test2: 2 }); */

        // 统计方法执行的结果，生成报表
        console.profileEnd();
        console.timeEnd("times");
    }

    /**
     * 绑定默认事件行为
     */
    function bindInitEvent() {
        $(document).on("click", ".content ul", function () {
            $(this).siblings().children().addClass("hidden");
            $(this).children().removeClass("hidden");
        });

        $(document).on("click", ".content li", function () {
            var methodName = $(this).attr("name");
            eval(methodName + '("' + $("#input-content").val() + '")');
        });
    }

    /**
     * 页面初始化样式
     */
    function initPageStyle() {
        // 显示第一章
        $(".content ul:first").nextAll().children().addClass("hidden");
    }

    // 输出
    function showOutput(result) {
        $("#msg").html("[" + result + "]");
    }

})(jQuery);