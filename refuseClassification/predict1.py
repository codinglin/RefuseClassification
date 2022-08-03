'''
predict.py有几个注意点
1、无法进行批量预测，如果想要批量预测，可以利用os.listdir()遍历文件夹，利用Image.open打开图片文件进行预测。
2、如果想要保存，利用r_image.save("img.jpg")即可保存。
3、如果想要获得框的坐标，可以进入detect_image函数，读取top,left,bottom,right这四个值。
4、如果想要截取下目标，可以利用获取到的top,left,bottom,right这四个值在原图上利用矩阵的方式进行截取。
'''
from PIL import Image

from yolo import YOLO

import socket
import sys
import threading
import json
import numpy as np
from urllib import request
import os
import requests

yolo = YOLO()


def main():
    # 创建服务器套接字
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 获取本地主机名称
    host = socket.gethostname()
    # 设置一个端口
    port = 12345
    # 将套接字与本地主机和端口绑定
    serversocket.bind((host, port))
    # 设置监听最大连接数
    serversocket.listen(5)
    # 获取本地服务器的连接信息
    myaddr = serversocket.getsockname()
    print("服务器地址:%s" % str(myaddr))
    # 循环等待接受客户端信息
    while True:
        # 获取一个客户端连接
        clientsocket, addr = serversocket.accept()
        print("连接地址:%s" % str(addr))
        try:
            t = ServerThreading(clientsocket)  # 为每一个请求开启一个处理线程
            t.start()
            pass
        except Exception as identifier:
            print(identifier)
            pass
        pass
    # serversocket.close()
    pass


class ServerThreading(threading.Thread):
    # words = text2vec.load_lexicon()
    def __init__(self, clientsocket, recvsize=1024 * 1024, encoding="utf-8"):
        threading.Thread.__init__(self)
        self._socket = clientsocket
        self._recvsize = recvsize
        self._encoding = encoding
        pass

    def run(self):
        print("开启线程.....")
        try:
            # 接受数据
            msg = ''
            while True:
                # 读取recvsize个字节
                rec = self._socket.recv(self._recvsize)
                # 解码
                msg += rec.decode(self._encoding)
                # 文本接受是否完毕，因为python socket不能自己判断接收数据是否完毕，
                # 所以需要自定义协议标志数据接受完毕
                if msg.strip().endswith('over'):
                    msg = msg[:-4]
                    break
            # 解析json格式的数据
            re = json.loads(msg)
            # 调用神经网络模型处理请求
            # res = nnservice.hand(re['content'])
            print(re['content'])

            headers = {
                'authority': 'cl.bc53.xyz',
                'upgrade-insecure-requests': '1',
                'user-agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36',
                'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
                'sec-fetch-site': 'none',
                'sec-fetch-mode': 'navigate',
                'accept-language': 'zh-CN,zh;q=0.9',
                'cookie': '__cfduid=d9b8dda581516351a1d9d388362ac222c1603542964',
            }
            a = re['content'].rfind('/')
            print(re['content'][a+1:])
            r = requests.get(re['content'], stream=True, headers=headers)
            with open('D:/PycharmProjects/refuseClassification/img/'+str(re['content'][a+1:]), 'wb') as fd:
                for chunk in r.iter_content():
                    fd.write(chunk)
            try:
                image = Image.open("D:/PycharmProjects/refuseClassification/img/"+re['content'][a+1:])
            except:
                print('Open Error!')
            else:
                r_image, label = yolo.detect_image(image)
            print(label)
            sendmsg = json.dumps(label)
            # 发送数据
            self._socket.send(("%s" % label).encode(self._encoding))
            pass
        except Exception as identifier:
            self._socket.send("500".encode(self._encoding))
            print(identifier)
            pass
        finally:
            self._socket.close()
        print("任务结束.....")

        pass

    def __del__(self):

        pass


if __name__ == "__main__":
    main()


# def predict(pic_dir):
#     try:
#         image = Image.open(pic_dir)
#     except:
#         print('Open Error!')
#     else:
#         r_image = yolo.detect_image(image)
#         return r_image
#
#
# prediction = predict(
#     sys.argv[1])  # sys.argv[0]表示当前运行的.py文件的名字，sys.arg[1]才是从外面传进来的参数，如果有需要，可以增加sys.arg[2]，sys.arg[3].......，以此类推
# print(prediction)
