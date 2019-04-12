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
        if (validatenull(classIds)){
            alert("请选择服务！");
            return;
        }
        window.location.href = "/pages/index2.html?classIds=" + classIds;
    })
}


/*提交预约信息页面*/
function affirmInit() {
    $("#codeBtn").click(function () {
        var sendCodeUrl = "/appointment/sendCode";
        var tel = $('#telphone').val();
        console.log(tel)
        console.log($('#telphone'))
        if (validatemobile(tel) == false){
            return;
        }
        clickTime();
        $.ajax({
            type: "get",
            url: sendCodeUrl,
            async: false,
            dataType: "json",
            date: tel,
            success: function (res) {
                if (res.code == "0") {
                    if (res.data == 0){
                        alert("发送成功");
                    }else{
                        alert("发送失败");
                    }
                } else {
                    alert(res.msg);
                }
            }
        });
    })
}

var wait = 60;
function clickTime() {
    if (wait == 0) {
        $("#codeBtn").html("获取验证码");
        $("#codeBtn").removeAttr("disabled")
        wait = 60;
    } else {
        $("#codeBtn").unbind();
        $("#codeBtn").html("重新发送(" + wait + ")");
        $("#codeBtn").attr("disabled", "disabled");
        wait--;
        setTimeout(function () {
                clickTime()
            },
            1000)
    }
}
