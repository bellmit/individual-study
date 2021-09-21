(function (window) {
    function DateUtil() {
        this.getCurrentDateTime = function (date) {
            if (!date) {
                date = new Date();
            }
            return date.getFullYear() + "月" + (date.getMonth() + 1) + "月" + date.getDate() + "日 "
                + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        }
    }

    window.DateUtil = new DateUtil();
    
})(window);