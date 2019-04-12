function isChinese(obj) {
    var reg = /^[\u0391-\uFFE5]+$/;
    if (obj != "" && !reg.test(obj)) {
        alert('必须输入中文！');
        return false;
    }
}

function CheckForm(obj)
{
    if (obj.length == 0) {
        alert("姓名不能为空!");
        return false;
    }
    return true;
    alert("姓名不能为空!");
}

function validatemobile(mobile)
{
    if(mobile.length==0)
    {
        alert('手机号码不能为空！');
        return false;
    }
    if(mobile.length!=11)
    {
        alert('请输入有效的手机号码，需是11位！');
        return false;
    }

    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if(!myreg.test(mobile))
    {
        alert('请输入有效的手机号码！');
        return false;
    }
}