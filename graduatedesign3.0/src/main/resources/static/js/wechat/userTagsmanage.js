$(function() {
	var userId = 1;
	//var listUrl = '/user/getMyTags?userId=' + userId;
	var listUrl = '/user/tags/getMyTags';
	var addUrl = '/user/tags/addTags';
	var deleteUrl = '/user/tags/removeTags';

	$.getJSON(listUrl, function(data) {
						if (data.success) {
							var dataList = data.data;
							$('.category-wrap').html('');
							var tempHtml = '';
							dataList.map(function(item, index) {
										tempHtml += ''
												+ '<div class="row row-product-category now">'
												+ '<div class="col-33 product-category-name">'
												+ item.tagName
												+ '</div>'
												+ '<div class="col-33">'
												+ item.priority
												+ '</div>'
												+ '<div class="col-33"><a href="#" class="button delete" data-id="'
												+ item.tagId
												+ '">删除</a></div>' + '</div>';
									});
							$('.category-wrap').append(tempHtml);
						}
						else
						{
							$('#error-message').text(data.errorMsg);
						}
					});

	function getList() {
		$.getJSON(listUrl,function(data) {
							if (data.success) {
								var dataList = data.data;
								$('.category-wrap').html('');
								var tempHtml = '';
								dataList
										.map(function(item, index) {
											tempHtml += ''
													+ '<div class="row row-product-category now">'
													+ '<div class="col-33 product-category-name">'
													+ item.tagName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.tagId
													+ '">删除</a></div>'
													+ '</div>';
										});
								$('.category-wrap').append(tempHtml);
							}
							else {
								$('#error-message').text(data.errorMsg);
							}
						});
	}
	//对应点击提交的事件
	//getList();
	$('#submit').click(function() {
		var tempArr = $('.temp'); //遍历新增的行（class）
		var tags = [];
		//
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.tagName = $(item).find('.category').val(); // .category控件
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.tagName && tempObj.priority) {
				tags.push(tempObj);
			}
		});
		//使用ajax提交到批量添加的按钮
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(tags),  //以json格式返回列表字符串
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					getList();
				} else {
					$.toast('提交失败！');
				}
			}
		});
	});
// 对应新增的功能
	$('#new').click(
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="标签名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);
					});
//对应删除功能的事件,这里的selector 对应div的class参数，
	$('.category-wrap').on('click', '.row-product-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定么?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							tagId : target.dataset.id,
							userId : userId
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getList();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
				// $.confirm({
				// 	title: 'What is up?',
				// 	content: '确定吗？',
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
				// 					data : {
				// 						tagId : target.dataset.id,
				// 						userId : userId
				// 					},
				// 					dataType : 'json',
				// 					success : function(data) {
				// 						if (data.success) {
				// 							toastr.info('删除成功！');
				// 							getList();
				// 						} else {
				// 							toastr.info('删除失败！');
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
			});
// 对应删除功能的事件，这里的selector对应是新增的类别
	$('.category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
});