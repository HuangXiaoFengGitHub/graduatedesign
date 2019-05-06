$(function() {
	var pageIndex=1;
	var pageSize=10;
	var loading=false;
	var maxItem=999;
	var activityId = getQueryString('activityId');
	var getCommentUrl='/activity/getActivityComment';
	function getComment(pageIndex,pageSize) {
		var url=getCommentUrl+'?activityId='+activityId+'&pageIndex='+pageIndex+'&pageSize='+pageSize;
		loading=true;
		$.getJSON(url,function (data) {
		if(data.success)
		{
			var commentList=data.commentList;
			if(commentList.length>0)
				$('.infinite-scroll-preloader').show();
			var commenthtml='';
			commentList.map(function (item, index) {
				commenthtml += '  <div class="card" data-comment-id="'+item.activityComment.commentId+'">\n' +
					'    <div class="card-header"></div>\n' +
					'    <div class="card-content">\n' +
					'      <div class="list-block media-list">\n' +
					'        <ul>\n' +
					'          <li class="item-content">\n' +
					'            <div class="item-media">\n' +
					'              <img src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg" width="44">\n' +
					'            </div>\n' +
					'            <div class="item-inner">\n' +
					'              <div class="item-title-row">\n' +
					'                <div class="item-title">'+item.user.userName+'</div>\n' +
					'              </div>\n' +
					'              <div class="item-subtitle">'+item.activityComment.content+'</div>\n' +
					'            </div>\n' +
					'          </li>\n' +
					'        </ul>\n' +
					'      </div>\n' +
					'    </div>\n' +
					'    <div class="card-footer ">\n' +
					'      <span class="color-gray">'+new Date(item.createTime).Format("yyyy-MM-dd hh:mm")+'</span>\n' +
					'      <span>'+item.activityComment.commentLikeCount+'赞</span>\n' +
					'      <a href="#" data-comment-id="'+item.activityComment.commentId+'" class="link like">赞</a>'+
					'    </div>\n' +
					'  </div>  ';
			});
			$('#activity-comment').append(commenthtml);
			//获取卡片总数，包括之前加载的
			var total = $('.list-div .card').length;
			//
			if (total >= maxItem) {
				// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
				// 删除加载提示符
				$('.infinite-scroll-preloader').hide();
			}
			//否则显示加载福
			else {
				$('.infinite-scroll-preloader').show();
			}
			//否则页码加1，继续load出新的活动
			pageIndex += 1;
			//加载结束，显示新加载的活动
			loading = false;
			$.refreshScroller();
			$('.infinite-scroll-preloader').hide();
		}
		});
	}
	var toposturl='';
	toposturl='<a href="/user/comment/toPost?activityId='+activityId+'" class="button button-fill">发表评论</a>';
	$('.comment-post').html(toposturl);
	//预先加载
	getComment(pageIndex,pageSize);
	// 下滑屏幕自动进行分页搜索，自动触发
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading) //如果正在加载，就不重复加载了
			return;
		pageIndex++;
		getComment(pageIndex,pageSize);
	});
	//监听赞事件

	//侧边栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	//定义触发事件
	$('.activity-comment').on(
		'click',
		'a',
		function(e) {
			var target = $(e.currentTarget);
			var commentId = e.target.dataset.commentId;
			if (target.hasClass('like')) {
				like(commentId);
			}
		});
	function like(commentId)
	{
		var likeurl='/user/comment/like?commentId='+commentId;
		$.ajax({
			url : likeurl,
			type : 'POST',
			//这个是全部发送的内容 data
			data : {
				commentId: commentId,
				statusChange : true
			},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.toast('点赞成功');
					//刷新界面
					pageIndex++;
					getComment(pageIndex,pageSize);
				} else {
					var errMsg=data.errMsg;
					$.toast(errMsg);
				}
			}
		});
	}
	//初始化
	$.init();
});
