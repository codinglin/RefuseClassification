// pages/address_panel/address_panel.js
var app = getApp();
let config = require("../../http/env.js")
let env="Dev"


Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    config:config[env],
  },

  onClose: function (e) {
    let self=this;
    console.log("[确认]"+this.data.confirmId);
    app.delete("/userAddress/removeAddress?id="+this.data.confirmId, {}, e).then(res => {
      self.setData({ show: false });
      self.checkMemberId();
    }).catch(err => {
      console.log(err);
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let { markerId, name, desc } = options;
    this.setData({
      markerId, name, desc
    });
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
          app.get("/userAddress/getAllAddress", {
            memberId: res.data.data.memberId
          }).then(res => {
            wx.setStorageSync('memberId',memberId);
            console.log("[检查ID]"+memberId)
            self.setData({
              addressList:res.list
            });
             console.log(res.list);
          }).catch(err => {
            console.log(err);
          })
        }
      })
    }else{
      app.get("/userAddress/getAllAddress", {
        memberId: memberId
      }).then(res => {
        wx.setStorageSync('memberId',memberId);
        self.setData({
          addressList:res.list
        });
         console.log(res.list);
      }).catch(err => {
        console.log(err);
      })
    }
    
  },

  doClickUpdateAddress:function(e){
    // console.log(e.currentTarget.dataset.id);
    wx.navigateTo({
      url: "../address_single/address_single?id="+ JSON.stringify(e.currentTarget.dataset.id)
    })
  },

  doClickMarkerId:function(e){
    // console.log(e.currentTarget.dataset.id);
    let self=this;
    let url = `/pages/retrieveOrder/retrieveOrder?addressId=${e.currentTarget.dataset.id.id}&addressName=${e.currentTarget.dataset.id.province+e.currentTarget.dataset.id.city+e.currentTarget.dataset.id.district+e.currentTarget.dataset.id.address}&markerId=${self.data.markerId}&name=${self.data.name}&desc=${self.data.desc}`;
    wx.navigateTo({
      url
    })
  },

  handleLongPress:function(e){
    this.setData({ 
      show: true,
      confirmId: e.currentTarget.dataset.id.id
     });
    console.log(e.currentTarget.dataset.id.id);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  doClickAddressSingle: function (event) {
    wx.navigateTo({
      url: "../address_single/address_single"
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.checkMemberId();
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