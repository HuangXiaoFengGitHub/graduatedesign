$(function() {
	var url = '/user/updatePassword';
	$('#submit').click(function() {
		var email = $('#email').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var repeatPassword=$('#repeatPassword').val();
		var formData = new FormData();
		formData.append('email',email );
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		formData.append('repeatPassword',repeatPassword);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = '/frontend/index';
				} else {
					var errMsg=data.errMsg;
					$.toast('提交失败！'+errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/frontend/index';
	});
});
