#NetRequest(基于Refrofit2+RxJava2+OkHttp3网络框架的整理)

-------------------

**简介**，在这个网络框架中使用到的三个类库：

- **Retrofit**：Retrofit是Square 公司开发的一款正对Android 网络请求的框架。底层基于OkHttp 实现，OkHttp 已经得到了google 官方的认可。[Retrofit官网](http://square.github.io/retrofit/)

- **OkHttp**：OkHttp也是由Square 公司贡献的一款高效的Http请求库。

- **RxJava2**：RxJava2是响应式编程（Reactive Extensions）在JVM平台上的实现，即用java语言实现的一套基于观察者模式的异步编程接口。

**封装成果**，封装后具有如下功能：

1. Retrofit+Rxjava+okhttp基本使用方法
2. 统一处理请求数据格式
3. 统一的ProgressDialog和回调Subscriber处理
4. 线程控制，网络请求再工作线程，最后处理在UI线程。
5. 缓存处理，默认缓存响应。在Offline时使用缓存。
6. 管理生命周期，根据Activity生命周期管理请求。
7. 基本的异常处理，
8. 添加了对Cookie的支持功能。
9. 多服务支持
10. 统一支持json解析数据

**基本使用**：一个最基本的请求

``` 

        CuriserWrapper.getService(this) 
                .login(params)
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                });
```

返回的数据格式：

{
"code": 0, 
"data": {
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}, 
 "message": "ok"
}

这里的code为0时，是请求正常响应成功。所以在EnumResCode类中，
public static final String REQUEST_SUCCESS = "0";如果不一致可自行更改。

其中TokenBean对应的是，data对应的JsonObject：

    public class TokenBean implements Serializable{
    @Expose
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "token='" + token + '\'' +
                '}';
    }
    }


**具体配置**：

- NetQuest目前作为一个Lib Module，需要添加依赖
- 配置权限：


        <uses-permission android:name="android.permission.INTERNET"/>
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>

- 配置BaseUrl：


        public class CuriserWrapper {
        public static CuriserService curiserService;

        public static CuriserService getService(Context context){
            if(curiserService == null){
        //Base_url-对应协议+域名;第三个参数Map<String,Object> headers-对应共同的请求头
                curiserService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL2, null).create(CuriserService.class);
            }
            return curiserService;
        }
        }



- 针对请求接口的配置
```
    @POST("v1/account/login")
    Observable<TokenBean> login(@Body Map<String, Object> params);
```

**拓展使用**：

- **添加统一的过度UI：**

需要添加网络请求时加载弹窗只需添加代码：.compose(new LoadingTransformer(this))

\#如果上面的返回值是Flowable则代码为： .compose(new LoadingFlowableTransformer(this))\#


        CuriserWrapper.getService(this) 
                .login(params)
                .compose(new LoadingTransformer(this))
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                });


- **通用的错误处理：**

针对交互中常见的异常，提供一个基本的处理类：BaseErrorConsumer(context),(针对不同的返回码需要与服务端协商一致)

        CuriserWrapper.getService(this)
                .login(params)
                .compose(new LoadingTransformer(this))
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                },new BaseErrorConsumer(context));

- **生命周期管理：**

继承响应的BaseActivity或BaseFragment,在请求时在外套一层addDisposable(),默认在onDestroy或onDestroyView中取消请求，防止内存泄漏：

        addDisposable(CuriserWrapper.getService(this)
                .login(params)
                .compose(new LoadingTransformer(this))
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                },new BaseErrorConsumer(context)));


- **如果实在需要与多个服务端交互：**

可在再配置一个:ApiWrapper+ApiService,与前面CuriserWrapper + CuriserService互不影响。

    public class ApiWrapper {

    public static ApiService apiService;

    public static ApiService getService(Context context){
        if(apiService == null){
    //这时BaseUrl换成另一个
            apiService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL, null).create(ApiService.class);
        }
        return apiService;
    }
    }

并且所有与此服务端交互的接口，都需要在对应的ApiService中配置。


- **异常数据格式的处理：**

正对不符合数据格式：
{
"code": 0, 
"data": {
...
}, 
 "message": "ok"
}
数据格式，直接在Service中配置返回类型Observabe<String\> ，再调用.map转换：

        addDisposable(CuriserWrapper.getService(this)
                .login3(params)
                .compose(new LoadingTransformer(this))
                .map(new Function<String, TotalBean>() {
                    @Override
                    public TotalBean apply(String json) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(json, TotalBean.class);
                    }
                })
                .subscribe(new Consumer<TotalBean>() {
                    @Override
                    public void accept(TotalBean totalBean) throws Exception {
                        Log.i("MNetRequest", "login:" + totalBean.toString());
                    }
                },new BaseErrorConsumer(context)));
