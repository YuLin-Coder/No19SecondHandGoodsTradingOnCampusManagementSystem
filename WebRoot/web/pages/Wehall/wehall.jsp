<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head id="Head1">
<meta name="viewport" content="width=320; user-scalable=no"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<link href="<%=path %>/web/public/css/jquery.mobile.min.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/web/public/js/jquery.min.js" type="text/javascript"></script>
<script src="<%=path %>/web/public/js/jquery.mobile.min.js" type="text/javascript"></script>
<script type="text/javascript">
var focusId = "";
var bill = [];
var goods = null;
var total = 0;
var totalprice = 0;
var __APP__ = "<%=path%>";

function findGoodById() {
    for (var i = 0; i < goods.length; i++) {
        if (goods[i].id == focusId) {
            return goods[i];
        }
    }
}
function findById(id) {
    for (var i = 0; i < goods.length; i++) {
        if (goods[i].id == id) {
            return goods[i];
        }
    }
}
function openCount(id) {
    focusId = id;
    var obj = findGoodById();
    $("#countimg").attr("src", "<%=path%>/upload/" + obj.img);
}
function sureCount() {
    var spanId = focusId + "_span";
    var count = $("#count").val();
    $("#" + spanId + "").text(count);

    var good = findGoodById();
    totalprice = totalprice+count*good.price;
    $("#total").text(totalprice);
    $("#btotal").text(totalprice);
}
$(function () {
    foodList();
    typeList();
    shopList();
    fulshDateSelect();
});

function shopList() {
    showLoader("请稍后...");    
    $.post(__APP__+"/Wehall!listShop",{},function(data){
    	hideLoader();
    	data = JSON.parse(data);
        fulshShopSelect(data);
        flushShopList(data);
    });
}
function fulshShopSelect(shops) {
    var html = "<option value=''>请选择</option>";
    for (var i = 0; i < shops.length; i++) {
        var shop = shops[i];
        var op = '<option value="' + shop.id + '">' + shop.name + '</option>';
        html += op;
    }
    $("#mendianselect").html(html);
}

function fulshDateSelect() {
    var d = new Date().getTime();
    var td1 = d + 86400000;
    var td2 = td1 + 86400000;
    var td3 = td2 + 86400000;

    var d1 = new Date();
    d1.setTime(td1);
    var rd1 = d1.getFullYear() + "-" + parseInt(d1.getMonth() + 1) + "-" + d1.getDate();
    var d2 = new Date(td2);
    d2.setTime(td2);
    var rd2 = d2.getFullYear() + "-" + parseInt(d2.getMonth() + 1) + "-" + d2.getDate();
    var d3 = new Date(td3);
    d3.setTime(td3);
    var rd3 = d3.getFullYear() + "-" + parseInt(d3.getMonth() + 1) + "-" + d3.getDate();
    var html = "<option value=''>请选择</option>";
    html += "<option value='" + rd1 + "'>" + rd1 + "</option>";
    html += "<option value='" + rd2 + "'>" + rd2 + "</option>";
    html += "<option value='" + rd3 + "'>" + rd3 + "</option>";
    $("#dateselect").html(html);
}
function foodList() {
    var type = $("#typelist").val();
    showLoader("请稍后..."); 
    $.post(__APP__+"/Wehall!listGood",{type:type},function(data){
    	hideLoader();
    	data = JSON.parse(data);
        if(!data) return;
        goods = data;
        flushList();
    });
}

function typeList() {

	showLoader("请稍后..."); 
    $.post(__APP__+"/Wehall!listType",{},function(data){
    	hideLoader();
    	data = JSON.parse(data);
        if(!data) return;
        var types = data;
        var html = "<option value=''>请选择</option>";
        for (var i = 0; i < types.length; i++) {
            var type = types[i];
            var op = '<option value="' + type.id + '">' + type.name + '</option>';
            html += op;
        }
        $("#typelist").html(html);
    });
}

