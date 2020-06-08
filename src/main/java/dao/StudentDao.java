package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoUtil;

import java.util.ArrayList;

public class StudentDao {
    public void save(String name,String sex,double age,String address){
        MongoDatabase database = MongoUtil.getDatabase();
        MongoCollection<Document> collection = database.getCollection("student2");
        ArrayList<Document> documents = new ArrayList<>();
        //collection.insertOne(docment);
        for(int i=0;i<20000;i++){
            Document docment=new Document();
            docment.put("name", name + i);
            docment.put("sex", sex);
            docment.put("age", age);
            docment.put("address", address + i);
            documents.add(docment);
        }
        //批量插入
        collection.insertMany(documents);
    }
}
