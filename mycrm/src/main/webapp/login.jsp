<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>

    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        $(function () {

            //如果当前窗口不是顶层窗口
            if (window.top != window) {
                //将顶层窗口设置为当前窗口
                window.top.location = window.location;
            }

            //页面加载完成后将用户名清空
            $("#loginAct").val("");

            // 为提交按钮绑定登录事件
            $("#submit").click(function () {
                login();
            });

            // 为页面绑定敲键盘事件
            $(window).keydown(function (event) {
                if (event.keyCode == 13) { //码值为13说明敲得是回车键
                    login();
                }
            });
            // 为用户名输入框绑定失去焦点的检验函数
            $("#loginAct").blur(function () {
                $("#span1").html("");
                var val = this.value;
                if (val == "") {
                    $("#span1").html("<font color='red'>用户名不能为空</font>");
                }
            });

            // 为密码输入框绑定失去焦点的检验函数
            $("#loginPwd").blur(function () {
                $("#span2").html("");
                var val = this.value;
                if (val == "") {
                    $("#span2").html("<font color='red'>密码不能为空</font>");
                }
            });
        });


        function login() {
            var loginAct = $("#loginAct").val();
            var loginPwd = $("#loginPwd").val();

            if (loginAct == "" || loginPwd == "") {
                $("#msg").html("<font color='red'>" + "账号密码不能为空" + "</font>");
                return false;
            }

            $.ajax({
                url: "settings/user/login.do",
                data: {
                    loginAct: loginAct,
                    loginPwd: loginPwd
                },
                dataType: "json",
                type: "post",
                success: function (data) {// {"success":true,"message":?}
                    if (data.success == true) {
                        // 跳转页面
                        window.location.href = "workbench/index.jsp";
                    } else {
                        // 登录失败
                        $("#msg").html("<font color='red'>" + data.message + "</font>");
                    }
                }
            });
        }


    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" id="loginAct" type="text" placeholder="用户名">
                    <span id="span1"></span>
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" id="loginPwd" type="password" placeholder="密码">
                    <span id="span2"></span>
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg"></span>

                </div>
                <button type="button" id="submit" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>