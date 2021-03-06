 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService, brandService, specificationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;
				//将字符串转成json对象或者将json对象转成json集合的方法
				$scope.entity.brandIds=JSON.parse($scope.entity.brandIds);
                $scope.entity.specIds=JSON.parse($scope.entity.specIds);
                $scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){
        if($scope.selectIds.length==0){
            alert("请先勾选要删除的选项！");
            return;
        }
		//获取选中的复选框
        if(confirm("确认删除？")) {
            typeTemplateService.dele($scope.selectIds).success(
                function (response) {
                    if (response.success) {
                        $scope.reloadList();//刷新列表
                        $scope.selectIds = [];
                    }else {
                    	alert(response.message);
					}
                }
            );
        }
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

    //$scope.brandList={data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]};//品牌列表

    $scope.brandList={data:[]};//品牌列表

    //读取品牌列表
    $scope.findBrandList=function(){
        brandService.selectOptionList().success(
            function(response){
                $scope.brandList={data:response};
            }
        );

    }
    $scope.specificationList={data:[]};//规格列表

    //读取规格列表
    $scope.findSpecificationList=function(){
        specificationService.selectOptionList().success(
            function(response){
                $scope.specificationList={data:response};
            }
        );
    }

    //新增扩展属性行
    $scope.addTableRow=function(){
        $scope.entity.customAttributeItems.push({});
    }

    //删除扩展属性行
    $scope.deleTableRow=function(index){
        $scope.entity.customAttributeItems.splice(index,1);
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
