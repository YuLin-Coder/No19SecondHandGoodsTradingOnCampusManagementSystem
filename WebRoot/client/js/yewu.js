/**
 * Created by Bowa on 2014/8/28.
 */
var shoplist = [];
var goodlist = [];
var focusobj = null;
var focushop = {};
var focuspost = null;
var gouwuche = "gouwuche";
var postslist = [];
var _yanzhengma = "";
var billlist = [];

$(function(){
//设置类别列表
    var p = {};
    p.tpl = '<li><a href="#" onclick="toShop(%s);">'+
                //'<img src="'+fileurl+'%s" class="ui-li-thumb">'+
                '<h2>%s</h2>'+
                //'<p>%s</p>'+
            '</a></li>';
    p.colums = ["id","sname"];
    $("#shops").data("property",JSON.stringify(p));

    var p2 = {};
    p2.tpl = '<li><a href="#" onclick="toGood(%s);">'+
        '<img style="height: 100px;" src="'+fileurl+'%s" class="ui-li-thumb">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p style="color: red;">%s 元</p>'+
        '</a></li>';
    p2.colums = ["id","img","gname","note","price"];
    $("#goods").data("property",JSON.stringify(p2));
    $("#goods2").data("property",JSON.stringify(p2));

    var p3 = {};
    p3.tpl = '<li>'+
        '<img src="'+fileurl+'%s" class="ui-li-thumb">'+
        '<h2>%s</h2>'+
        '<p style="color: red;">%s 元</p>'+
        '<p class="ui-li-aside"><a href="#" onclick="removeCar(%s);" style="font-size: 20px;text-decoration:none;">删除</a></p>'+
        '</li>';
    p3.colums = ["img","gname","price","id"];
    $("#cars").data("property",JSON.stringify(p3));

    var p4 = {};
    p4.tpl = '<li><a href="#" onclick="billDetail(%s);">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p style="color: red;">总价:%s</p>'+
        '<p>电话:%s</p>'+
        '<p>地址:%s</p>'+
        '</a></li>';
    p4.colums = ["id","ndate","gnames","total","tel","address"];
    $("#bills").data("property",JSON.stringify(p4));

    var p7 = {};
    p7.tpl = '<li><a href="#" onclick="toGood(%s,1);">'+
        '<img src="'+fileurl+'%s" class="ui-li-thumb">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p style="color: red;">%s 元</p>'+
        '</a></li>';
    p7.colums = ["id","img","gname","note","price"];
    $("#billgoods").data("property",JSON.stringify(p7));

    var p5 = {};
    p5.tpl = '<li onclick="postDetail(%s);">'+
    '<h2>%s</h2>'+
    '<p>%s</p>'+
        '<p>%s</p>'+
    '</li>';
    p5.colums = ["id","title","type","ndate"];
    $("#posts").data("property",JSON.stringify(p5));

    var p6 = {};
    p6.tpl = '<li><a href="#" onclick="">'+
    '<h2>%s</h2>'+
    '<p>%s</p>'+
    '<p>%s<span style="color: #3e6790;margin-left: 50px;" onclick="huifu(\'%s\')">回复</span> </p>'+
    '</a></li>';
    p6.colums = ["ndate","note","username","username"];
    $("#replays").data("property",JSON.stringify(p6));

    var p666 = {};
    p666.tpl = '<li><a href="#" onclick="">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p>%s</p>'+
        '</a></li>';
    p666.colums = ["ndate","note","username"];
    $("#replays2").data("property",JSON.stringify(p666));
    $("#replays3").data("property",JSON.stringify(p666));


    var p6666 = {};
    p6666.tpl = '<li onclick="noticeDetail(%s)">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '</li>';
    p6666.colums = ["id","title","note"];
    $("#noticelist").data("property",JSON.stringify(p6666));

    clickYanzhengma();

});




var focuslist = [];


function toNewest(){
    changePage('goodnew');
    ajaxCallback("goodList",{},function(data){
        $("#goods2").refreshShowListView(data);
    });
}

