function exitApp(){
    navigator.app.exitApp();
}
/****************文件上传****************/
//cordovaFlag是否有phonegap环境，从而决定是否调用本地方法
var cordovaFlag = false;
var uploadFileUrl = null;
document.addEventListener("deviceready", onDeviceReady, false);
var pictureSource; //  getPicture:数据来源参数的一个常量
var destinationType; // getPicture中：设置getPicture的结果类型
var watchID = null;
function onDeviceReady() {
    pictureSource = navigator.camera.PictureSourceType;
    destinationType = navigator.camera.DestinationType;
    cordovaFlag = true;
    //getContacts();
    //showComfire("提示!","测试对话框",function(index){
     //       alert(index);
    //});

    /*watchID = navigator.accelerometer.watchAcceleration(function(acceleration) {
        alert('Acceleration X: ' + acceleration.x + '\n' +
                'Acceleration Y: ' + acceleration.y + '\n' +
                'Acceleration Z: ' + acceleration.z + '\n' +
                'Timestamp: '      + acceleration.timestamp + '\n');
      }, function() {
          alert('onError!');
      }, { frequency: 3000 });*/
}
/**
 * 读取联系人
 */
function getContacts(){
// find all contacts
    var options = new ContactFindOptions();
    options.filter = "";//搜索条件
    options.multiple = true;
    var filter = ["displayName", "phoneNumbers"];
    navigator.contacts.find(filter, function(contacts){
        for (var i = 0; i < contacts.length; i++) {
            //for (var j = 0; j < contacts[i].phoneNumbers.length; j++) {
            //    alert(contacts[i].displayName+":"+contacts[i].phoneNumbers[j]);
            //}
            alert(contacts.length+":"+JSON.stringify(contacts[i]));
        }
    }, function(contactError){
        alert('onError!');
    }, options);
}

function getMsgs(){

}

function showComfire(title,msg,callback){
    navigator.notification.confirm(
        msg,
        function(buttonIndex){
            callback && callback(buttonIndex);
        },
        title,
        ['确定','取消']
    );
}

function getFilePic(source, element) {
    navigator.camera.getPicture(function (imageURI) {
        console.log("imageURI:"+imageURI);
        uploadFileUrl = imageURI;
        var pre = uploadFileUrl.substring(0,4);
        if(pre!="file"){
        	uploadFileUrl = myObj.getPath(uploadFileUrl);
        }
        if(element){
            $("#" + element).attr("src", uploadFileUrl);
        }else{
            $("#path") && $("#path").text(imageURI);
            $("#path2") && $("#path2").text(imageURI);
        }

    }, function () {
        if (source == pictureSource.CAMERA)
            console.log('加载照相机出错!');
        else
            console.log('加载相册出错!');
    }, {
        quality: 90,
        destinationType: destinationType.FILE_URI,
        sourceType: source
        //allowEdit:false,
        //encodingType: Camera.EncodingType.JPEG,//去掉这个就可以选择文件
        //targetWidth: 300,
        //targetHeight: 300,
        //popoverOptions: CameraPopoverOptions,
        //saveToPhotoAlbum: true
    });
}

function getFileAttach(callback) {
    navigator.camera.getPicture(function (imageURI) {
        callback && callback(imageURI);
    }, function () {

    }, {
        quality: 50,
        destinationType: destinationType.FILE_URI,
        sourceType: 0
    });
}

function captureSound(){
    navigator.device.capture.captureAudio(captureSuccess, captureError, {limit:1});
}

// capture callback
var captureSuccess = function(mediaFiles) {
    changePage("lushuaddpage","none");
    var i, path, len;
    for (i = 0, len = mediaFiles.length; i < len; i += 1) {
        path = mediaFiles[i].fullPath;
        uploadFileUrl = path;
        $("#soundbar").attr("src",path);
        // do something interesting with the file
    }
};

// capture error callback
var captureError = function(error) {
    navigator.notification.alert('Error code: ' + error.code, null, 'Capture Error');
};

// start audio capture


/**
 * 上传意外终止处理。
 * @param message
 */
function uploadBroken(message) {
    alert(message);
};

/**
 * 上传过程回调，用于处理上传进度，如显示进度条等。
 */
function uploadProcessing(progressEvent) {

    if (progressEvent.lengthComputable) {
        //已经上传
        var loaded = progressEvent.loaded;
        //文件总长度
        var total = progressEvent.total;
        //计算百分比，用于显示进度条
        var percent = parseInt((loaded / total) * 100);
        showLoader("正在上传文件:" + percent + "%");
        //换算成MB
        //loaded=(loaded/1024/1024).toFixed(2);
        //total=(total/1024/1024).toFixed(2);
    }
};

/**
 * 选择文件后回调上传。
 */
function uploadFile(fileURI, url, success, fail) {
    var options = new FileUploadOptions();
    options.fileKey = "file";
    options.fileName = fileURI.substr(fileURI.lastIndexOf('/') + 1);
    options.mimeType = "multipart/form-data";
    options.chunkedMode = false;
    ft = new FileTransfer();
    var uploadUrl = encodeURI(url);
    console.log(fileURI);
    ft.upload(fileURI, uploadUrl, success, fail, options);
    //获取上传进度
    ft.onprogress = uploadProcessing;
}


