/**
 * 格式化日期
 */
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/**
 * 获取js值
 * @param name
 * @returns {string}
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}
//更改验证码的方法
function changeVerifyCode(img) {
	img.src = "/tool/kaptcha?" + Math.floor(Math.random() * 100);
}

function getActivityStatus(status)
{
	if (status == 1) {
		return '审核中';
	} else if (status ==2) {
		return '未通过审核';
	}  else if(status==3) {
		return '审核通过';
	} else if (status==4)
	{
		return '未开始';
	} else if(status==5)
	{
		return '正在进行'
	} else if(status==6)
	{
		return '已结束';
	}
	else {
		return '不明状态';
	}
}
//侧边栏的登出
$('#logout').click(function() {
	var logout='/user/logout';
	$.confirm('确定退出吗?', function () {
		$.ajax({
			url: logout,
			type: 'POST',
			//这个是全部发送的内容 data
			data: {
				statusChange: true
			},
			dataType: 'json',
			success: function (data) {
				if (data.success) {
					$.toast('退出成功！');
					window.location.href = '/frontend/toLogin';
				} else {
					$.toast('退出失败！');
				}
			}
		});
	});
});
//侧边栏的修改个人信息
$('#user-edit').click(function () {
	$.ajax({
		url : "/user/getcurrentuser",
		type : "get",
		dataType : "json",
		success : function(data) {
			if (data.success) {
				window.location.href = '/frontend/toRegister?userId='+data.userId;
			}
			else {
				var errMsg=data.errMsg;
				$.toast(errMsg);
			}
		}
	});

});
//修改密码
$('#update-password').click(function () {
	window.location.href = '/frontend/updatepassword';
});