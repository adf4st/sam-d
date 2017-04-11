//package net.alexfabian.swarmview.config;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//
///**
// * Created by alexfabian on 2/5/17.
// */
//
//@Configuration
//public class SpringMongoConfig extends AbstractMongoConfiguration {
//    @Override
//    protected String getDatabaseName() {
//        return "sampleApp";
//    }
//
//    @Override
//    public Mongo mongo() throws Exception {
//        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
//                .connectTimeout(10000)
//                .build();
//
//        return new MongoClient("localhost:27017", mongoClientOptions);
//    }
//}
