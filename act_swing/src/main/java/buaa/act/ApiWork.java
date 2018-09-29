package buaa.act;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
//import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//import org.springframework.stereotype.Controller;

import java.io.*;
import java.util.ArrayList;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

public class ApiWork
{
	public final static int SEARCH_SIZE = 10;
	
	public final static String HOST = "192.168.7.101";//localhost";
	
	public final static int PORT = 9300;//http请求的端口是9200，客户端是9300

	public ArrayList<Double> scores = new ArrayList<Double>();
	
	public TransportClient client;
	
	public ApiWork(){
		client = ESConnection.getConnection();   //连接ES
	}
	
	public void afterWork2(int type) throws IOException{ //type:1 All 2: only different
	}
	
	public String search4(String queryString, String queryType){ //查询最多的那个种类的答案
		System.out.println(queryString+"            "+queryType);
		if (queryType.equals("Doc")){
			queryType = "sentence";
		}
//		TransportClient client = ESConnection.getConnection();   //连接ES
		queryType = queryType.toLowerCase();
		SearchResponse searchResponse = client.prepareSearch("api")//指定索引库
				.setTypes("javaCode")//指定类型
				 .setQuery(QueryBuilders.termQuery("multi", "test"))
				.setQuery(multiMatchQuery(
						queryString,
						queryType))
						 //"code","sentence")) //delete "code", //modified by ohazyi 14:57
//				.setQuery(QueryBuilders.matchQuery("sentence", queryString))//指定查询条件
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//指定查询方式
				.get();

		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		System.out.println("TOTAL ："+totalHits);

		//获取满足条件数据的详细内容
		SearchHit[] hits2 = hits.getHits();
		SearchHit sh;
		String ans_usage = "", ans_sentence = "", ans_doc = "", ans_code = "";//+id+"		"+ queryString+ "				";
		if (hits2.length != 0) {
			ans_usage = ans_usage + hits2[0].getSourceAsMap().get("usage").toString();
			ans_sentence = ans_sentence + hits2[0].getSourceAsMap().get("sentence").toString();
			ans_doc = ans_doc + hits2[0].getSourceAsMap().get("doc").toString();
			ans_code = ans_sentence + hits2[0].getSourceAsMap().get("code").toString();
		}
		
		if (queryType.equals("code"))
			return ans_code;
		if (queryType.equals("usage"))
			return ans_usage;
		if (queryType.equals("sentence"))
			return ans_sentence;
		return ans_doc;
	}
	
	public String[] search2(String queryString, String queryType, int id){ //返回的是匹配得分中最多的那个
//		TransportClient client = ESConnection.getConnection();   //连接ES
		System.out.println("queryType="+queryType.toLowerCase());
		if (queryType.equals("Doc")){
			queryType = "sentence";
		}
		SearchResponse searchResponse = client.prepareSearch("api")//指定索引库
				.setTypes("javaCode")//指定类型
				 .setQuery(QueryBuilders.termQuery("multi", "test"))
				.setQuery(multiMatchQuery(
						queryString,
						queryType.toLowerCase()))
						 //"code","sentence")) //delete "code", //modified by ohazyi 14:57
//				.setQuery(QueryBuilders.matchQuery("sentence", queryString))//指定查询条件
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//指定查询方式
				.get();

		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		System.out.println("总数："+totalHits);

		String[] res = new String[5];
		if (totalHits <= id){
			return res;
		}
		
		//获取满足条件数据的详细内容
		SearchHit[] hits2 = hits.getHits();
		SearchHit sh;
		String ans_usage = "", ans_sentence = "", ans_doc = "", ans_code = "";//+id+"		"+ queryString+ "				";
		if (hits2.length != 0) {
			ans_usage = ans_usage + hits2[id].getSourceAsMap().get("usage").toString();
			ans_sentence = ans_sentence + hits2[id].getSourceAsMap().get("sentence").toString();
			ans_doc = ans_doc + hits2[id].getSourceAsMap().get("doc").toString();
			ans_code = ans_code + hits2[id].getSourceAsMap().get("code").toString();
		}
		
		res[0] = ans_usage;
		res[1] = ans_sentence;
		res[2] = ans_doc;
		res[3] = ans_code;
		return res;
	}
	public int mymin(int x, int y){
		return (x<y)?x:y;
	}
	public ArrayList<String> search3(String queryString, int tot){ //返回查询后的arrayList,返回tot个匹配得分最高的结果
		scores.clear();
		ArrayList<String> ans = new ArrayList<String>();
//		TransportClient client = ESConnection.getConnection();   //连接ES
		SearchResponse searchResponse = client.prepareSearch("api")//指定索引库
				.setTypes("javaCode")//指定类型
				.setQuery(QueryBuilders.termQuery("multi", "test"))
				.setSize(SEARCH_SIZE)
				.setQuery(multiMatchQuery(
						queryString,
						"sentence")) //delete "code", //modified by ohazyi 14:57
//				.setQuery(QueryBuilders.matchQuery("sentence", queryString))//指定查询条件
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//指定查询方式
				.get();

		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();

		//获取满足条件数据的详细内容
		SearchHit[] hits2 = hits.getHits();
		SearchHit sh;
		String ans_usage = "", ans_nl = "";
		if (hits2.length != 0) {
			for (int i = 0; i < mymin(hits2.length,tot); ++i){
				ans_usage = hits2[i].getSourceAsMap().get("usage").toString(); //ans_usage + 
				ans_nl = hits2[i].getSourceAsMap().get("sentence").toString(); //ans_nl + 
				if (ans_usage.length() > 1000)
					continue;
				ans.add(ans_usage);
				scores.add((double) hits2[i].getScore());
			}
		}
		return ans;
	}
}
