$(function () {
        $('#commit').click(function() { //点击提交的时候响应，获取提交的内容，表单提交的属性id是“submit”
            var email=$('#email').val();
            var password=$('#password').val();
            var formData = new FormData(); //接受表单内容
            formData.append('email',email);
            formData.append('password',password);
            //用Ajax将表单提交到后台
            $.ajax({
                url : '/manager/login', //
                type : 'POST', //用post方法提交
                data : formData, //传送formData
                contentType : false,//文件，名字
                processData : false,
                cache : false,
                success : function(data) { //接受到后台返回的data信息，这里要实现定义一个后台方法，这里data就是返回的json字符串
                    if (data.success1) {
                        alert("登陆成功！");
                        window.location.href = '/manager/index';
                    }
                    else {
                        var errMsg=data.errMsg;
                        alert('登录失败！'+errMsg);
                        window.location.href = '/manager/toLogin';
                        //window.location.href ='/user/toRegister';
                    }
                }
            });
        });
});