$(function () {
    var activityId = getQueryString('activityId');
    var posturl='/user/comment/post?activityId='+activityId;
    var comment='';
    //var registerUrl = '/user/userRegister';//后台方法,在controller层中实现，并写出对应的mappering
    $('#submit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
        comment=$('#comment').val();
        var formData = new FormData(); //接受表单内容
        formData.append('comment', comment);//传输参数
        var verifyCodeActual = $('#j_captcha').val(); //接收验证码，验证码的控件id是j_captcha
        if (comment=='') {
            $.toast('评论不能为空');
            return;
        }
        //用Ajax将表单提交到后台
        $.ajax({
            url : posturl, //
            type : 'POST', //用post方法提交
            data : formData, //传送formData
            contentType : false,//文件，名字
            processData : false,
            cache : false,
            //回调
            success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台方法，这里data就是返回的json字符串
                if (data.success) {
                    $.toast('提交成功！');
                    window.location.href = '/frontend/activity/getComment?activityId='+activityId;
                }
                else {
                    var errMsg=data.errMsg;
                    $.toast('提交失败！'+errMsg);
                }
            }
        });
    });
});