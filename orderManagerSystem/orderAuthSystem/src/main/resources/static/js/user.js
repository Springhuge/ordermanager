$(function () {
    getUserList(0);
});

//获取用户列表
function getUserList(pageindex){
    var params = new URLSearchParams();
    params.append("pageIndex",pageindex+1);
    params.append("pageSize",10);
    var queryText = $("#queryText").val();
    if(queryText != null||queryText != ""){
        params.append("queryText",queryText)
    }
    axios.post('/user/getAllUser',params)
        .then(function (response) {
            if(response.data.message == "ok"){
                //解析后端传过来的json数据
                var page = response.data.page;
                var data  = response.data.page.lists;
                var content = "";

                $.each(data,function (i,n) {
                    content += "<tr>";
                    content += "<td rowspan='1'><input type='checkbox' value='"+n.id+"'/></td>";
                    content += " <td>"+(i+1)+"</td>";
                    content += "<td>"+n.username+"</td>";
                    //根据数据库中的m和w解析出男女
                    switch (n.sex){
                        case "m":
                            content += "<td><span class='btn-info'>男</span></td>";
                            break;
                        case "w":
                            content += "<td><span class='btn-primary'>女</span></td>";
                            break;
                        default:
                            content += "<td><span class='badge badge-danger'>无</span></td>";
                            break;
                    }
                    content += "</tr>";
                });
                $("#t-userlist tbody").html(content);


                // 创建分页
                $("#Pagination").pagination(page.total, {
                    num_edge_entries : 1, //边缘页数
                    num_display_entries : 2, //主体页数
                    callback : getUserList,
                    items_per_page : 10, //每页显示1项
                    current_page : pageindex,
                    prev_text : "上一页",
                    next_text : "下一页"
                });
            }else {
                layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time : 1000, icon : 5, shift : 6});
            }
      })
        .catch(function (error) {
            layer.msg("<em style='color:red'>" + error + "</em>", {time : 1000, icon : 5, shift : 6});
    });
}

//模糊查询
function fuzzyQuery() {
    getUserList(0);
}

$(".pull-right").click(function () {
    $(".header-icon").prop("class","header-icon active");
});

//全选
$("#t_checkbox").click(function () {
    var checkedStatus = this.checked;
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']");
    $.each(tbodyCheckBox,function (i,n) {
        n.checked = checkedStatus;
    })
});

//添加模态框
function addUser() {
    $("#addModel").modal();
    $("#addForm")[0].reset();
}

$("#addUser").click(function () {
    var username = $("#addUsername").val();
    var password = $("#addPassword").val();
    var sex = $("#addSex input[type='radio'][name='sex']:checked").val();
    if(username == "" || password == ""){
        layer.msg("<em style='color:red'>" + "用户名或密码不能为空" + "</em>", {time : 1000, icon : 5, shift : 6});
    }else{
        var param = new URLSearchParams();
        param.append("username",username);
        param.append("password",password);
        param.append("sex",sex);
        axios.post("/user/addUser",param)
            .then(function (response) {
                if(response.data.message == "ok"){
                    window.location.reload()
                }else{
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time : 1000, icon : 5, shift : 6});
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {time : 1000, icon : 5, shift : 6});
            })
    }
});

function updateUser() {
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']:checked");
    if(tbodyCheckBox.length == 0){
        layer.msg("<em style='color:red'>请选中需要修改的用户</em>", {time : 1000, icon : 5, shift : 6});
    }else if(tbodyCheckBox.length == 1){
        $("#updateForm")[0].reset();
        $("#updateModel").modal();
        var id = tbodyCheckBox.val();
        axios.get("/user/getUserById", {
            params:{"id":id}
        })
            .then(function (response) {
                if (response.data.message == "ok") {
                    var user = response.data.user;
                    $("#updateUsername").val(user.username);
                    $("#updateUsername").attr("updateId",id);
                    switch (user.sex){
                        case "m":
                            $("input[name='updateSex'][type='radio'][value='m']").prop("checked","true");
                            break;
                        case "w":
                            $("input[name='updateSex'][type='radio'][value='w']").prop("checked","true");
                            break;
                        case "":
                            $("input[name='updateSex'][type='radio'][value='w']").prop("checked","");
                            $("input[name='updateSex'][type='radio'][value='m']").prop("checked","");
                            break;
                    }
                } else {
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {time: 1000, icon: 5, shift: 6});
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {time: 1000, icon: 5, shift: 6});
            })

    }else{
        layer.msg("<em style='color:red'>最多只能选中一个用户进行修改</em>", {time : 1000, icon : 5, shift : 6});
    }
}

function deleteUsers() {
    var tbodyCheckBox = $("tbody tr td input[type='checkbox']:checked");
    var ids = "";
    if(tbodyCheckBox.length == 0){
        layer.msg("<em style='color:red'>请至少选择一个用户进行删除</em>", {time : 1000, icon : 5, shift : 6});
    }else{
        layer.confirm("确认要删除这些用户吗?",{icon : 3,title : '提示'},function(cindex) {
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
            axios.post("/user/deleteLogicUsers", param)
                .then(function (response) {
                    if (response.data.message == "ok") {
                        window.location.reload()
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
        }, function(cindex) {
            layer.close(cindex);
        });
    }
}


$("#updateUser").click(function () {
    var username = $("#updateUsername").val();
    var password = $("#updatePassword").val();
    var id = $("#updateUsername").attr("updateId");
    var sex = $("#updateSex input[type='radio'][name='updateSex']:checked").val();
    if(username == ""){
        layer.msg("<em style='color:red'>" + "用户名不能为空" + "</em>", {time : 1000, icon : 5, shift : 6});
    }else{
        var param = new URLSearchParams();
        param.append("username",username);
        param.append("sex",sex);
        param.append("password",$.trim(password));
        param.append("id",id);
        axios.put("/user/updateUser",param)
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
});



