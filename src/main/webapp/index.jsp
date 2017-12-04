<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <!-- For-Mobile-Apps-and-Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="static/bootstrap.min.css" rel="stylesheet">
    <script src="static/jquery-3.2.0.min.js" type="text/javascript"></script>
</head>
<body>
    <div class="container">
        <h2>欢迎来到攀岩大师后台管理页面</h2>
        <div class="panel panel-success">
            <div class="panel-heading">
                <h3 class="panel-title">启动对战模块</h3>
            </div>
            <div class="panel-body">
                是否启动对战模块?&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-default" id="startFight" onclick="startFight()">启动</button>
            </div>
        </div>
    </div>
<script>
    function startFight() {
        alert("启动成功");
        var startButtom = document.getElementById("startFight");
        startButtom.disabled = "disabled";
        $.ajax({
                url:"http://39.106.117.252:8080/climbe/match/startFight.action",
                type:"GET",
                success:function () {
                    alert("对战模块已经启动");
                }
        });
    }
</script>
</body>
</html>