function toNotice(){
    changePage('noticepage');
    listNotice();
}

function listNotice(){
    ajaxCallback("listNotice",{},function(data){
        focuslist = data;
        $("#noticelist").refreshShowListView(data);
    });
}

function noticeDetail(id){
    var obj = getObjectById(id,focuslist);
    changePage('noticedetailpage');
    $("#vtitle").text(obj.title);
    $("#vnote").text(obj.note);
    $("#vndate").text("时间:"+obj.ndate);
}



function huifu(username){
    $("#rnote").val("回复 "+username+":");
}

function toMain(){
    /*changePage("mainpage");
    listShop();*/
    toGoods();
}

function listShop(){
    ajaxCallback("listShop",{},function(data){
        shoplist = data;
        $("#shops").refreshShowListView(data);
    });
}


function toMapPage(){
    changePage('mappage');
    if(!map){
        initMap();
    }
}

function toUser(id){
    goback();
    refreshGood(null,null,id);
}

function toJiaoliu(){
    changePage('jiaoliupage');
    listReplay3();
}


function toShop(id){
    for(var i=0;i<shoplist.length;i++){
        if(shoplist[i].id == id){
            focushop = shoplist[i];
            break;
        }
    }
    toGoods(id)
}

function toGoods(id){
    var sid = id || focushop.id;
    changePage("goodspage");
    listType();
    listGood(sid);
}

function refreshGood(title,type,uid){
    var stype = title || $("#type").val() || "";
    ajaxCallback("listGood",{stitle:title,stype:stype,sid:focushop.id,uid:uid},function(data){
        $("#goods").refreshShowListView(data);
    });
}

function listGood(sid){
    ajaxCallback("listGood",{sid:sid},function(data){
        goodlist = data;
        $("#goods").refreshShowListView(data);
    });
}

function listType(){
    ajaxCallback("listType",{},function(data){
        $("#type").empty();
        var html = "<option value=''>请选择</option>";
        $("#type").append(html);
        for(var i=0;i<data.length;i++){
            var obj = data[i];
            var html = "<option value='"+obj.id+"'>"+obj.title+"</option>";
            $("#type").append(html);
        }
    });
}

function toGood(id){
    var obj = getGoodById(id);
    focusobj = obj;
    changePage("goodpage");
    $("#gname2").text("商品名:"+obj.gname);
    $("#gimg2").attr("src",fileurl+obj.img);
    $("#gnote2").text("简介:"+obj.note);
    $("#gprice2").text("价格:"+obj.price);

    if(userinfo.id==obj.uid){
        $("#deleteGood").html(' <a href="#" onclick="deleteGoods('+id+');"  class="ui-btn ui-btn-f"  style=" background-color: #ef2525; text-shadow: none;">提交</a>');
    }
    listReplay2();
}

function getGoodById(id){
    for(var i=0;i<goodlist.length;i++){
        var good = goodlist[i];
        if(good.id == id){
            return good;
        }
    }
    return null;
}

function payBill(){
    //if(userinfo.money && userinfo.money*1>focusobj.total*1){
    var id = focusobj.id;
    var statecn="已付款";
    ajaxCallback("billState",{id:id,statecn:statecn},function(data){
        showTipTimer("付款成功!",function(){
            billDetail(data);
        });

    });
    /*    }else{
     showLoader("余额不足!",true);
     }*/

}

