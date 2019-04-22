$(function () {

	function getlist(e) {
		$.ajax({
			url : "/activity/getLikeActivity",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.likeActivityList);//展示用户列表
					handleUser(data.user); //展示用户名
				}
				else {
					$('#user-name').text("你尚未报名任何活动")
				}
			}
		});
	}

	function handleUser(data) {
		$('#user-name').text(data.userName);
	}

	function handleList(data) {
		var html = '';
		data.map(function (item, index) {
			html += '<div class="row row-shop"><div class="col-40">'+ item.activityName +'</div><div class="col-40">'+ item.startTime +'</div><div class="col-20">'+ goShop(item.status, item.activityId) +'</div></div>';

		});
		$('.shop-wrap').html(html);//前后端分离
	}

	function goShop(status, id) {
		if (status >=3) {
			return '<a href="/activity/getActivityDetail?activityId='+ id +'">查看详情</a>';
		} else {
			return '';
		}
	}

	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == -1) {
			return '店铺非法';
		} else {
			return '审核通过';
		}
	}


	$('#log-out').click(function () {
		$.ajax({
			url : "/user/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/user/toLogin';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});


	getlist();
});
