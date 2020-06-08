import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;
import util.MongoUtil;

import java.util.HashMap;
import java.util.Map;

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
}
