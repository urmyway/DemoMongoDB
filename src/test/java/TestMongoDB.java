import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.StudentDao;
import org.bson.Document;
import org.junit.Test;
import util.MongoUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TestMongoDB {

    @Test
    public void add(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        Map<String, Object> map=new HashMap();
        map.put("name", "诸葛亮");
        map.put("sex", "男");
        map.put("age", 35.0);
        map.put("address", "南阳");
        Document doc=new Document(map);
        col.insertOne(doc);//插入一条记录
        //collection.insertMany(documents);//一次性插入多条文档
    }

    @Test
    public void delete(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        BasicDBObject bson=new BasicDBObject("name", "诸葛亮");
        col.deleteOne(bson);//删除记录（符合条件的第一条记录）
        //collection.deleteMany(bson);//删除符合条件的全部记录
    }

    @Test
    public void update(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        //修改的条件
        BasicDBObject bson= new BasicDBObject("name", "诸葛亮");
        //修改后的值
        BasicDBObject bson2 = new BasicDBObject("$set",  new BasicDBObject("address", "新野"));
        //参数1：修改条件  参数2：修改后的值
        col.updateOne(bson, bson2);
        //collection.updateMany(filter, update);//修改符合条件的所有记录
    }

    //查询所有
    @Test
    public void findAll(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        //构建查询条件
        BasicDBObject bson=new BasicDBObject("name", "诸葛亮");
        //得到查询结果
        FindIterable<Document> find = col.find(bson);
        //遍历查询结果
        for(Document doc:find ){
            System.out.println("name:"+ doc.getString("name") );
            System.out.println("sex:"+doc.getString("sex"));
            System.out.println("age:"+doc.getDouble("age"));
            System.out.println("address:"+doc.getString("address"));
        }
    }

    // 模糊查询
    @Test
    public void findLike(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        //模糊查询
        Pattern queryPattern= Pattern.compile("^.*新.*$");
        //构建查询条件
        BasicDBObject bson=new BasicDBObject("address",queryPattern);

        //得到查询结果
        FindIterable<Document> find = col.find(bson);
        //遍历查询结果
        for(Document doc:find ){
            System.out.println("name:"+ doc.getString("name") );
            System.out.println("sex:"+doc.getString("sex"));
            System.out.println("age:"+doc.getDouble("age"));
            System.out.println("address:"+doc.getString("address"));
        }
    }

    // 条件查询：大于小于
    @Test
    public void findLt(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");
        //构建查询条件 查询age>20
        BasicDBObject bson=new BasicDBObject("age", new BasicDBObject("$gt", 20));

        //得到查询结果
        FindIterable<Document> find = col.find(bson);
        //遍历查询结果
        for(Document doc:find ){
            System.out.println("name:"+ doc.getString("name") );
            System.out.println("sex:"+doc.getString("sex"));
            System.out.println("age:"+doc.getDouble("age"));
            System.out.println("address:"+doc.getString("address"));
        }
    }

    // 条件查询：并且
    @Test
    public void findAnd(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");

        BasicDBObject bson1=new BasicDBObject("age", new BasicDBObject("$gte",30));
        BasicDBObject bson2=new BasicDBObject("age", new BasicDBObject("$lt",40));
        //构建查询条件 >=30 & <40
        BasicDBObject bson=new BasicDBObject("$and", Arrays.asList( bson1, bson2 )  );

        //得到查询结果
        FindIterable<Document> find = col.find(bson);
        //遍历查询结果
        for(Document doc:find ){
            System.out.println("name:"+ doc.getString("name") );
            System.out.println("sex:"+doc.getString("sex"));
            System.out.println("age:"+doc.getDouble("age"));
            System.out.println("address:"+doc.getString("address"));
        }
    }

    // 条件查询：或
    @Test
    public void findOr(){
        //获取连接
        MongoDatabase database = MongoUtil.getClient();
        //得到集合封装对象
        MongoCollection<Document> col = database.getCollection("col");

        BasicDBObject bson1=new BasicDBObject("age", new BasicDBObject("$gte",20));
        BasicDBObject bson2=new BasicDBObject("sex", "女");
        //构建查询条件 age>=20 & sex = "女"
        BasicDBObject bson=new BasicDBObject("$or", Arrays.asList( bson1, bson2 )  );

        //得到查询结果
        FindIterable<Document> find = col.find(bson);
        //遍历查询结果
        for(Document doc:find ){
            System.out.println("name:"+ doc.getString("name") );
            System.out.println("sex:"+doc.getString("sex"));
            System.out.println("age:"+doc.getDouble("age"));
            System.out.println("address:"+doc.getString("address"));
        }
    }

    // 线程池插入数据测试
    @Test
    public void testPool(){
        long startTime = new Date().getTime();//开始时间

        StudentDao studentDao=new StudentDao();
        studentDao.save("测试", "男", 25.0, "测试地址");
        long endTime = new Date().getTime();//完成时间
        System.out.println("完成时间："+(endTime-startTime)+"毫秒");
    }
}
