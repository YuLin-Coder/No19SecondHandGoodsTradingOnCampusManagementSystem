
var IdeaScroll = function(data){
    var me = this;
    me.data = data;
    me.cidx = 1;
    me.leng = data.length;
    me.ewidth = 360;
    me.translength = 0;
    me.length=0;
    me.delay=300;
    me.containerEl = document.getElementById("ideascroller");
    me.pointersEL = document.getElementById("idxptcontainer");
    me.pcontainerEl = document.getElementById("scrollcontainer");
    me.parentEl = me.pcontainerEl.parentNode;
    me.pwidth = $(me.parentEl).width();
    me.scroll2left = function(){
        me.delay=300;
        if(me.cidx<me.leng){
            me.length = me.translength-me.ewidth;
            me.cidx++;
        }else{
            me.cidx=1;
            me.length = 0;
            me.delay=0;
        }
        me.translength = me.length;
        $(me.containerEl).css({"-webkit-transform": "translateX("+me.translength+"px)", "-webkit-transition": "-webkit-transform "+me.delay+"ms ease-in"});
        var pidx = me.leng-me.cidx;
        $(".pointer").removeClass("pointerfocus");
        $($(".pointer")[pidx]).addClass("pointerfocus");
    }
    me.init = function(){
        var pright = 10;
        var ileft = 0;
        for(var i=0;i<data.length;i++){
            var pel = document.createElement("DIV");
            pel.className="pointer";
            pel.style.right=pright+"px";
            me.pointersEL.appendChild(pel);
            pright+=20;

            var item = document.createElement("DIV");
            item.className="scrollitem";
            item.style.left=ileft+"px";
            item.style.backgroundImage="url('images/2.jpg')";
            me.containerEl.appendChild(item);
            ileft+=360;
        }

        $($(".pointer")[me.leng-1]).addClass("pointerfocus");
        //$(me.pcontainerEl).css("transform","scale("+me.pwidth/480+")");
    };
    me.init();
};


function initIdeaScroll(){
    var adata=[1,2,4];
    var scroll = new IdeaScroll(adata);
    setInterval(function(){
        scroll.scroll2left();
    },2000);
}
