/**
 * 1，从后台获取区域，分类等信息，转发到前台，填充到控件
 * 2，将前台的信息获取并转发到后台并注册店铺
 * 3，验证表单输入
 */
//从函数开头
$(function() {
	toastr.options = {

		"closeButton": false, //是否显示关闭按钮

		"debug": false, //是否使用debug模式

		"positionClass": "toast-top-full-width",//弹出窗的位置

		"showDuration": "300",//显示的动画时间

		"hideDuration": "1000",//消失的动画时间

		"timeOut": "5000", //展现时间

		"extendedTimeOut": "1000",//加长展示时间

		"showEasing": "swing",//显示时的动画缓冲方式

		"hideEasing": "linear",//消失时的动画缓冲方式

		"showMethod": "fadeIn",//显示时的动画方式

		"hideMethod": "fadeOut" //消失时的动画方式
	};
	var registerUrl = '/myo2o/shop/ownerregister';//后台方法,在controller层中实现，并写出对应的mappering
	$('#submit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
		var localAuth = {};//变量，json对象
		var personInfo = {};//变量，一个json对象
		localAuth.userName = $('#userName').val();//前端规定的控件名字userName
		localAuth.password = $('#password').val();//前端规定的控件名字password
		personInfo.phone = $('#phone').val();
		personInfo.email = $('#email').val();
		personInfo.name = $('#name').val();
		localAuth.personInfo = personInfo;
		//获取提交的头像文件
		var thumbnail = $('#user-image')[0].files[0]; //这里是上传的文件
		console.log(thumbnail);
		var formData = new FormData(); //接受表单内容
		formData.append('thumbnail', thumbnail);//传输参数
		formData.append('localAuthStr', JSON.stringify(localAuth)); //传送参数
		var verifyCodeActual = $('#j_captcha').val(); //接收验证码，验证码的控件id是j_captcha
		if (!verifyCodeActual) {
			toastr.info('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		//用Ajax将表单提交到后台
		$.ajax({
			url : registerUrl, //
			type : 'POST', //用post方法提交
			data : formData, //传送formData
			contentType : false,//文件，名字
			processData : false,
			cache : false,
			success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台
				if (data.success) {
					toastr.info('提交成功！');
					window.location.href = '/myo2o/shop/ownerlogin';
				} else {
					toastr.info('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/myo2o/shop/ownerlogin';
	});
});
