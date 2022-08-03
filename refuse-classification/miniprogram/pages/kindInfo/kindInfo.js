// miniprogram/pages/kindInfo/kindInfo.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    kindInfo:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log("【加载分类信息】", options);
    
    this.setData({
      kindInfo: JSON.parse(options._info)
    })
  },
})