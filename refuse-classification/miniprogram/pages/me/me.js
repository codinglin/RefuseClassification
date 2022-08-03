// pages/me/me.js
var Auth = require('../../utils/auth.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    userLevel: {},
    openid: '',
    isLoginPopup: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var self = this;
    // 查看是否授权
    // wx.getSetting({
    //   success: function(res){
    //     if (res.authSetting['scope.userInfo']) {
    //       wx.getUserInfo({
    //         success: function(res) {
    //           console.log(res.userInfo)
    //           //用户已经授权过
    //         }
    //       })
    //     }
    //   }
    // });
    Auth.setUserInfoData(self);
    Auth.checkLogin(self);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var self = this;
    wx.getSetting({
      success(res){
        if(res.authSetting['scope.userInfo']){
          wx.getUserInfo({
            success:function(res){
              console.log(res.userInfo)
            }
          })
        }
      }
    })
    Auth.checkSession(self, 'isLoginNow');
  },

  agreeGetUser: function (e) {
    let self = this;
    Auth.checkAgreeGetUser(e, app, self, '0');
  },



  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  doClickArticle: function (event) {
    wx.navigateTo({
      url: "../article/article"
    })
  },

  doClickRecord: function (event) {
    wx.navigateTo({
      url: "../searchRecord/searchRecord"
    })
  },

  doClickComment: function (event) {
    wx.navigateTo({
      url: "../myComment/myComment"
    })
  },

  doClickRetrieve: function (event) {
    wx.navigateTo({
      url: "../myRetrieve/myRetrieve"
    })
  },

  doClickAddress: function (event) {
    wx.navigateTo({
      url: "../address_panel/address_panel"
    })
  },

  closeLoginPopup() {
    this.setData({ isLoginPopup: false });
  },
  openLoginPopup() {
    this.setData({ isLoginPopup: true });
  },
  confirm: function () {
    this.setData({
      'dialog.hidden': true,
      'dialog.title': '',
      'dialog.content': ''
    })
  }
})