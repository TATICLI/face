/*****************************************************************
                  jQuery Ajax文件上传封装通用类      
*****************************************************************/
/**调用示例：
   $.uploadAjaxRequest({options});
*/
/**
 * ajax封装
 * url 发送请求的地址,url?传参时，参数名誉data的key相同时会被覆盖data的参数值
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * async 默认值: false。默认设置下，所有请求均为同步请求。如果需要发送异步请求，请将此选项设置为 true。
 *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 * type 请求方式("POST" ， "GET" ，"")， 默认为 "POST"
 * cache 缓存，默认为false
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 * beforeSendfun 在发送请求之前调用，并且传入一个XMLHttpRequest作为参数。
 * successfun 成功回调函数
 * errorfun 失败回调函数
 */
if(typeof face =="undefined"){
		var face=new Object();
}//end if
if(typeof face.tools =="undefined"){
	face.tools=new Object();
}
;(function(tools){
	tools.uploadAjaxRequest=function(options) {
    	var defaults={
			type: "POST",
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: null,
            url: null,
            dataType: "json",
            beforeSend:null,
            successfun:null,
            errorfun:null
    	}
    	
    	var options = $.extend(defaults, options);
    	
    	var settings={
				type: options.type,
	            async: options.async,
	            cache: options.cache,
	            contentType: options.contentType,
	            processData: options.processData,
	            data: options.data,
	            url: options.url,
	            dataType: options.dataType,
	            success:options.successfun
    	}
    	
    	//请求之前加入‘等待遮罩层’事件
    	settings.beforeSend= function(){
			
		  var loading=$('<div class="loading" style="position:fixed;top: 0;right: 0; bottom: 0;left: 0;text-align: center; z-index: 1050;color: white;background-color:rgba(0, 0, 0, 0.2);">'+
		            '<div style="position:absolute;top:50%;left:50%;"><center><font size=\"3\"><i class=\"fa fa-spinner fa-spin\"></i>内容加载中，请稍后</font></center></div>'+
		        '</div>');
		   //遮罩层为全屏
		   top.$("body").append(loading);
		   //禁止滚动
		   document.body.style.overflow='hidden';
		}
    	
    	//请求完毕后，加入去除‘遮罩层’事件，和判断登陆超时跳转事件
		settings.complete= function(XMLHttpRequest, textStatus){
			 top.$("body").find(".loading").remove();
			 document.body.style.overflow='auto';
			 
			 var response = JSON.parse(XMLHttpRequest.responseText);
			 if (response.status == 100||response.status == 103) {
	    		window.top.location.href=SystemGlobal.ssoLoginUrl+"?msg="+encodeURIComponent(response.msg);
				return;
			 }
			
		}
    	
		if(options.errorfun != null && options.errorfun != "" && typeof(options.errorfun) != "undefined"){
    		settings.error=options.errorfun;
    	}
    	
        $.ajax(settings);
    };

})(face.tools);
