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
            if (!validateName(name)) {
                alert("请输入正确的姓名！");
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
            if (validatemobile(telphone) == false) {
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
                    "time": appointmentTime,
                    "telphone": telphone,
                    "validateCode": validateCode,
                    "remark": remark,
                    "classId": classIds
                },
                success: function (res) {
                    if (res.code == "0") {
                        window.location.href = "/pages/success.html?classIds=" + classIds;
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


function appointmentSuccessInit() {
    var classIds = getQueryString("classIds");
    if (validatenull(classIds) == true) {
        $(".center").css("display", "none");
        return;
    }
    $.ajax({
        type: "get",
        url: "/material/getByClass",
        async: false,
        dataType: "json",
        data: {
            "classIds": classIds
        },
        success: function (res) {
            if (res.code == "0") {
                if (res.data.length > 0) {
                    var materialList = "资料清单：";
                    for (var i = 0; i < res.data.length; i++) {
                        if (i == res.data.length - 1) {
                            materialList += res.data[i].materialText + "。";
                        } else {
                            materialList += res.data[i].materialText + ",";
                        }
                    }
                    console.log(materialList);
                    $("#materialP").html(materialList);
                    $(".center").css("display", "block")
                } else {
                    $(".center").css("display", "none")
                }

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
                for (var i = 0; i < res.data.length; i++) {
                    appointmentList += "<div class='myAppointment'>"
                    appointmentList += "<div class='appointmentTime'>";
                    appointmentList += res.data[i].time;
                    if (res.data[i].appointmentType == 1) {
                        appointmentList += "--项目预约"
                    } else if (res.data[i].appointmentType == 2) {
                        appointmentList += "--展厅预约"
                    }
                    appointmentList += "</div>"
                    appointmentList += "<div class='infoHead classList'></div>"
                    appointmentList += "<p>已预约项目："
                    for (var j = 0; j < res.data[i].classList.length; j++) {
                        appointmentList += (j + 1) + ":" + res.data[i].classList[j] + " ";
                    }
                    appointmentList += "</p></br>"

                    appointmentList += "<div class='infoHead materialList'></div>"
                    appointmentList += "<p>需准备资料："
                    if (res.data[i].materialList.length > 0) {
                        for (var k = 0; k < res.data[i].materialList.length; k++) {
                            appointmentList += (k + 1) + ":" + res.data[i].materialList[k].materialText;
                        }
                    } else {
                        appointmentList += "无"
                    }
                    appointmentList += "</p></br>"
                    appointmentList += "</div>"

                }
                if (appointmentList == "") {
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


/*提交预约信息页面*/
function exAffirmInit() {
    $("#s_codeBtn").click(function () {
        var sendCodeUrl = "/appointment/sendCode";
        var tel = $('#s_telphone').val();
        console.log(tel)
        console.log($('#s_telphone'))
        if (validatemobile(tel) == false) {
            return;
        }
        exClickTime();
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

    $("#s_class").change(function () {
        var value = $("#s_class").val();
        if (value == '其他') {
            $("#hide_class").css("display", "block");
        } else {
            $("#hide_class").css("display", "none");
        }
    });

    $("#s_object").change(function () {
        var value = $("#s_object").val();
        if (value == '其他') {
            $("#hide_object").css("display", "block");
        } else {
            $("#hide_object").css("display", "none");
        }
    });

    $("#s_affirmBtn").click(function () {
            var name = $("#s_name").val();
            if (validatenull(name) == true) {
                alert("请输入姓名！");
                return;
            }
            if (!validateName(name)) {
                alert("请输入正确的姓名！");
                return;
            }
            var company = $("#s_company").val();
            if (validatenull(company) == true) {
                alert("请输入公司名称！");
                return;
            }
            var job = $("#s_job").val();
            if (validatenull(job) == true) {
                alert("请输入职位！");
                return;
            }
            var s_class = $("#s_class").val();
            if (validatenull(s_class) == true) {
                alert("请选择预约类型！");
                return;
            }
            if (s_class == "其他") {
                s_class == $("#s_sca_class").val();
                if (validatenull(s_class) == true) {
                    alert("请备注预约类型！");
                    return;
                }
            }

            var s_object = $("#s_object").val();
            if (validatenull(s_object) == true) {
                alert("请输入接待对象！");
                return;
            }
            if (s_object == "其他") {
                s_object == $("#s_sca_obeject").val();
                if (validatenull(s_object) == true) {
                    alert("请备注接待对象！");
                    return;
                }
            }

            var telphone = $("#s_telphone").val();
            if (validatemobile(telphone) == false) {
                return;
            }
            var validateCode = $("#s_validateCode").val();
            if (validatenull(validateCode) == true) {
                alert("请输入验证码！");
                return;
            }
            var formDate = $("#s_formDate").val();
            if (validatenull(formDate) == true) {
                alert("请选择来访时间！");
                return;
            }
            var toDate = $("#s_toDate").val();
            if (validatenull(toDate) == true) {
                alert("请选择结束时间！");
                return;
            }
            var people = $("#s_people").val();
            if (validatenull(people) == true) {
                alert("请选择入厅人数！");
                return;
            }
            var remark = $("#remark").val();

           /* if (validatenull(remark) == true) {
                var notice = "";
                if (s_class == "其他") {
                    notice += "请备注预约类型"
                }
                if (s_object == "其他") {
                    if (validatenull(notice) == true) {
                        notice += "请备注来访对象"
                    } else {
                        notice += "和来访对象"
                    }

                }
                if (validatenull(notice) == false) {
                    alert(notice);
                    return;
                }
            }*/
            $.ajax({
                type: "post",
                url: "/ex-appointment/affirm",
                async: false,
                dataType: "json",
                data: {
                    "name": name,
                    "company": company,
                    "job": job,
                    "type": s_class,
                    "object": s_object,
                    "formDate": formDate,
                    "toDate": toDate,
                    "telphone": telphone,
                    "validateCode": validateCode,
                    "people": people,
                    "remark": remark
                },
                success: function (res) {
                    if (res.code == "0") {
                        window.location.href = "/pages/success.html";
                    } else {
                        alert(res.msg);
                    }
                }
            });
        }
    )
}

var exwait = 60;

function exClickTime() {
    if (exwait == 0) {
        $("#s_codeBtn").html("获取验证码");
        $("#s_codeBtn").removeAttr("disabled")
        exwait = 60;
    } else {
        $("#s_codeBtn").html("重新发送(" + exwait + ")");
        $("#s_codeBtn").attr("disabled", "disabled");
        exwait--;
        setTimeout(function () {
                exClickTime()
            },
            1000)
    }
}

