app.controller('baseController', function ($scope) {
//分页控件配置
    $scope.paginationConf = {
        currentPage: 1,//当前页
        totalItems: 10,//总记录数，默认为10
        itemsPerPage: 10,//每页记录数
        perPageOptions: [10, 20, 30, 40, 50],//每页记录数选项
        //当前页码变更时调用的函数，第一次加载时也会调用
        onChange: function () {
            $scope.reloadList();//重新加载
            //$scope.findPage();
        }
    };


    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage, $scope.searchEntity);
    }

    //用户集合
    $scope.selectIds = [];

    //用户勾选或者取消勾选时对id集合进行相应操作
    $scope.updateSelection = function ($event, id) {
        //$event作为参数时表示源，$event.targer当前操作的节点
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            var index = $scope.selectIds.indexOf(id)
            $scope.selectIds.splice(index, 1);
        }
    }

    //将json字符串转成普通字符串,key要取的值
    $scope.jsonToString = function (jsonString, key) {
        var json = JSON.parse(jsonString);
        var value = "";
        for (var i = 0; i < json.length; i++) {
            if (i > 0) {
                value += ", ";
            }
            //不能直接.key，因为key会直接被当属性,但是[]中的值为字符串,key会转成字符串对应属性
            value += json[i][key];
        }

        return value;
    }


    //从集合中根据keyValue查询key(属性名)的值
    $scope.searchObjectByKey = function (list, key, keyValue) {
        for (var i = 0; i < list.length; i++) {
            if (list[i][key] == keyValue) {
                return list[i];
            }
        }
        return null;
    }


});