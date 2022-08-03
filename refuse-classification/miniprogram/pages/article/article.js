// pages/searchRecord/searchRecord.js
var app = getApp();
let config = require("../../http/env.js")
let env="Dev"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    config:config[env]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.checkMemberId();
  },

  checkMemberId: function(){
    let self=this;
    let memberId=wx.getStorageSync('memberId');
    console.log("[检查ID]"+memberId)
    if(memberId==""||memberId==null){
      wx.request({
        url: this.data.config.baseApi+'/user/getMemberInfo',
        header: {
          'content-type': 'application/json',
          'token': wx.getStorageSync('token')
        },
        success(res) {
          memberId = res.data.data.memberId;
          app.get("/sortingArticle/getAllArticle", {
            memberId: res.data.data.memberId
          }).then(res => {
            wx.setStorageSync('memberId',memberId);
            self.setData({
              articleList:res.list
            });
          }).catch(err => {
            console.log(err);
          })
        }
      })
    }else{
      app.get("/sortingArticle/getAllArticle", {
        memberId: memberId
      }).then(res => {
        wx.setStorageSync('memberId',memberId);
        self.setData({
          articleList:res.list
        });
         console.log(res.list);
      }).catch(err => {
        console.log(err);
      })
    }
    
  },

  doClickDetail: function (event) {
    // console.log(event.currentTarget.dataset.id)
    wx.navigateTo({
      url: "../detail/detail?article_Id=" + JSON.stringify(event.currentTarget.dataset.id)
    })
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

  }
})