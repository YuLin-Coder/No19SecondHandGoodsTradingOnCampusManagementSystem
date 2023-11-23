/**
 * Created by ideabobo on 14-6-28.
 * jquery mobile tools
 */
var mobiletool={
    confirmDialog:false,
    confirmBack:null
}

function changePage(id, animation) {
    animation = animation || randAnimation();
    $.mobile.changePage($("#" + id), {transition: animation})
}

function confirmCallback(flag){
    mobiletool.confirmBack && mobiletool.confirmBack(flag);
    hideLoader();
}

function randAnimation(){
    var animation=['pop','flow','pop','fade','slidedown','slideup','slidefade','flow','slide','pop'];
    var r = parseInt(10*Math.random());
    return animation[r];
}

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

function showTipTimer(str,callback){
    var textonly = true;
    var textVisible = true;
    var theme = "b";
    var html = html || "";
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
        text: str, //加载器中显示的文字
        textVisible: textVisible, //是否显示文字
        theme: theme,        //加载器主题样式a-e
        textonly: textonly,   //是否只显示文字
        html: html           //要显示的html内容，如图片等
    });
    setTimeout(function () {
        hideLoader();
        callback && callback();
    }, 1000);
}

function showConfirm(str,cb) {
    mobiletool.confirmBack = cb;
    var html = "<div><h1>"+str+"</h1><a href='#' onclick='confirmCallback(1);' class='ui-btn ui-btn-a'>确定</a><a href='#' onclick='confirmCallback(0);' class='ui-btn ui-btn-a'>取消</a></div>";
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
        text: str, //加载器中显示的文字
        textVisible: true, //是否显示文字
        theme: "b",        //加载器主题样式a-e
        textonly: true,   //是否只显示文字
        html: html           //要显示的html内容，如图片等
    });
    mobiletool.confirmDialog = true;
}

function showAlert(str,cb) {
    mobiletool.confirmBack = cb;
    var html = "<div><h1>"+str+"</h1><a href='#' onclick='confirmCallback(1);' class='ui-btn ui-btn-a'>确定</a></div>";
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
        text: str, //加载器中显示的文字
        textVisible: true, //是否显示文字
        theme: "b",        //加载器主题样式a-e
        textonly: true,   //是否只显示文字
        html: html           //要显示的html内容，如图片等

    });
    mobiletool.confirmDialog = true;
}

//隐藏加载器.for jQuery Mobile 1.2.0
function hideLoader() {
    //隐藏加载器
    $.mobile.loading('hide');
    mobiletool.confirmDialog = false;
}

function goback(){
    $.mobile.back();
}



function rrplace(tpl,colums,records,index){
    index = index || 0;
    var int = tpl.indexOf("%s");
    if(int!=-1){
        var p = colums[index];
        var str = records[p];
        tpl = tpl.replace("%s",str);
        index++;
        return rrplace(tpl,colums,records,index);
    }else{
        return tpl;
    }
}

$.fn.extend({
    refreshShowListView:function(records){
        var attr = $(this).data("property");
        if(attr && records && records.length){
            attr = JSON.parse(attr);
            var html = "";
            var tpl = attr.tpl;
            var colums = attr.colums;
            for(var i=0;i<records.length;i++){
                var li = rrplace(tpl,colums,records[i]);
                html+=li;
            }
            $(this).html(html);
            $(this).listview('refresh');
        }else{
            $(this).html("");
            $(this).listview('refresh');
        }
    }
});

$.fn.extend({
    refreshShowSelectMenu:function(records,placeholder,id,text){
        var html = "";
        if(placeholder){
            html = '<option value="" selected="selected">'+placeholder+'</option>';
        }
        if(records && records.length){
            id = id || "id";
            text = text || "title";

            var tpl = '<option value="%s">%s</option>';
            var colums = [id,text];

            for(var i=0;i<records.length;i++){
                var op = rrplace(tpl,colums,records[i]);
                html+=op;
            }
        }
        $(this).html(html);
        $(this).selectmenu('refresh');
    }
});

$.fn.extend({
    refreshInsertView:function(records){
        var attr = $(this).data("property");
        if(attr && records && records.length){
            attr = JSON.parse(attr);
            var html = "";
            var tpl = attr.tpl;
            var colums = attr.colums;
            for(var i=0;i<records.length;i++){
                var li = rrplace(tpl,colums,records[i]);
                html+=li;
            }
            $(this).html(html);
        }else{
            $(this).html("");
        }
    }
});