function billDetail(id) {
    //clearInterval(_linetimmer);
    /*if(flag){

     }else{*/
    changePage("billgoodspage");
    //}

    var bill = {};
    if (typeof id == "object") {
        bill = id;
    } else {
        bill = getObjectById(id, billlist);
    }

    focusobj = bill;
    $("#btotal").text("总价:" + bill.total);
    //$("#bfenqi").text("积分:"+bill.fenqi+"个月");
    $("#bndate").text("下单时间:" + bill.ndate);

    //var m = bill.total*1/bill.fenqi;
    //m = m.toFixed(2);
    //$("#bmoney").text("每月3号还款:"+m);
    $("#statecn").text("订单状态:" + bill.statecn);
    if (bill) {
        var gids = bill.gids;
        ajaxCallback("listBillGoods", {gids: gids}, function (data) {
            $("#billgoods").refreshShowListView(data);
        },true);
    }
    if (bill.statecn == "未付款") {
        $("#paydiv").show();
    } else {
        $("#paydiv").hide();
        $("#surediv").hide();
    }

    /*if (bill.statecn == "送餐中") {
        $("#surediv").show();
    } else if (bill.statecn == "未付款") {
        $("#surediv").hide();
    }else{
        $("#surediv").hide();
    }*/

    /*if(bill.statecn=="未叫号"){
     getLineNumber(id,function (count){
     if(count==0){
     $("#statecn").text("订单状态:已叫号");
     }else if(count<3){
     $("#statecn").text("准备取餐");
     myObj.showIntentActivityNotify("点餐提示","请准备取餐",100);
     }else{
     $("#statecn").text("等待人数:" + count+"人");
     }
     _linetimmer = setInterval(function(){
     billDetail(id,true);
     },5000);
     });
     }else{
     clearInterval(_linetimmer);
     $("#statecn").text("订单状态:" + bill.statecn);
     }*/




}

function tijiaouser(){
    var note = $("#infobeizhu2").val();
    var bill = {};
    bill.uid = userinfo.id;
    bill.user = userinfo.username;
    bill.shop = focushop.sname;
    bill.sid = focushop.id;
    bill.gids = focusobj.id;
    bill.gnames = focusobj.gname;
    bill.total = focusobj.price;
    bill.tel = userinfo.tel;
    bill.address = userinfo.address;
    bill.note = note;
    ajaxCallback("saveBill",bill,function(){
        showLoader("订单提交成功!",true);
        showTipTimer("订单提交成功!",function(){
            toMyBill();
        });

    });
}

function tijiao(){
    if(userinfo){
        changePage("infopage2");
        $("#dianhua").text("电话:"+userinfo.tel);
        $("#dizhi").text("地址:"+userinfo.address);
        $("#iscar2").val("1");
    }else{
        changePage("infopage");
        $("#iscar").val("1");
    }
}

function deleteGoods(id){
    var good = {};

    good.id = id;
    ajaxCallback("deleteGood",good,function(){
        showLoader("删除成功!",true);
        toGoods();

    });
}


function tijiaoyouke(){
    var tel = $("#infotel").val();
    var address = $("#infoaddress").val();
    var note = $("#infobeizhu").val();
    if($.trim(tel)=="" || $.trim(address)==""){
        showLoader("请填写电话和地址信息!",true);
        return;
    }
    if(tel.length<11){
        showLoader("请填写正确的电话号码!",true);
        return;
    }
    var bill = {};
    bill.shop = focushop.sname;
    bill.sid = focushop.id;
    bill.gids = focusobj.id;
    bill.gnames = focusobj.gname;
    bill.total = focusobj.price;
    bill.tel = tel;
    bill.address = address;
    bill.note = note;
    ajaxCallback("saveBill",bill,function(){
        showLoader("订单提交成功!",true);
        showTipTimer("订单提交成功!",function(){
            toMyBill();
        });

    });
}

function youketijiao(){
    var type = $("#iscar").val();
    if(type == 1){
        tijiaoyouke();
    }else{
        tijiaocaryouke();
    }
}

function usertijiao(){
    var type = $("#iscar2").val();
    if(type == 1){
        tijiaouser()
    }else{
        tijiaocaruser();
    }
}

function addToCar(){
    var str = localStorage[gouwuche];
    var list = [];
    if(str){
        list = JSON.parse(str);
    }
    list.push(focusobj);
    localStorage[gouwuche] = JSON.stringify(list);
    showLoader("已经添加到收藏!",true);
}

function showCar(){
    changePage("carspage");
    carlist();
}

