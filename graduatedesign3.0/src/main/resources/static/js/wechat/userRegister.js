/**
 * 1，从后台获取区域，分类等信息，转发到前台，填充到控件
 * 2，将前台的信息获取并转发到后台并注册店铺
 * 3，验证表单输入
 */
//从函数开头
$(function() {
	var userId = getQueryString('userId');//从URL中获取shopId

	var isEdit = userId ? true : false;

	//var userInfoUrl = '/myo2o/shop/getshopbyid?shopId=1';
	var userInfoUrl = '/user/getUserInfo?userId=' + userId;
	var initUrl = '/user/registerInitInfo';
	var registerUrl = '/user/userRegister';
	//修改用户信息
	if (isEdit) {
		registerUrl= '/user/userEdit?userId='+userId;
	}
	function getInfo(userId) {
		//这个是有userId的
		$.getJSON(userInfoUrl, function(data) {
			if (data.success) {
				var user = data.user;
				//获取原本的用户信息，渲染到表单
				$('#userName').val(user.userName);
				$('#nickName').val(user.nickName);
				$('#email').val(user.email);
				$('#studentNumber').val(user.studentNumber);
				$('#phone').val(user.phone);
				$('#password').val(user.password);
				$('#password').val(user.password);
				$('#birthday').val(user.birthday);
				$('#userDesc').val(user.userDesc);
				// //生成前端类别列表，并默认选择原来的类别，data-id=''
				// var shopCategory = '<option data-id="'
				// 	+ shop.shopCategory.shopCategoryId + '" selected>'
				// 	+ shop.shopCategory.shopCategoryName + '</option>';
				// var tempAreaHtml = '';
				// data.areaList.map(function(item, index) {
				// 	tempAreaHtml += '<option data-id="' + item.areaId + '">'
				// 		+ item.areaName + '</option>';
				// });
				// $('#shop-category').html(shopCategory);
				// //设置无法更改类别信息
				// $('#shop-category').attr('disabled','disabled');
				// $('#area').html(tempAreaHtml);
				// $('#area').attr('data-id',shop.areaId);
				getCategory();
			}
		});
	}
//没有userId的，用来注册用的
	function getCategory() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.academyList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.academyId
						+ '">' + item.academyName + '</option>';
				});
				data.majorList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.majorId + '">'
						+ item.majorName + '</option>';
				});
				$('#academy').html(tempHtml);
			//	$('#academy').removeAttr('disabled');
				$('#major').html(tempAreaHtml);
			}
		});
	}

	if (isEdit) {
		getInfo(userId);
	} else {
		getCategory();
	}
	//var registerUrl = '/user/userRegister';//后台方法,在controller层中实现，并写出对应的mappering
	$('#submit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
		var user = {};//变量，json对象
		//var personInfo = {};//变量，一个json对象
		user.userName = $('#userName').val();//前端规定的控件名字userName
		//localAuth.password = $('#password').val();//前端规定的控件名字password
		user.phone = $('#phone').val();
		user.email = $('#email').val();
		user.nickName = $('#nickName').val();
		user.studentNumber=$('#studentNumber').val();
		user.gender=$('#gender option:selected').val();
		user.grade=$('#grade option:selected').val();
		//user.academy=$('#academy option:selected').val();
		//user.major=$('#major option:selected').val();
		var userAcademy=$('#academy option:selected').val();
		var userMajor=$('#major option:selected').val();
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
			var thumbnail = $('#profile')[0].files[0];
			console.log(thumbnail);
		 //这里是上传的文件}

		var formData = new FormData(); //接受表单内容
		formData.append('thumbnail', thumbnail);//传输参数
		formData.append('user', JSON.stringify(user)); //传送参数
		formData.append('userMajor',userMajor);
		formData.append('userAcademy',userAcademy);
		var verifyCodeActual = $('#j_captcha').val(); //接收验证码，验证码的控件id是j_captcha
		if (!thumbnail && !isEdit) {
			$.toast('请上传头像！');
			return;
		}
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
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
			//回调
			success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台方法，这里data就是返回的json字符串
				if (data.success) {
					$.toast('注册成功！');
					window.location.href = '/user/toLogin';
				}
				else {
					var errMsg=data.errMsg;
					$.toast('提交失败！'+errMsg);
					if(data.isWrong)
						$.toast('验证码错误');
					//window.location.href ='/user/toRegister';
					$('#captcha_img').click();//每次注册完之后更换一下验证码
				}
			}
		});
	});
	$('#back').click(function() {
		window.location.href = '/frontend/index';
	});
});
