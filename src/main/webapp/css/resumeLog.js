// /**
//  * Created by asus-pc on 2017/4/30.
//  */
$(window).load(function () {
    //刷新页面让其停留在顶部
    $(function(){
        setTimeout(function(){window.scrollTo(0,0);}, 50);
    });
    var centerTop=($(window).height()-$("#login,#signin").height())/2;
    var centerLeft=($(window).width()-$("#login,#signin").width())/2;
    $("#login,#signin").css('left',centerLeft+"px").css('top',centerTop+"px");
    $(window).resize(function () {
        var centerTop=($(window).height()-$("#login,#signin").height())/2;
        var centerLeft=($(window).width()-$("#login,#signin").width())/2;
        $("#login,#signin").css('left',centerLeft+"px").css('top',centerTop+"px");
    });
    //登录页面的消失与出现
    $(".logIn").click(function () {
        $("#login").css('display','block')
        if($("#signin").css('display','block')){
            $("#signin").css('display','none')
        }
    });
    $(".login-close").click(function () {
        $("#login").css('display','none')
    });
    $(".login-buttonLogin").click(function () {
        $("#login").css('display','none')
    });
    //注册界面的消失与出现
    $(".signIn").click(function () {
        $("#signin").css('display','block')
        if($("#login").css('display','block')){
            $("#login").css('display','none')
        }
    });
    $(".signin-close").click(function () {
        $("#signin").css('display','none')
    });
    $(".signin-buttonSign").click(function () {
        $("#signin").css('display','none')
    });
    //倒计时10s的实现
    $(" .verify span").click(function () {
        $(".verify span").css('display','none');
        $(".verify-again").css('display','block');
        var $time=$(".verify-again span");
        var time=10;
        timeOut();
        function timeOut() {
            if($time.text()==0){
                $(".verify span").css('display','block');
                $(".verify-again").css('display','none');
            }
            else if($time.text()<=10){
                setTimeout(function () {
                    time--;
                    $(".verify-again span").text(time);
                    timeOut();
                }
                ,1000)
            }

        }
    });
});
