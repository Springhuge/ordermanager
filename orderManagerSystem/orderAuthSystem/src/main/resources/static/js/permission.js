$(function () {
    getPermissionList(0);
});

//获取用户列表
function getPermissionList(pageindex) {
    var params = new URLSearchParams();
    params.append("pageIndex", pageindex + 1);
    params.append("pageSize", 10);
    var queryText = $("#queryText").val();
    if (queryText != null || queryText != "") {
        params.append("queryText", queryText)
    }
    axios.post('/permission/getPermissionList', params)
        .then(function (response) {
            if (response.data.message == "ok") {
                //解析后端传过来的json数据
                var page = response.data.page;
                var data = response.data.page.lists;
                var content = "";

                $.each(data, function (i, n) {
                    content += "<tr>";
                    content += "<td rowspan='1'><input type='checkbox' value='" + n.id + "'/></td>";
                    content += " <td>" + (i + 1) + "</td>";
                    content += "<td>" + n.permission + "</td>";
                    content += "<td>" + n.description + "</td>";
                    content += "</tr>";
                });
                $("#t-userlist tbody").html(content);

                // 创建分页
                $("#Pagination").pagination(page.total, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 2, //主体页数
                    callback: getPermissionList,
                    items_per_page: 10, //每页显示1项
                    current_page: pageindex,
                    prev_text: "上一页",
                    next_text: "下一页"
                });
            } else {
                layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time: 1000, icon: 5, shift: 6});
            }
        })
        .catch(function (error) {
            layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
        });
}

//模糊查询
function fuzzyQuery() {
    getPermissionList(0);
}

$(".pull-right").click(function () {
    $(".header-icon").prop("class", "header-icon active");
});

//全选
$("#t_checkbox").click(function () {
    var checkedStatus = this.checked;
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']");
    $.each(tbodyCheckBox, function (i, n) {
        n.checked = checkedStatus;
    })
});

//添加模态框
function addPermission() {
    $("#addForm")[0].reset();
    $("#exampleModalLabel").html("添加许可");
    $("#commonDialog").html("添加")
    $("#addModel").modal();
}

function doAddPermission() {
    var permission = $("#permission").val();
    var description = $("#description").val();
    if (permission == "") {
        layer.msg("<em style='color:red'>" + "许可不能为空" + "</em>", {time: 1000, icon: 5, shift: 6});
    } else if (description == "") {
        layer.msg("<em style='color:red'>" + "描述不能为空" + "</em>", {time: 1000, icon: 5, shift: 6});
    } else {
        var param = new URLSearchParams();
        param.append("permission", permission);
        param.append("description", description);
        axios.post("/permission/addPermission", param)
            .then(function (response) {
                if (response.data.message == "ok") {
                    window.location.reload();
                } else {
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {
                        time: 1000,
                        icon: 5,
                        shift: 6
                    });
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
            })
    }
}

function updatepermission() {
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']:checked");
    if (tbodyCheckBox.length == 0) {
        layer.msg("<em style='color:red'>请选中需要修改的许可</em>", {time: 1000, icon: 5, shift: 6});
    } else if (tbodyCheckBox.length == 1) {
        $("#addForm")[0].reset();
        $("#exampleModalLabel").html("修改许可");
        $("#commonDialog").html("修改")
        $("#addModel").modal();
        var id = tbodyCheckBox.val();
        axios.get("/permission/getPermissionById", {
            params: {"id": id}
        })
            .then(function (response) {
                if (response.data.message == "ok") {
                    var permission = response.data.permission;
                    $("#permission").val(permission.permission);
                    $("#description").val(permission.description);
                    $("#description").attr("updateId", id);
                } else {
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {
                        time: 1000,
                        icon: 5,
                        shift: 6
                    });
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
            })

    } else {
        layer.msg("<em style='color:red'>最多只能选中一个许可进行修改</em>", {time: 1000, icon: 5, shift: 6});
    }
}

function deletePermissions() {
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']:checked");
    var ids = "";
    if (tbodyCheckBox.length == 0) {
        layer.msg("<em style='color:red'>请至少选择一个许可进行删除</em>", {time: 1000, icon: 5, shift: 6});
    } else {
        layer.confirm("确认要删除这些许可吗?", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.each(tbodyCheckBox, function (i, n) {
                if (i == 0) {
                    ids += $(this).val();
                } else {
                    ids += ",";
                    ids += $(this).val();
                }
            });
            var param = new URLSearchParams();
            param.append("ids", ids);
            axios.post("/permission/deleteLogicPermissions", param)
                .then(function (response) {
                    if (response.data.message == "ok") {
                        window.location.reload();
                    } else {
                        layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time: 1000, icon: 5, shift: 6});
                    }
                })
                .catch(function (error) {
                    layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
                })
        }, function (cindex) {
            layer.close(cindex);
        });
    }
}

function doUpdtePermission() {
    var permission = $("#permission").val();
    var description = $("#description").val();
    var id = $("#description").attr("updateId");
    if (permission == "") {
        layer.msg("<em style='color:red'>" + "许可不能为空" + "</em>", {time: 1000, icon: 5, shift: 6});
    } else if (description == "") {
        layer.msg("<em style='color:red'>" + "描述不能为空" + "</em>", {time: 1000, icon: 5, shift: 6});
    } else {
        var param = new URLSearchParams();
        param.append("permission", permission);
        param.append("description", description);
        param.append("id", id);
        axios.put("/permission/updatePermission", param)
            .then(function (response) {
                if (response.data.message == "ok") {
                    window.location.reload()
                } else {
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time: 1000, icon: 5, shift: 6});
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
            })
    }
}

/**
 * 通用模态框
 * 控制是添加许可还是删除许可
 */
$("#commonDialog").click(function () {
    var title = $("#exampleModalLabel").html();
    switch (title) {
        case "添加许可":
            doAddPermission();
            break;
        case "修改许可":
            doUpdtePermission();
            break;
    }
});