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
}
