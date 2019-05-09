$(function () {
    var activityId=getQueryString('activityId');
    var getactivity='/manager/activity/getActivityDetail?activityId='+activityId;

    $.getJSON(getactivity, function (data) {
        if (data.success) {
            var item = data.data;
            var activityhtml = '';
                activityhtml+=' <div class="layui-card">\n' +
                    '        <div class="layui-card-header">' + item.organization.organizationName + '</div>\n' +
                    '        <div class="layui-card-body">\n' +
                    '<p><span>活动简介:</span><span>' + item.activityDesc + ' </span> </p>\n' +
                    '                    <p><span>活动时间:</span><span >' + new Date(item.startTime).Format("yyyy-MM-dd hh:mm") + ' </span>---<span>' + new Date(item.endTime).Format("yyyy-MM-dd hh:mm") + '</span> </p>\n' +
                    '                    <p><span>活动状态:</span><span >' + getActivityStatus(item.status) + '</span> </p>\n' +
                    '                    <p><span>活动经费:</span><span>' + item.minPrice + '￥</span>---<span>' + item.maxPrice + '￥</span> </p>\n' +
                    '                    <p><span>活动类别:</span><span>' + item.category.activityCategoryName + '</span> </p>\n' +
                    '                    <p><span>活动地点:</span><span>' + item.place.placeName + '</span> </p>\n' +
                    '        </div>\n' +
                    '      </div>';
            $('#activity').append(activityhtml);
        } else {
            var errMsg = data.errMsg;
            layer.msg(errMsg);
        }
    });


        // $('#commit').click(function () {
        //     layer.msg('提交成功！');
        // });
        $('#commit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
            //var personInfo = {};//变量，一个json对象
            var checkurl='/manager/activity/check?activityId='+activityId;
            var checkComment = $('#checkComment').val();//前端规定的控件名字userName
            var isPass=$('#isPass').val();
            var formData = new FormData(); //接受表单内容
            formData.append('isPass',isPass);
            formData.append('checkComment',checkComment);
            //用Ajax将表单提交到后台
            $.ajax({
                url : checkurl, //
                type : 'POST', //用post方法提交
                data : formData, //传送formData
                contentType : false,//文件，名字
                processData : false,
                cache : false,
                //回调
                success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台方法，这里data就是返回的json字符串
                    if (data.success) {
                        toastr.info('审核成功！');
                        alert("审核成功");
                        window.location.href = '/manager/frontend/tocheck';
                    }
                    else {
                        var errMsg=data.errMsg;
                        toastr.info('审核失败！'+errMsg);
                       alert("审核失败");
                        window.location.href ='/manager/frontend/tocheck';
                    }
                }
            });
        });

});
