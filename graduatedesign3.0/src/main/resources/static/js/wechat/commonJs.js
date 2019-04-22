//因为额外引入了webjars-locator，所以相关静态资源的引入可省略版本号便于升级-->
document.write('<script th:src="@{/webjars/jquery/jquery.min.js}"></script>');
document.write('<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>');
//    <!--<link rel="stylesheet" th:href="@{/webjars/bootstrap/css/bootstrap.min.css}"/>-->
 //   <!--引入页面提示工具-->
document.write('<script th:src="@{/build/toastr.min.js}"></script>');
    document.write('<link th:href="@{/build/toastr.css}" rel="stylesheet" />');
    document.write('<link th:href="@{/build/toastr.min.css}" rel="stylesheet" />');

