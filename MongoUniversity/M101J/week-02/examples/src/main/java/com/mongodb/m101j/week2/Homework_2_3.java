package com.mongodb.m101j.week2;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Homework_2_3 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");
        ArrayList<Document> filteredSorted = collection.find(Filters.eq("type", "homework"))
            .sort(Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score")))
            .into(new ArrayList<Document>());

        int prevStudentId = -1;
        for (Document grade : filteredSorted) {
            Integer currStudentId = grade.getInteger("student_id");
            if (currStudentId != prevStudentId) {
                prevStudentId = currStudentId;
                // record with lowest score is the 1st one. remove it
                collection.deleteOne(Filters.and(Filters.eq("_id", grade.get("_id"))));
            }
        }

        // System.out.println(filteredSorted.size());
    }
}
