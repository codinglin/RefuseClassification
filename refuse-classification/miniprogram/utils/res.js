var app = getApp();
// let questions = [{
//   "answer": "C", "stem": "垃圾可以分为几类？",
//   "options": [{ "content": "两类", "option": "A" },
//   { "content": "三类", "option": "B" },
//   { "content": "四类", "option": "C" },
//   { "content": "五类", "option": "B" }], "img": null, "no": 1
// },
// {
//   "answer": "A", "stem": "下列哪个不属于湿垃圾?",
//   "options": [{ "content": "整包过期零食", "option": "A" },
//   { "content": "剩菜剩饭", "option": "B" },
//   { "content": "鱼刺", "option": "C" },
//   { "content": "鸡骨头", "option": "D" }], "img": null, "no": 2
// },
// {
//   "answer": "B", "stem": "下列哪个不属于可回收垃圾?",
//   "options": [{ "content": "废铁丝、废铁", "option": "A" },
//   { "content": "用过的餐巾纸、茶叶渣", "option": "B" },
//   { "content": "玻璃瓶、废塑料", "option": "C" },
//   { "content": "旧衣服、废报纸", "option": "D" }], "img": null, "no": 3
// },
// {
//   "answer": "A", "stem": "下列哪个不是有害垃圾",
//   "options": [{ "content": "碎玻璃片", "option": "A" },
//   { "content": "过期药品", "option": "B" },
//   { "content": "废水银温度计", "option": "C" },
//   { "content": "油漆桶", "option": "D" }], "img": null, "no": 4
// },
// {
//   "answer": "C", "stem": "有害垃圾桶是什么颜色?",
//   "options": [{ "content": "蓝色", "option": "A" },
//   { "content": "棕色", "option": "B" },
//   { "content": "红色", "option": "C" },
//   { "content": "黑色", "option": "D" }], "img": null, "no": 5
// },
// {
//   "answer": "B", "stem": "湿垃圾桶是什么颜色？",
//   "options": [{ "content": "蓝色", "option": "A" },
//   { "content": "棕色", "option": "B" },
//   { "content": "红色", "option": "C" },
//   { "content": "黑色", "option": "D" }], "img": null, "no": 6
// },
// {
//   "answer": "A", "stem": "哪种垃圾可以作为肥料滋养土壤、庄稼?",
//   "options": [{ "content": "湿垃圾", "option": "A" },
//   { "content": "可回收垃圾", "option": "B" },
//   { "content": "有害垃圾", "option": "C" },
//   { "content": "干垃圾", "option": "D" }], "img": null, "no": 7
// },
// {
//   "answer": "D", "stem": "大块的骨头应归类到哪种垃圾？",
//   "options": [{ "content": "湿垃圾", "option": "A" },
//   { "content": "可回收垃圾", "option": "B" },
//   { "content": "有害垃圾", "option": "C" },
//   { "content": "干垃圾", "option": "D" }], "img": null, "no": 8
// },
// {
//   "answer": "B", "stem": "哪种垃圾可以再造成新瓶子、再生纸和塑料玩具?",
//   "options": [{ "content": "湿垃圾", "option": "A" },
//   { "content": "可回收垃圾", "option": "B" },
//   { "content": "有害垃圾", "option": "C" },
//   { "content": "干垃圾", "option": "D" }], "img": null, "no": 9
// },
// {
//   "answer": "B", "stem": "过期药品属于____，需要特殊安全处理",
//   "options": [{ "content": "干垃圾", "option": "A" },
//   { "content": "有害垃圾", "option": "B" },
//   { "content": "可回收垃圾", "option": "C" },
//   { "content": "湿垃圾", "option": "D" }], "img": null, "no": 10
// },
// {
//   "answer": "A", "stem": "下面应放入蓝色垃圾桶的是?",
//   "options": [{ "content": "玻璃瓶", "option": "A" },
//   { "content": "鱼骨", "option": "B" },
//   { "content": "电池", "option": "C" },
//   { "content": "尿布", "option": "D" }], "img": null, "no": 11
// },
// {
//   "answer": "C", "stem": "根据“___”的原则，产生生活垃圾的单位和个人是分类投放的第一责任人",
//   "options": [{ "content": "谁产生、水丢弃", "option": "A" },
//   { "content": "谁丢弃、谁分类", "option": "B" },
//   { "content": "谁产生、谁负责", "option": "C" },
//   { "content": "谁负责、谁分类", "option": "D" }], "img": null, "no": 12
// }]
//var questions=[];
const questions = {};
questions.getQuestion = function () {
  return new Promise((resolve, reject) => {
    wx.request({
      url: require("../http/env.js")['Dev'].baseApi+'/sorting-question/pageQuestion', //仅为示例，并非真实的接口地址
      method: 'GET',
      data: {},
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res) {
        //console.log(res);
        resolve(res.data.data);
      },
      fail(err){
        reject(err);
      }
    })
  })

  // app.get("/sorting-question/pageQuestion", {
  // }).then(res => {
  //   console.log(res.questions);
  //   //questions=res.questions;
  //   appPage.setData({ question: 1 });
  // }).catch(err => {
  //   console.log(err);
  // })
}
module.exports = questions