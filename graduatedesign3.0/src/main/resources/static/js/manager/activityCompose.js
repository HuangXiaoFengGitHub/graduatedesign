$(function() {
	var loading = false;
	//分页允许返回的最大条数，超过此条数则禁止访问后台
	var maxItems = 999;
	//一页返回的最大条数
	var pageSize = 10;
	//获取活动列表的URL
	var listUrl = '/activity/getActivityList';
	var activityId='';
	var getCheckedActivity = '/frontend/activityCategory/getActivityTags';

	function getSearchDivData() {
		//如果传入了活动类别id，则取出活动类别下的所有活动标签（二级类别），这个要怎么绑定呢？直接绑定或自己添加标签
		var url = searchDivUrl + '?' + 'activityCategoryId=' + activityCategoryId;
		$.getJSON(url,function(data) {
							if (data.success)
							{
								$('#category-name').text(data.activityCategoryName);
								var shopCategoryList = data.tagList;
								var html = '';
								html += '<a href="#" class="button" data-category-id="">全部标签</a>';
								shopCategoryList.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.tagId
													+ '>'
													+ item.tagName
													+ '</a>';
										});
								$('#shoplist-search-div').html(html);
								//TODO 改为活动状态 activityStatus
								// var selectOptions = '<option value="">全部活动</option>';
								// var areaList = data.areaList;
								// areaList.map(function(item, index) {
								// 	selectOptions += '<option value="'
								// 			+ item.areaId + '">'
								// 			+ item.areaName + '</option>';
								// });
								// $('#area-search').html(selectOptions);
							}
						});
	}
	getSearchDivData();

	//展示活动，从后台获取组织查询的活动
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&activityCategoryId=' + activityCategoryId + '&activitystatus=' + activitystatus
				+ '&tagId=' + tagId + '&keyword=' + keyword;
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
				//将卡片集合添加到目标HTML组件里，这里是插入append,不是html，覆盖掉原来的HTML代码
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
	// 预先加载20条
	addItems(pageSize, pageNum);

	// 下滑屏幕自动进行分页搜索，自动触发
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading) //如果正在加载，就不重复加载了
			return;
		addItems(pageSize, pageNum);
	});

	//点击活动的卡片进入该活动的详情页
	$('.shop-list').on('click', '.card', function(e) {
		var activityId = e.currentTarget.dataset.shopId;
		window.location.href = '/activity/getActivityDetail?activityId=' + activityId; //获取活动详情
	});
    //选择新的活动标签之后，重置页码，清空原先的店铺列表，按照新的标签去查询
	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
			//	if (activityCategoryId) {
					// 如果传递过来的是一个父类下的子类
					tagId = e.target.dataset.categoryId;
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
					// 重置页码，由第一页开始
					pageNum = 1;
					// 新加载内容
					addItems(pageSize, pageNum);
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
	//侧边栏工具
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	// 调用init，初始化界面，显示调用
	$.init();
});
