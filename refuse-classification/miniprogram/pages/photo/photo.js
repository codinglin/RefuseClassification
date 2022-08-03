var app = getApp();
let config = require("../../http/env.js")
let env="Dev"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    config:config[env],
    src: '',//图片路径
    show: false,//标识

  },

  // 重新拍照(取消)
  cancelBtn () {
    this.setData({show: false})
  },

  // 完成拍照(保存)
  saveImg () {

    wx.showModal({
      title: '图片地址',
      content: this.data.src,
    })

  },

  // 拍摄功能函数
  takePhoto() {

    // 创建CameraContext对象
    const ctx = wx.createCameraContext()

    // 监听
    const listener = ctx.onCameraFrame((frame) => {
      console.log(frame)
    })

    // 拍照实时帧
    ctx.takePhoto({

      quality: 'high',//成像质量(高)
      
      // 成功回调
      success: (res) => {

        this.setData({//更新数据
          src: res.tempImagePath,//图像路径
          show: true
        })

        app.post("/eduoss/fileoss", {
          file: this.data.src
        },res).then(res => {
          console.log(res)
        }).catch(err => {
          console.log(err);
        })
        console.log(this.data.src)
        
        // 停止监听实时帧
        listener.stop({

          success: (res) => {//成功回调
            
          },

          fail: (err) =>{//失败回调(错误)
            wx.showToast({
              title: '监听实时帧失败，请退出重试！'
            })
          }
        })
      },

      // 错误处理(接口调用失败)
      fail: (err) => {
        wx.showToast({
          title: '失败，请退出重试！'
        })
      }
    })
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
