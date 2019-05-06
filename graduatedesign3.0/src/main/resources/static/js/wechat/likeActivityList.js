//我关注的活动界面
$(function () {
	//取消报名的URL
	var deleteUrl='/user/activityOperate/cancelLikeActivity';
	function getlist(e) {
		$.ajax({
			url : "/user/activityOperate/getMyLikeActivity",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.likeActivityList);//展示用户列表
					handleUser(data.user); //展示用户名
				}
				else {
					$('#user-name').text("你尚未关注任何活动")
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
			html += '<div class="row row-shop"><div class="col-40">'+ item.activityName +'</div><div class="col-40">'+ item.startTime +'</div><div class="col-20">'+ goShop(item.status, item.activityId)+ '&nbsp' +'<a href="#" class="delete" data-id="'
				+ item.activityId
				+ '">取消报名'
				+ '</a>'+'</div></div>';

		});
		$('.shop-wrap').html(html);//前后端分离
	}

	function cancel(id) {
	    return '<a href="/user/activityOperate/cancelLikeActivity?activityId='+id+'">取消报名</a>'
    }
    //查看活动详情
	function goShop(status, id) {
		if (status >=3) {
			return '<a href="/activity/getActivityDetail?activityId='+ id +'">进入</a>';
		} else {
			return '<a>无法查看该活动</a>>';
		}
	}

	function shopStatus(status) {
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
// 取消关注的函数
	function deleteItem(id) {
		$.confirm('Are you sure?', function () {
			$.ajax({
				url : deleteUrl,
				type : 'POST',
				//这个是全部发送的内容 data
				data : {
					activityId: id,
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('取消关注成功！');
						//刷新界面
						getlist();
					} else {
						$.toast('取消关注失败！');
					}
				}
			});
		});
		// $.confirm({
		// 	title: 'What is up?',
		// 	content: '确定要取消关注该组织么？',
		// 	type: 'green',
		// 	buttons: {
		// 		ok: {
		// 			text: "ok!",
		// 			btnClass: 'btn-primary',
		// 			keys: ['enter'],
		// 			action: function(){
		// 				$.ajax({
		// 					url : deleteUrl,
		// 					type : 'POST',
		// 					//这个是全部发送的内容 data
		// 					data : {
		// 						activityId: id,
		// 						statusChange : true
		// 					},
		// 					dataType : 'json',
		// 					success : function(data) {
		// 						if (data.success) {
		// 							toastr.info('取消关注成功！');
		// 							//刷新界面
		// 							getlist();
		// 						} else {
		// 							toastr.info('取消关注失败！');
		// 						}
		// 					}
		// 				});
		// 			}
		// 		},
		// 		cancel: function(){
		// 			console.log('the user clicked cancel');
		// 		}
		// 	}
		// });
	}
//定义触发事件
	$('.shop-wrap').on(
		'click',
		'a',
		function(e) {
			var target = $(e.currentTarget);
		if (target.hasClass('delete')) {
			deleteItem(e.currentTarget.dataset.id);
		}
		});

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
