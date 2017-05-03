<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/meta.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zw
  Date: 2017/3/30
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>回执单</title>
</head>
<body>
<div class="container-fluid" style="padding: 10px 15px 10px 15px;">
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i> 工单号：${taskInfo.taskNo}
                    <%--<small class="pull-right">工单日期: <fmt:formatDate value="${balanceInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>--%>
                </h2>
            </div><!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                <strong>客户信息</strong>
                <address>
                    客户名称：${taskInfo.customer.name}<br>
                    客户地址:${taskInfo.customer.customerAddress}<br>
                    客户电话：${taskInfo.customer.lmPhone}<br>
                </address>
            </div><!-- /.col -->
            <div class="col-sm-4 invoice-col">
                <strong>工单信息</strong>
                <address>
                    工单号：${taskInfo.taskNo}<br>
                    状态：${p:stateDisplayName(taskInfo.state,"cn.publink.servicemobile.base.entity.EnumTaskTarget")}
                    <%--<c:if test="${balanceInfo.balanceConfirm == 1}">已结算</c:if>
                    <c:if test="${balanceInfo.balanceConfirm == 0}">未结算</c:if>--%>
                    <br>
                </address>
            </div><!-- /.col -->
            <div class="col-sm-4 invoice-col">
                <strong>负责人</strong><br>
                负责人：${taskInfo.executor.displayName}<br>
            </div><!-- /.col -->
        </div><!-- /.row -->

        <div>
            <form>
                <div class="row form-group">
                    <label for="receiptContent" class="col-md-1 col-sm-2 control-label">内容：</label>
                    <div class="col-md-9 col-sm-8">
                        <textarea rows="3" class="form-control" style="overflow-y: auto;white-space:normal;" id="receiptContent" name="receiptContent" data-rule="length[~200]"></textarea>
                    </div>
                    <div class="validation-tip"></div>
                </div>
            </form>
            <br>
            <div class="row form-group">
                <label for="attachment" class="col-md-1 col-sm-2 control-label">附件：</label>
                <div class="col-md-9 col-sm-8">
                    <p2:attachment items="${workTask.attachment}" id="attachment" readonly="false"/>
                </div>
            </div>
        </div>
        <!-- Table row -->
        <div class="row">
            <div class="col-xs-12 table-responsive">
                <p class="lead">备件</p>
                <a href="javascript:" style="font-size: 14px" id="part-add-btn"> 添加</a>
                <table class="table" id="partTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>备件</th>
                        <th>序列号</th>
                        <th>规格</th>
                        <th>数量</th>
                        <th>单价</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="whtbody">
                    <%--<c:forEach items="${expenseSheet.sparePartsExpense}" var="item" varStatus="i">
                        <tr>
                            <td><input type='checkbox'></td>
                            <td>${item.name}</td>
                            <td>${item.standard}</td>
                            <td>${item.number}${item.unit}</td>
                            <td>${item.salePrice}</td>
                            <td>${item.number * item.salePrice}</td>
                        </tr>
                    </c:forEach>--%>
                    </tbody>
                </table>
                <p2:frameModal target="#part-add-btn" id="partAdd" title="备件添加"
                               callback="partAddCallBack" onReady="" onOpening="partAddOnOpen"
                               url="/task/sparePart/modal/edit" height="350px"></p2:frameModal>
                <script>
                    var otherCost = 0;//其他价格
                    var serviceCost = 0;//服务小计价格
                    var partCost = 0;//备件小计价格
                    var totalCost = 0;//总价
                    //（用于向后台传递数据,请查看ExpenseSheet类）
                    var expenseSheet = {};
                    //配件数据
                    var sparePartsExpense = [];
                    //服务数据
                    var serviceExpense = [];
                    //其他数据
                    var otherExpense = {};

                    var partTable = $('#partTable').DataTable({
                        "paging": false,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": false,
                        "info": false,
                        "autoWidth": false
                    });
                    function partAddOnOpen(obj) {
                        obj.param = "&taskId=${taskInfo.id}&id=" + 0;
                        return true;
                    }
                    function templateOnOpen(obj) {

                    }

                    function partAddCallBack(o, partObj) {
                        //var sum = parseFloat($("#workHourSum").text() || 0) + parseFloat(o.workHours || 0);
                        var data = parsePartTd(o);
                        if (data) {
                            var row = partTable.row.add(data).draw().node();
                            //$("#wh-table_info").append(parseWorkHourSum(sum));
                            $(row).addClass("info-row").attr("data-id", o.id);
                        }

                        $(row).on("input", function () {
                            if (!/^[\d]*$/.test($(this).find(".partNumber").val())) {
                                return false;
                            }
                        });
                        $(row).on("change", function () {
                            var pSumTotal = 0;//改变数量时，配件的总价格变为0
                            var sumTotal = 0;//总价格置为0（配件总价格+服务总价格 + 其他总价格）
                            var partPrice = $(this).find(".partPrice");
                            var partTotal = $(this).find(".partTotal");
                            var partNumber = $(this).find(".partNumber");
                            $(partTotal).text((parseFloat($(partPrice).text()) * parseInt($(partNumber).val())).toFixed(2));

                            for (var i = 0; i < $(".partTotal").length; i++) {
                                var pTotal = $($(".partTotal")[i]).text();
                                pSumTotal = (parseFloat(pSumTotal) + parseFloat(pTotal)).toFixed(2);
                            }
                            partCost = parseFloat(pSumTotal).toFixed(2);
                            $("#partCost").text(pSumTotal);
                            sumTotal = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2);// + parseFloat($("#otherCost").text())
                            totalCost = parseFloat(sumTotal).toFixed(2);
                            $("#totalCost").text(sumTotal);
                        });
                        //计算成本
                        var pSumTotal123 = 0;
                        for (var i = 0; i < $(".partTotal").length; i++) {
                            var pTotal = $($(".partTotal")[i]).text();
                            pSumTotal123 = (parseFloat(pSumTotal123) + parseFloat(pTotal)).toFixed(2);
                        }
                        //partCost = (parseFloat(partCost) + parseFloat(o.total)).toFixed(2);
                        partCost = (parseFloat(pSumTotal123)).toFixed(2);
                        //totalCost = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text()) + parseFloat($("#otherCost").text())).toFixed(2);
                        totalCost = (parseFloat(partCost) + parseFloat(serviceCost)).toFixed(2);// + parseFloat(otherCost)
                        $("#partCost").text(partCost);
                        $("#totalCost").text(totalCost);

                        var partFlag = true;
                        if (sparePartsExpense.length > 0) {
                            for (var j = 0; j < sparePartsExpense.length; j++) {
                                if (sparePartsExpense[j].id == partObj.id) {
                                    var partNumber234 = parseInt(sparePartsExpense[j].number) + parseInt(partObj.number);
                                    if (partNumber234 > partObj.repertoryCount) {
                                        sparePartsExpense[j].number = partObj.repertoryCount;
                                    } else {
                                        sparePartsExpense[j].number = parseInt(sparePartsExpense[j].number) + parseInt(partObj.number);
                                    }
                                    partFlag = false;
                                    break;
                                }
                            }
                        }
                        if (partFlag) {
                            partObj.taskId = "${taskInfo.id}";
                            partObj.type = "备件";
                            sparePartsExpense.push(partObj);
                            expenseSheet.sparePartsExpense = sparePartsExpense;
                        }
                        $("#partAddModal").modal('hide');

                        $(row).find(".partDelete").click(function () {
                            if (confirm("确认要删除吗？")) {
                                var sumTotal = 0;
                                var pSumTotal = 0;
                                $(this).parents("tr").empty();
                                for (var i = 0; i < $(".partTotal").length; i++) {
                                    var pTotal = $($(".partTotal")[i]).text();
                                    pSumTotal = (parseFloat(pSumTotal) + parseFloat(pTotal)).toFixed(2);
                                }
                                partCost = parseFloat(pSumTotal).toFixed(2);
                                $("#partCost").text(pSumTotal);
                                sumTotal = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2);// + parseFloat($("#otherCost").text())
                                totalCost = parseFloat(sumTotal).toFixed(2);
                                $("#totalCost").text(sumTotal);
                            }
                        });
                    }

                    function parsePartTd(o) {
                        var partTableTr = $("#partTable").find("tr");
                        for (var i = 0; i < partTableTr.length; i++) {
                            var trDataId = $(partTableTr[i]).attr("data-id");
                            if (trDataId == o.id) {
                                //如果所选的数据在table中已经存在了
                                var partNumber123 = parseInt($(partTableTr[i]).find("input[type='number']").val()) + parseInt(o.number);
                                if (partNumber123 >= parseInt(o.repertoryCount)) {
                                    //如果数量超过库存
                                    $(partTableTr[i]).find("input[type='number']").val(o.repertoryCount);
                                    $(partTableTr[i]).find(".partTotal").text(parseFloat(parseInt(o.salePrice) * parseInt(o.repertoryCount)).toFixed(2));
                                } else {
                                    $(partTableTr[i]).find("input[type='number']").val(parseInt($(partTableTr[i]).find("input[type='number']").val()) + parseInt(o.number));
                                    $(partTableTr[i]).find(".partTotal").text(parseFloat(parseInt($(partTableTr[i]).find(".partTotal").text()) + parseInt(o.total)).toFixed(2));
                                }
                                return false;
                            }
                        }
                        var data = [];
                        if ($("#whtbody").find("td").text() == "暂无相关数据") {
                            data.push(1);
                        } else  {
                            data.push($("#partTable").find("tr").length);
                        }
                        data.push(o.name);
                        data.push(o.serialNumber);
                        data.push(o.standard);
                        data.push("<input type='number' step='1' min='1' max='" + o.repertoryCount + "' class='partNumber' value='" + o.number + "' onchange='changeCount(this, " + o.repertoryCount + ")'>");
                        data.push("<span class='partPrice'>"+o.salePrice+"</span>");
                        data.push("<span class='partTotal'>"+o.total+"</span>");
                        data.push('<a href="javascript:void(0);" class="partDelete">删除</a>');
                        return data;
                    }

                    function changeCount(ele, repertoryCount) {
                        if ($(ele).val() > repertoryCount) {
                            alert("库存数量为：" + repertoryCount);
                            $(ele).val(repertoryCount);
                        } else if ($(ele).val() < 0) {
                            alert("请输入正确数量");
                            $(ele).val(1);
                        }
                    }
                </script>
            </div><!-- /.col -->
        </div><!-- /.row -->
        <div class="row">
            <!-- accepted payments column -->
            <div class="col-xs-6">
                <p class="lead">服务</p><a href="javascript:" style="font-size: 14px" id="service-add-btn"> 添加</a>
                <table class="table" id="serviceTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>服务内容</th>
                        <th>数量</th>
                        <th>单价</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="serviceTbody">
                    <%--<c:forEach items="${expenseSheet.serviceExpense}" var="item" varStatus="i">
                        <tr>
                            <td>${i.index + 1}</td>
                            <td>${item.name}</td>
                            <td>${item.number}${item.unit}</td>
                            <td>${item.salePrice}</td>
                            <td>${item.number * item.salePrice}</td>
                        </tr>
                    </c:forEach>--%>
                    </tbody>
                </table>
                <p2:frameModal target="#service-add-btn" id="serviceAdd" title="服务添加"
                               callback="serviceAddCallBack" onReady="" onOpening="serviceAddOnOpen"
                               url="/task/service/modal/edit" height="350px"></p2:frameModal>
                <script>
                    var serviceTable = $('#serviceTable').DataTable({
                        "paging": false,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": false,
                        "info": false,
                        "autoWidth": false
                    });
                    function serviceAddOnOpen(obj) {
                        obj.param = "&taskId=${taskInfo.id}&id=" + 0;
                        return true;
                    }
                    function templateOnOpen(obj) {

                    }
                    function serviceAddCallBack(o, serviceObj) {
                        //var sum = parseFloat($("#workHourSum").text() || 0) + parseFloat(o.workHours || 0);
                        var data = parseServiceTable(o);
                        if (data) {
                            var row = serviceTable.row.add(data).draw().node();
                            //$("#wh-table_info").append(parseWorkHourSum(sum));
                            $(row).addClass("info-row").attr("data-id", o.id);
                        }

                        $(row).on("input", function () {
                            if (!/^[\d]*$/.test($(this).find(".serviceNumber").val())) {
                                return false;
                            }
                        });

                        $(row).on("change", function () {
                            var sSumTotal = 0;//改变数量时，配件的总价格变为0
                            var sumTotal2 = 0;//总价格置为0（配件总价格+服务总价格 + 其他总价格）
                            var servicePrice = $(this).find(".servicePrice");
                            var serviceTotal = $(this).find(".serviceTotal");
                            var serviceNumber = $(this).find(".serviceNumber");
                            $(serviceTotal).text((parseFloat($(servicePrice).text()) * parseInt($(serviceNumber).val())).toFixed(2));

                            for (var i = 0; i < $(".serviceTotal").length; i++) {
                                var sTotal = $($(".serviceTotal")[i]).text();
                                sSumTotal = (parseFloat(sSumTotal) + parseFloat(sTotal)).toFixed(2);
                            }
                            serviceCost = parseFloat(sSumTotal).toFixed(2);
                            $("#serviceCost").text(sSumTotal);
                            sumTotal2 = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2);// + parseFloat($("#otherCost").text())
                            totalCost = parseFloat(sumTotal2).toFixed(2);
                            $("#totalCost").text(sumTotal2);
                        });

                        //计算成本
                        serviceCost = (parseFloat(serviceCost) + parseFloat(o.serviceTotal)).toFixed(2);
                        //totalCost = (parseFloat(totalCost) + parseFloat(o.serviceTotal)).toFixed(2);
                        totalCost = (parseFloat(partCost) + parseFloat(serviceCost)).toFixed(2);// + parseFloat(otherCost)
                        $("#serviceCost").text(serviceCost);
                        $("#totalCost").text(totalCost);

                        var serviceFlag = true;
                        if (serviceExpense.length > 0) {
                            for (var j = 0; j < serviceExpense.length; j++) {
                                if (serviceExpense[j].id == serviceObj.id) {
                                    serviceExpense[j].number = parseInt(serviceExpense[j].number) + parseInt(serviceObj.number);
                                    serviceFlag = false;
                                    break;
                                }
                            }
                        }
                        if (serviceFlag) {
                            serviceObj.taskId = "${taskInfo.id}";
                            serviceObj.type = "服务";
                            serviceExpense.push(serviceObj);
                            expenseSheet.serviceExpense = serviceExpense;
                        }
                        $("#serviceAddModal").modal('hide');

                        $(row).find(".serviceDelete").click(function () {
                            if (confirm("确认要删除吗？")) {
                                var sumTotal2 = 0;
                                var sSumTotal = 0;
                                $(this).parents("tr").empty();
                                for (var i = 0; i < $(".serviceTotal").length; i++) {
                                    var sTotal = $($(".serviceTotal")[i]).text();
                                    sSumTotal = (parseFloat(sSumTotal) + parseFloat(sTotal)).toFixed(2);
                                }
                                serviceCost = parseFloat(sSumTotal).toFixed(2);
                                $("#serviceCost").text(sSumTotal);
                                sumTotal2 = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2);// + parseFloat($("#otherCost").text())
                                totalCost = parseFloat(sumTotal2).toFixed(2);
                                $("#totalCost").text(sumTotal2);
                            }
                        });
                    }

                    function parseServiceTable(o) {
                        var serviceTableTr = $("#serviceTable").find("tr");
                        for (var i = 0; i < serviceTableTr.length; i++) {
                            var trDataId = $(serviceTableTr[i]).attr("data-id");
                            if (trDataId == o.id) {
                                //如果所选的数据在table中已经存在了
                                $(serviceTableTr[i]).find("input[type='number']").val(parseInt($(serviceTableTr[i]).find("input[type='number']").val()) + parseInt(o.serviceNumber));
                                $(serviceTableTr[i]).find(".serviceTotal").text(parseFloat(parseInt($(serviceTableTr[i]).find(".serviceTotal").text()) + parseInt(o.serviceTotal)).toFixed(2));
                                return false;
                            }
                        }
                        var data = [];
                        if ($("#serviceTbody").find("td").text() == "暂无相关数据") {
                            data.push(1);
                        } else  {
                            data.push($("#serviceTable").find("tr").length);
                        }
                        //data.push("<input type='checkbox'>");
                        data.push(o.serviceName);
                        data.push("<input type='number' step='1' min='1' class='serviceNumber' value='" + o.serviceNumber + "'>");
                        data.push("<span class='servicePrice'>"+o.servicePrice+"</span>");
                        data.push("<span class='serviceTotal'>"+o.serviceTotal+"</span>");
                        data.push('<a href="javascript:void(0);" class="serviceDelete">删除</a>');
                        return data;
                    }
                </script>
                <%--<div style="margin-top: 5px">
                    <p class="lead">其他</p>
                    <div class="form-group row">
                        <label for="receiptContent" class="col-md-1 col-sm-2 control-label">金额：</label>
                        <div class="col-md-9 col-sm-8">
                            <input type="number" min="0" step="0.01" class="form-control" id="otherPrice" name="salePrice" value="0.00">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="receiptContent" class="col-md-1 col-sm-2 control-label">备注：</label>
                        <div class="col-md-9 col-sm-8">
                            <textarea rows="2" class="form-control" style="overflow-y: auto;white-space:normal;" id="otherRemark" name="otherRemark" data-rule="length[~100]"></textarea>
                        </div>
                    </div>
                </div>--%>
            </div><!-- /.col -->
            <div class="col-xs-6">
                <div class="row">
                    <p class="lead">合计</p>
                    <div class="table-responsive">
                        <table class="table">
                            <tbody>
                            <tr>
                                <th style="width:50%">备件费用:</th>
                                <td id="partCost">0.00</td>
                            </tr>
                            <tr>
                                <th>服务费用</th>
                                <td id="serviceCost">0.00</td>
                            </tr>
                            <%--<tr>
                                <th>其他费用:</th>
                                <td id="otherCost">0.00</td>
                            </tr>--%>

                            <tr>
                                <th>总计:</th>
                                <td id="totalCost">0.00</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->

        <!-- this row will not appear when printing -->
        <div class="row no-print">
            <div class="col-xs-12">
                <%--<c:if test="${balanceInfo.balanceConfirm == 1}">
                    <form id="mainForm" action="/balance/confirm" method="post">
                        <input type="hidden" name="taskId" value="${balanceInfo.taskId}"/>
                    </form>
                    <button id="btnOK" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> 确认并结算</button>
                </c:if>--%>
                <button id="btnOK" type="button" class="btn btn-primary pull-right"><i class="fa fa-credit-card"></i> 完成</button>
                <button id="btnBack" class="btn btn-default  pull-right" style="margin-right: 5px;"><i class="fa fa-reply"></i> 返回</button>
            </div>
        </div>
    </section>
