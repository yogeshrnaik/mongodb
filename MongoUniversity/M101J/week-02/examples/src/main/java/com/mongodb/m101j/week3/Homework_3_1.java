package com.mongodb.m101j.week3;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Homework_3_1 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");
        ArrayList<Document> students = collection.find().into(new ArrayList<Document>());
        for (Document s : students) {
            Integer currStudentId = s.getInteger("_id");
            List<Document> scores = (List<Document>)s.get("scores");

            List<Document> updated = new ArrayList<Document>();
            Document homeWork = null;
            for (Document score : scores) {
                if (score.get("type").equals("homework")) {
                    if (homeWork == null) {
                        homeWork = score;
                    } else {
                        // compare values and add highest to updated list
                        updated.add(homeWork.getDouble("score") > score.getDouble("score") ? homeWork : score);
                    }
                } else {
                    updated.add(score);
                }
            }

            collection.updateOne(Filters.eq("_id", currStudentId), new Document("$set", new Document("scores", updated)));
            System.out.println(currStudentId + "\t" + updated);
        }
    }
}