function flushShopList(shops) {
    var html = "";
    for (var i = 0; i < shops.length; i++) {
        var shop = shops[i];
        var div = '<div data-role="collapsible">' +
                '<h3>' +
                '<img width="64px;" src="' + "<%=path%>/upload/" + shop.img + '">'
                + shop.name +
                '</h3>' +

                '<p>' + shop.note + '</p>' +
                '<a href="#diancan" class="ui-btn" data-transition="flow">点餐</a>' +
                '<a href="#dingzuo" class="ui-btn"  data-transition="flow">订座</a>' +
                '<a href="http://api.map.baidu.com/geocoder?address='+shop.address+'&output=html&src=ideamap" class="ui-btn"  data-transition="flow">地图</a>' +
                '</div>';
        html += div;        
    }   
    $("#shops").html(html);
    //$("#shops").collapsibleset( "refresh" );
}
function flushList() {
    var html = "";
    for (var i = 0; i < goods.length; i++) {
        var good = goods[i];
        var li = '<li>' +
                '<a href="#popupLogin" onclick="openCount(' + good.id + ');" data-rel="popup" data-position-to="window" data-transition="pop">' +
                '<img src="' + "<%=path%>/upload/" + good.img + '">' +
                '<h2>' + good.gname + '</h2>' +
                '<p>￥：' + good.price + '</p>' +
                '<span class="ui-li-count billcount" id="' + good.id + '_span">0</span>' +
                '</a>' +
                '</li>';
        html += li;
    }
    $("#goodlist").html(html);
    $("#goodlist").listview('refresh');
}
function tijiao() {
    bill = [];
    var spans = $(".billcount");
    for (var i = 0; i < spans.length; i++) {
        var span = spans[i];
        var tid = $(span).attr("id");
        var id = tid.split("_")[0];
        var count = $(span).text();
        var obj = new Object();
        if (count > 0) {
            obj.id = id;
            obj.count = count;
            bill.push(obj);
        }
    }
    total = 0;
    for (var i = 0; i < bill.length; i++) {
        var b = findById(bill[i].id);
        bill[i].gname = b.gname;
        bill[i].img = b.img;
        bill[i].price = b.price;
        bill[i].note = b.note;
        bill[i].total = parseInt(bill[i].count) * parseInt(b.price);
        total = (parseInt(total) + parseInt(b.price) * parseInt(bill[i].count));
    }
    if(bill.length){
        $("#something").show();
        $("#nothing").hide();
        mybills();
    }else{
        $("#something").hide();
        $("#nothing").show();
    }


}

function sureBill(){
    if(bill.length == 0){
        return;
    }
    showLoader("请稍后...");
    var way = $("#way").val();
    showLoader("请稍后..."); 
    $.post(__APP__+"/Wehall!saveBill",{
        bill: JSON.stringify(bill),
        total: total,
        way:way
    },function(data){
    	hideLoader();
    	showLoader(data, true);
        bill = [];
    });
}

function saveDingzuo() {

    var renshu = $("#renshu").val();
    var xingming = $("#xingming").val();
    var shouji = $("#shouji").val();
    var dateselect = $("#dateselect").val();
    var shijian = $("#shijian").val();
    var mendianselect = $("#mendianselect").val();
    var beizhu = $("#beizhu").val();
    var shopname = $("#mendianselect").find("option:selected").text();

    showLoader("请稍后..."); 
    $.post(__APP__+"/Wehall!saveDingzuo",{
        renshu: renshu,
        xingming: xingming,
        shouji: shouji,
        shijian: shijian,
        todate: dateselect,
        beizhu: beizhu,
        shopid: mendianselect,
        shopname: shopname
    },function(data){
    	hideLoader();
    	showLoader(data, true);
        //wode();
    });
}

/* function myBill() {
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        url: "__APP__/Client/myBill",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            //var ndate = data.ndate;
            //var da = new Date();
            //da.setTime(ndate);
            //var datestr = da.getFullYear() + "-" + parseInt(da.getMonth() + 1) + "-" + da.getDate() + " " + da.getHours() + ":" + da.getMinutes() + ":" + da.getSeconds();
            $("#ndate").text(data.ndate);
            var bills = JSON.parse(data.bill);
            diancanDetail(bills);
            totalprice = 0;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });
} */

