/**
 * 基础组件库
 * powered by flym
 */
$.ajaxSettings.traditional = true;
$(document)["ajaxError"](function (e, xhr) {
	Base.exceptionHandle && Base.handleException(xhr);
});
$(document)["ajaxSuccess"](function (e, xhr) {
	Base.postDebug && alert(xhr.responseText);
});
$(document)["ajaxSend"](function (e, xhr, s) {
	Base.preDebug && alert(s.data);
});

var Base = {
	preDebug: false,
	postDebug: false,
	exceptionHandle: true,
	isArray: function (object) {
		return object && object.constructor === Array;
	},
	isFunction: function (object) {
		return typeof object == "function";
	},
	isString: function (object) {
		return typeof object == "string";
	},
	isNumber: function (object) {
		return typeof object == "number";
	},
	isUndefined: function (object) {
		return typeof object == "undefined";
	},
	handleException: function (exception) {
		var errorDivJquery = $("#_errorDiv");
		if(!errorDivJquery.length) {
			errorDivJquery = $("<div></div>").attr("id", "_errorDiv").css("position", "absolute").css("z-indx",
					99999).css("background", "#aaaaaa").css("top", "30px").css("width", "100%").css("height",
					"80%").css("left", "0").css("color", "#ffffff").click(function () {
					$(this).hide();
				}).appendTo("body");
		}
		errorDivJquery.html(exception.responseText).show();
	},
	util: {
		contextPathPattern: function () {
			if(!this.regExp) {
				this.regExp = new RegExp("^(?:http(?:s)?:.*)?" + window["contextPath"] + "/.*$")
			}
			return this.regExp;
		},
		addContextPath: function (action) {
			if(window["contextPath"] && !Base.util.contextPathPattern().test(action)) {
				return action.indexOf("/") == 0 ? window["contextPath"] + action : window["contextPath"] + "/" + action;
			}
			return action;
		}
	},
	ui: {
		fillSelect: function (select, vList, keyProperty, valueProperty, initSize) {
			select = $(select)[0];
			initSize = initSize || 0;
			select.options.length = initSize;

			for(var i = 0; i < vList.length; i++) {
				var r = vList[i];
				var option = new Option(r[keyProperty], r[valueProperty]);
				select.options.add(option);
			}
		}
	},
	token: function (async) {
		async = async === undefined ? true : async;
		$().invoke("/cp/token/ajax/generateToken", {}, function (message, re) {
			window["session-token"] = re;
		}, {"async": async});
	},
	loadData: function (clazz, fields, params, orders, callback, async) {
		if(!clazz)
			alert("必须指定clazz参数");
		if(!fields)
			alert("必须指定field参数");
		if(Base.isArray(fields) && !fields.length)
			alert("必须指定field参数");

		fields = Base.isArray(fields) ? fields : [fields];

		params = params || {};

		orders = orders || [];
		orders = Base.isArray(orders) ? orders : [orders];
		var ordersExt = [];
		for(var i = 0; i < orders.length; i++) {
			var order = orders[i];
			if(!order)
				continue;
			if(Base.isString(order))
				order = {"order": order};
			ordersExt.push(order);
		}

		async = async === undefined ? true : async;

		var p = {"clazz": clazz, "f": fields};

		//特殊处理ct
		if(params["_ct"]) {
			p["ct"] = params["_ct"];
			params["_ct"] = undefined;
		}

		//特殊处理page,即加载数目
		if(params["_ps"]) {
			p["page.pageSize"] = params["_ps"];
			params["_ps"] = undefined;
		}

		for(var key in params) {
			if(params[key] !== undefined)
				p["p." + key] = params[key];
		}

		for(var i = 0; i < ordersExt.length; i++) {
			var order = ordersExt[i];
			order["order"] && (p["by[" + i + "].order"] = order["order"]);
			order["by"] && (p["by[" + i + "].by"] = order["by"]);
		}

		$().invoke("/cp/dataload/ajax/load", p, callback, {"async": async})
	},
	loadSelectData: function (select, clazz, keyProperty, valueProperty, params, orders, initSize) {
		Base.loadData(clazz, [keyProperty, valueProperty], params, orders, function (message, re) {
			Base.ui.fillSelect(select, re, keyProperty, valueProperty, initSize);
		});
	}
};
//jquery load组件的改进版,将指定的值映射到指定的区域,并进行解析,将值写到指定的jquery结点中,并提供默认的操作.
(function ($) {
	var _jsonpStart = 1;
	var jsonFormat = /^\s*\{[\s\S]*\}\s*$/m;
	$.fn.invoke = function (url, param, fun, config) {

		function createJsonpCallback(fun) {
			var currentIndex = ++_jsonpStart;
			var jsoncallback = "_jsonpcallback" + currentIndex;
			window[jsoncallback] = fun[0];
			fun[1] && (window[jsoncallback + "Fail"] = fun[1]);
			return jsoncallback;
		}

		function handleJsonp(url, param, jsonpCallback, config) {
			//拼接参数信息
			param = Base.isString(param) ? param : $.param(param);
			if(url.indexOf('?') == -1) {
				url += "?1=1";
			}
			url += "&" + param;

			//以下代码直接来源于jquery 1.7.2.js中关于script的处理
			var head = document["head"] || document.getElementsByTagName("head")[0] || document.documentElement;
			var script = document.createElement("script");
			script.async = "async";
			script.src = url;

			// Attach handlers for all browsers
			script.onload = script.onreadystatechange = function () {
				if(!script.readyState || /loaded|complete/.test(script.readyState)) {
					script.onload = script.onreadystatechange = null;
					if(head && script.parentNode) {
						head.removeChild(script);
					}

					script = undefined;
				}
			};
			head.insertBefore(script, head.firstChild);
		}

		config = $.extend({}, $.fn.invoke.defaultConfig, config);
		param = param || {};

		//处理回调函数,升级为数组解析,数组格式为[successFun,failFun,throwFun]
		if(!Base.isArray(fun)) {
			fun = [fun];
		}

		var jsonp = config.ajaxMode == "jsonp";
		var jsonpcallback = jsonp ? createJsonpCallback(fun) : null;

		//设置参数 追加token
		if(Base.isArray(param)) {
			param.push({name: "_random", value: Math.random()}, {name: "ajax", value: "ajaxFlag"},
				{name: "ajaxMode", value: config.ajaxMode}, {name: "session_token", value: window["session-token"]});
			jsonp && param.push({name: "jsonpCallback", value: jsonpcallback});
		} else if(typeof param == "object") {
			param["_random"] = Math.random();
			param["ajax"] = "ajaxFlag";
			param["ajaxMode"] = config.ajaxMode;
			param["session_token"] = window["session-token"];
			jsonp && (param["jsonpCallback"] = jsonpcallback);
		} else if(Base.isString(param)) {
			if(param.length && param.charAt(param.length - 1) != '&')
				param += "&";
			param += "_random=" + Math.random() + "&ajax=ajaxFlag&ajaxMode=" + config.ajaxMode + "&session_token=" + window["session-token"];
			jsonp && (param += "&jsonpCallback=" + jsonpcallback);
		}

		if(!jsonp) {
			//url追加上下文
			url = Base.util.addContextPath(url);
			//如果原url中已经有类似.action?表示已经增加了相应后缀的情况下,则不再添加后缀
			if(url.indexOf(".action?") == -1 && url.lastIndexOf(".action") != url.length - 7)
				url += ".action";
		}
		//特殊处理jsonp
		if(jsonp) {
			handleJsonp(url, param, jsonpcallback, config);
			return this;
		}

		var self = this;
		$.ajax({
			url: url,
			type: config.type,
			dataType: config.dataType,
			async: config.async,
			data: param,
			complete: function (res, status) {
				if(status == "success" || status == "notmodified") {
					var responseText = res.responseText;
					//首先处理传统json格式
					if(jsonFormat.test(responseText)) {
						//这里直接调用相应处理api,为与下面的this引用相一致
						var re = eval("(" + responseText + ")");
						if(re["bool"] === false) {
							fun[1] ? fun[1].apply(self, [re["message"], re["result"]]) : (alert(re["message"]));
							return;
						}
						if(Base.isString(fun[0])) {
							self[fun[0]](re["result"]);
							$.trim(re["message"]) && $("#message").html(re["message"]);
						} else {
							fun[0].apply(self, [re["message"], re["result"]]);
						}
						return;
					}
					var result = $("<div></div>").append(responseText.replace(/<script(.|\s)*?\/script>/g,
						"")).find("#_ajaxDiv");
					if(result.length) {
						var bool = $.trim(result.find("#_ajaxBoolDiv").html());
						var message = result.find("#_ajaxMessageDiv").html();
						//处理返回结构
						var html = result.find("#_ajaxResultDiv").html();
						if(bool == "true") {//返回正确
							//处理返回消息
							if(Base.isString(fun[0])) {
								self[fun[0]](html);
								$("#message").html(message);
							} else {
								fun[0].apply(self, [message, html]);
							}
						} else if(bool == "false") {//返回错误标记
							fun[1] ? fun[1].apply(self, [message, html]) : alert(message);
						} else {//不可能发生
							fun[2] ? fun[2].apply(self, res) : Base.handleException(res);
						}
					} else {//未找到，被认为是错误
						fun[2] ? fun[2].apply(self, res) : Base.handleException(res);
					}
				}
			}
		});
		return this;
	};
	//默认参数，异步，且以对象模式进行ajax访问
	$.fn.invoke.defaultConfig = {type: "POST", dataType: "html", async: true, ajaxMode: "object"};

	//简便jsonp方法
	$.fn.invokeJsonp = function (url, param, fun, config) {
		config || (config = {});
		config.ajaxMode = "jsonp";
		return $.fn.invoke(url, param, fun, config);
	}
})(jQuery);

//提供一个允许将一个dom对象数据信息序列化为key:value对象，而非jquery中的字符串或name:nameV,value:valueV格式
//实现参考默认的jquery serializeArray实现
(function ($) {
	$.fn.serializeObject = function () {
		var array = $(this).serializeArray();
		var p = {};
		$.each(array, function (i, e) {
			var key = e.name;
			var value = e.value;

			var sv = p[key];
			sv && (sv.push(value)) || (p[key] = [value]);
		});
		return p;
	}
})(jQuery);