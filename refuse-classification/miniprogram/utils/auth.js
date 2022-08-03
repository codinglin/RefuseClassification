const Auth = {}

var app = getApp();

Auth.checkSession = function (appPage, flag) {
    let token = wx.getStorageSync('token');
    //console.log(token);
    if (!token) {
        if ('isLoginNow' == flag) {
            var userInfo = { avatarUrl: "../../images/gravatar.png", nickName: "登录", isLogin: false }
            appPage.setData({ isLoginPopup: true, userInfo: userInfo });
        }

    }
}

Auth.checkLogin = function (appPage) {
    let wxLoginInfo = wx.getStorageSync('wxLoginInfo');
    wx.checkSession({
        success: function () {
            if (!wxLoginInfo.js_code) {
                Auth.wxLogin().then(res => {
                    appPage.setData({ wxLoginInfo: res });
                    wx.setStorageSync('wxLoginInfo', res);
                    console.log('checkSession_success_wxLogins');
                })
            }
        },
        fail: function () {
            Auth.wxLogin().then(res => {
                appPage.setData({ wxLoginInfo: res });
                wx.setStorageSync('wxLoginInfo', res);
                console.log('checkSession_fail_wxLoginfo');
            })
        }
    })
}


Auth.wxLogin = function () {
    return new Promise(function (resolve, reject) {
        wx.login({
            success: function (res) {
                let args = {};
                args.js_code = res.code;
                resolve(args);
            },
            fail: function (err) {
                console.log(err);
                reject(err);
            }
        });
    })
}

Auth.setUserInfoData = function (appPage) {
    let userInfo=wx.getStorageSync('userInfo');
    if (!userInfo.isLogin) {
        appPage.setData({
            userInfo: wx.getStorageSync('userInfo')
        })
    }
}

Auth.checkAgreeGetUser = function (e, app, appPage, authFlag) {
    let wxLoginInfo = wx.getStorageSync('wxLoginInfo');
    //console.log(wxLoginInfo)
    if (wxLoginInfo.js_code) {
        Auth.agreeGetUser(e, wxLoginInfo, authFlag).then(res => {
            if (res.errcode == "") {
                wx.setStorageSync('userInfo', res.userInfo);
                appPage.setData({ userInfo: res.userInfo });
            }
            else {
                var userInfo = { avatarUrl: "../../images/gravatar.png", nickName: "点击登录", isLogin: false }
                appPage.setData({ userInfo: userInfo });
                console.log("用户拒绝了授权");
            }
            appPage.setData({ isLoginPopup: false });

        })
    }
    else {
        console.log("登录失败");
        wx.showToast({
            title: '登录失败',
            mask: false,
            duration: 1000
        });

    }
}


Auth.agreeGetUser = function (e, wxLoginInfo, authFlag) {
    return new Promise(function (resolve, reject) {
        let args = {};
        let data = {};
        args.js_code = wxLoginInfo.js_code;
        if (authFlag == '0' && e.detail.errMsg == 'getUserInfo:fail auth deny') {
            args.errcode = e.detail.errMsg;
            args.userInfo = { isLogin: false }
            args.userSession = "";
            resolve(args);
            return;
        }
        var userInfoDetail = {};
        if (authFlag == '0')//未授权过,通过按钮授权
        {
            userInfoDetail = e.detail;
            //console.log(e.detail.userInfo)
            // console.log("未授权")
        }
        else if (authFlag == '1')//已经授权过，直接通过wx.getUserInfo获取
        {
            userInfoDetail = e;
            console.log("授权")
        }
        if (userInfoDetail && userInfoDetail.userInfo) {
            let userInfo = userInfoDetail.userInfo;
            userInfo.isLogin = true;
            args.avatarUrl = userInfo.avatarUrl;
            args.nickname = userInfo.nickName;
            data.userInfo = userInfo;
            userInfo.nickName="林";
            userInfo.avatarUrl="../../images/head.jpg"
            //console.log(userInfo.avatarUrl)
            //console.log(wxLoginInfo.js_code);
            //console.log(userInfo.nickName);
            // app.post("/user/login",{
            //     code: wxLoginInfo.js_code,
            //     nickName: userInfo.nickName
            // },e).then(res=>{
            //     console.log(res);
            // }).catch(err=>{
            //     console.log(res);
            // })
            
            wx.request({
                url: require("../http/env.js")['Dev'].baseApi+"/user/login",
                data: {
                    code: wxLoginInfo.js_code,
                    nickName: userInfo.nickName,
                    avatar: userInfo.avatarUrl
                },
                method: "POST",
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function (res) {
                    //console.log(res.data.data.token);
                    wx.setStorageSync('token', res.data.data.token);
                    //console.log(res);
                    data.errcode="";                        
                    resolve(data);
                },
                fail: function (error) {
                    console.log(error);
                }
            })
        }
        else {
            args.errcode = "error";
            args.userInfo = { isLogin: false };
            args.userSession = "";
            resolve(args);
        }
    })
}

module.exports = Auth;