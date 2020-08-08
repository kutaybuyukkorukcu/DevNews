package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.repository.ArticleRepository;
import com.scalx.devnews.utils.CacheLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    LikeService likeService;

    @Autowired
    ArticleService articleService;

    @PostConstruct
    private void postConstruct() {
        CacheLists.generateLists();
    }

    public void recommendationIntoRecommendationList(JsonObject jsonObject, List<Recommendation> recommendationList) {

        JsonArray jsonArray =  jsonObject.getAsJsonArray("list");
        Iterator<JsonElement> iter = jsonArray.iterator();

        while(iter.hasNext()) {
            Recommendation recommendation = new Recommendation();
            JsonArray arr = (JsonArray) iter.next();
            recommendation.setArticleId(arr.get(0).getAsInt() + 1); // Because recom.py subtracts 1 from articleID
            recommendation.setSimilarityScore(arr.get(1).getAsDouble());
            recommendationList.add(recommendation);
        }
    }

    public void getRecommendations() {

        List<Like> likes = likeService.getNewLikes();

        if (likes.isEmpty()) {
//            throw new ResourceNotFoundException();
        }

        // TODO : exception handling if likes empty, or something else
        Iterator<Like> iter = likes.iterator();

        while (iter.hasNext()) {
            Like like = iter.next();

            JsonObject jsonObject = getRecommendation(like.getTitle());

            // TODO : handle empty jsonObject -> If the whole jsonObject is null then throw exception otherwise catch
            if (jsonObject.isJsonNull()) {
                throw new ResourceNotFoundException();
            }

            if (like.getMainTopic().equals(MainTopics.DEVELOPMENT.getMainTopic())) {
                recommendationIntoRecommendationList(jsonObject, CacheLists.development);
            } else if (like.getMainTopic().equals(MainTopics.ARCHITECTURE.getMainTopic())) {
                recommendationIntoRecommendationList(jsonObject, CacheLists.architecture);
            } else if (like.getMainTopic().equals(MainTopics.AI.getMainTopic())) {
                recommendationIntoRecommendationList(jsonObject, CacheLists.ai);
            } else if (like.getMainTopic().equals(MainTopics.CULTURE.getMainTopic())) {
                recommendationIntoRecommendationList(jsonObject, CacheLists.culture);
            } else if (like.getMainTopic().equals(MainTopics.DEVOPS.getMainTopic())) {
                recommendationIntoRecommendationList(jsonObject, CacheLists.devops);
            } else {
                recommendationIntoRecommendationList(jsonObject, new ArrayList<>());
            }

            // TODO: i shouldnt check for else

        }
    }
}
