<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>

    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

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

            // 为打开模态窗口的按钮绑定点击事件（先取得user的数据放到模态窗口中form表单中，
            // 并清空form表单之前的数据）
            $("#addActivity").click(function () {

                // 在打开添加市场活动的模态窗口前先清除其内嵌的form表单中的数据
                // 由于jquery没有reset()方法，所以要转为普通dom对象调用reset()方法
                $("#saveActivityForm")[0].reset();

                $.ajax({
                    url: "workbench/Activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (jsonArray) {
                        var html = "";
                        $.each(jsonArray, function (i, n) {
                            html += "<option value='" + n.id + "'>" + n.name + "</option>";
                        });
                        $("#create-marketActivityOwner").html(html);

                        var id = "${user.id}";// 在js里用el表达式要打引号

                        // 用户默认为当前用户
                        $("#create-marketActivityOwner").val(id);

                        // 前面的准备工作完成了，下面展示模态窗口
                        $("#createActivityModal").modal("show");
                    }
                });
            });

            /**
             * 为添加市场活动的模态窗口的保存按钮绑定事件
             */
            $("#saveActivity").click(function () {
                $.ajax({
                    url: "workbench/Activity/saveActivity.do",
                    data: {
                        owner: $.trim($("#create-marketActivityOwner").val()),
                        name: $.trim($("#create-marketActivityName").val()),
                        startDate: $.trim($("#create-startTime").val()),
                        endDate: $.trim($("#create-endTime").val()),
                        cost: $.trim($("#create-cost").val()),
                        description: $.trim($("#create-describe").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        /**
                         * data{success:true/false}
                         */
                        if (data.success) {
                            // 刷新Activity列表
                            getActivityList(1, 3);
                            //添加成功就关闭模态窗口
                            $("#createActivityModal").modal("hide");
                        } else {
                            alert("添加市场活动失败");
                        }
                    }

                })
            });

            /**
             * 为模糊查询绑定方法
             */
            $("#searchBtn").click(function () {
                $("#hidden-name").val($("#search-name").val());
                $("#hidden-owner").val($("#search-owner").val());
                $("#hidden-startDate").val($("#search-startDate").val());
                $("#hidden-endDate").val($("#search-endDate").val());
                getActivityList(1, 5)
            });

            // 页面加载完成后自动查全部市场活动数据
            getActivityList(1, 3);

            /**
             * 为全选框绑定事件
             */
            $("#checkAll").click(function () {
                $(":checkbox[name=xz]").prop("checked", this.checked);
            });

            /**
             * 为复选框绑定事件
             */
            $("#activityList").on("click", $(":checkbox[name=xz]"), function () {
                // 判断全部复选框的数量
                var length1 = $(":checkbox[name=xz]").length;
                // 判断全部选中的复选框数量
                var length2 = $(":checkbox[name=xz]:checked").length;

                $("#checkAll").prop("checked", length2 == length1);

            });

            // 为删除市场活动按钮绑定事件
            $("#deleteBtn").click(function () {
                var $checkbox = $(":checkbox[name=xz]:checked");
                if ($checkbox.length == 0) {
                    alert("请选择你要删除的数据");
                    return false;
                }

                if (confirm("真的要删除记录吗")) {

                    // 拼接参数格式
                    var ajaxData = "";
                    for (var i = 0; i < $checkbox.length; i++) {
                        ajaxData += ("id=" + $checkbox[i].value);
                        if (i != $checkbox.length - 1) {
                            ajaxData += "&";
                        }
                    }

                    $.ajax({
                        url: "workbench/Activity/deleteActivity.do",
                        data: ajaxData,
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                getActivityList(1, 3);
                                return;
                            }
                            alert("删除失败");
                        }
                    })
                }
            });
        });

        /**
         * 查询Activity数据的方法
         */
        function getActivityList(pageNo, pageSize) {

            // 将全选框的选中状态取消
            $("#checkAll").prop("checked", false);

            $.ajax({
                url: "workbench/Activity/getActivityList.do",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#hidden-name").val()),
                    "owner": $.trim($("#hidden-owner").val()),
                    "startDate": $.trim($("#hidden-startDate").val()),
                    "endDate": $.trim($("#hidden-endDate").val())
                },
                type: "get",
                dataType: "json",
                success: function (data) {
                    var html = "";

                    $.each(data.dataList, function (i, n) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\'">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';
                    });

                    $("#activityList").html(html);

                    // 计算总页数
                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;

                    //以上列表数据处理完毕之后，添加bs_pagination分页插件
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 4, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //以下函数的触发时机是在，当我们点击了分页组件后，触发该方法
                        onChangePage: function (event, data) {
                            getActivityList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="saveActivityForm" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">
                                <option>zhangsan</option>
                                <option>lisi</option>
                                <option>wangwu</option>
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">活动名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveActivity">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                                <option>zhangsan</option>
                                <option>lisi</option>
                                <option>wangwu</option>
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" value="5,000">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" id="search-name" type="text">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" id="search-owner" type="text">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button id="searchBtn" type="button" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addActivity">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" id="deleteBtn" class="btn btn-danger"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input id="checkAll" type="checkbox"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityList">
                <%--                            <tr class="active">--%>
                <%--                                    <td><input type="checkbox"/></td>--%>
                <%--                                    <td><a style="text-decoration: none; cursor: pointer;"--%>
                <%--                                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
                <%--                                    <td>zhangsan</td>--%>
                <%--                                    <td>2020-10-10</td>--%>
                <%--                                    <td>2020-10-20</td>--%>
                <%--                                </tr>--%>
                <%--                                <tr class="active">--%>
                <%--                                    <td><input type="checkbox"/></td>--%>
                <%--                                    <td><a style="text-decoration: none; cursor: pointer;"--%>
                <%--                                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
                <%--                                    <td>zhangsan</td>--%>
                <%--                                    <td>2020-10-10</td>--%>
                <%--                                    <td>2020-10-20</td>--%>
                <%--                                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
        </div>

    </div>

</div>
</body>
</html>