/* function diancanDetail(bills){
	$.mobile.changePage($("#wodediancan"),{transition:"flow"});
    var html = "";
    for (var i = 0; i < bills.length; i++) {
        var good = bills[i];
        var li = '<li>' +
                '<img src="' + "__ROOT__/Uploads/" + good.img + '">' +
                '<h2>' + good.gname + '</h2>' +
                '<p>￥：' + good.price + '</p>' +
                '<p>总价：' + good.total + '</p>' +
                '<span class="ui-li-count">' + good.count + '</span>' +
                '</li>';
        html += li;
    }
    
    $("#mylist").html(html);
    $("#mylist").listview('refresh');
} */

<%-- function mybills(){
    var bills = bill;
    var html = "";
    for (var i = 0; i < bills.length; i++) {
        var good = bills[i];
        var li = '<li>' +
                '<img src="' + "<%=path%>/upload/" + good.img + '">' +
                '<h2>' + good.gname + '</h2>' +
                '<p>￥：' + good.price + '</p>' +
                '<p>总价：' + good.total + '</p>' +
                '<span class="ui-li-count">' + good.count + '</span>' +
                '</li>';
        html += li;
    }

    $("#mybills").html(html);
    $("#mybills").listview('refresh');
} --%>



/* function myDingzuo() {
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        url: "__APP__/Client/myDingzuo",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {

        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            dzDetail(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });
} */
/* function dzDetail(data){
	$.mobile.changePage($("#mydingzuo"),{transition:"flow"});
    $("#renshu2").text("人数:" + data.renshu);
    $("#xingming2").text("姓名:" + data.xingming);
    $("#shouji2").text("电话:" + data.shouji);
    $("#dateselect2").text("日期:" + data.todate);
    $("#shijian2").text("时间:" + data.shijian);
    $("#mendianselect2").text("校区:" + data.shopname);
    $("#beizhu2").text("说明:" + data.beizhu);
    
} */

/* function saveVip() {
    var qq = $("#qq").val();
    var vname = $("#vname").val();
    var mobile = $("#mobile").val();
    var sex = $("#sex").val();
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        url: "__APP__/Client/saveVip",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
            qq: qq,
            vname: vname,
            mobile: mobile,
            sex: sex
        },
        success: function (data) {
            vipname = vname;
            wode();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });
} */

/* function wode() {
    if (vipname) {
        $.mobile.changePage($("#wodevip"), {transition: "flow"});
        $("#vipname").text(vipname);
    } else {
        $.mobile.changePage($("#wode"), {transition: "flow"});
    }
} */

/* function dcjl() {
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        async: false,
        url: "__APP__/Client/dcjl",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            var objs = data;
            var html = "";
            for(var i=0;i<objs.length;i++){
                var obj = objs[i];
                var li = '<li><a href="javascript:dcxx(\''+obj.id+'\');">'+obj.ndate+'</a></li>';
                html+=li;
            }            
            $("#dcjllist").html(html);
            $("#dcjllist").listview('refresh');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });

}

function dzjl() {
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        async: false,
        url: "__APP__/Client/dzjl",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            var objs = data;
            var html = "";
            for(var i=0;i<objs.length;i++){
                var obj = objs[i];
                var li = '<li><a href="javascript:dzxx(\''+obj.id+'\');">'+obj.ndate+'</a></li>';
                html+=li;
            }           
            $("#dzjllist").html(html);
            $("#dzjllist").listview('refresh');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });

}

function dcxx(id){
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        async: false,
        url: "__APP__/Client/getDcById",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
            id:id
        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            var bills = JSON.parse(data.bill);
            diancanDetail(bills);
            $("#ndate").text(data.ndate);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });
}

function dzxx(id){
    showLoader("请稍后...");
    $.ajax({
        type: "GET",
        async: false,
        url: "__APP__/Client/getDzById",
        dataType: "jsonp",
        jsonp: "callback",
        contentType: "text/html; charset=utf-8",
        data: {
            id:id
        },
        success: function (data) {
            hideLoader();
            if(!data) return;
            dzDetail(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoader();
        }
    });
} */
//显示加载器
function showLoader(str, textonly, textVisible, theme, html) {
    textonly = textonly || false;
    textVisible = textVisible || true;
    theme = theme || "b";
    html = html || "";
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
        text: str, //加载器中显示的文字
        textVisible: textVisible, //是否显示文字
        theme: theme,        //加载器主题样式a-e
        textonly: textonly,   //是否只显示文字
        html: html           //要显示的html内容，如图片等
    });
    if (textonly) {
        setTimeout(function () {
            hideLoader();
        }, 2000);
    }
}

