package com.isaac.serviceprovider1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.serviceprovider1.domain.ElasticUser;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user1");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void checkIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("user1");
        boolean exist = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user1");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
    }

    @Test
    void saveDoc() throws IOException {
        ElasticUser elasticUser = new ElasticUser();
        elasticUser.setEmail("a@a");
        elasticUser.setAddress("a");
        elasticUser.setFirstName("A");
        elasticUser.setHeight(1.82);
        elasticUser.setPhone("1111111111");
        elasticUser.setLastName("A");

        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("10");
        indexRequest.timeout("1s");
        indexRequest.source(objectMapper.writeValueAsString(elasticUser), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    @Test
    void checkDoc() throws IOException {
        GetRequest getRequest = new GetRequest("user", "10");
        boolean exist = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exist);
    }
    @Test
    void getDoc() throws IOException {
        GetRequest getRequest = new GetRequest("user", "10");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getResponse);
    }

    @Test
    void updateDoc() throws IOException {
        ElasticUser elasticUser = new ElasticUser();
        elasticUser.setEmail("a@a");
        elasticUser.setAddress("a");
        elasticUser.setFirstName("A");
        elasticUser.setHeight(1.85);
        elasticUser.setPhone("1111111111");
        elasticUser.setLastName("A");

        UpdateRequest updateRequest = new UpdateRequest("user", "10");
        updateRequest.timeout("1s");
        updateRequest.doc(objectMapper.writeValueAsString(elasticUser), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse);
        System.out.println(updateResponse.status());
    }

    @Test
    void deleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("user", "10");
        deleteRequest.timeout("1s");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
        System.out.println(deleteResponse.status());
    }

    @Test
    void saveBulkDocs() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        for (int i = 0; i < 5; i ++) {
            ElasticUser elasticUser = new ElasticUser();
            elasticUser.setEmail(i + "@isaac");
            elasticUser.setAddress("a");
            elasticUser.setFirstName("A");
            elasticUser.setHeight(1.80 + 0.01 * i);
            elasticUser.setPhone("1111111111");
            elasticUser.setLastName("A");
            bulkRequest.add(new IndexRequest("user")
                    .id("isaac" + i)
                    .source(objectMapper.writeValueAsString(elasticUser), XContentType.JSON)
            );
//            bulkRequest.add(new DeleteRequest("user", "10"));
//            bulkRequest.add(new UpdateRequest("user", "10")
//                    .doc(objectMapper.writeValueAsString(elasticUser), XContentType.JSON)
//            );

        }
        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkItemResponses.hasFailures());

    }
    @Test
    void Search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // get exact matches
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("email", "a@a");
        // fuzzy search
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("email", "0");
        // get all matches
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        searchSourceBuilder.query(fuzzyQueryBuilder);
        // set request duration
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // pagination
        searchSourceBuilder.from(0); // from witch hit
        searchSourceBuilder.size(5); // page size

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(objectMapper.writeValueAsString(searchResponse.getHits()));
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
