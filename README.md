# RxRetrofit
rxjava+retrofit封装网络请求框架
主要功能：
1.Retrofit+Rxjava+Okhttp基本网络请求
2.统一处理请求数据格式
3.统一的ProgressDialog和回调Subscriber处理
4.http请求的取消和预处理
5.统一HTTP请求头的处理
6.异常的统一处理
7.RxLifecycle管理生命周期，防止内存泄露
8.文件上传下载(支持多文件，断点续传)
9.Cache数据持久化和数据库（greenDao）两种缓存机制
# 使用方法
>1.新建项目，引入rxretrofitlib
>2.在APP的Application中初始化RxRetrofit
  //初始化RxRetrofitLib
  RxRetrofitApp.init(this, Constants.HTTP_RESUCCESS);
  //设置数据库名称
  RxRetrofitApp.setCacheDBName(Constants.CACHE_DB_NAME);
  //设置网络请求基础Url
  RxRetrofitApp.setBaseUrl(Constants.BASE_URL);
