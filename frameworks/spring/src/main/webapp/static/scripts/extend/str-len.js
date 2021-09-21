var strLen = (
    function () {
        var trim = function(chars) {
            return (chars || "").replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g, "");
        }
        return function(str, model) {
            str = trim(str);
            model = model || "Ch";
            var _strLen = str.length;
            if (_strLen == 0) {
                return 0;
            } else {
                var chiness = str.match(/[\u4e00-\u9fa5]/g); // 匹配中文
                // 判断是什么模式
                return _strLen + (chiness && model == "Ch" ? chiness.length : 0);
            }
        };


    })();