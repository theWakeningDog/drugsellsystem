<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- edited by dongls on 2016/8/5. -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>客户信息</title>
</head>
<body>
<style>
    .label-btn {
        visibility: hidden;
        cursor: pointer;
    }

    .info-row:hover .label-btn {
        visibility: visible;
    }
   


</style>
<div class="container-fluid" style="padding: 10px 15px 10px 15px;" id="page">

    <div class="box box-solid " style="border-bottom:solid 1px #ccc;">
        <div class="box-body" >
            <div class="btn-group">
                <a class="btn btn-default   btn-group-discrete" href="javascript:backOperater();">
                    <li class="fa fa-angle-left"></li>
                    &nbsp;&nbsp;返回
                </a>
                <c:if test="${customer.isDelete == 0 }">
                    <a href="/customer/edit/${customer.id}" class="btn btn-warning btn-group-discrete"><i class="fa fa-edit"></i>&nbsp;&nbsp;编辑</a>
                    <a type="button " href="javascript:void(0);" class="btn  btn-danger  btn-group-discrete" id="del-btn">
                        <li class="fa fa-trash-o"></li>
                        &nbsp;&nbsp;删除
                    </a>
                </c:if>
            </div>

            <c:if test="${customer.isDelete != 1}">
                <div class="pull-right ">
                    <a class="btn btn-primary " href="javaScript:void(0);" id="addProducts"><i
                            class="fa fa-plus"></i>添加产品</a>
                <c:if test="${customer.status == 1}">
                    <a class="btn btn-primary " href="/task/createFromCustomer/${customer.id}"><i
                            class="fa fa-plus"></i>新建工单</a>
                </c:if>
                </div>
            </c:if>
        </div>
    </div>
    <script>
        $("#del-btn").click(function () {
            var msg = [];
            var ids = '${customer.id}';
            if (confirm("确定要删除该客户？")) {
                $.get("/customer/delete/" + ids, {}, function (data) {
                    if(data.status != 0){
                        alert(data.message);
                    }else{
                        window.location.href = "/customer";
                    }

                });
            }
        });

        function deleteCustomer() {
            var msg = [];
            var ids = getIds();
            if (ids.length < 1) {
                alert("请选择需要删除的客户！");
                return false;
            }
            if (confirm("确定要删除选择的客户？")) {
                $.get("/customer/delete/" + getIds(), {}, function (data) {
                    data.forEach(function (item) {
                        if (item.status == 0) {
                            msg.push(item.message);
                        }
                    });
                    if (msg.length > 0) {
                        alert(msg.join('\n\n'))
                    }
                    window.location.reload();
                });
            }
        }
    </script>


    <div class="row">
        <div class="col-sm-4">
            <div class="box box-solid">
                <div class="box-body form-horizontal">
                    <div style="padding-top: 10px;padding-bottom: 10px;border-bottom: 1px dashed #ccc;">
                        <span style="font-weight: 700 ;font-size: large ;">${customer.name}</span>
                        <c:if test="${customer.isDelete == 1}">
                            <span style="color: red">&nbsp;&nbsp;[已删除]</span>
                        </c:if>
                        <c:if test="${customer.status == 0}">
                            <span style="color: red">&nbsp;&nbsp;[已禁用]</span>
                        </c:if>
                    </div>
                    <div style="padding-top: 10px;padding-bottom: 10px">
                        <div class="form-group">
                            <div class="col-md-3 col-sm-3 control-label">
                                <span  style="font-weight: 700">电话：</span>
                            </div>
                            <div class="col-md-9 col-sm-9" style="padding-top: 7px">
                                <label id="lmPhone"
                                       class="">${customer.lmPhone}</label>
                            </div>





                            <div class="col-md-3 col-sm-3 control-label">
                                <span  style="font-weight: 700">区域：</span>
                            </div>
                            <div class="col-md-9 col-sm-9" style="padding-top: 7px">
                                <label id="address"
                                       class="">${customer.customerAddress.adProvince}-${customer.customerAddress.adCity}<c:if test="${!empty customer.customerAddress.adDist}">-${customer.customerAddress.adDist}</c:if></label>
                            </div>


                            <div class="col-md-3 col-sm-3 control-label">
                                <span  style="font-weight: 700">详细地址：</span>
                            </div>
                            <div class="col-md-9 col-sm-9" style="padding-top: 7px">
                                <label id="addressDetial"
                                       class="">${customer.customerAddress.adAddress}</label>
                            </div>
                            <div class="col-md-3 col-sm-3 control-label">
                                <span  style="font-weight: 700">创建时间：</span>
                            </div>
                            <div class="col-md-9 col-sm-9" style="padding-top: 7px">
                                <span><fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                            </div>
                            <p2:formData fieldInfos="${fieldInfo}" entity="${customer.attribute}" readonly="true"></p2:formData>
                        </div>
                        </div>
                        <div style="padding-top: 10px;padding-bottom: 10px">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="box-solid">
                    <div class="box-header with-border">
                        <div class="nav-tabs-custom ">

                <ul class="nav nav-tabs" id="nav-tabs">

                    <li class="<c:if test="${empty active}">active</c:if>"><a href="#tab-tasks" data-toggle="tab">工单</a></li>
                    <c:if test="${customer.isDelete == 0}">
                        <li class="<c:if test="${active == 'product'}">active</c:if>"><a href="#tab-customerProduct" data-toggle="tab">客户产品</a></li>
                    </c:if>
                </ul>
                <div class="tab-content">
                    <%--历史任务--%>

                    <div class="tab-pane <c:if test="${empty active}">active</c:if>" id="tab-tasks">
                        <div class="tab-pane-wrap">
                            <table id="task-table" class="table table-select  table-striped table-bordered">
                                <thead>
                                <tr class="active">

                                    <th >工单编号</th>
                                    <th >产品名称</th>
                                    <th>任务状态</th>
                                    <th >负责人</th>
                                    <th >创建时间</th>
                                    <th >完成时间</th>
                                    <th>客户评价</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tasks}" var="task">
                                    <tr data-id="${task.id}" class="info-row">
                                        <td><a href="/task/view/${task.id}">${task.taskNo}</a></td>

                                        <td>${task.product.name}</td>
                                        <td>${p:stateDisplayName(task.state,"cn.publink.servicemobile.base.entity.EnumTaskTarget") }</td>
                                        <td>${task.executor.displayName}</td>
                                        <td><fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><fmt:formatDate value="${task.completeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><label  data-container="body" data-toggle="popover" data-title="客户反馈" data-trigger="manual" data-html="true"
                                                    data-content="反馈内容：${task.suggestion}">${task.degree}</label></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <script>
                            $(function () {
                                var tbody = $("#task-table tbody");
                                //联系人列表--------------
                                var lmTable = $('#task-table').DataTable({
                                    paging: true,
                                    lengthChange: false,
                                    searching: false,
                                    ordering: true,
                                    info: true,
                                    autoWidth: false,
                                    iDisplayLength : 10,
                                    order: [[ 5, "desc" ]]
                                });
                                console.log(lmTable);


                                $('[data-toggle="popover"]').popover({ trigger:'manual',
                                    placement : 'right',delay: {hide: 100}}).on("mouseenter", function () {
                                    var _this = this;
                                    $(this).popover("show");
                                    $(this).siblings(".popover").on("mouseleave", function () {
                                        $(_this).popover('hide');
                                    });
                                }).on("mouseleave", function () {
                                    var _this = this;
                                    setTimeout(function () {
                                        if (!$(".popover:hover").length) {
                                            $(_this).popover("hide")
                                        }
                                    }, 100);
                                });
                                $("[data-toggle='popover']").mouseleave(function () {
                                    $(".popover").mouseleave(function () {
                                        $(this).remove();
                                    });


                                });
                            });
                        </script>
                    </div>
                    <%--历史任务--%>

                    <%--已安装产品--%>
                    <div class="tab-pane  <c:if test="${active == 'product'}">active</c:if>" id="tab-customerProduct">
                        <div class="box-body no-padding">

                            <table id="cp-table" class="table table-select  table-striped table-bordered">
                                <thead>
                                    <tr  class="active" >
                                        <th name="serialNumber">序号</th>
                                        <th name="name">名称</th>
                                        <th name="productType">类型</th>
                                        <th name="createTime">创建时间</th>
                                        <th name="delete" style="width: 150px">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${customer.products}" var="product">
                                    <tr class="cp-info-row" >
                                        <td name="serialNumber">${product.serialNumber}</td>
                                        <td name="productName">${product.name}</td>
                                        <td name="productType">${product.type}</td>
                                        <td name="createTime"><fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td name="isDelete">
                                            <a type="button" cpId="${product.id}" href="javascript:void(0);" onclick="editProduct(this)" class="btn btn-warning aaa btn-group-discrete" >
                                            <li class="fa fa-edit"></li>
                                            &nbsp;&nbsp;修改
                                            </a>
                                            <a type="button" cpId="${product.id}" href="javascript:void(0);" onclick="deleteProduct(this)" class="btn btn-danger btn-group-discrete" >
                                            <li class="fa fa-trash-o"></li>
                                            &nbsp;&nbsp;删除
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table><!-- /.table -->
                        </div><!-- /.box-body -->
                        <script>

                            $(function () {

                                //联系人列表--------------
                                var cpTable = $('#cp-table').DataTable({

                                    paging: true,
                                    lengthChange: false,
                                    searching: false,
                                    ordering: true,
                                    info: true,
                                    autoWidth: false,
                                    iDisplayLength : 10,
                                    order: [[ 3, "desc" ]],
                                    columnDefs:[{
                                        orderable:false,//禁用排序
                                        targets:[4]   //指定的列
                                    }]
                                });
                            });




                            function deleteProduct(obj){
                                if(confirm("你确定要删除该产品吗？")){
                                    var id = $(obj).attr("cpId");
                                    $.ajax({
                                        type:'GET',
                                        url:'/customer/product/delete?id='+id,
                                        success:function (data) {
                                            if(data.status == 0){
                                                window.location.href="/customer/view/"+customerId+"?active=product";
                                            }else{
                                                alet("删除失败");
                                            }
                                        }
                                    });
                                };
                            }
                            function editProduct(obj){
                                var id = $(obj).attr("cpId");
                                var name = $(obj).parent("td").siblings("td[name='productName']").html();
                                var serialNumber =$(obj).parent("td").siblings("td[name='serialNumber']").html();
                                var type = $(obj).parent("td").siblings("td[name='productType']").html();
                                $("#editModal").modal("show");
                                $("#editForm #productId").val(id);
                                $("#editForm #serialNumber").val(serialNumber).trigger('validate');
                                $("#editForm #name").val(name).trigger('validate');
                                $("#editForm #type").val(type);

                            }
                        </script>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<div class="modal fade" id="product-modal" tabindex="-1" role="dialog" aria-labelledby="字段信息" data-backdrop="false">
    <div class="modal-dialog" role="document">
        <div class="modal-content" >
            <form id="productForm">
                <input  type="hidden" value="${customer.id}" name="id"/>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加产品</h4>
            </div>
            <div class="modal-body" style="height: 132px">

                <div class="col-md-10 col-sm-10" style="margin-top: 20px">
                    <button type="button" class="btn btn-default  center-block" id="addProductItem"  style="width:100px">添加产品</button>
                </div>
            </div>

            <div class="modal-footer">

                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="btnSave" type="submit" class="btn btn-primary">保存</button>
            </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="字段信息" data-backdrop="false">
    <div class="modal-dialog" role="document">
        <div class="modal-content" >
            <form class="form-horizontal" id="editForm" >
                <input  type="hidden" id="productId" name="id"/>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel2">编辑产品</h4>
                </div>
                <div class="modal-body " style="height: 180px">
                    <div class="container-fluid" style="padding : 15px;">


                            <input type="hidden" name="sparePart.id" id="sparePartId">
                            <div class="form-group">
                                <label class="control-label col-xs-2" for="serialNumber">产品编码：</label>
                                <div class=" col-xs-7">
                                    <input type="text" name="serialNumber" class="form-control" id="serialNumber"/>
                                </div>
                                <div class="col-xs-3 validation-tip"></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2" for="name">产品名称：</label>
                                <div class=" col-xs-7">
                                    <input type="text" name="name" class="form-control" id="name" data-rule="required;"/>
                                </div>
                                <div class="col-xs-3 validation-tip"></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-2" for="type">类型：</label>
                                <div class=" col-xs-7">
                                    <select  class="form-control "  name="type" id="type">
                                        <c:forEach items="${options.productType}" var="productType">
                                            <option value="${productType}">${productType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-xs-3"></div>
                            </div>



                    </div>

                </div>

                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="btnEditSave" type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="form-group products hidden addition" id="productTmpl">
    <div class="col-md-10 col-sm-10">
        <div class=" col-sm-4 no-padding">
            <input type="text" class="form-control notNull productSerialNumber"  placeholder="请输入产品序号" />
        </div>
        <div class=" col-sm-4 no-padding">
            <input type="text"  class="form-control notNull productName"  placeholder="请输入产品名称" />
        </div>
        <div class=" col-sm-4 no-padding">
            <select  class="form-control notNull productType"  >
                <c:forEach items="${options.productType}" var="productType">
                    <option value="${productType}">${productType}</option>
                </c:forEach>
            </select>
        </div>

    </div>
    <div class="col-sm-1 validation-tip"></div>
    <div class=" col-sm-1  ">
        <span class="fa-stack fa-md " >
            <a href="javascript:void(0);" onclick="delAddition(this)">
                <i class="fa fa-trash " style="font-size: 16px "></i>
            </a>
            </span>
    </div>
</div>
<script>
    var index =0;
    var customerId = '${customer.id}';
    var action="/product/create/list";
    var itemIndex = 1;
    function  delAddition(ele) {
        $("#product-modal .modal-body").css("height",100+32*(--itemIndex));
        $(ele).parents(".products").remove();
    }
    $(function(){
        $("#addProducts").click(function () {
            $("#product-modal").modal('show');
            addItem();
            $("#product-modal .products span").addClass("hidden");
        });
        $("#addProductItem").click(function () {
            var height = $("#product-modal .modal-body").css("height");
            $("#product-modal .modal-body").css("height",100+32*(++itemIndex));
            addItem();
        });
        function addItem() {
            var $item = $("#productTmpl").clone(true).removeClass("hidden").removeProp("id");
            $item.find("input.serialNumber").prop("name","products"+index++);
            $item.find("input.productName").attr("data-rule","required").prop("name","products"+index++);
            $("#addProductItem").parent("div").before($item);
        }
        $("#product-modal").on('hide.bs.modal', function () {
            $("#product-modal").find(".products").remove();
            index = 0;
            itemIndex = 1;
            $("#product-modal .modal-body").css("height",132);
        });
        $('#editForm').validator({
            valid: function (form) {
                var that = this;
                that.holdSubmit(true);
                $.ajax({
                    type:'post',
                    url:"/product/update",
                    data:$(form).serialize(),
                    success:function (data) {
                        $("#product-modal").modal('hide');
                       if(data.status == 0){
                           window.location.href="/customer/view/"+customerId+"?active=product";
                       }else{
                           alert(data.message);
                       }
                    },
                    complete:function () {
                        that.holdSubmit(false);
                    }
                });

                return false;
            }
        });
        $('#productForm').validator({
            valid: function (form) {

                var $products= $(".products");
                $products.each(function (index,value) {
                    $(value).find(".productName").prop("name","products["+index+"].name")
                    $(value).find(".productSerialNumber").prop("name","products["+index+"].serialNumber")
                    $(value).find(".productType").prop("name","products["+index+"].type")
                });
                var that = this;
                that.holdSubmit(true);
                $.ajax({
                    type:'post',
                    url:action,
                    data:$(form).serialize(),
                    success:function (data) {
                        $("#product-modal").modal('hide');
                        if(data.status == 0){
                            window.location.href="/customer/view/"+customerId+"?active=product";
                        }else{
                            alert(data.message);
                        }
                    },
                    complete:function () {
                        that.holdSubmit(false);
                    }
                });

                return false;

            }
        });
    });
</script>
</body>
</html>
