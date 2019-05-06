$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;
	//获取组织下的活动列表
	var listUrl = '/activity/getActivityList';
	var cancellikeorganization='/user/organizationOperate/cancelLikeOrganization';
	var likeorganization='/user/organizationOperate/addLikeOrganization';
	var pageNum = 1;
	var organizationId = getQueryString('organizationId');
	var activityCategoryId = '';
	var activitystatus = '';
	var tagId = '';
	var keyword = '';
   //获取组织信息的以及活动标签，活动类别
	var searchDivUrl = '/user/organizationOperate/getOrganization?organizationId='
			+ organizationId;
	function getSearchDivData() {
		var url = searchDivUrl;
		$.getJSON(url,function(data) {
							if (data.success) {
								var organization = data.organization;
								var parentOrganization=data.parentOrganization;
								$('#shop-cover-pic').attr('src', organization.organizationImg);
								$('#like-count').text(organization.likeCount);
								$('#activity-count').text(organization.activityCount);
								var parentOrganizationhtml='';
								if(parentOrganization!=null)
									parentOrganizationhtml='<a href="/frontend/organizationDetail?organizationId='+parentOrganization.organizationId+'">'+parentOrganization.organizationName+'</a>';
								else
									parentOrganizationhtml='<span class="color-gray">父组织</span>';
								$('#parent-organization').html(parentOrganizationhtml);
								$('#organization-name').html(organization.organizationName);
								$('#organization-desc').html(organization.organizationDesc);
								//活动类别
								var activityCategoryList = data.activitycategorylist;
								var html = '';
								html += '<a href="#" class="button" data-product-search-id="">全部活动</a>';
								activityCategoryList.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.activityCategoryId
													+ '>'
													+ item.activityCategoryName
													+ '</a>';
										});
								$('#category-button-div').html(html);
								//活动标签
								var tags=data.tagsList;
								var tagsHtml='';
								tagsHtml += '<a href="#" class="button" data-tags-search-id="">全部标签</a>';
								tags.map(function(item, index) {
									tagsHtml += '<a href="#" class="button" data-tags-search-id='
										+ item.tagId
										+ '>'
										+ item.tagName
										+ '</a>';
								});
								$('#tags-button-div').html(tagsHtml);
								//微信图片
								var organizationImg='';
								organizationImg +='<div align="center" ><p class="color-gray">微信公众号</p><p><b>'+organization.wechatName+'</b></p>' +
									'<img src="'
									+ organization.wechatImg + '"/></div>';
								$('#organizationImg-list').html(organizationImg);
								//组织操作，关注，评论
								var operation = '';
								if (data.isLike) {
									operation += '<a href="#" class="link cancelLike">取消关注</a>';
								} else {
									operation += '<a href="#" class="link like">关注</a>';
								}
								operation += '<a href="/frontend/organization/comment?organizationId=' + organizationId + '" class="link">评论</a>';
								$('#organization-operation').html(operation);
							}
						});
	}
	//渲染出活动信息
	getSearchDivData();
   //查找出商品列表
	function addItems(pageSize, pageIndex) {
		$('.infinite-scroll-preloader').show();
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&activityCategoryId=' + activityCategoryId + '&activitystatus=' + activitystatus
			+ '&tagId=' + tagId + '&keyword=' + keyword+'&organizationId='+organizationId;
		// 设定加载符号，若还在后头取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		// 获取活动列表
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count; //获取活动总数
				var html = '';
				if(data.activityList.length>0)
					$('.infinite-scroll-preloader').show();
				data.activityList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
						+ item.activityId + '">' + '<div class="card-header">'
						+ item.activityName + '</div>'
						+ '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.imgAddr + '" width="44">' + '</div>'
						+ '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.activityDesc
						+ '</div>' + '</div>' + '</li>' + '</ul>'
						+ '</div>' + '</div>' + '<div class="card-footer">'
						+ '<p class="color-gray">'
						+ new Date(item.updateTime).Format("yyyy-MM-dd")
						+ '更新</p>' +'<span>'+getActivityStatus(item.status)+'</span>'+'<span>点击查看</span>' + '</div>'
						+ '</div>';
				});
				//将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				//获取卡片总数，包括之前加载的
				var total = $('.list-div .card').length;
				//
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').hide();
				}
				//否则页码加1，继续load出新的活动
				pageNum += 1;
				//加载结束，显示新加载的活动
				loading = false;
				$.refreshScroller();
				$('.infinite-scroll-preloader').hide();
			}
		});
	}
	//选择新的活动标签之后，重置页码，清空原先的店铺列表，按照新的标签去查询
	$('#tags-button-div').on(
		'click',
		'.button',
		function(e) {
			//	if (activityCategoryId) {
			// 如果传递过来的是一个父类下的子类
			tagId = e.target.dataset.tagsSearchId;
			// 若之前已经选定了别的category，则移除其选定效果，改成选定新的活动效果
			if ($(e.target).hasClass('button-fill')) {
				$(e.target).removeClass('button-fill');
				tagId = '';
			} else {
				//添加选定效果
				$(e.target).addClass('button-fill').siblings()
					.removeClass('button-fill');
			}
			// 由于查询条件改变，清空活动列表再进行查询
			$('.list-div').empty();
			// 重置页码
			pageNum = 1;
			// 新加载内容
			addItems(pageSize, pageNum);
		});
	addItems(pageSize, pageNum);
  //无极分页
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	//选择活动类别
	$('#category-button-div').on(
			'click',
			'.button',
			function(e) {
				activityCategoryId = e.target.dataset.productSearchId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						activityCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
			});
	//点击活动的卡片进入该活动的详情页
	$('.activity-list').on('click', '.card', function(e) {
		var activityId = e.currentTarget.dataset.shopId;
		window.location.href = '/activity/getActivityDetail?activityId=' + activityId; //获取活动详情
	});
	//获取搜索控件的值
	$('#search').on('change', function(e) {
		keyword = e.target.value;
		//清空原先的活动列表，按照新的关键字查询
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	//活动状态发生变化的时候，重置页码，再次查询
	$('#area-search').on('change', function() {
		activitystatus = $('#area-search').val();
		//	activitystatus=$('#area-search option:selected').val();
		// 清空原先的活动列表
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	//手动初始化
	$.init();
	//取消关注
	function cancelLike() {
		$.confirm('Are you sure?', function () {
			$.ajax({
				url : cancellikeorganization,
				type : 'POST',
				//这个是全部发送的内容 data
				data : {
					organizationId: organizationId,
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('取消关注成功！');
						//刷新界面
						getSearchDivData()
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
		});
	//关注活动
	function like() {
		$.confirm('确定关注该活动吗？',function () {
			$.ajax({
				url : likeorganization,
				type : 'POST',
				//这个是全部发送的内容 data
				data : {
					organizationId: organizationId,
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('关注成功！');
						//刷新界面
						getSearchDivData()
					} else {
						$.toast('关注失败！');
					}
				}
			});
		})
	}
});
