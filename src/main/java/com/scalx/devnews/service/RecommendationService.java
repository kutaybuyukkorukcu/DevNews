package com.scalx.devnews.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.exception.GetRecommendationHttpException;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.ArticleRepository;
import com.scalx.devnews.utils.CacheLists;
import com.scalx.devnews.utils.MainTopics;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private LikeService likeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @PostConstruct
    private void postConstruct() {
        CacheLists.generateLists();
    }

    public void recommendationIntoRecommendationList(JsonNode jsonNode, List<Recommendation> recommendationList) {

        Iterator<JsonNode> iter = jsonNode.path("list").iterator();

        while(iter.hasNext()) {
            Recommendation recommendation = new Recommendation();
            JsonNode arr = iter.next();
            // Because recom.py subtracts 1 from articleID
            recommendation.setArticleId(arr.path(0).asInt() + 1);
            recommendation.setSimilarityScore(arr.path(1).asDouble());
            recommendationList.add(recommendation);
        }
    }

    // TODO : test api/recommend under Integration
    // Belli bir formata soktuktan sonra hem elimde bulunan makale verilerini hem de kisinin begendiklerini artik recommendation icin yollayabilirim.
    public JsonNode getRecommendation(String title) {

            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://localhost:5000/api/recommend";

            ResponseEntity<JsonNode> response = restTemplate.getForEntity(resourceUrl, JsonNode.class);


            List<JsonNode> jsonNodeList = response.getBody().findValues("title");
            jsonNodeList
                    .iterator()
                    .next()
                    .equals(title);
            Iterator<JsonNode> iter = jsonNodeList.iterator();

            JsonNode jsonNode = null;

            while (iter.hasNext()) {
                if (iter.next().path("title").equals(title)) {
                    jsonNode = iter.next();
                }
            }

            if (jsonNode.isNull()) {
                // TODO : test it / not sure if it works for json data that only has 1 null val in size() 3 data.
                throw new ResourceNotFoundException();
            }

            return jsonNode;
    }

    public List<Recommendation> getTopRecommendationsFromList(List<Recommendation> recommendationList) {
        //        Comparator<Recommendation> comparator = new Comparator<Recommendation>() {
//            @Override
//            public int compare(Recommendation i1, Recommendation i2) {
//                int a1 = (int) Math.round(i1.getSimilarityScore());
//                int a2 = (int) Math.round(i2.getSimilarityScore());
//                return a2 - a1;
//            }
//        };
        // TODO : should we check if static list recommendations is present or not ? Dont forget to implement tests

        // TODO : test edilecek ofc. Ondan dolayi eski implementationu silmiyorum
        recommendationList.sort(Comparator.comparingDouble(Recommendation::getSimilarityScore));

        // Set kontrolu yapilsin. Ayni articleID'ye sahipler alinmasin.
        return recommendationList.stream()
//                .sorted(comparator)
                .limit(5)
                .collect(Collectors.toList());
    }

    public void recommendationListToArticleLIst(List<Recommendation> recommendationList, List<Article> recommendedArticles) {
        // TODO : should we check if static list recommendations is present or not ? Dont forget to implement tests
        Iterator<Recommendation> iter = recommendationList.iterator();

        while(iter.hasNext()) {
            int articleID = iter.next().getArticleId();

            Article article = articleRepository.findByArticleId(articleID);

            if (article == null) {
                throw new ResourceNotFoundException();
            }

            recommendedArticles.add(article);
        }

    }

    public void getRecommendations() {

        List<Like> likes = likeService.getNewLikes();

        if (likes.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        // TODO : exception handling if likes empty, or something else
        Iterator<Like> iter = likes.iterator();

        while (iter.hasNext()) {
            Like like = iter.next();

            JsonNode jsonNode = getRecommendation(like.getTitle());

            // TODO : handle empty jsonObject -> If the whole jsonObject is null then throw exception otherwise catch
            if (jsonNode.isNull()) {
                throw new ResourceNotFoundException();
            }

            if (like.getMainTopic().equals(MainTopics.DEVELOPMENT.getMainTopic())) {
                recommendationIntoRecommendationList(jsonNode, CacheLists.development);
            } else if (like.getMainTopic().equals(MainTopics.ARCHITECTURE.getMainTopic())) {
                recommendationIntoRecommendationList(jsonNode, CacheLists.architecture);
            } else if (like.getMainTopic().equals(MainTopics.AI.getMainTopic())) {
                recommendationIntoRecommendationList(jsonNode, CacheLists.ai);
            } else if (like.getMainTopic().equals(MainTopics.CULTURE.getMainTopic())) {
                recommendationIntoRecommendationList(jsonNode, CacheLists.culture);
            } else if (like.getMainTopic().equals(MainTopics.DEVOPS.getMainTopic())) {
                recommendationIntoRecommendationList(jsonNode, CacheLists.devops);
            } else {
                recommendationIntoRecommendationList(jsonNode, new ArrayList<>());
            }

            // TODO: i shouldnt check for else because of isNull exception

        }
    }
}
