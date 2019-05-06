$(function() {
	var activityId = getQueryString('activityId');
	var getNoticeUrl='/activity/getActivityNotice?activityId='+activityId;
	function getNotice() {
		$.getJSON(getNoticeUrl,function (data) {
		if(data.success)
		{
			var noticeList=data.noticeList;
			var noticehtml='';
			noticeList.map(function (item, index) {
				noticehtml += '<div class="card" data-notice-id="'+item.noticeId+'">\n' +
					'\t\t\t\t\t\t<div class="card-header">'+item.title+'<span class="icon icon-down"></span></div>\n' +
					'\t\t\t\t\t\t<div class="card-content">\n' +
					'\t\t\t\t\t\t\t<div class="card-content-inner">'+item.content+'</div>\n' +
					'\t\t\t\t\t\t</div>\n' +
					'\t\t\t\t\t\t<div class="card-footer">'+'<p class="color-gray">'
					+ new Date(item.createTime).Format("yyyy-MM-dd hh:mm")
					+ '发布</p>' +'</div>\n' +
					'\t\t\t\t\t</div>'
			})
			$('#activity-notice').html(noticehtml);
		}
		});
	}
	getNotice();
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