//隐藏加载器.for jQuery Mobile 1.2.0
function hideLoader() {
    //隐藏加载器
    $.mobile.loading('hide');
}
</script>
</head>
<body>
<div data-role="page" data-control-title="Home" id="mendian">
    <div data-theme="a" data-role="header">
        <h1>
            微餐厅
        </h1>
    </div>
    <div role="main" class="ui-content">
        <div id="shops" data-role="collapsible-set">

        </div>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>
<div data-role="page" id="diancan">
    <div data-role="header" data-position="fixed">
        <h1>当前总价:<span style="color: red" id="total">0</span></h1>
        <a href="#billList" onclick="tijiao()" class="ui-btn ui-btn-right" data-rel="popup" data-position-to="window" data-transition="pop">
            提交交易
        </a>
        <!--<a href="javascript:addClass();" data-rel="button"  class="ui-btn ui-btn-inline ui-icon-bars ui-btn-icon-left ui-btn-right">我的交易</a>-->
    </div>
    <div data-role="content">
        <select id="typelist" onchange="foodList();">
            <option value="">请选择</option>
        </select>
        <ul id="goodlist" data-role="listview" data-inset="true">

        </ul>
    </div>
    <div data-role="popup" id="popupLogin" data-theme="a" class="ui-corner-all">
        <div style="padding:10px 20px;">
            <img width="100%" height="" id="countimg" src=""/>
            <select id="count">
                <option value="0">0</option>
                <option value="1" selected="selected">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select>
            <a href="#index" onclick="sureCount()"
               class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">确定</a>
        </div>
    </div>

    <div data-role="popup" id="billList" data-theme="a" class="ui-corner-all">
        <div style="padding:10px 20px;">
            <div id="something">
                <label>总价:<span id="btotal"></span></label>
                <ul id="mybills" data-role="listview" data-inset="true">

                </ul>
                <select id="way">
                    <option value="店里吃">店里吃</option>
                    <option value="带走">带走</option>
                </select>
            </div>
            <div id="nothing">
                   您没有选择任何东西!
            </div>
            <a href="#index" onclick="sureBill()"
               class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">确定</a>
        </div>
    </div>

    <div data-theme="a" data-role="footer" data-position="fixed">
    </div>
</div>


<div data-role="page" data-control-title="dingzuo" id="dingzuo">
    <div data-theme="a" data-role="header">
        <h1 class="ui-title">
            填写预订信息
        </h1>
    </div>
    <div role="main" class="ui-content">
        <div class="ui-field-contain" data-controltype="textinput">
            <label for="xingming">
                姓名:
            </label>
            <input name="" id="xingming" placeholder="" value="" type="text">
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label for="shouji">
                手机:
            </label>
            <input name="" id="shouji" placeholder="" value="" type="text">
        </div>
        <div class="ui-field-contain" data-controltype="selectmenu">
            <label for="dateselect">
                日期
            </label>
            <select id="dateselect" name="dateselect">

            </select>
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label for="shijian">
                时间:
            </label>
            <select id="shijian" name="dateselect">
                <option value="">请选择</option>
                <option value="8:00~9:00">8:00~9:00</option>
                <option value="9:00~10:00">9:00~10:00</option>
                <option value="10:00~11:00">10:00~11:00</option>
                <option value="11:00~12:00">11:00~12:00</option>
                <option value="12:00~13:00">12:00~13:00</option>
                <option value="13:00~14:00">13:00~14:00</option>
                <option value="14:00~15:00">14:00~15:00</option>
                <option value="15:00~16:00">15:00~16:00</option>
                <option value="16:00~17:00">16:00~17:00</option>
                <option value="17:00~18:00">17:00~18:00</option>
                <option value="18:00~19:00">18:00~19:00</option>
                <option value="19:00~20:00">19:00~20:00</option>
                <option value="20:00~21:00">20:00~21:00</option>
                <option value="21:00~22:00">21:00~22:00</option>
            </select>
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label for="renshu">
                人数:
            </label>
            <input name="" id="renshu" placeholder="" value="" type="text">
        </div>
        <div class="ui-field-contain" data-controltype="selectmenu">
            <label for="mendianselect">
                校区:
            </label>
            <select id="mendianselect" name="">
                <option value="">
                    请选择
                </option>
            </select>
        </div>
        <div class="ui-field-contain" data-controltype="textarea">
            <label for="beizhu">
                备注:
            </label>
            <textarea name="" id="beizhu" placeholder=""></textarea>
        </div>
        <a href="#" onclick="saveDingzuo();" class="ui-btn ">
            提交
        </a>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>

