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
    <link rel="stylesheet" href="static/webuploader.css" />
    <script type="text/javascript" src="static/webuploader.js"></script>
    <script type="text/javascript" src="static/webuploader.js"></script>
    <%--<script type="text/javascript" src="static/js/upload3.js"></script>--%>
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
                <%--<form action="sys/upload.action" enctype="multipart/form-data" method="post" id="uploadForm">
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
                </form>--%>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputtext">版本号</label>
                                <input type="text" id="inputtext" placeholder="1.0">
                        </div>
                    </div>
                    <div id="thelist" class="uploader-list"></div>
                    <div style="margin:20px 20px 20px 0;">
                        <div id="picker" class="form-control-focus">选择文件</div>
                    </div>
                    <button id="btnSync" type="button" class="btn btn-warning">开始同步</button>
            </div>
            <%--<button type="submit" id="uploadBtn" class="btn btn-default" onclick="upload()">上传</button>--%>
    </div>



</body>
<script>
    var uploader = WebUploader.create({

        // swf文件路径
        swf :'static/Uploader.swf',

        // 文件接收服务端。
        server : 'http://39.106.117.252:8080/climbe/sys/upload.action',

        chunked : true,

        threads : 1,

        fromData : {
            version : '1'
        },

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick : '#picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize : false
    });

//    uploader.options.formData.version = document.getElementById('inputtext').value;

    uploader.on( 'uploadBeforeSend', function( block, data ) {
        data.version = document.getElementById('inputtext').value
    },2);

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function(file) {
        //alert(123);
        $("#thelist").append(
            '<div id="' + file.id + '" class="item">'
            + '<h4 class="info">' + file.name + '</h4>'
            + '<p class="state">等待上传...</p>' + '</div>');
    });

    uploader.on('uploadSuccess', function(file) {
        $('#' + file.id).find('p.state').text('已上传');
    });

    uploader.on('uploadError', function(file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function(file) {
        $('#' + file.id).find('.progress').fadeOut();
    });

    $("#btnSync").on('click', function() {
        if ($(this).hasClass('disabled')) {
            return false;
        }
        uploader.options.formData.guid = Math.random();
        uploader.upload();

    });
</script>
</html>
