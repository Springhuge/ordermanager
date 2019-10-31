//登陆
function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    if(username == "" || password == ""){
        layer.msg("<em style='color:red'>" + "用户名或密码不能为空" + "</em>", {
            time : 1000,
            icon : 5,
            shift : 6
        });
    }else{
        var params = new URLSearchParams();
        params.append("username",username);
        params.append("password",password);
        axios.post('/user/login',params)
            .then(function (response) {
                if(response.data.message == "ok"){
                   window.location.href="auth/index";
                }else {
                    layer.msg("<em style='color:red'>" + response.data.message + "</em>", {
                        time : 1000,
                        icon : 5,
                        shift : 6
                    });
                }
            })
            .catch(function (error) {
                layer.msg("<em style='color:red'>" + error + "</em>", {
                    time : 1000,
                    icon : 5,
                    shift : 6
                });
            })
    }
}

//注销a
function logout() {
  axios.get("/user/logout")
      .then(function (response) {
          if(response.data.message == "ok"){
              window.location.href="http://localhost:8080/";
          }else {
              layer.msg("<em style='color:red'>" + response.data.message + "</em>", {
                  time : 1000,
                  icon : 5,
                  shift : 6
              });
          }
      })
      .catch(function (error) {
          layer.msg("<em style='color:red'>" + error + "</em>", {
              time : 1000,
              icon : 5,
              shift : 6
          });
      })
}


//回车登录
$("body").keydown(function () {
    if(window.event.keyCode == 13){
        $("#f_login").click();
    }
});