function localFile(fileUrl,filename) {
    fileUrl = fileUrl || downloadUrl+"?attach="+focusobj.attach;
    filename = filename || focusobj.attach;
    window.requestFileSystem(LocalFileSystem.PERSISTENT,  5*1024*1024, function(fileSystem){
        //创建目录
        fileSystem.root.getDirectory("file_mobile", {create:true},
            function(fileEntry){ },
            function(){  console.log("创建目录失败");});

        var _localFile = "file_mobile/"+filename;
        var _url = fileUrl;
        //查找文件
        fileSystem.root.getFile(_localFile, null, function(fileEntry){
            //文件存在就直接显示
            showLoader("文件已经下载过了!",true);
        }, function(){
            //否则就到网络下载图片!
            fileSystem.root.getFile(_localFile, {create:true}, function(fileEntry){
                var targetURL = fileEntry.toURL();
                download(_url,targetURL);
            },function(){
                alert('下载图片出错');
            });
        });

    }, function(evt){
        console.log("加载文件系统出现错误");
    });
}


function download(fileUrl,targetUrl){
    showLoader("正在下载请稍候!");
    var fileTransfer = new FileTransfer();
    var uri = encodeURI(fileUrl);

    fileTransfer.download(uri,targetUrl,
        function(entry) {
            showLoader("下载成功,文件已保存到file_mobile文件夹下",true);
            console.log("download complete: " + entry.fullPath);
        },
        function(error) {
            console.log("download error source " + error.source);
            console.log("download error target " + error.target);
            console.log("upload error code" + error.code);
        },
        false
    );
}

/**
 * 上传成功回调.
 * @param r
 */
function uploadSuccess(r) {
    alert('文件上传成功:' + r.response);
}

/**
 * 上传失败回调.
 * @param error
 */
function uploadFailed(error) {
    alert('上传失败了。');
}

/****************文件上传结束****************/
function openJisuanqi(){
    cordova.exec(function(data){

    }, null, "Plugs", "jisuanqi", []);
}


function toBaidu(city,address){
    cordova.exec(function(){},function(){},"Plugs","tobaidu",[{city: city,str:address}]);
}

function toShare(subject,text){
    cordova.exec(function(){},function(){},"Plugs","share",[{subject: subject,text:text}]);
}

function toDail(el){
    var tel = $(el).text();
    tel = tel.split(":")[1];
    cordova.exec(function(){},function(){},"Plugs","toDail",[{tel:tel}]);
}

function toAddress(el){
    var address = $(el).text();
    address = address.split(":")[1];
    cordova.exec(function(){},function(){},"Plugs","toAddress",[{address:address}]);
}

function uplaodImg(callback){
    if(uploadFileUrl){
        uploadFile(uploadFileUrl,uploadUrl,function(r){
            var img = r.response;
            callback && callback(img);
        },function(error){
            showLoader(error,true);
        });
    }else{
        showLoader("请选择图片!",true);
        callback && callback("");
    }
}

function uploadAttach(fileUrl,callback){
    if(fileUrl){
        uploadFile(fileUrl,uploadUrl,function(r){
            var img = r.response;
            callback && callback(img);
        },function(error){
            showLoader(error,true);
        });
    }else{
        showLoader("请选择图片!",true);
    }
}

function scanCode(callback){
    cordova.exec(function(data){
        callback && callback(data);
    }, null, "Plugs", "scan", []);
}

function getLocationH5(cb){
    var option = {enableHighAccuracy: true};
    navigator.geolocation.getCurrentPosition(function (position){
        cb && cb(position);
    }, function (){

    },option);
}



var _watchtimmer = null;
var xiaohaipoint = null;
function startWatch(){
    showLoader("正在定位!");
    //_watchtimmer = setInterval(function (){
        getLocationH5(function (position){
            hideLoader();
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            ajaxCallback("addLocation",{latitude:latitude,longitude:longitude,uid:userinfo.id},function(data){

            });
            /*alert('Latitude: '          + position.coords.latitude          + '\n' +
             'Longitude: '         + position.coords.longitude         + '\n' +
             'Altitude: '          + position.coords.altitude          + '\n' +
             'Accuracy: '          + position.coords.accuracy          + '\n' +
             'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
             'Heading: '           + position.coords.heading           + '\n' +
             'Speed: '             + position.coords.speed             + '\n' +
             'Timestamp: '         + position.timestamp                + '\n');*/
            //xiaohaipoint = new BMap.Point(longitude,latitude+0.001);
            var mypoint = new BMap.Point(longitude,latitude);
            /*map.clearOverlays();
            addMarketIcon(mypoint,"mylocation.png");*/
            //addMarketIcon(xiaohaipoint,"xiaohai.png");
            map.centerAndZoom(mypoint, 16);
            //getDistances(xiaohaipoint,mypoint);
        });
    //},5000);
}

