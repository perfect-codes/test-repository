(function($) {
    /**
     * 表单数据填充
     * @param obj 数据对象
     * @returns {jQuery}
     */
    $.fn.fillForm = function(obj) {
        obj = obj || {};
        this.find("input,select,textarea").each(function(i) {
            var el = this;
            if (el.attr("name") == null) {
                el.val(obj[el.attr("id")]);
            } else {
                el.val(obj[el.attr("name")]);
            }
        });
        this.find("img[id]").each(function(i) {
            var el = $(this);
            el.attr("src", obj[el.attr('id').split('_')[0]]);
        });
        return this;
    };
    /**
     * 表单序列化为JSON
     * @param obj 附加对象，可不填
     * @returns {string}
     */
    $.fn.handleForm = function(obj) {
        obj = obj || {};
        if (this.selector != "body") {
            var array = this.serializeArray();
            for (var i in array) {
                obj[array[i].name] = array[i].value;
            }
        }
        return JSON.stringify(obj);
    };
    //TODO 需要结合项目UI框架完善
    $.messager = {
        error: function() {},
        alert: function(msg, ok) {},
        confirm: function(msg, ok, cancel) {},
        popup: function(title, content) {},
        loading: function() {},
        actions: function() {}
    };
    /**
     * 浏览器存储方法
     * @type {{save: jQuery.store.save, get: jQuery.store.get}}
     */
    $.store = {
        save: function(key, value) {
            if (typeof(value) != "string") {
                localStorage.setItem(key, JSON.stringify(value));
            } else {
                localStorage.setItem(key, value);
            }
        },
        get: function(key) {
            if (key) {
                if (localStorage.getItem(key) != null) {
                    return JSON.parse(localStorage.getItem(key))
                }
            }
            return null;
        }
    };
    /**
     * 退出方法，清除浏览器存储内容
     */
    $.logout = function() {
        localStorage.clear();
        window.location.href = "login.html";
    };
})(jQuery)