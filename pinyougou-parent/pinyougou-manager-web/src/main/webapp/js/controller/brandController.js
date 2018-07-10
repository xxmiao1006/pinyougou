//品牌控制层
app.controller("brandController",function ($scope,$controller,brandService) {
    //============controller Begin====================

    //继承其他控制器,其实是伪继承，传递$scope
    $controller('baseController',{$scope:$scope});

    $scope.findAll=function(){
        //get传入请求的url,success传入请求成功执行的回调函数，函数中的传的参数来接受返回的数据
        brandService.findAll().success(
            function (response) {
                $scope.list=response;
            }
        );
    }




    //查询页数据
    $scope.findPage=function (page,size) {
        brandService.findPage(page,size).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    $scope.searchEntity={};//初始化
    //条件分页查询
    $scope.search=function (page, size) {

        brandService.search(page,size,$scope.searchEntity).success(
            function (response) {
                //alert(response.rows);
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
                //$scope.searchEntity={};这里不能加这段话，因为查询一次search方法其实会被调用2次，另外一个在分页空间的onChange方法中
            }
        );
    }

    //新增或修改品牌
    $scope.save=function () {
        var method=null;
        if($scope.entity.id!=null){
            method=brandService.update($scope.entity);
        }else{
            method=brandService.add($scope.entity);
        }
        method.success(
            function (response) {
                if(response.success){
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
            }
        );
    }

    //取出数据(直接从前端获取)
    // $scope.selectOne=function(id, name, firstChar) {
    //     $scope.entity={"id":id,"name":name,"firstChar":firstChar};
    // }

    //取出数据
    $scope.findOne=function(id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            });
    }



    //删除品牌
    $scope.dele=function () {
        if($scope.selectIds.length==0){
            alert("请先勾选要删除的选项！");
            return;
        }
        if(confirm("确认删除？")){
            brandService.dele($scope.selectIds).success(
                function (response){
                    if(response.success){
                        $scope.selectIds=[];
                        $scope.reloadList();
                        $scope.select_all=false;
                    }else {
                        alert(response.message);
                    }
                });
        }

    }

    //全选/反选
    $scope.selectAll = function () {
        if(!$scope.select_all) {
            angular.forEach($scope.list, function (i) {//i对应entity in list中的entity
                i.checked = true;//checked对应循环里ng-model中的entity.checked
                $scope.selectIds.push(i.id);
            });
        }else {
            angular.forEach($scope.list, function (i) {
                i.checked = false;
                $scope.selectIds = [];
            });
        }
    };





    //==============controller End====================
});