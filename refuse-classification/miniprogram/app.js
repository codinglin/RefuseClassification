//app.js
let Api = require("./http/api.js")
let request = require("./http/request.js")
let config = require("./http/env.js")
let router = require("./utils/router.js")
let env="Dev"
App.version="1.0";
App.config=config[env]
App({
  config:config[env],
  Api,
  router,
  get:request.fetch,
  post:(url,data,option) =>{
    option.method = "post";
    return request.fetch(url,data,option);
  },
  put:(url,data,option) =>{
    option.method = "PUT";
    return request.fetch(url,data,option);
  },
  delete:(url,data,option) =>{
    option.method = "DELETE";
    return request.fetch(url,data,option);
  },
  globalSortData:{
    kindList: [
      {
        imgPath: "/images/kehuishouwu.png",
        color: "#215287",
        text: "可回收物",
        description: "可回收物就是再生资源，指生活垃圾中未经污染、适宜回收循环利用的废物，主要包括废弃电器电子产品、废纸张、废塑料、废玻璃、废金属等五类，是现阶段生活垃圾分类的主要工作和影响垃圾减量的重要因素。",
        dispose: [
          "尽量保持清洁干燥，避免污染",
          "立体包装物请清空内容物，清洁后压扁投放",
          "废纸应保持平整",
          "废玻璃制品及带尖锐角物品，应包裹后投放"
        ]
      },
      {
        imgPath: "/images/youhailaji.png",
        color: "#EA3E30",
        text: "有害垃圾",
        description: "有害垃圾，指生活垃圾中对人体健康或自然环境造成直接或潜在危害的物质，必须单独收集、运输、存贮，由环保部门认可的专业机构进行特殊安全处理。",
        dispose: [
          "杀虫剂等压力罐装容器，应排空内容物后投放",
          "投放时请注意轻放",
          "易破损的请连带包装或包裹后轻放",
          "如易挥发，请密封后投放",
        ]
      },
      {
        imgPath: "/images/shilaji.png",
        color: "#62423A",
        text: "湿垃圾",
        description: "湿垃圾又称为厨余垃圾，即易腐垃圾，指食材废料、剩菜剩饭、过期食品、瓜皮果核、花卉绿植、中药药渣等易腐的生物质生活废弃物。",
        dispose: [
          "纯流质的食物垃圾，如牛奶等，应直接倒入下水口",
          "有包装的湿垃圾应将包装物去除后分类投放，包装物请投放对应的垃圾容器"
        ]
      },
      {
        imgPath: "/images/ganlaji.png",
        color: "#2E2C2A",
        text: "干垃圾",
        description: "干垃圾（其它垃圾）指除可回收物、有害垃圾、餐厨垃圾外的其他生活垃圾，即现环卫体系主要收集和处理的垃圾。",
        dispose: [
          "尽量沥干水分",
          "难以辨识类别的垃圾投入干垃圾容器内"
        ]
      },
    ],
  },
  globalData: {},
  onLaunch() {
    if (!wx.cloud) {
      console.error('请使用 2.2.3 或以上的基础库以使用云能力')
    } else {
      wx.cloud.init({
        // env 参数说明：
        //   env 参数决定接下来小程序发起的云开发调用（wx.cloud.xxx）会默认请求到哪个云环境的资源
        //   此处请填入环境 ID, 环境 ID 可打开云控制台查看
        //   如不填则使用默认环境（第一个创建的环境）
        // env: 'my-env-id',
        traceUser: true,
      })
    }

    this.globalData = {}
  },
  // getUserInfo:function(cb){
  //   var that = this
  //   if(this.globalData.userInfo){
  //     typeof cb == "function" && cb(this.globalData.userInfo)
  //   }else{
  //     //调用登录接口
  //     wx.login({
  //       success: function (res) {
  //         console.log(res.code);
  //         console.log("11111");
  //         that.globalData.code=res.code;
  //         wx.getUserInfo({
  //           success: function (res) {
  //             that.globalData.userInfo = res.userInfo;
  //             typeof cb == "function" && cb(that.globalData.userInfo);
  //           }
  //         })
  //       }
  //     })
  //   }
  // }

})
