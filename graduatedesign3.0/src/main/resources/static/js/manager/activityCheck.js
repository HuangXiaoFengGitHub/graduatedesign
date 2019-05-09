$(function () {
        var getCheckActivityUrl = '/manager/activity/getCheckActivity';
        $.getJSON(getCheckActivityUrl, function (data) {
            if (data.success) {
                var activityList = data.activityList;
                var activityhtml = '';
                activityList.map(function (item, index)
                {
                    activityhtml+=' <div class="layui-card">\n' +
                        '        <div class="layui-card-header">' + item.activityName + '</div>\n' +
                        '        <div class="layui-card-body">\n' +
                        '<p><span>活动简介:</span><span>' + item.activityDesc + ' </span> </p>\n' +
                    '                    <a class="link" href="/manager/frontend/checking?activityId=' + item.activityId + '" >查看详情</a>' +
                        '        </div>\n' +
                        '      </div>';
                });
                $('#activitylist').append(activityhtml);
            } else {
                var errMsg = data.errMsg;
                alert(errMsg);
            }
        });

});




