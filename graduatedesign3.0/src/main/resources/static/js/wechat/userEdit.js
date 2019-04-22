/**
 * 1，从后台获取区域，分类等信息，转发到前台，填充到控件
 * 2，将前台的信息获取并转发到后台并注册店铺
 * 3，验证表单输入
 */
//从函数开头
$(function() {
	var userId = getQueryString('userId');//从URL中获取shopId
	//
	// var isEdit = shopId ? true : false;
	//
	// var shopInfoUrl = '/myo2o/shop/getshopbyid?shopId=1';
	// // var shopInfoUrl = '/myo2o/shop/getshopbyid?shopId=' + shopId;
	// var initUrl = '/myo2o/shop/getshopinitinfo';
	// var editShopUrl = '/myo2o/shop/registershop';
	// if (isEdit) {
	// 	editShopUrl = '/myo2o/shop/modifyshop';
	// }
	//
	// function getInfo(shopId) {
	// 	$.getJSON(shopInfoUrl, function(data) {
	// 		if (data.success) {
	// 			var shop = data.shop;
	// 			$('#shop-name').val(shop.shopName);
	// 			$('#shop-addr').val(shop.shopAddr);
	// 			$('#shop-phone').val(shop.phone);
	// 			$('#shop-desc').val(shop.shopDesc);
	// 			var shopCategory = '<option data-id="'
	// 				+ shop.shopCategory.shopCategoryId + '" selected>'
	// 				+ shop.shopCategory.shopCategoryName + '</option>';
	// 			var tempAreaHtml = '';
	// 			data.areaList.map(function(item, index) {
	// 				tempAreaHtml += '<option data-id="' + item.areaId + '">'
	// 					+ item.areaName + '</option>';
	// 			});
	// 			$('#shop-category').html(shopCategory);
	// 			$('#shop-category').attr('disabled','disabled');
	// 			$('#area').html(tempAreaHtml);
	// 			$('#area').attr('data-id',shop.areaId);
	// 		}
	// 	});
	// }
	//
	// function getCategory() {
	// 	$.getJSON(initUrl, function(data) {
	// 		if (data.success) {
	// 			var tempHtml = '';
	// 			var tempAreaHtml = '';
	// 			data.shopCategoryList.map(function(item, index) {
	// 				tempHtml += '<option data-id="' + item.shopCategoryId
	// 					+ '">' + item.shopCategoryName + '</option>';
	// 			});
	// 			data.areaList.map(function(item, index) {
	// 				tempAreaHtml += '<option data-id="' + item.areaId + '">'
	// 					+ item.areaName + '</option>';
	// 			});
	// 			$('#shop-category').html(tempHtml);
	// 			$('#shop-category').removeAttr('disabled');
	// 			$('#area').html(tempAreaHtml);
	// 		}
	// 	});
	// }
	//
	// if (isEdit) {
	// 	getInfo(shopId);
	// } else {
	// 	getCategory();
	// }
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
	var registerUrl = '/user/userEdit';//后台方法,在controller层中实现，并写出对应的mappering
	$('#submit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
		var user = {};//变量，json对象
		user.userId=userId; //获取要编辑的userid
		//var personInfo = {};//变量，一个json对象
		user.userName = $('#userName').val();//前端规定的控件名字userName
		//localAuth.password = $('#password').val();//前端规定的控件名字password
		user.phone = $('#phone').val();
		user.email = $('#email').val();
		user.nickName = $('#nickName').val();
		user.studentNumber=$('#studentNumber').val();
		user.gender=$('#gender').val();
		user.grade=$('#grade option:selected').val();
		user.academy=$('#academy option:selected').val();
		user.major=$('#major option:selected').val();
		user.birthday=$('#birthday').val();
		user.userDesc=$('#userDesc').val();
		user.password=$('#password').val();
		if($('#isStudent').is(':checked'))
		{
			user.isStudent=1;
		}
		else
		{
			user.isStudent=0;
		}
		//localAuth.personInfo = personInfo;
		//获取提交的头像文件
		var thumbnail = $('#profile')[0].files[0]; //这里是上传的文件
		console.log(thumbnail);
		var formData = new FormData(); //接受表单内容
		formData.append('thumbnail', thumbnail);//传输参数
		formData.append('user', JSON.stringify(user)); //传送参数
		var verifyCodeActual = $('#j_captcha').val(); //接收验证码，验证码的控件id是j_captcha
		if (!thumbnail) {
			toastr.info('请上传头像！');
			return;
		}
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
			success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台方法，这里data就是返回的json字符串
				if (data.success) {
					toastr.info('提交成功！');
					window.location.href = '/user/toLogin';
				}
				else {
					toastr.info('提交失败！');
					toastr.info(data.success)
					//toastr.info(data);
					window.location.href ='/user/registerResult';
					$('#captcha_img').click();//每次注册完之后更换一下验证码
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/wechat/index';
	});
});
