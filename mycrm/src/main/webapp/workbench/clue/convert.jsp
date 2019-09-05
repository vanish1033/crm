<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>

    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <script type="text/javascript">
        $(function () {

            // 选择日期的控件
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#isCreateTransaction").click(function () {
                if (this.checked) {
                    $("#create-transaction2").show(150);
                } else {
                    $("#create-transaction2").hide(150);
                }
            });

            // 为模糊搜索市场活动text框绑定回车事件
            $("#searchActivityByNameVague").keydown(function (event) {
                if (event.keyCode == 13) {
                    $.ajax({
                        url: "workbench/clue/searchActivityByNameVague.do",
                        data: {
                            aname: $("#searchActivityByNameVague").val(),
                        },
                        type: "get",
                        dataType: "json",
                        success: function (data) {
                            /*
                                data:[{Activity1},{Activity2},{Activity3}...]
                             */
                            var html = "";
                            $.each(data, function (i, n) {
                                html += '<tr>';
                                html += '<td><input type="radio" name="activity" value="' + n.id + '"/></td>';
                                html += '<td id="e' + n.id + '">' + n.name + '</td>';
                                html += '<td>' + n.startDate + '</td>';
                                html += '<td>' + n.endDate + '</td>';
                                html += '<td>' + n.owner + '</td>';
                                html += '</tr>';
                            });
                            $("#activityBody").html(html);
                        }
                    });
                    return false;
                }

            });

            // 为交易中搜索市场活动模态窗口的保存按钮绑定事件
            $("#saveSearchActivity").click(function () {

                var $radio = $(":radio[name=activity]:checked");
                if ($radio.val() == null) {
                    alert("请先选择一条市场活动数据");
                    return;
                }

                // 把市场活动id刷到隐藏域中
                $("#activityId").val($radio.val());

                // 把市场活动名字刷到文本框中
                $("#activity").val($("#e" + $radio.val()).html());

                // 关闭模态窗口
                $("#searchActivityModal").modal("hide");
            });

            // 为转换按钮绑定事件
            $("#convertBtn").click(function () {
                if ($("#isCreateTransaction").prop("checked")) {
                    // 用户需要创建交易
                    $("#tranForm").submit();
                } else {
                    // 不需要创建交易，直接把cid提交后台就行
                    window.location.href = "workbench/clue/convert.do?cid=${param.id}";
                }
            });
        });
    </script>

</head>
<body>

<!-- 搜索市场活动的模态窗口 -->
<div class="modal fade" id="searchActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 90%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">搜索市场活动</h4>
            </div>
            <div class="modal-body">
                <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                    <form class="form-inline" role="form">
                        <div class="form-group has-feedback">
                            <input type="text" id="searchActivityByNameVague" class="form-control" style="width: 300px;"
                                   placeholder="请输入市场活动名称，支持模糊查询">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td></td>
                        <td>名称</td>
                        <td>开始日期</td>
                        <td>结束日期</td>
                        <td>所有者</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody id="activityBody">
                    <%--                    <tr>--%>
                    <%--                        <td><input type="radio" name="activity"/></td>--%>
                    <%--                        <td>发传单</td>--%>
                    <%--                        <td>2020-10-10</td>--%>
                    <%--                        <td>2020-10-20</td>--%>
                    <%--                        <td>zhangsan</td>--%>
                    <%--                    </tr>--%>
                    <%--                    <tr>--%>
                    <%--                        <td><input type="radio" name="activity"/></td>--%>
                    <%--                        <td>发传单</td>--%>
                    <%--                        <td>2020-10-10</td>--%>
                    <%--                        <td>2020-10-20</td>--%>
                    <%--                        <td>zhangsan</td>--%>
                    <%--                    </tr>--%>
                    </tbody>
                </table>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="saveSearchActivity">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="title" class="page-header" style="position: relative; left: 20px;">
    <h4>转换线索
        <small>${param.fullname}${param.appellation}-${param.company}</small>
    </h4>
</div>
<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
    新建客户：${param.company}
</div>
<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
    新建联系人：${param.fullname}${param.appellation}
</div>
<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
    <input type="checkbox" id="isCreateTransaction"/>
    为客户创建交易
</div>
<div id="create-transaction2"
     style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;">

    <form id="tranForm" action="workbench/clue/convert.do" method="post">
        <input type="text" name="cid" value="${param.cid}" hidden>  <!--隐藏域-->
        <div class="form-group" style="width: 400px; position: relative; left: 20px;">
            <label for="amountOfMoney">金额</label>
            <input type="text" class="form-control" name="money" id="amountOfMoney">
        </div>
        <div class="form-group" style="width: 400px;position: relative; left: 20px;">
            <label for="tradeName">交易名称</label>
            <input type="text" class="form-control" id="tradeName" name="tranName" value="动力节点-">
        </div>
        <div class="form-group" style="width: 400px;position: relative; left: 20px;">
            <label for="expectedClosingDate">预计成交日期</label>
            <input type="text" class="form-control time" id="expectedClosingDate" name="predictDate" readonly>
        </div>
        <div class="form-group" style="width: 400px;position: relative; left: 20px;">
            <label for="stage">阶段</label>
            <select id="stage" name="stage" class="form-control">
                <c:forEach var="stage" items="${stageList}">
                    <option value="${stage.value}">${stage.text}</option>
                </c:forEach>
                <%--                <option></option>--%>
                <%--                <option>资质审查</option>--%>
                <%--                <option>需求分析</option>--%>
                <%--                <option>价值建议</option>--%>
                <%--                <option>确定决策者</option>--%>
                <%--                <option>提案/报价</option>--%>
                <%--                <option>谈判/复审</option>--%>
                <%--                <option>成交</option>--%>
                <%--                <option>丢失的线索</option>--%>
                <%--                <option>因竞争丢失关闭</option>--%>
            </select>
        </div>
        <div class="form-group" style="width: 400px;position: relative; left: 20px;">
            <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal"
                                                      data-target="#searchActivityModal" style="text-decoration: none;"><span
                    class="glyphicon glyphicon-search"></span></a></label>
            <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
        </div>
        <input type="text" id="activityId" name="activityId" hidden>
    </form>

</div>

<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
    记录的所有者：<br>
    <b>${param.owner}</b>
</div>
<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
    <input class="btn btn-primary" type="button" id="convertBtn" value="转换">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <input class="btn btn-default" type="button" value="取消">
</div>
</body>
</html>