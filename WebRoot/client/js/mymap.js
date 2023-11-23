// 百度地图API功能
var map = new BMap.Map("allmap");            // 创建Map实例                     // 初始化地图,设置中心点坐标和地图级别。
map.addControl(new BMap.ZoomControl());
var point = new BMap.Point(116.404, 39.915);
map.centerAndZoom(point,15);//添加地图缩放控件//添加地图缩放控件

// 定义自定义覆盖物的构造函数
function MyOverlay(center, length,src,click,id){
    this._center = center;
    this._length = length;
    this._src = src;
    this._id = id;
    this._click = click;
}
MyOverlay.prototype = new BMap.Overlay();
MyOverlay.prototype.initialize = function(map){
    this._map = map;
    var div = document.createElement("div");
    var img = document.createElement("img");
    div.style.width=this._length+ "px";
    div.style.height=this._length+ "px";
    div.style.backgroundColor="#444444";
    img.style.width="44px";
    img.style.height="44px";
    img.style.left="3px";
    img.style.top = "3px";
    img.src = this._src;
    div.appendChild(img);
    img.style.position = "absolute"
    div.style.position = "absolute";
    div.name = this._id;
    map.getPanes().markerPane.appendChild(div);
    this._div = div;
    this._div.onclick = this._click;
    return div;
}

MyOverlay.prototype.draw = function(){
    var position = this._map.pointToOverlayPixel(this._center);
    this._div.style.left = position.x - this._length / 2 + "px";
    this._div.style.top = position.y - this._length / 2 + "px";
}

MyOverlay.prototype.show = function(){
    if (this._div){
        this._div.style.display = "";
    }
}
MyOverlay.prototype.hide = function(){
    if (this._div){
        this._div.style.display = "none";
    }
};

function toMap(){
    changePage("mappage");
    PhoneGap.getLocation();
}
