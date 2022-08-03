// pages/microphone/microphone.js
const record_manager=wx.getRecorderManager()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    authed: false,
    recording: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.get_record_auth()
  },


  //获取录音授权
  get_record_auth(){
    var that=this;
    //使用getSetting获取录音机权限
    wx.getSetting().then(res=>{
      //如果返回值中，有scope.record，就说明用户之前已经授权
      if(res.authSetting['scope.record']){
        that.setData({authed:true})
      }
      //如果没有，就说明用户还未授权，则需要发起授权申请
      else{
        //使用authorize发起授权申请
        wx.authorize({
          scope: 'scope.record',//这里申请record（录音）权限
        }).then(res=>{//用户授权，申请成功
          that.setData({authed:true})
        }).catch(err=>{//用户没有授权，申请失败
          that.cancel_auth()
        })
      }
    })
  },


  //用户没有授权，之后就不会再出现授权申请了
  //需要用户打开设置页面，手动重新授权
  cancel_auth(){
    var that=this;
    wx.showModal({
      title:'提示',
      content:'不授权无法录音哦～',
      cancelText:'不授权',
      confirmText:'去授权',
      success:res=>{
        if(res.confirm){//用户点击去授权执行
          wx.openSetting({//点击去授权后，会打开设置页面
            success(res){//用户从设置页面返回到小程序时，小程序会获取到其授权情况
              if(res.authSetting['scope.record']){//判断用户是否授权录音
                that.setData({authed:true})
              }
            }
          })
        }
      }
    })
  },

   //录音授权完成，如果用户授权了，authed就是true就可以进行录音
  //如果用户最终还是没有授权，那么authed就是false，就无法进行录音

  //开始录音
  start_record(){
    //录音参数，需要按照百度语音接口的要求进行
    const options={
      sampleRate:16000,
      numberOfChannels:1,
      encodeBitRate:48000,
      format:'PCM'
    }
    record_manager.start(options)
    this.setData({recording:true})
  },

  //停止录音
  stop_record(){
    record_manager.stop()
    this.setData({recording:false})
    this.bind_stop()
  },
  //这里是监听record_manager什么时候停止
  //所以需要在onLoad的时候就初始化监听函数
  bind_stop(){
    var that=this;
    record_manager.onStop(res=>{
      //res.tempFilePath为录音文件的本地临时地址
      var tf=res.tempFilePath;
      //使用getFileSystemManager这个文件读取器，来读取本地文件为buffer数据流
      //初始化文件读取器
      const fs=wx.getFileSystemManager()
      fs.readFile({//读取文件
        filePath:tf,//文件地址
        success(res){//读取成功
          const buffer=res.data
          that.audio_rec(buffer)//获取到buffer数据流后，执行语音识别
        }
      })
    })
  },

  //开始语音识别
  audio_rec(data){//调用语音识别云函数
    var that=this;
    wx.showLoading({
      title: '语音识别中',
    })
    wx.cloud.callFunction({
      name:'audio_rec',
      data:{data}//这个data就是buffer数据流，把数据传递到了云函数里
    }).then(res=>{
      console.log('res',res)
      //云函数调用成功，且语音识别调用成功
      if(res.errMsg=='cloud.callFunction:ok' && res.result.err_no==0){
        var result_list=res.result.result
        //返回来的识别结果是数组的形式，所以需要使用join，合并为字符串
        //返回来的数据，会自带句号，这里把句号替换掉了
        var result=(result_list.join('')).replace(/。/g,'')

        this.setData({result:result})
        console.log(result)
        wx.hideLoading()
        wx.navigateTo({
          url: "../search/search?result=" + JSON.stringify(result)
        })
      }
      //反之
      else{
        wx.showToast({
          title: '识别失败',
          icon:'none'
        })
      }
    }).catch(err=>{
      console.log("err",err)
      wx.showToast({
        title: '识别失败',
        icon:'none'
      })
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