</div>

<script type="text/javascript">
    <c:if test="${confirmResult.stateus > 0}">
    alert('${confirmResult.message}');
    </c:if>
    $(function () {
        //传递数据
        var task = {};
        /*$("#otherPrice").on("input", function () {
            if (!/^(?!0\.00)(?:0|[1-9]\d*)(?:\.\d{1,2})?$/.test($(this).val())) {
                return false;
            }
        });*/

        /*$("#otherPrice").on("change", function () {
//            var otherCost = parseFloat($("#otherCost").text()).toFixed(2);
//            $("#otherCost").text(parseFloat($(this).val()).toFixed(2));
//            $(document).trigger("updateTotal", parseFloat($(this).val() - otherCost));
            var otherPrice = parseFloat($(this).val()).toFixed(2);
            otherCost = parseFloat($(this).val()).toFixed(2);
            totalCost = (parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2); //+ parseFloat($("#otherCost").text())
            $("#otherCost").text(otherPrice);
            $("#totalCost").text((parseFloat($("#partCost").text()) + parseFloat($("#serviceCost").text())).toFixed(2));// + parseFloat($("#otherCost").text())
        });*/

//        $(document).on("updateTotal", function (event, data) {
//            totalCost = parseFloat(totalCost + data).toFixed(2);
//            $("#totalCost").text(totalCost);
//        });

        $("#btnOK").click(function () {
            if ($("#receiptContent").val() == "") {
                alert("请填写内容！");
            } else {
                if(confirm("确认进行结算操作吗？")){
                    /*otherExpense.taskId = "${taskInfo.id}";
                     otherExpense.name = $("#otherRemark").val();
                     otherExpense.number = 1;
                     otherExpense.type = "其他";
                     otherExpense.salePrice = $("#otherPrice").val();
                     expenseSheet.otherExpense = otherExpense;*/
                    $("#btnOK").prop("disabled", true);
                    $("#btnBack").prop("disabled", true);
                    //附件处理
                    /*console.log($("#attachmentContent .btn-group").length); console.log($("input[type='hidden']").length)*/
                    var attachment = [];
                    //var fileInfo = {};
                    for (var i = 0; i < $("#attachmentContent .btn-group").length; i++) {
                        console.log($("#attachmentHiddenFileId"+ i +""));
                        var id = $("#attachmentHiddenFileId"+ i +"").val();
                        var filename = $("#attachmentHiddenFileName"+ i +"").val();
                        attachment.push({filename:filename, id:id, url:"/files/get?fileId=" + id});
                    }
                    task.id = "${taskInfo.id}";
                    task.receiptContent = $("#receiptContent").val();
                    task.attachment = attachment;
                    var receiptForm = {};
                    receiptForm.expenseSheet = expenseSheet;
                    receiptForm.task = task;
                    $.ajax({
                        type : "post",
                        url: "/task/finish",
                        contentType: "application/json",
                        data: JSON.stringify(receiptForm),
//                        {
//                        expenseSheet: JSON.stringify(expenseSheet),
//                        task: JSON.stringify(task)
//                    },
                        success: function (data) {
                            console.log(data.status);
                            if (data.status == 0) {
                                location.href = "/task"
                            } else {
                                alert(data.message);
                            }
                        },
                        error: function (data) {
                            console.log(data.status);
                        }
                    });
                    //mainForm.submit();
                }
            }
        });

        $("#btnBack").click(function () {
            window.history.back();
        })
    })
</script>
</body>
</html>
