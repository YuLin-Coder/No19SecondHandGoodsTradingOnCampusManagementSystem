<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head id="Head1">
<%@ include file="/web/common/common.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>二手闲鱼</title>
<style type="text/css">
        body{
            padding: 0;
            margin: 0;
        }
        .page{
            padding: 0;
            width: 100%;
            margin-left: auto;
            margin-right: auto;
        }
        .titlebar{
            height: 90px;
            width: 100%;
            background-color: #ffffff;
            min-width: 670px;
        }
        .logodiv{
            width: 30%;
            height: 50px;
            float: left;
        }
        .homelogo{
            width: 80px;
            height: 80px;
            display: inline-block;
            float: left;
            margin-left: 30px;
            margin-top: 5px;
        }
        .containerarea{
            background-color: #EDF0F2;
            width: 100%;
            height: 1080px;
            float: right;
        }

        .logincontainer{
            height: 430px;
            width: 450px;
            margin: 60px auto;
            background-color: #FFFFFF;
        }
        .loginheader{
            height: 58px;
            width: 100%;
            background-color: #677381;
            text-align: center;
            line-height: 58px;
            font-size: 25px;
            color: #ffffff;
        }
        .loginlogo{
            width: 80px;
            height: 80px;
            background-image: url('<%=__APP__ %>/web/public/images/login/logo.png');
            margin: 20px auto 0 auto;
            background-size: 100% 100%;
        }
        .welcomlabel{
            color: #677381;
            width: 302px;
            height: 25px;
            font-size: 14px;
            margin: 0 auto;
        }

        .logininput{
            width: 330px;
            height: 60px;
            margin: 0 auto;
            display: block;
            border: 0;
            background-color: #F4F4F4;
            outline: none;
            font-size: 18px;
            color: #888888;
            padding-left: 60px;
            background-size: 26px 26px;
            background-position: 20px 20px;
            background-repeat: no-repeat;
        }
        .lkeypic{
            background-image: url('<%=__APP__ %>/web/public/images/login/lkey.png');
        }
        .userpic{
            background-image: url('<%=__APP__ %>/web/public/images/login/user.png');
        }

        .loginbtn{
            width: 268px;
            height: 48px;
            display: block;
            background-color: #51D2B7;
            margin: 10px auto;
            text-align: center;
            line-height: 48px;

            text-decoration: none;
            color: #fff;
            cursor: pointer;
        }
        .bottom{
            height: 160px;
            width: 100%;
            background-color: #1B9FCB;
            clear: both;
        }
        .copyriht{
            width:100%;text-align: center;padding-top: 50px;color: #222222;
        }

        @-webkit-keyframes shake {
            0%, 100% {-webkit-transform: translateX(0);}
            10%, 30%, 50%, 70%, 90% {-webkit-transform: translateX(-10px);}
            20%, 40%, 60%, 80% {-webkit-transform: translateX(10px);}
        }

        @-moz-keyframes shake {
            0%, 100% {-moz-transform: translateX(0);}
            10%, 30%, 50%, 70%, 90% {-moz-transform: translateX(-10px);}
            20%, 40%, 60%, 80% {-moz-transform: translateX(10px);}
        }

        @-o-keyframes shake {
            0%, 100% {-o-transform: translateX(0);}
            10%, 30%, 50%, 70%, 90% {-o-transform: translateX(-10px);}
            20%, 40%, 60%, 80% {-o-transform: translateX(10px);}
        }

        @keyframes shake {
            0%, 100% {transform: translateX(0);}
            10%, 30%, 50%, 70%, 90% {transform: translateX(-10px);}
            20%, 40%, 60%, 80% {transform: translateX(10px);}
        }

        .shake {
            -webkit-animation-name: shake;
            -moz-animation-name: shake;
            -o-animation-name: shake;
            -webkit-animation-duration: .5s;
            -moz-animation-duration: .5s;
            -o-animation-duration: .5s;
            animation-name: shake;
            animation-duration: .5s;
        }


        .zoom {
            -webkit-animation-name: zoom;
            -webkit-animation-duration: 20000ms;
            -webkit-animation-timing-function: linner;
            -webkit-animation-iteration-count:infinite;

            -o-animation-name: zoom;
            -o-animation-duration: 20000ms;
            -o-animation-timing-function: linner;
            -o-animation-iteration-count:infinite;

            -moz-animation-name: zoom;
            -moz-animation-duration: 20000ms;
            -moz-animation-timing-function: linner;
            -moz-animation-iteration-count:infinite;

            animation-name: zoom;
            animation-duration: 20000ms;
            animation-timing-function: linner;
            animation-iteration-count:infinite;
        }
        @-webkit-keyframes zoom {
            0% {
                -webkit-transform: scale3d(1,1,1);
            }
            5% {
                -webkit-transform: scale3d(1.5,1.5,1);
            }
            100% {
                -webkit-transform: scale3d(1.8,1.8,1);
            }
        }
        @-moz-keyframes zoom {
            0% {
                -moz-transform: scale3d(1,1,1);
            }
            5% {
                -moz-transform: scale3d(1.5,1.5,1);
            }
            100% {
                -moz-transform: scale3d(1.8,1.8,1);
            }
        }
        @-o-keyframes zoom {
            0% {
                -o-transform: scale3d(1,1,1);
            }
            5% {
                -o-transform: scale3d(1.5,1.5,1);
            }
            100% {
                -o-transform: scale3d(1.8,1.8,1);
            }
        }

        @keyframes zoom {
            0% {
                transform: scale3d(1,1,1);
            }
            5% {
                transform: scale3d(1.5,1.5,1);
            }
            100% {
                transform: scale3d(1.8,1.8,1);
            }
        }
    </style>
