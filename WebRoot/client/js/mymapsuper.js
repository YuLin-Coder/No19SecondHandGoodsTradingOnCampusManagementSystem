/**
 * Created by Bowa on 2015/1/13.
 */
var map = null;
var _userlist = [];
var focusobj = {};
var _count = 0;
var parklist = null;
function initMap(){
    // 百度地图API功能
    map = new BMap.Map("allmap");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(106.547966,29.570746), 14);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.setCurrentCity("重庆");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);    //开启鼠标滚轮缩放

    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    //var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
    //var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL});
    //右上角，仅包含平移和缩放按钮
    //缩放控件type有四种类型:
    // BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*

    //map.addControl(top_left_control);
    //map.addControl(top_left_navigation);
    //map.addControl(top_right_navigation);


    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    map.addControl(navigationControl);
    // 添加定位控件
    var geolocationControl = new BMap.GeolocationControl();
    geolocationControl.addEventListener("locationSuccess", function(e){
        // 定位成功事件
        var address = '';
        address += e.addressComponent.province;
        address += e.addressComponent.city;
        address += e.addressComponent.district;
        address += e.addressComponent.street;
        address += e.addressComponent.streetNumber;
        //alert("当前定位地址为：" + address);
    });
    geolocationControl.addEventListener("locationError",function(e){
        // 定位失败事件
        alert(e.message);
    });
    map.addControl(geolocationControl);


    /**
     * 添加图片标注
     * @type {BMap.Point}
     */
//    var pt = new BMap.Point(116.417, 39.909);
//    var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
//    var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
//    map.addOverlay(marker2);              // 将标注添加到地图中

    //$.post(__APP__+"/User!getUserList", {roletype: 2}, function (data) {
    //    var list = JSON.parse(data);
    //    _userlist = list;
    //    for(var i=0;i<list.length;i++){
    //    	var user = list[i];
    //    	var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(user.longitude,user.latitude), user,function(el){
    //            //alert(el.dataset.json);
    //            var user = JSON.parse(el.dataset.json);
    //            openSendDialog(user);
    //
    //        });
    //    	map.addOverlay(myCompOverlay);
    //    }
    //});
    var parklist = null;
    /*ajaxCallback("listUser",{},function(list){
        parklist = list;
        // 创建地址解析器实例

        for(var i=0;i<list.length;i++){
            var obj = list[i];
            // 将地址解析结果显示在地图上,并调整地图视野
            //if(parklist.length){
                //var obj = parklist[_count];
                addMarket(obj);
            //}


        }
    });*/

    ajaxCallback("listUser",{},function(list){
        parklist = list;
        // 创建地址解析器实例

        for(var i=0;i<list.length;i++){
            var obj = list[i];
             //将地址解析结果显示在地图上,并调整地图视野
            if(parklist.length){
            var obj = parklist[_count];
            addMarket(obj);
            }


        }
    });

}

function addMarket(obj){
    var myGeo = new BMap.Geocoder();
    myGeo.getPoint(obj.address, function(point){
        //var point = new BMap.Point(obj.longitude,obj.latitude);
        if (point) {
            var myCompOverlay = new ComplexCustomOverlay(point, obj,function(el){
                //alert(el.dataset.json);
                var eldata = JSON.parse(el.dataset.json);
                //alert(eldata);
                toUser(eldata.id);
                //openSendDialog(eldata);
            });
            map.addOverlay(myCompOverlay);
            _count++;
            if(_count<parklist.length){
                addMarket(parklist[_count]);
            }
        }
    }, "");
}

/*function addMarket(obj){
    //var myGeo = new BMap.Geocoder();
    //myGeo.getPoint(obj.address, function(point){
    var point = new BMap.Point(obj.longitude,obj.latitude);
    if (point) {
        var myCompOverlay = new ComplexCustomOverlay(point, obj,function(el){
            //alert(el.dataset.json);
            var eldata = JSON.parse(el.dataset.json);
            //alert(eldata);
            toUser(eldata.id);
            //openSendDialog(eldata);
        });
        map.addOverlay(myCompOverlay);
        _count++;
    }
    //}, "深圳市");
}*/

