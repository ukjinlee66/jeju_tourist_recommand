package kr.pe.playdata.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import kr.pe.playdata.service.HighLevelClientElasticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HighLevelClientElasticServiceImpl implements HighLevelClientElasticService {
	
	private final RestHighLevelClient restHighLevelClient;
	
	public List<String> getTopFiveSearchKeywords(String term){
		SearchRequest searchRequest = new SearchRequest("test");
		SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
		TermsAggregationBuilder terms = AggregationBuilders.terms(term).field(term);
		
		sourceBuilder.size(0);
		sourceBuilder.aggregation(terms);
		
		searchRequest.source(sourceBuilder);
		try {
			Map<String, Long> result = new HashMap<>();
			SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			List<Aggregation> aggregations = response.getAggregations().asList();
			aggregations
				.forEach(aggregation->{
					((Terms) aggregation).getBuckets()
						.forEach(bucket -> result.put(bucket.getKeyAsString(), bucket.getDocCount()));
				});
			
			// Map.Entry 리스트
			List<Entry<String, Long>> entryList = new ArrayList<Entry<String, Long>>(result.entrySet());
			
			// Comparator를 사용하여 정렬
			Collections.sort(entryList, new Comparator<Entry<String, Long>>(){
				
				public int compare(Entry<String, Long> obj1, Entry<String, Long> obj2) {
					
					// 오름차순 정렬
					// return obj1.getValue().compareTo(obj2.getValue());
					// 내림차순 정렬
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});
			List<String> result_list = new ArrayList<String>();
			
			// 상위 5개 검색어 리스트로 저장
			int count = 0;
			for (Entry<String, Long> entry : entryList) {
				count++;
				if (count>5) break;
				System.out.println(entry.getKey()+":"+entry.getValue());
				result_list.add(entry.getKey());
			}
			
			return result_list;
		} catch(IOException e) {
			e.printStackTrace();	
		}
		
		return null;
	}

}
