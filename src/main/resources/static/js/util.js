/**
 * 判断是否为空
 */
function validatenull(val,name) {
    if (val == 'null' || val == null || val == 'undefined' || val == undefined || val == '') {
        return true
    } else {
        return false
    }
}

/**
 * 判断手机号码是否正确
 */
function validatemobile(mobile) {

    if (validatenull(mobile)) {
        alert('手机号码不能为空！');
        return false;
    }
    if (mobile.length != 11) {
        alert('请输入有效的手机号码，需是11位！');
        return false;
    }

    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (!myreg.test(mobile)) {
        alert('请输入有效的手机号码！');
        return false;
    }
}