function carlist(){
    var str = localStorage[gouwuche];
    var list = [];
    if(str){
        list = JSON.parse(str);
    }
    $("#cars").refreshShowListView(list);
}

function removeCar(id){
    var str = localStorage[gouwuche];
    var list = [];
    var newlist = [];
    if(str){
        list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var obj = list[i];
            if(obj.id == id){
                continue;
            }
            newlist.push(obj);
        }
        localStorage[gouwuche] = JSON.stringify(newlist);
        $("#cars").refreshShowListView(newlist);
    }
}

function tijiaocar(){
    if(userinfo){
        changePage("infopage2");
        $("#iscar2").val("2");
    }else{
        changePage("infopage");
        $("#iscar").val("2");
    }
}

function tijiaocaruser(){
    var note = $("#infobeizhu2").val();
    var str = localStorage[gouwuche];
    var sids = [];
    var shopgoods = {};
    var bills = [];
    if(str){
        var list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var flag = false;
            var good = list[i];
            for(var n=0;n<sids.length;n++){
                if(sids[n]==good.sid){
                    shopgoods[good.sid].push(good);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                shopgoods[good.sid] = [];
                shopgoods[good.sid].push(good);
                sids.push(good.sid);
            }
        }
    }

    for(var i=0;i<sids.length;i++){
        var goodlist = shopgoods[sids[i]];
        var gids = "";
        var gnames = "";
        var sname = "";
        var total = 0;
        var sid = sids[i];
        var bill = {};
        bill.uid = userinfo.id;
        bill.user = userinfo.username;
        for(var n=0;n<goodlist.length;n++){
            var good = goodlist[n];
            if(n==0){
                sname = good.shop;
                gids+=good.id;
                gnames+=good.gname;
            }else{
                gids+=","+good.id;
                gnames+=","+good.gname;
            }
            total+=parseInt(good.price);
        }
        bill.shop = sname;
        bill.sid = sid;
        bill.gids = gids;
        bill.gnames = gnames;
        bill.total = total;
        bill.tel = userinfo.tel;
        bill.address = userinfo.address;
        bill.note = note;
        bills.push(bill);
    }
    if(bills.length){
        ajaxCallback("saveBills",{bills:JSON.stringify(bills)},function(data){
            localStorage[gouwuche] = "";
            showTipTimer("订单提交成功!",function(){
                toMyBill();
            });
        });
    }

}

function tijiaocaryouke(){
    var tel = $("#infotel").val();
    var address = $("#infoaddress").val();
    var note = $("#infobeizhu").val();
    if($.trim(tel)=="" || $.trim(address)==""){
        showLoader("请填写电话和地址信息!",true);
        return;
    }
    if(tel.length<11){
        showLoader("请填写正确的电话号码!",true);
        return;
    }
    var str = localStorage[gouwuche];
    var sids = [];
    var shopgoods = {};
    var bills = [];
    if(str){
        var list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var flag = false;
            var good = list[i];
            for(var n=0;n<sids.length;n++){
                if(sids[n]==good.sid){
                    shopgoods[good.sid].push(good);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                shopgoods[good.sid] = [];
                shopgoods[good.sid].push(good);
                sids.push(good.sid);
            }
        }
    }

    for(var i=0;i<sids.length;i++){
        var goodlist = shopgoods[sids[i]];
        var gids = "";
        var gnames = "";
        var sname = "";
        var total = 0;
        var sid = sids[i];
        var bill = {};
        bill.uid = "";
        bill.user = "";
        for(var n=0;n<goodlist.length;n++){
            var good = goodlist[n];
            if(n==0){
                sname = good.shop;
                gids+=good.id;
                gnames+=good.gname;
            }else{
                gids+=","+good.id;
                gnames+=","+good.gname;
            }
            total+=parseInt(good.price);
        }
        bill.shop = sname;
        bill.sid = sid;
        bill.gids = gids;
        bill.gnames = gnames;
        bill.total = total;
        bill.tel = tel;
        bill.address = address;
        bill.note = note;
        bills.push(bill);
    }
    if(bills.length){
        ajaxCallback("saveBills",{bills:JSON.stringify(bills)},function(data){
            localStorage[gouwuche] = "";
            showTipTimer("订单提交成功!",function(){
                toMyBill();
            });
        });
    }

}



