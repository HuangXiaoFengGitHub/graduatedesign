$(function() {
	var loginUrl = '/user/login';
	var loginCount = 0;

	$('#submit').click(function() {
		var email = $('#email').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				email : email,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					window.location.href = '/frontend/index';
				} else {
					var errMsg=data.errMsg;
					$.toast('登录失败！'+errMsg);
					loginCount++;
					if (loginCount >= 3) {
						$('#verifyPart').show();
					}
				}
			}
		});
	});
	//注册事件
	$('#register').click(function() {
		window.location.href = '/frontend/toRegister';
	});
});