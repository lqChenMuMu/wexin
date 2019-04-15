/*   类别选择页面     */

function classInit() {
    var classInfoUrl = "/second-class/classInfo";
    $.ajax({
        type: "get",
        url: classInfoUrl,
        async: false,
        dataType: "json",
        success: function (res) {
            if (res.code == "0") {
                var leftBar = "<ul>";
                var rightBar = "";
                for (var i = 0; i < res.data.length; i++) {
                    var firstClass = res.data[i].firstClass;
                    if (i == 0) {
                        leftBar += "<li class='active'>" + firstClass.className + "</li>";
                        rightBar += "<section class='menu-right padding-all j-content'><ul>";
                    } else {
                        leftBar += "<li>" + firstClass.className + "</li>";
                        rightBar += "<section class='menu-right padding-all j-content' style='display: none'><ul>";
                    }

                    var secondClassList = res.data[i].secondClassList;
                    for (var j = 0; j < secondClassList.length; j++) {
                        rightBar += "<li class='w-3'>";
                        rightBar += "<span>" + secondClassList[j].className + "</span>";
                        rightBar += "<div class='checkbox icheck-peterriver'><input type='checkbox' id='" + i + j + "' value='" + secondClassList[j].id + "'/>"
                        rightBar += "<label for='" + i + j + "'></label></div></li>";
                        rightBar += "<p>" + secondClassList[j].details + "</p>";
                    }
                    rightBar += "</ul></section>";
                }
                leftBar += "</ul>";

                $("#sidebar").html(leftBar);
                $("#rightbar").html(rightBar);
            } else {
                alert(res.msg);
            }
        }
    });

    $('#sidebar ul li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        var index = $(this).index();
        $('.j-content').eq(index).show().siblings('.j-content').hide();
    });

    $('.menu-right ul li span').click(function () {
        $(this).offsetParent().next("p").slideToggle(100);
        $('p').not($(this).offsetParent().next()).slideUp('fast');
    });

    $('#affirmClassBtn').click(function () {
        var classIds = "";
        $("input[type='checkbox']:checked").each(function () {
            classIds += this.value + ",";
        });
        if (validatenull(classIds)) {
            alert("请选择服务！");
            return;
        }
        window.location.href = "/pages/affirm.html?classIds=" + classIds;
    })
}


/*提交预约信息页面*/
function affirmInit() {
    $("#codeBtn").click(function () {
        var sendCodeUrl = "/appointment/sendCode";
        var tel = $('#telphone').val();
        console.log(tel)
        console.log($('#telphone'))
        if (validatemobile(tel) == false) {
            return;
        }
        clickTime();
        $.ajax({
            type: "get",
            url: sendCodeUrl,
            async: false,
            data: {
                telphone: tel
            },
            success: function (res) {
                if (res.code == "0") {
                    if (res.data == 0) {
                        alert("发送成功");
                    } else {
                        alert("发送失败");
                    }
                } else {
                    alert(res.msg);
                }
            }
        });
    });

    $("#affirmBtn").click(function () {
            var classIds = getQueryString("classIds");

            var name = $("#name").val();
            if (validatenull(name) == true) {
                alert("请输入姓名！");
                return;
            }
            var company = $("#company").val();
            if (validatenull(company) == true) {
                alert("请输入公司名称！");
                return;
            }
            var appointmentTime = $("#dateText").val();
            if (validatenull(appointmentTime) == true) {
                alert("请选择预约时间！");
                return;
            }
            var telphone = $("#telphone").val();
            if (validatenull(telphone) == true) {
                alert("请输入手机号码！");
                return;
            }
            var validateCode = $("#validateCode").val();
            if (validatenull(validateCode) == true) {
                alert("请输入验证码！");
                return;
            }
            var remark = $("#remark").val();
        $.ajax({
            type: "post",
            url: "/appointment/affirm",
            async: false,
            dataType: "json",
            data: {
                "name": name,
                "company": company,
                "time":appointmentTime,
                "telphone":telphone,
                "validateCode":validateCode,
                "remark":remark,
                "classId":classIds
            },
            success: function (res) {
                if (res.code == "0") {
                    window.location.href="/pages/success.html?classIds=" + classIds;;
                } else {
                    alert(res.msg);
                }
            }
        });
        }
    )
}

var wait = 60;

function clickTime() {
    if (wait == 0) {
        $("#codeBtn").html("获取验证码");
        $("#codeBtn").removeAttr("disabled")
        wait = 60;
    } else {
        $("#codeBtn").html("重新发送(" + wait + ")");
        $("#codeBtn").attr("disabled", "disabled");
        wait--;
        setTimeout(function () {
                clickTime()
            },
            1000)
    }
}

function appointmentSuccessInit(){
    $.ajax({
        type: "get",
        url: "/material/get",
        async: true,
        dataType: "json",
        data: {
            "classIds": getQueryString("classIds")
        },
        success: function (res) {
            if (res.code == "0") {
                var materialList = "资料清单：";
                for(var i=0; i<res.data.length; i++){
                    if(i == res.data.length-1){
                        materialList += res.data[i].materialText+"。";
                    }else{
                        materialList += res.data[i].materialText+",";
                    }
                }
                console.log(materialList);
                $("#materialP").html(materialList);
            } else {
                alert(res.msg);
            }
        }
    });
}

function myAppointmentInit() {
    $.ajax({
        type: "get",
        url: "/appointment/myAppointment",
        async: false,
        success: function (res) {
            if (res.code == "0") {
                var appointmentList = "";
                for(var i=0; i<res.data.length; i++){
                    appointmentList += "<div class='myAppointment'>"
                    appointmentList += "<div class='appointmentTime'>";
                    appointmentList += res.data[i].appointment.time;
                    appointmentList += "</div>"
                    appointmentList += "<div class='infoHead classList'></div>"
                    appointmentList += "<p>已预约项目："
                    for(var j=0; j<res.data[i].secondClassList.length; j++){
                        appointmentList += (j+1)+":"+  res.data[i].secondClassList[j].className + " ";
                    }
                    appointmentList += "</p></br>"

                    appointmentList += "<div class='infoHead materialList'></div>"
                    appointmentList += "<p>需准备资料："
                    for(var k=0; k< res.data[i].materialList.length; k++){
                        appointmentList += (k+1)+":"+  res.data[i].materialList[k].materialText;
                    }
                    appointmentList += "</p></br>"
                    appointmentList += "</div>"

                }
                if (appointmentList == ""){
                    appointmentList += " <img src='../img/noexist.png'/>" +
                        "    <p class='nop'>暂无预约</p>"
                }
                $(".page").html(appointmentList);
            } else {
                alert(res.msg);
            }
        }
    });
}
