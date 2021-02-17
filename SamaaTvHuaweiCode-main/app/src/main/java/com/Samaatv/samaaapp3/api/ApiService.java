package com.Samaatv.samaaapp3.api;

import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectBlogsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectNewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectNewsContactList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author S.Shahzaib Ahmed.
 */
public interface ApiService
{

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    // testing url for Home

    @GET
    Call<ObjectNewsContactList> getHomeNewsObj(@Url String url);

    //URLs for English News
    @GET("jappmain/")
    Call<ObjectNewsContactList> getMyJSON();

//    @GET("videos/jfeedmostwatchedprograms/")
    @GET("programs/jfeedmostwatchedprograms/")
    Call<ContactList> getWebAPI();

    @GET("jfeednational/")
    Call<NewsCategoryList> getPakistanNews();

    @GET("jappfeedsports/")
    Call<NewsCategoryList> getSportsNews();

    @GET("jappfeededitorschoice/")
    Call<NewsCategoryList> getEditorsNews();

    @GET("jappfeedentertainment/")
    Call<NewsCategoryList> getEnterNews();

    @GET("jappfeedeconomy/")
    Call<NewsCategoryList> getEconomyNews();

    @GET("jappfeedworld/")
    Call<NewsCategoryList> getGlobalNews();

//    @GET("videos/jfeedprograms/")
    @GET("programs/jfeedprograms/")
    Call<NewsCategoryList> getTVShows();

    @GET("jappfeedblogs/")
    Call<NewsCategoryList> getBlogNews();

    @GET("blog-data/")
    Call<ObjectBlogsCategoryList> getBlogDataNews();

    @GET("jappfeedhealth/")
    Call<NewsCategoryList> getHealthBlogs();

    @GET("jappfeedlifestyle/")
    Call<NewsCategoryList> getLifeStyleBlogs();

    @GET("jappfeedsocialbuzz/")
    Call<NewsCategoryList> getSocialBuzzBlogs();

    @GET("jappfeedscitech/")
    Call<NewsCategoryList> getSciTechBlogs();

    @GET("jappfeedweird/")
    Call<NewsCategoryList> getWeirdBlogs();


    //URLs for Urdu News
    @GET("urdu/jfeedurdumain/")
    Call<ObjectNewsContactList> getTrendUrdu();

    @GET("urdu/jfeedurdumostwatched/")
    Call<ContactList> getMostWatchUrdu();

    @GET("urdu/jfeedurdunational/")
    Call<NewsCategoryList> getPakistanNewsUrdu();

    @GET("urdu/jfeedurdusports/")
    Call<NewsCategoryList> getSportsNewsUrdu();

    @GET("urdu/jfeedurdueditorschoice/")
    Call<NewsCategoryList> getEditorsNewsUrdu();

    @GET("urdu/jfeedurduentertainment/")
    Call<NewsCategoryList> getEnterNewsUrdu();

    @GET("urdu/jfeedurdueconomy/")
    Call<NewsCategoryList> getEconomyNewsUrdu();

    @GET("urdu/jfeedurduglobal/")
    Call<NewsCategoryList> getGlobalNewsUrdu();

    @GET("urdu/jfeedurduweird/")
    Call<NewsCategoryList> getWeirdUrdu();

    @GET("urdu/jfeedurdublogs/")
    Call<NewsCategoryList> getBlogNewsUrdu();

    @GET("urdu/jfeedurduhealth/")
    Call<NewsCategoryList> getHealthNewsUrdu();

    @GET("urdu/jfeedurdulifestlye/")
    Call<NewsCategoryList> getLifeNewsUrdu();

    @GET("urdu/jfeedurdusocialbuzz/")
    Call<NewsCategoryList> getSocialNewsUrdu();

    @GET("urdu/jfeedurduscitech/")
    Call<NewsCategoryList> getSciTechNewsUrdu();

    @GET("urdu/blogs-data/")
    Call<ObjectBlogsCategoryList> getBlogDataUrdu();

    @GET("urdu/jfeedurduhealth/")
    Call<NewsCategoryList> getHealthBlogDataUrdu();

    @GET("urdu/jfeedurdulifestlye/")
    Call<NewsCategoryList> getLifeStyleBlogDataUrdu();

    @GET("urdu/jfeedurdusocialbuzz/")
    Call<NewsCategoryList> getSocialBuzzBlogDataUrdu();

    @GET("urdu/jfeedurduscitech/")
    Call<NewsCategoryList> getSciTechBlogDataUrdu();

    @GET("urdu/jfeedurduweird/")
    Call<NewsCategoryList> getWeirdBlogDataUrdu();


    //English URLs for related and also watch news (Embedded with news id)
    @GET
    Call<NewsCategoryList> getSeeURL(@Url String url);

    @GET
    Call<NewsCategoryList> getAlsoURL(@Url String url);

    @GET
    Call<NewsCategoryList> getPopularVideosURL(@Url String url);

    @GET
    Call<NewsCategoryList> getTVShowsEpisodeURL(@Url String url);

    @GET
    Call<NewsCategoryList> getMyNewsEnglish(@Url String url);

    //Get custom blogs for youtube video
    @GET
    Call<NewsCategoryList> getBlogNewsYoutube(@Url String url);

    @GET
    Call<ObjectNewsCategoryList> getMyNewsObj(@Url String url);


    //Urdu URLs for related and also watch news (Embedded with news id)
    @GET
    Call<NewsCategoryList> getSeeURLUrdu(@Url String url);

    @GET
    Call<NewsCategoryList> getAlsoURLUrdu(@Url String url);


}
