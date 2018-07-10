//文件上传服务层
app.service("uploadService",function($http){
    this.uploadFile=function(){
        var formData=new FormData();
        //files[0]为文件上传框的name
        formData.append("file",file.files[0]);
        return $http({
            method:'POST',
            url:"../upload.do",
            data: formData,
            //不指定默认为JSON格式，指定undefined浏览器会帮我们把Content-Type 设置为 multipart/form-data
            headers: {'Content-Type':undefined},
            //对表单进行序列化
            transformRequest: angular.identity
        });
    }
});
