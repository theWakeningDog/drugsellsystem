<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Created by dongls on 2016/10/12 16:54. -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="//webapi.amap.com/ui/1.0/ui/misc/MarkerList/examples/"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>工单指派</title>
    <link rel="stylesheet" href="//cache.amap.com/lbs/static/main1119.css"/>
</head>
<body>
<style type="text/css">
    html, body {
        height: 100%
    }

    #container {
        height: 92%
    }
</style>
<div class="panel" style="margin:0!important;">
    <div class="panel-heading bg-info" style="height: 50px;">
        <span class="label label-warning">已创建</span>
        <span>工单#${task.taskNo}</span>
        <div class="btn-group pull-right">
            <a class="btn btn-default btn" href="javascript:backOperater();">
                <li class="fa fa-angle-left"></li>
                &nbsp;&nbsp;返回
            </a>
            <%--<button type="button" class="btn btn-default btn-xs" aria-label="Right Align" id="outdoor_back">--%>
            <%--<span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>&nbsp;返回--%>
            <%--</button>--%>
            <button type="button" onclick="noUserAllot()" class="btn btn-success btn" title="地图中无人选请继续！"
                    aria-label="Light Align">
                继续&nbsp;<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
            </button>

        </div>
    </div>
</div>
<div id="container"></div>