function toMyBill(){
    if(userinfo){
        changePage("mybillpage");
        mybillslist();
    }else{
        changePage("loginpage");
    }

}

function mybillslist(){
    ajaxCallback("mybills",{uid:userinfo.id},function(data){
        billlist = data;
        $("#bills").refreshShowListView(data);
    });
}



function toLuntan(id){
    changePage("luntanpage");
    listPosts(id);
}

function toFabu(){
    changePage("fabupage");
    ajaxCallback("listType",{},function(data){
        shoplist = data;
        $("#fcity").refreshShowSelectMenu(data,"选择分类","id","title");
    });
}


function listPosts(id){
    ajaxCallback("listPosts",{uid:id},function(data){
        postslist = data;
        $("#posts").refreshShowListView(data);
    });
}
function toAddForm(){
    changePage("addformpage");
}
function addForm(){
    var note = $("#fnote").val();
    var title = $("#ftitle").val();
    var type = $("#ftype").val();
    uplaodImg(function(r){
        ajaxCallback("addPosts",{uid:userinfo.id,title:title,note:note,username:userinfo.username,img:r,type:type},function(){
            toLuntan();
        });
    });

}
function postDetail(id){
    var obj = getPostsById(id);
    focuspost = obj;
    changePage("postdetail");
    $("#vptitle").text("标题:"+obj.title);
    $("#vpnote").text("内容:"+obj.note);
    $("#vpusername").text("发布者:"+obj.username);
    $("#vpdate").text("时间:"+obj.ndate);
    $("#pimg").attr("src",fileurl+obj.img);
    if(obj.uid == userinfo.id){
        $("#mypost").show();
    }else{
        $("#mypost").hide();
    }
    listReplay();
}
function listReplay(){
    ajaxCallback("listReplay",{pid:focuspost.id},function(data){
        $("#replays").refreshShowListView(data);
    });
}
function listReplay2(){
    ajaxCallback("listReplay",{pid:focusobj.id},function(data){
        $("#replays2").refreshShowListView(data);
    });
}
function addReplay(){
    var note = $("#rnote").val();
    ajaxCallback("addReplay",{pid:focuspost.id,uid:userinfo.id,username:userinfo.username,note:note},function(data){
        listReplay();
        $("#rnote").val("");
    });
}
function addReplay2(){
    var note = $("#rnote2").val();
    ajaxCallback("addReplay",{pid:focusobj.id,uid:userinfo.id,username:userinfo.username,note:note},function(data){
        listReplay2();
        $("#rnote2").val("");
    });
}


function addReplay3(){
    var note = $("#rnote3").val();
    ajaxCallback("addReplay",{pid:focusobj.sid,uid:userinfo.id,username:userinfo.username,note:note},function(data){
        listReplay3();
        $("#rnote3").val("");
    });
}


function listReplay3(){
    ajaxCallback("listReplay",{pid:focusobj.sid},function(data){
        $("#replays3").refreshShowListView(data);
    });
}

function getPostsById(id){
    for(var i=0;i<postslist.length;i++){
        if(postslist[i].id == id){
            return postslist[i];
        }
    }
}

function mygood(){
    toGoods(userinfo.id);
}


function mypost(){
    toLuntan(userinfo.id);
}

function saveGood(){
    var fdata = serializeObject($("#goodform"));
    fdata.sid = focushop.id;
    fdata.shop = focushop.title;
    fdata.uid = userinfo.id;
    fdata.username = userinfo.username;

    uplaodImg(function(img){
        fdata.img = img;
        ajaxCallback("saveGood",fdata,function(){
            showLoader("发布成功!",true);
            toGoods();
        });
    });
}

function delPosts(){
    ajaxCallback("delPosts",{id:focuspost.id},function(data){
        toLuntan();
    });
}