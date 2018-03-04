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
        <br/>
        <%--<div class="panel panel-success">
            <div class="panel-heading">
                <h3 class="panel-title">启动对战模块</h3>
            </div>
            <div class="panel-body">
                是否启动对战模块?&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-default" id="startFight" onclick="startFight()">启动</button>
            </div>
        </div>--%>

            <div class="panel panel-success">
                <form action="sys/upload.action" enctype="multipart/form-data" method="post" id="uploadForm">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="exampleInputEmail1">版本号</label>
                        <input type="text" class="form-control" id="exampleInputEmail1" name="version" placeholder="1.0">
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputFile">游戏安装包上传</label>
                    <input type="file" id="exampleInputFile" name="file" accept=".apk">
                    <p class="help-block">选择要上传的apk</p>
                </div>
                </form>
            </div>
            <button type="submit" id="uploadBtn" class="btn btn-default" onclick="upload()">上传</button>
    </div>
</body>
<script>
    function upload(){
        document.getElementById("uploadForm").submit();
    }
</script>
</html>
