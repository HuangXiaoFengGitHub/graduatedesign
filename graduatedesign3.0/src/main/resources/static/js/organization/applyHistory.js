$(function () {

        var layer = layui.layer;
        var getCheckActivityUrl = '/organization/activityOperate/getAllActivity/';
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
                        '<p><span>活动状态:</span><span>' + getActivityStatus(item.status) + ' </span> </p>\n'+
                        '<a class="link" href="/organization/frontend/getActivityDetail?activityId=' + item.activityId + '" >查看详情</a>' ;
                    if(item.status==3)
                    {
                        activityhtml+='<p><a class="link layui-bg-green" href="/organization/frontend/tocompose?activityId=' + item.activityId + '" >发布活动</a></p>';
                    }
                    activityhtml += '</div>\n' +
                        '</div>';
                    //通过审核的活动，加一个链接
                });
                $('#activitylist').html(activityhtml);
            } else {
                var errMsg = data.errMsg;
                alert(errMsg);
                toastr.info(errMsg);
            }
        });

});




