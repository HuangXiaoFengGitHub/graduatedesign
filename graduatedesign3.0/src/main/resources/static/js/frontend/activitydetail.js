$(function() {
	var activityId = getQueryString('activityId');
	var activityUrl = '/activity/getActivity?activityId='
			+ activityId;
	var cancelLikeActivity='/user/activityOperate/cancelLikeActivity?activityId='+activityId;
	var likeActivity='/user/activityOperate/addLikeActivity?activityId='+activityId;
	var cancelSignUp='/user/activityOperate/cancelSignUp?activityId='+activityId;
	var signUp='/user/activityOperate/signUp?activityId='+activityId;
	var getNoticeUrl='/frontend/getActivityNotice?activityId='+activityId;
	var array=new Array(10);
	//取消报名
	function cancelsignUp() {
		$.confirm('Are you sure?', function () {
			$.ajax({
				url: cancelSignUp,
				type: 'POST',
				//这个是全部发送的内容 data
				data: {
					activityId: activityId,
					statusChange: true
				},
				dataType: 'json',
				success: function (data) {
					if (data.success) {
						$.toast('取消报名成功！');
						//刷新界面
						getlist();
					} else {
						$.toast('取消报名失败！');
					}
				}
			});
		});
	}
	//报名
	function signup() {
		$.confirm('Are you sure?', function () {
			$.ajax({
				url: signUp,
				type: 'POST',
				//这个是全部发送的内容 data
				data: {
					activityId: activityId,
					statusChange: true
				},
				dataType: 'json',
				success: function (data) {
					if (data.success) {
						$.toast('报名成功！');
						//刷新界面
						getlist();
					} else {
						$.toast('报名失败！');
					}
				}
			});
		});
	}
	function getlist(e)
	{
		$.getJSON(activityUrl, function (data) {
			if (data.success) {
				var activity = data.activity;
				$('#product-img').attr('src', activity.imgAddr);
				$('#start-time').text(
					new Date(activity.startTime).Format("yyyy-MM-dd hh:mm"));
				$('#end-time').text(
					new Date(activity.endTime).Format("yyyy-MM-dd hh:mm"));
				$('#product-name').text(activity.activityName);
				$('#product-desc').text(activity.activityDesc);
				//活动类别和活动标签
				var categoryandtag='';
				categoryandtag += '<a href="/frontend/activityList?activityCategoryId=' + data.activity.category.activityCategoryId+'" class="button" '
					+ '>'
					+ data.activity.category.activityCategoryName
					+ '</a>';
				categoryandtag += '<a href="#" class="button" '
					+ '>'
					+ data.activity.tags.tagName;
					+ '</a>';
				$('#category-tag').html(categoryandtag);
				var organizationName='';
				organizationName +=  '<a href="/frontend/organizationDetail?organizationId=' + data.activity.organization.organizationId+'" class="link" '
					+ '>'
					+ data.activity.organization.organizationName
					+ '</a>';
				$('#organization-name').html(organizationName);
				var imgListHtml = '';
				array = new Array(activity.count);
				activity.activityImgs.map(function (item, index) {
					array[index] = item.imageAddr;
					imgListHtml += '<div align="center" ><img src="'
						+ item.imageAddr + '"/></div>';
				});

				// 生成购买商品的二维码供商家扫描
				// imgListHtml += '<div> <img src="/myo2o/frontend/generateqrcode4product?productId='
				// 		+ product.productId + '"/></div>';
				$('#activityImg-list').html(imgListHtml);
				//活动操作，关注，评论，报名
				var operation = '';
				if (data.isLike) {
					operation += '<a href="#" class="link cancelLike">取消关注</a>';
				} else {
					operation += '<a href="#" class="link like">关注</a>';
				}
				if (data.isSignUp) {
					operation += '<a href="#" class="link cancelSignUp">取消报名</a>';
				} else {
					operation += '<a href="#" class="link signUp">报名</a>';
				}
				operation += '<a href="/frontend/activity/getComment?activityId=' + activityId + '" class="link">评论</a>';
				$('#activity-operation').html(operation);
				var notiehtml='';
				notiehtml+= '<a href="'+getNoticeUrl+'" class="button">活动公告</a>';
				$('#notice').html(notiehtml);
			}
		});
	}
	getlist();
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	//定义触发事件
	$('.card-footer').on(
		'click',
		'a',
		function(e) {
			var target = $(e.currentTarget);
			if (target.hasClass('cancelLike')) {
				cancelLike();
			}
			else if(target.hasClass('like'))
			{
				like();
			}
			else if(target.hasClass('cancelSignUp'))
			{
				cancelsignUp();
			}
			else if(target.hasClass('signUp'))
			{
				signup();
			}
		});
	/*=== 有标题 图片浏览器 ===*/
	var myPhotoBrowserCaptions = $.photoBrowser({
		photos : [
			{
				url: '//img.alicdn.com/tps/i3/TB1kt4wHVXXXXb_XVXX0HY8HXXX-1024-1024.jpeg',
				caption: 'Caption 1 Text'
			},
			{
				url: '//img.alicdn.com/tps/i1/TB1SKhUHVXXXXb7XXXX0HY8HXXX-1024-1024.jpeg',
				caption: 'Second Caption Text'
			},
			// 这个没有标题
			{
				url: '//img.alicdn.com/tps/i4/TB1AdxNHVXXXXasXpXX0HY8HXXX-1024-1024.jpeg',
			},

		],
		theme: 'dark',
		type: 'standalone'
	});
	$(document).on('click','.pb-standalone-captions',function () {
		myPhotoBrowserCaptions.open();
	});

	$.init();
	//取消关注
	function cancelLike() {
		$.confirm('Are you sure?', function () {
			$.ajax({
				url : cancelLikeActivity,
				type : 'POST',
				//这个是全部发送的内容 data
				data : {
					activityId: activityId,
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
		// 	content: '确定要取消关注该活动么？',
		// 	type: 'green',
		// 	buttons: {
		// 		ok: {
		// 			text: "ok!",
		// 			btnClass: 'btn-primary',
		// 			keys: ['enter'],
		// 			action: function(){
		// 				$.ajax({
		// 					url : cancelLikeActivity,
		// 					type : 'POST',
		// 					//这个是全部发送的内容 data
		// 					data : {
		// 						activityId: activityId,
		// 						statusChange : true
		// 					},
		// 					dataType : 'json',
		// 					success : function(data) {
		// 						if (data.success) {
		// 							$.toast('取消关注成功！');
		// 							//刷新界面
		// 							getlist();
		// 						} else {
		// 							$.toast('取消关注失败！');
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
	//关注活动
	function like() {
		$.confirm('确定关注该活动吗？',function () {
			$.ajax({
									url : likeActivity,
									type : 'POST',
									//这个是全部发送的内容 data
									data : {
										activityId: activityId,
										statusChange : true
									},
									dataType : 'json',
									success : function(data) {
										if (data.success) {
											$.toast('关注成功！');
											//刷新界面
											getlist();
										} else {
											$.toast('关注失败！');
										}
									}
								});
		})
	}
});
