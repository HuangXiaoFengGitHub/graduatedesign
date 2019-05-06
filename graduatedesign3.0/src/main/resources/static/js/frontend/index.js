$(function() {
    //获取活动类别列表和头条列表
    var url = '/frontend/listMainPageInfo';
    var username='';
   // $.init();
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;//获取活动列表
            var swiperHtml = '';
            headLineList.map(function (item, index) {
                //拼接轮播图
                swiperHtml += ''
                            + '<div class="swiper-slide img-wrap">'
                            + '<a href="'+item.link+'">'
                    //TO DO 这里的imgAddr是相对路径，数据库存储的也是相对路径，需要使用Tomcat的docbase属性配置替换URL
                            + '<img class="banner-img" src="'+ item.imgAddr +'" alt="'+ item.headlineName +'"/>'
                            +'</a>'
                            + '</div>';
            });
            // var mySwiper = new Swiper('.swiper-container', {
            //     speed: 400,
            //     spaceBetween: 100
            // });
            // mySwiper.init();
            // $.init();
            // $.config = {
            //     router: false
            // };
            //赋值控件
            $('.swiper-wrapper').html(swiperHtml);//将轮播图代码添加到控件
           // 设定轮播图的设置
            $(".swiper-container").swiper({
                pagination: '.swiper-pagination',
                paginationClickable: true,
                spaceBetween: 30,
                centeredSlides: true,
                autoplay: 5000, //切换时间
                autoplayDisableOnInteraction: false //将鼠标停在轮播图的时候停止自动播放
            });
            username=data.username;
            if(username==null)
                $('#user-name').text("你尚未登录");
            else
                $('#user-name').text(username);
            var shopCategoryList = data.activityCategoryList; //获取活动类别
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                //拼接活动类别展示的URL
                categoryHtml += ''
                             +  '<div class="col-50 shop-classify" data-category='+ item.activityCategoryId +'>'
                             +      '<div class="word">'
                             +          '<p class="shop-title">'+ item.activityCategoryName +'</p>'
                             +          '<p class="shop-desc">'+ item.activityCategoryDesc +'</p>'
                             +      '</div>'
                             +      '<div class="shop-classify-img-warp">'
                             +          '<img class="shop-img" src="'+ item.imgAddr +'">'
                             +      '</div>'
                             +  '</div>';
            });
            $('.activity-category').html(categoryHtml);//将活动类别展示的代码添加到控件
        }
    });
//侧边栏响应事件
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
//给类别添加响应事件，当点击活动列表的时候，获取对应的类别,查看对应的列表
    $('.activity-category').on('click', '.shop-classify', function (e) {
        var activityCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/frontend/activityList?activityCategoryId=' + activityCategoryId; //活动类别详情URL
        window.location.href = newUrl; //跳转到活动类别展示界面
    });

});