<div data-role="page" data-control-title="dingzuo" id="mydingzuo">
    <div data-theme="a" data-role="header">
        <h1 class="ui-title">
            订座信息
        </h1>
    </div>
    <div role="main" class="ui-content">
        <div class="ui-field-contain" data-controltype="textinput">
            <label id="xingming2">
                姓名:
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label id="shouji2">
                手机:
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="selectmenu">
            <label id="dateselect2">
                日期
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label id="shijian2">
                时间:
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="textinput">
            <label id="renshu2">
                人数:
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="selectmenu">
            <label id="mendianselect2">
                校区:
            </label>
        </div>
        <div class="ui-field-contain" data-controltype="textarea">
            <label id="beizhu2">
                备注:
            </label>
        </div>

    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>



<div data-role="page" data-control-title="Home" id="wode">
    <div data-theme="a" data-role="header">
    </div>
    <div role="main" class="ui-content">
        <ul data-role="listview" data-divider-theme="a" data-inset="true">
            <li data-role="list-divider" role="heading">
                我的交易
            </li>
            <li data-theme="a">
                <a href="#" onclick="myBill();" data-transition="slide">
                    我的点餐
                </a>
            </li>
            <li data-theme="a">
                <a href="#" onclick="myDingzuo();" data-transition="slide">
                    我的订座
                </a>
            </li>
        </ul>
        <ul data-role="listview" data-divider-theme="a" data-inset="true">
            <li data-role="list-divider" role="heading">
                交易记录
            </li>
            <li data-theme="a">
                <a href="#wodediancanjl" onclick="dcjl();" data-transition="slide">
                    点餐记录
                </a>
            </li>
            <li data-theme="a">
                <a href="#mydingzuojl" onclick="dzjl();" data-transition="slide">
                    订座记录
                </a>
            </li>
        </ul>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>

<div data-role="page" data-control-title="Home" id="wodediancanjl">
    <div data-theme="a" data-role="header">
        <h1>点餐记录</h1>
    </div>
    <div role="main" class="ui-content">
        <ul data-role="listview" id="dcjllist" data-filter="true" data-filter-placeholder="输入日期搜索..." data-inset="true">

        </ul>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>

<div data-role="page" data-control-title="Home" id="mydingzuojl">
    <div data-theme="a" data-role="header">
        <h1>订座记录</h1>
    </div>
    <div role="main" class="ui-content">
        <ul data-role="listview" id="dzjllist" data-filter="true" data-filter-placeholder="输入日期搜索..." data-inset="true">

        </ul>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>



<div data-role="page" data-control-title="Home" id="wodediancan">
    <div data-theme="a" data-role="header">
        <h1>我的点餐</h1>

        <h2 id="ndate"></h2>
    </div>
    <div role="main" class="ui-content">
        <ul id="mylist" data-role="listview" data-inset="true">

        </ul>
    </div>
    <div data-theme="a" data-role="footer" data-position="fixed">
        
    </div>
</div>

</body>
</html>