function searchAddress(){
    changePage("mainpage");
    showLoader("请稍候...");
    var address = $("#search").val();
    var myGeo = new BMap.Geocoder();
    // 将地址解析结果显示在地图上,并调整地图视野
    myGeo.getPoint(address, function(point){
        if (point) {
            //map.centerAndZoom(point, 16);
            map.addOverlay(new BMap.Marker(point));
            hideLoader();
        }else{
            alert("您选择地址没有解析到结果!");
        }
    }, "深圳市");
}


// 复杂的自定义覆盖物
function ComplexCustomOverlay(point, jsondata, clickback){
    this._point = point;
    this._jsondata = jsondata;
    this._clickback = clickback;
}
ComplexCustomOverlay.prototype = new BMap.Overlay();
ComplexCustomOverlay.prototype.initialize = function(map){
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.className="mappointer";
    div.id = "mark"+this._jsondata.id;
    div.dataset.json = JSON.stringify(this._jsondata);
    div.style.backgroundImage="url('"+fileurl+this._jsondata.img+"')";
    var callback = this._clickback;
    $(div).touchstart(function(){
        callback && callback(this);
    });
    $(div).click(function(){
        callback && callback(this);
    });
    map.getPanes().labelPane.appendChild(div);
    return div;
};
ComplexCustomOverlay.prototype.draw = function(){
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x+25 + "px";
    this._div.style.top  = pixel.y+100 + "px";
};

function setSearchDiv(){
    var cr = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_TOP_RIGHT});   //设置版权控件位置
    map.addControl(cr); //添加版权控件

    var bs = map.getBounds();   //返回地图可视区域
    cr.addCopyright({id: 1, content: "<div style='position: relative;right: 150px;top:8px;'><input placeholder='输入地址搜索...' id='saddress' /><button onclick='getPointByAddress();'>搜索</button></div>", bounds: bs});
    //Copyright(id,content,bounds)类作为CopyrightControl.addCopyright()方法的参数
}

function getDistanceEarch(pb){
	for(var i=0;i<_userlist.length;i++){
		var user = _userlist[i];
		var pa = new BMap.Point(user.longitude,user.latitude);
		user.distance = getDistanceDrawLine(pa,pb);
	}	
}

function getPointByAddress(address){
    // 创建地址解析器实例
    address = address || $("#saddress").val();
    var myGeo = new BMap.Geocoder();
    // 将地址解析结果显示在地图上,并调整地图视野
    myGeo.getPoint(address, function(point){
        if (point) {
            map.centerAndZoom(point, 16);
            map.addOverlay(new BMap.Marker(point));
            setSearchDiv();
            getDistanceEarch(point);
            var user = getNearest();
            openSendDialog(user);
        }
    }, "");
}

function getNearest(){
	_userlist.sort(function(a,b){
		return parseFloat(a.distance)-parseFloat(b.distance);
	});
	return _userlist[0];
}

function openSendDialog(user){
	focusobj = user;
	$("#personDialog").dialog("open");
	$("#xingming").text(user.username);
	$("#dianhua").text(user.tel);
	if(user.distance){
		$("#zuijin").show();
		$("#zuijin").text("最近的警员,距离:"+user.distance+"米");
	}else{
		$("#zuijin").hide();
	}
}

function getDistanceDrawLine(pa,pb){
    var pointA =pa || new BMap.Point(106.486654,29.490295);  // 创建点坐标A--大渡口区
    var pointB =pb || new BMap.Point(106.581515,29.615467);  // 创建点坐标B--江北区
    //alert((map.getDistance(pointA,pointB)).toFixed(2));  //获取两点距离,保留小数点后两位
    return (map.getDistance(pointA,pointB)).toFixed(2);
    //var polyline = new BMap.Polyline([pointA,pointB], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});  //定义折线
    //map.addOverlay(polyline);     //添加折线到地图上
}


//function getDistances(map,point1,point2){
//    var pointA = point1 || new BMap.Point(106.486654,29.490295);  // 创建点坐标A--大渡口区
//    var pointB = point2 || new BMap.Point(106.581515,29.615467);  // 创建点坐标B--江北区
//    alert('从大渡口区到江北区的距离是：'+(map.getDistance(pointA,pointB)).toFixed(2)+' 米。');  //获取两点距离,保留小数点后两位
//    var polyline = new BMap.Polyline([pointA,pointB], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});  //定义折线
//    map.addOverlay(polyline);     //添加折线到地图上
//}