<script type="text/javascript">

if(top.location!=self.location) top.location=self.location;

var loginuserinfo =  localStorage["loginuserinfo"];
var isremember = localStorage['isremember'];

if(loginuserinfo){
    var info = JSON.parse(loginuserinfo);
    $("#passwd").val(info.passwd);
    $("#username").val(info.username);
}

if(isremember=="1"){
    $("#remember").attr("checked","checked");
}

document.onkeydown=function (e){
    e = e ? e : event;
    if(e.keyCode == 13){
        login();
    }
};
    function login() {
        var username = $("#username").val();
        var passwd = $("#passwd").val();
        if(trim(username) == "" || trim(passwd) == ""){
            //$.messager.alert("提示", '请输入user name和密码！');
            $("#logindialog").addClass("shake");
            setTimeout(function(){
                $("#logindialog").removeClass("shake");
            },500);
            return;
        }
        $("#logindialog").addClass("zoom");
        $("#loginimg").hide();
        $.post(__APP__+"/Index!login",{username:username,passwd:passwd},function(data){
            closeBackGround();
            //data = JSON.parse(data);
            if (data != "success") {
                $("#logindialog").removeClass("zoom");
                $("#logindialog").addClass("shake");
                setTimeout(function(){
                    $("#logindialog").removeClass("shake");
                },500);
                //$.messager.alert("提示", 'user name或密码错误！');
                $("#loginimg").show();
            } else {
                //$.messager.alert("提示", '登录成功！');
                var flag = $("#remember").is(":checked");
                if(flag){
                    var loginobj = {};
                    loginobj.username = username;
                    loginobj.passwd = passwd;
                    localStorage["loginuserinfo"] = JSON.stringify(loginobj);
                    localStorage["isremember"] = "1";
                }else{
                    localStorage.removeItem("loginuserinfo");
                    localStorage.removeItem("isremember");
                }
                window.location.href = __APP__+"/Index!index";
            }
        });
    }


        </script>
</head>
<body>
	<div class="page">
    <header class="titlebar">
        <div class="logodiv">
            <%-- <img src="<%=__APP__ %>/web/public/images/logo.png" class="homelogo"> --%>
        </div>
    </header>
    <section class="containerarea" id="rightarea">
        <div class="logincontainer" id="logindialog">
            <div class="loginheader">校园二手物品交易APP</div>
            <!-- <div class="loginlogo"></div> -->
            <div class="welcomlabel"></div>
            <form>
                <input id="username" name="username" value="" class="logininput userpic" type="text" placeholder="用户名" style="border-bottom: 1px #CCCCCC solid;">
                <input id="passwd" name="passwd" class="logininput lkeypic" value="" type="password" placeholder="密码">

                <div style="margin: 10px 0 0 27px;">
                    <input style="width: 20px;height: 20px;" type="checkbox" value="1" id="remember">
                    <label for="remember" style="position: relative;top: -5px;color: #888888;">
                        记住密码
                    </label>
                </div>


                <div class="loginbtn" onclick="login();">登录</div>
            </form>
        </div>
    </section>
    <footer class="bottom">
        <div class="copyriht"></div>
    </footer>
</div>
</body>
</html>