<%--&callback=init--%>
<script src="//webapi.amap.com/maps?v=1.3&key=9a39887aae6a01141f66e4c4a1edbd81"></script>
<script type="text/javascript" src="//cache.amap.com/lbs/static/addToolbar.js"></script>
<!-- UI组件库 1.0 -->
<script src="//webapi.amap.com/ui/1.0/main.js"></script>
<script type="text/javascript">
    var userList = ${userList};
    var map, marker;
    //经纬度
    var jsAdLatitude = "${task.customer.customerAddress.adLatitude}";
    var jsAdLongitude = "${task.customer.customerAddress.adLongitude}";
    var center = [jsAdLongitude, jsAdLatitude];
    if (jsAdLongitude == "" || jsAdLatitude == "") {
        center = [116.397428, 39.90923];
    }
    //    $(document).ready(function() {
    //        var script = document.createElement_x("script");
    //        script.type = "text/javascript";
    //        script.src = "https://webapi.amap.com/maps?v=1.3&key=9a39887aae6a01141f66e4c4a1edbd81&callback=init";
    //        document.body.appendChild(script);
    //        $.ajax({
    //            type: 'get',
    //            url: "/task/user/list",
    //            success: function (data) {
    //                userList = data;
    //                makeMarker();
    //            }
    //        });
    //    });


    //初始化
    //    function init(){
    map = new AMap.Map('container', {
        resizeEnable: true,
        center: center,
        zoom: 10
    });
    if (jsAdLatitude != "" && jsAdLongitude != "") {
        var marker1 = new AMap.Marker({ //添加自定义点标记
            map: map,
            position: center, //基点位置
            offset: new AMap.Pixel(-17, -42), //相对于基点的偏移位置
            //draggable: false,  //是否可拖动
            //animation: "AMAP_ANIMATION_BOUNCE",//动画效果
            //label: {content: "客户地址", offset: new AMap.Pixel(0, -20)}
            content: '<img width="24" height="24" src="/resource/images/customerAddress.png"/>' +
            '<br><div class="marker-route marker-marker-bus-from" style="position: absolute; left: -12px; width: 100px; font-size: 14px; font-weight: 700; color: red;">客户地址</div>'   //自定义点标记覆盖物内容
        });
        marker1.on("click", function (e) {
            var customerInfo = '<div style="height:100px;width: 220px">' +
                '<div style="overflow: hidden;width:220px;height: 100px; border-radius: 5px;float:left;margin-right:20px">' +
                '<h4  style="margin-top:5px; margin-bottom:5px">${task.customer.name}</h4>' +
                '<p>电话：${task.customer.lmPhone}&nbsp;&nbsp;</p>' +
                '<span style="display:inline-block">地址：${task.customer.customerAddress.adCountry}${task.customer.customerAddress.adProvince}${task.customer.customerAddress.adCity}${task.customer.customerAddress.adAddress}</span>' +
                '</div>' +
                '</div>';
            var infoWindow = new AMap.InfoWindow({
                showShadow: true,
                content: customerInfo, //使用默认信息窗体框样式，显示信息内容
                offset: new AMap.Pixel(0, -20)
            });
            infoWindow.open(map, e.target.getPosition());
        });
    }
    //        makeMarker();
    //    }
    var markers = [];
    if (userList != undefined && userList.length > 0) {
        for (var i = 0; i < userList.length; i += 1) {
            var lng = userList[i].longitude;
            var lat = userList[i].latitude;
            if (lng != undefined && lat != undefined) {
                marker = new AMap.Marker({
                    position: [lng, lat],
                    title: userList[i].displayName,
                    map: map,
                    extData: userList[i]
                });
                markers.push(marker);
                marker.on("click", function (e) {
                    //这才是每个点的坐标，不是marker.getPostion
                    //console.log(e.target.getPosition());
                    //console.log(e.target.getExtData());
                    var user = e.target.getExtData();
                    console.log(user);
                    var accept = 0;//未完成任务
                    var finish = 0;//已完成任务
                    <c:forEach items="${tasks}" var="workTask">
                    if (user.userId == "${workTask.executor}") {
                        if ("${workTask.state}" == "accepted" && "${formatDate == workTask.planTime}") {
                            //accept += ${workTask.taskCount};
                            accept++;
                        } else if (("${workTask.state}" == "finished" || "${workTask.state}" == "costed" || "${workTask.state}" == "closed") && "${formatDate == workTask.completeTime}") {
                            //finish += ${workTask.taskCount};
                            finish++
                        }
                    }
                    </c:forEach>
                    var phone = user.cellPhone || "";
                    var lastLoginTime = "";
                    <c:forEach items="${dauList}" var="dau">
                    if ("${dau.userId}" == user.userId) {
                        var loginTime = "${dau.loginTime}";
                        lastLoginTime = /\d{4}-\d{1,2}-\d{1,2}/g.exec(loginTime) != null ? /\d{4}-\d{1,2}-\d{1,2}/g.exec(loginTime) : "";
                    }
                    </c:forEach>
                    //var lastLoginTime = /\d{4}-\d{1,2}-\d{1,2}/g.exec(user.lastLoginTime) != null ? /\d{4}-\d{1,2}-\d{1,2}/g.exec(user.lastLoginTime) : "";
                    var head = user.head;
                    if (head == null || head == "") {
                        head = "/resource/images/account_userhead.png";
                    }
                    var infoHtml = '<div style="height:70px;width: 210px">' +
                        '<div  style="overflow: hidden;width: 50px;height: 70px; border-radius: 5px;float:left;margin-right:20px">' +
                        '<img headid="' + user.userId + '" style="padding:0px;width: 50px;height: auto;"  alt="" src="' + head + '" >' +
                        '</div>' +
                        '<div style="overflow: hidden;width:120px;height: 70px; border-radius: 5px;float:left;margin-right:20px">' +
                        '<h4  style="margin-top:5px; margin-bottom:5px"><a href="javascript:" onclick="allotUser(this);" data-id="' + user.userId + '">' + user.displayName + '</a></h4>' +
                        '<p>登陆时间:' + lastLoginTime + '</p>' +
                        '<p>已：' + finish + '&nbsp;&nbsp;未：' + accept + '</p>' +
                        '</div>' +
                        '</div>';
                    var infoWindow = new AMap.InfoWindow({
                        showShadow: true,
                        content: infoHtml, //使用默认信息窗体框样式，显示信息内容
                        offset: new AMap.Pixel(0, -20)
                    });
                    infoWindow.open(map, e.target.getPosition());
                });
            }
        }
    }

    //给工程师派单
    function allotUser(ele) {
        if (confirm("你确定将此单派给此员工吗？")) {
            window.location.href = "/task/view/${task.id}?userId=" + ele.dataset.id + "&allot=" + true;
        }
    }

    function noUserAllot() {
        location.href = "/task/view/${task.id}?allot=" + true;
    }

    //    AMapUI.loadUI(['overlay/SimpleInfoWindow'], function(SimpleInfoWindow) {

    //        var marker = new AMap.Marker({
    //            map: map,
    //            zIndex: 9999999,
    //            position: map.getCenter()
    //        });

    //        var infoWindow = new SimpleInfoWindow({
    //
    //            infoTitle: '<strong>这里是标题</strong>',
    //            infoBody: '<p class="my-desc"><strong>这里是内容。</strong> <br/> 高德地图</p>',
    //
    //            //基点指向marker的头部位置
    //            offset: new AMap.Pixel(0, -31)
    //        });
    //
    //        function openInfoWin() {
    //            infoWindow.open(map, marker.getPosition());
    //        }
    //
    //        //marker 点击时打开
    //        AMap.event.addListener(marker, 'click', function(result) {
    //            var info = [];
    //            info.push("a");
    //            var inforWindow = new AMap.InfoWindow({
    //                content:info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容
    //            });
    //            //inforWindow.open(map, new AMap.LngLat(117, 36));
    //            inforWindow.open(map, marker.getPosition());
    ////            for (var i = 0; i < userList.length; i++) {
    ////                var user = userList[i];
    ////                if (user.longitude != undefined && user.latitude) {
    ////                    info.push(user.displayName);
    ////                    var inforWindow = new AMap.InfoWindow({
    ////                        content:info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容
    ////                    });
    ////                    inforWindow.open(map, new AMap.LngLat(user.longitude, user.latitude));
    ////                }
    ////            }
    //            //openInfoWin();
    //        });
    //
    //        //openInfoWin();
    //    });

    //    AMap.event.addListener(markers, 'click', function(result) {
    //        console.log(result);
    //        var clouddata = userList;
    ////        var photo = [];
    ////        if (clouddata._image[0]) {//如果有上传的图片
    ////            photo = ['<img width=240 height=100 src="' + clouddata._image[0]._preurl + '"><br>'];
    ////        }
    //        var infoWindow = new AMap.InfoWindow({
    //            content: "更新时间：",
    //            size: new AMap.Size(0, 0),
    //            autoMove: true,
    //            offset: new AMap.Pixel(0, -25)
    //        });
    //        infoWindow.open(map, clouddata._location);
    //    });


    <%--var userList;
    var jsAdLatitude = "${task.customer.customerAddress.adLatitude}";
    var jsAdLongitude = "${task.customer.customerAddress.adLongitude}";
    var markersArray = [];
    window.onload = function () {
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = "http://map.qq.com/api/js?v=2.exp&callback=init";
        document.body.appendChild(script);
        $.ajax({
            type: 'get',
            url: "/task/user/list",
            success: function (data) {
                userList = data;
                clearOverlays();
                setCenter();
                makeMarker();
            }
        });
    };

    //初始化地图
    var map;
    //渲染下拉列表框
    function RenderingSelectTag(controlDiv) {
        controlDiv.style.padding = "3px 1px 3px 1px";
        controlDiv.style.margin="2px";
        controlDiv.style.width="360px";
        controlDiv.index = 1;//设置在当前布局中的位置
        //function update() {
        var htmlSelectTag = "";
        htmlSelectTag += '<div class="panel" style="margin: 0!important;">';
        htmlSelectTag += '<div class="panel-heading bg-info">';
        htmlSelectTag += '<span class="label label-info">${p:stateDisplayName(task.state, "cn.publink.servicemobile.base.entity.EnumTaskTarget") }</span><span>&nbsp;&nbsp;${task.name}</span>';
        htmlSelectTag += '<div class="btn-group pull-right">';
        htmlSelectTag += '<button type="button" class="btn btn-default btn-xs" aria-label="Right Align" id="outdoor_back" onclick="backOperater()">';
        htmlSelectTag += '<span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>&nbsp;取消';
        htmlSelectTag += '</button>';
        htmlSelectTag += '<button type="button" class="btn btn-success btn-xs" id="noUserAllot" aria-label="Light Align" onclick="noUserAllot()">';
        htmlSelectTag += '继续&nbsp;<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>';
        htmlSelectTag += '</button>';
        htmlSelectTag += '</div>';
        htmlSelectTag += '</div>';
        htmlSelectTag += '</div>';
        controlDiv.innerHTML = htmlSelectTag;
//        }
//        update();
    }

    function init() {
        //定义以客户为中心点
        //var center = new qq.maps.LatLng("${task.customer.customerAddress.adLatitude}", "${task.customer.customerAddress.adLongitude}");
        var center = new qq.maps.LatLng(39.916527,116.397128);
        if (jsAdLatitude != undefined && jsAdLongitude != undefined) {
            center = new qq.maps.LatLng(jsAdLatitude, jsAdLongitude);
        }
        //获取dom元素添加地图信息，设置全部setCenter需要使用
        map = new AMap.Map('container', {
            resizeEnable: true,
            zoom:11,
            center: center
        });
        var customSelectTagDiv = document.createElement("div");
        //获取控件接口设置控件
        var renderingSelectTag = new RenderingSelectTag(customSelectTagDiv);
        //将控件添加到controls栈变量并将其设置在顶部位置
        map.controls[qq.maps.ControlPosition.TOP_CENTER].push(customSelectTagDiv);
        setCenter();
        makeMarker();
    }

    function setCenter() {
        if(map==undefined){
            return;
        }
        //panTo改变中心点的位置
        map.panTo(new qq.maps.LatLng("${task.customer.customerAddress.adLatitude}", "${task.customer.customerAddress.adLongitude}"));
        //设置中心点
        var anchor = new qq.maps.Point(8, 8),
            size = new qq.maps.Size(24, 26),
            origin = new qq.maps.Point(0, 0),
            icon = new qq.maps.MarkerImage('/resource/images/customer.png',size,(0,0),(0,0),new qq.maps.Size(24,39));
        var marker = new qq.maps.Marker({
            icon: icon,
            map: map,
            position: map.getCenter()
        });

        var info = new qq.maps.InfoWindow({
            map: map
        });

        //获取标记的点击事件
        qq.maps.event.addListener(marker, 'click', function() {
            info.open();
            info.setContent('<div style="text-align:center;white-space:nowrap;"margin:10px;">'+$("#teamSelect").find("option:selected").text()+'</div>');
            info.setPosition(new qq.maps.LatLng(jsAdLatitude, jsAdLongitude));
            setTimeout(function() {
                info.close();
            }, 5 * 1000);
        });

        markersArray.push(marker);


    }

    //添加覆盖层
    function makeMarker() {
        if(map==undefined){return;}
        if (userList != undefined && userList.length > 0) {
            //循环添加坐标点 空座标比作添加
            for (var i = 0; i < userList.length; i++) {
                if (userList[i].latitude == null || userList[i].longitude == null) {
                    continue;
                }
                var marker = new qq.maps.Marker({
                    position: new qq.maps.LatLng(userList[i].latitude, userList[i].longitude),
                    map: map,
                    icon: new qq.maps.MarkerImage("/resource/images/engineer_0.png",new qq.maps.Size(24,39),(0,0),(0,0),new qq.maps.Size(24,39)),
                    shadow: new qq.maps.MarkerImage("/resource/images/engineer_0.png",new qq.maps.Size(24,39),(0,0),(0,0),new qq.maps.Size(24,39)),
                    "index1":i
                });
                //添加到提示窗
                var info = new qq.maps.InfoWindow({
                    map: map
                });

                //获取标记的点击事件
                qq.maps.event.addListener(marker, 'click', function(event) {
                    var user = userList[event.target.index1];
                    info.open();
                    info.setContent(createEngineerMapInfo(user.displayName, user.cellPhone, user.head, user.userId));
                    info.setPosition(new qq.maps.LatLng(userList[event.target.index1].latitude, userList[event.target.index1].longitude));
                    setTimeout(function() {
                        info.close();
                    }, 8 * 1000);
                });
                markersArray.push(marker);
            }
        }
    }

    function createEngineerMapInfo(name, cellPhone, head, userId){
        var phone = cellPhone || "";
        if(head == ""){
            head = "/resource/images/account_userhead.png";
        }else{
            head = "/files/get?fileId="+head;
        }
        var html = '<div style="height:70px;width: 210px">' +
            '<div  style="overflow: hidden;width: 50px;height: 70px; border-radius: 5px;float:left;margin-right:20px">' +
            '<img headid="'+userId+'" style="padding:0px;width: 50px;height: auto;"  alt="" src="'+head+'" >' +
            '</div>' +
            '<div style="overflow: hidden;width:120px;height: 70px; border-radius: 5px;float:left;margin-right:20px">' +
            '<h4  style="margin-top:5px; margin-bottom:5px"><a href="javascript:" onclick="allotUser(this);" data-id="'+userId+'">' + name + '</a></h4>' +
            '<p>电话:' + phone + '&nbsp;&nbsp;</p>' +
            '</div>' +
            '</div>';
        return html;
    }

    //清除覆盖层
    function clearOverlays() {
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setMap(null);
            }
        }
        markersArray = [];
    }

    //给工程师派单
    function allotUser(ele) {
        if (confirm("你确定将此单派给此员工吗？")) {
            window.location.href="/task/view/${task.id}?userId=" + ele.dataset.id + "&allot=" + true;
        }
    }

    function noUserAllot() {
        location.href="/task/view/${task.id}?allot=" + true;
    }--%>
</script>
</body>
</html>
