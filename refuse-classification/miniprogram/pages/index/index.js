const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatorDots: false,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    kindList:[],
    active: 0,
    imgUrls: [
      "../../images/nav_1.jpg",
      "../../images/nav_2.jpg",
      "../../images/nav_3.jpg"
    ],
    // notice: [
    //   "垃圾分类最新通知",
    //   "垃圾分类从我做起"
    // ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      kindList: app.globalSortData.kindList
    })
    app.get("/sortingArticle/getNotice", {
      showIndex: 2
    }).then(res => {
      //console.log(res.noticeVOS);
      this.setData({
        notice:res.noticeVOS
      })
    }).catch(err => {
      console.log(err);
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

  },

  doClick: function (event) {
    wx.navigateTo({
      url: "../kindInfo/kindInfo?_info=" + JSON.stringify(event.currentTarget.dataset.item)
    })
  },

  doClickMicrophone: function (event) {
    wx.navigateTo({
      url: "../microphone/microphone"
    })
  },

  doClickPhoto: function (event) {
    wx.navigateTo({
      url: "../takePhoto/takePhoto"
    })
  },

  doClickSearch: function (event) {
    wx.navigateTo({
      url: "../search/search"
    })
  },

  doClickDetail: function (event) {
    wx.navigateTo({
      url: "../detail/detail?article_Id=" + JSON.stringify(event.currentTarget.dataset.item)
    })
  }
})