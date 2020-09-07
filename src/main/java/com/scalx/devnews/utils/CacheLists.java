package com.scalx.devnews.utils;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class CacheLists {

    public static List<Recommendation> development = null;
    public static List<Recommendation> architecture = null;
    public static List<Recommendation> ai = null;
    public static List<Recommendation> culture = null;
    public static List<Recommendation> devops = null;
    public static List<Article> recommendedArticles = null;

    public CacheLists() {
        development = new ArrayList<>();
        architecture = new ArrayList<>();
        ai = new ArrayList<>();
        culture = new ArrayList<>();
        devops = new ArrayList<>();
        recommendedArticles = new ArrayList<>();
    }

//    public static void generateLists() {
//        development = new ArrayList<>();
//        architecture = new ArrayList<>();
//        ai = new ArrayList<>();
//        culture = new ArrayList<>();
//        devops = new ArrayList<>();
//        recommendedArticles = new ArrayList<>();
//    }
}