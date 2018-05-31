package ua.in.zeusapps.mediar.services;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ua.in.zeusapps.mediar.models.Card;
import ua.in.zeusapps.mediar.models.CardAnimation;
import ua.in.zeusapps.mediar.models.Event;
import ua.in.zeusapps.mediar.models.EventRequest;
import ua.in.zeusapps.mediar.models.Login;
import ua.in.zeusapps.mediar.models.SnapRequest;
import ua.in.zeusapps.mediar.models.Token;

public interface SnapSafariService {
    String AUTH_HEADER = "Authorization";


    @POST("authorization/registration/")
    Flowable<Token>register(@Body Login credentials);

    @POST("authorization/login/")
    Flowable<Token> getToken(@Body Login login);

    @GET("cards/cards_list/")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/cards_list/")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token);

    @GET("cards/my_cards/")
    Flowable<List<Card>> getMyCards(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/my_cards/")
    Flowable<List<Card>> getMyCards(@Header(AUTH_HEADER) String token);

    @POST("cards/catch_card/")
    Flowable<Card> snapCard(@Header(AUTH_HEADER)String token, @Body SnapRequest request);

    @POST("events/events_with_filter/")
    Flowable<List<Event>> getEvents(@Header(AUTH_HEADER)String header, @Body EventRequest request);

    @GET
    Flowable<CardAnimation> getAnimation(@Header(AUTH_HEADER) String token, @Url String url);
}
