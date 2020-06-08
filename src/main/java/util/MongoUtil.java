package util;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

//mongodb 连接数据库工具类
public class MongoUtil {

    private static MongoClient mongoClient=null;

    //不通过认证获取连接数据库对象
    public static MongoDatabase getClient(){
        //获取连接 不传参默认为localhost，27017
        mongoClient = new MongoClient();
        MongoDatabase runoob = mongoClient.getDatabase("runoob");
        return runoob;
    }

    //需要密码认证方式连接
    public static MongoDatabase getClient2(){
        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
        credentials.add(mongoCredential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        //返回连接数据库对象
        return mongoDatabase;
    }

    //对mongoClient初始化
    private static void init(){
        //连接池选项
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();//选项构建者
        builder.connectTimeout(5000);//设置连接超时时间
        builder.socketTimeout(5000);//读取数据的超时时间
        builder.connectionsPerHost(30);//每个地址最大请求数
        builder.writeConcern(WriteConcern.NORMAL);//写入策略，仅抛出网络异常
        MongoClientOptions options = builder.build();
        mongoClient = new MongoClient();
    }

    public static MongoDatabase getDatabase(){
        if(mongoClient==null){
            init();
        }
        return mongoClient.getDatabase("runoob");
    }
}
