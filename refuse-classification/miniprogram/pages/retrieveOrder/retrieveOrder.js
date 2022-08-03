// pages/retrieveOrder/retrieveOrder.js
var app = getApp();
let config = require("../../http/env.js")
let env = "Dev"
var dateTimePicker = require('../../utils/dateTimePicker.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    config: config[env],
    date: '2021-04-01',
    time: '12:00',
    dateTime: null,
    dateTimeArray: null,
    startYear: 2000,
    endYear: 2050,
    day: '',
    estimatedWeight: '',
    message: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    // var timestamp = new Date().getTime()
    // console.log(new Date())
    var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    var time = dateTimePicker.getHourMinu();
    obj.dateTime[2] = parseInt((obj.defaultDay).substring(0, 2)) - 1; //day 字符串 'xx日' 转 'int'
    this.setData({
      dateTime: obj.dateTime,
      dateTimeArray: obj.dateTimeArray,
      day: obj.defaultDay,
      time: time
    });
    let { addressId, addressName, markerId, name, desc } = e;
    this.setData({
      addressId, addressName, markerId, name, desc
    });
    // console.log(this.data.addressId);
    // console.log(this.data.addressName);
    // console.log(this.data.markerId);
    // console.log(this.data.name);
  },

  doClickAddress: function (event) {
    // console.log(event.currentTarget.dataset.id)
    let self = this;
    let url = `/pages/address_panel/address_panel?markerId=${self.data.markerId}&name=${self.data.name}&desc=${self.data.desc}`;
    wx.navigateTo({
      url
    })
  },

  bindTimeChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      time: e.detail.value
    })
  },
  bindEstimatedWeight(e) {
    this.data.estimatedWeight = e.detail;
    // console.log(e.detail)
    this.setData({
      estimatedWeight: this.data.estimatedWeight
    })
  },
  bindMessage(e) {
    //方法一
    this.data.message = e.detail;
    // console.log(e.detail)
    this.setData({
      message: this.data.message
    })
  },
  doClickSubmit: function (e) {
    //console.log(document.getElementById('#estimatedWeight').value)
    // console.log(this.data.estimatedWeight)
    var start_time = (this.data.dateTimeArray[0][this.data.dateTime[0]]).substring(0, 4) + '/' + (this.data.dateTimeArray[1][this.data.dateTime[1]]).substring(0, 2) + '/' + (this.data.day).substring(0, 2) + ' ' + this.data.time
    console.log(start_time)
    var time = new Date(start_time)
    var timestamp = time.getTime()
    console.log(timestamp)
    let self = this;
    let memberId = wx.getStorageSync('memberId');
    console.log("[检查ID]" + memberId)
    if (memberId == "" || memberId == null) {
      wx.request({
        url: self.data.config.baseApi + '/user/getMemberInfo',
        header: {
          'content-type': 'application/json',
          'token': wx.getStorageSync('token')
        },
        success(res) {
          memberId = res.data.data.memberId;
          app.post("/sortingOrder/addOrder", {
            type: 1,
            userId: res.data.data.memberId,
            companyId: self.data.markerId,
            addressId: self.data.addressId,
            anticipationTime: timestamp,
            evaluateWeight: self.data.estimatedWeight,
            itemDetail: self.data.message
          }, e).then(res => {
            wx.setStorageSync('memberId', memberId);
            wx.navigateTo({
              url: "../myRetrieve/myRetrieve"
            })
          }).catch(err => {
            console.log(err);
          })
        }
      })
    } else {
     
      console.log(memberId+"----"+self.data.markerId+"--"+self.data.addressId+"----"+timestamp+"--预估重量"+self.data.estimatedWeight+"---信息"+self.data.message)
      app.post("/sortingOrder/addOrder", {
        userId: memberId,
        type: 1,
        companyId: self.data.markerId,
        addressId: self.data.addressId,
        anticipationTime: timestamp,
        evaluateWeight: self.data.estimatedWeight,
        itemDetail: self.data.message
      }, e).then(res => {
        wx.navigateTo({
          url: "../myRetrieve/myRetrieve"
        })
      }).catch(err => {
        console.log(err);
      })
    }
  },

  changeDateTimeColumn(e) {
    var arr = this.data.dateTime, dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);
    //console.log(arr);
    this.setData({
      dateTimeArray: dateArr,
      dateTime: arr,
      day: dateArr[2][arr[2]].substring(0, 3),
    });
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