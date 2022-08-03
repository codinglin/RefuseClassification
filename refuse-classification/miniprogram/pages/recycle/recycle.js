// pages/recycle/recycle.js
//获取应用实例
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    numbers: 0,
    stores: [],
    focus:true,
    searched:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options)
    this.loadData();
  },

  onReachBottom: function () {
    this.loadData();
  },
  loadData:function(keywords){
    this.loadMarkers();
  },
  loadMarkers: function () {
    app.get("/sorting-position/getAllPositions", {})
    .then(res => {
      if (res.markers.length == 0) {
        this.setData({
          searched:true
        })
      }
      this.setData({
        stores: this.data.stores.concat(res.markers),
        numbers: this.data.numbers + res.markers.length
      });
      //console.log(res.markers);
    }).catch(err => {
      console.log(err);
    })
  },
  search:function(e){
    this.setData({
      keywords: e.detail.value
    },res => {
      this.loadData();
    }) 
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