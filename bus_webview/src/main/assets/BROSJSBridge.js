var BROSJSBridge = new BROSJSBridgeImpl();
var callBackMap = new BROSMap();
function BROSJSBridgeImpl() {
	function makeCallback(params) {
		for (var key in params) {
			const item = params[key];
			if (typeof item == 'function') {
				const rnd = Math.random().toString(36).substr(2);
				const callbackName = `callback_${rnd}`;
				window[callbackName] = (function (fn, callbackName) {
					return function (res) {
						fn(res);
					}
				})(item, callbackName);
				params[key] = callbackName;
			} else if (typeof item == 'object') {
				params[key] = makeCallback(item)
			}
		}
		return params;
	}

	this.invoke = function (funcName, paramObj, callback) {
		console.info("H5>>>>>Native");
		console.info("H5>>>>>Native>>>>>funcName:"+funcName);
		console.info("H5>>>>>Native>>>>>上送参数:"+JSON.stringify(paramObj));
		if (!callback) {
		    console.info("H5>>>>>Native callback null");
		} else {
			if (typeof callback != "function") {
				throw new Error("'callback' must be a function: " + callback);
			}
		}
		var localCallbackId = "BROS_" + Math.random().toString(36).substr(2);
		callBackMap.put(localCallbackId, callback);
		JSBridge.invoke(funcName, JSON.stringify(makeCallback(paramObj)), localCallbackId);
	};
	window.BROSJSBridge = BROSJSBridge;
	window.callBackMap = callBackMap;
}

function onCallback(res) {
	try {
		var callbackId = res.callbackId;
		var callback = window.callBackMap.get(callbackId);
		if (callback != null) {
		    //回调用完要及时移除，确保回调函数只会被执行一次
		    window.callBackMap.remove(callbackId);
			callback(res);
			console.info("Native>>>>>H5");
			return;
		} else {
			console.log("callbackMap: " + JSON.stringify(window.callBackMap));
		}

	} catch (err) {
		console.log("onCallback err: " + err.message);
	}
}


function handleCreateFunction() {
	try {

		var readyEvent = document.createEvent("Events");
		readyEvent.initEvent("BROSJSBridgeReady");
		document.dispatchEvent(readyEvent);
	} catch (err) {
		console.log("onInit err: " + err.message);
	}
}

function BROSMap() {
	this.map = {};
	this.length = 0;
	this.size = function () {
		return this.length;
	};
	this.put = function (a, b) {
		if (!this.map["_" + a]) {
			++this.length;
		}
		this.map["_" + a] = b;
	};
	this.remove = function (a) {
		if (this.map["_" + a]) {
			--this.length;
			return delete this.map["_" + a];
		} else {
			return false;
		}
	};
	this.containsKey = function (a) {
		return this.map["_" + a] ? true : false;
	};
	this.get = function (a) {
		return this.map["_" + a] ? this.map["_" + a] : null;
	};
	this.toString = function () {
		var b = "";
		for (var a in this.map) {
			b += "\n" + a + "-" + this.map[a];
		}
		return b;
	};
}
