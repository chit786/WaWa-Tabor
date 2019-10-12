package com.wawa_applications.wawa_tabor.network.retrofit;

import com.google.gson.Gson;
import com.wawa_applications.wawa_tabor.network.retrofit.model.ZTMAPILine;
import com.wawa_applications.wawa_tabor.network.retrofit.model.ZTMAPIResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ZTMAPIRetrofitTest {

    private final MockWebServer server = new MockWebServer();
    private Gson gson;
    private ZTMAPIService service;

    @Before
    public void setUp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(ZTMAPIService.class);
        gson = new Gson();
    }

    @Test
    public void givenCorrectBusNo_whenCallRetrofitMethod_ThenGetCorrectResponse() throws IOException{
        //given
        ZTMAPILine exampleLine =   new ZTMAPILine(52.20676, 21.037996, "2018-10-17 05:52:50:", "131", "11");
        ZTMAPIResult exampleResult = new ZTMAPIResult();
        exampleResult.addLine(exampleLine);
        server.enqueue(new MockResponse().setBody(gson.toJson(exampleResult)));
        //when
        Observable<ZTMAPIResult> busObservable = service.getBuses("131");
        ZTMAPIResult result =  busObservable.blockingFirst();
        //then
        assertEquals( exampleResult,result);
    }

    @Test
    public void givenInCorrectBusNo_whenCallRetrofit_ThenGetEmptyResponse() throws IOException{
        //given
        String errorResponse = "{ \"result\": [] }";
        server.enqueue(new MockResponse().setBody(errorResponse));
        //when
        Observable<ZTMAPIResult> busObservable = service.getBuses("ABC");
        ZTMAPIResult result =  busObservable.blockingFirst();
        //then
        assertEquals(new ArrayList<ZTMAPILine>(),result.getLinesList());
    }

    @Test
    public void givenCorrectTramNo_whenCallRetrofit_ThenGetOkResponse() throws IOException{
        //given
        ZTMAPILine exampleLine =   new ZTMAPILine(52.20676, 21.037996, "2018-10-17 05:52:50:", "11", "12");
        ZTMAPIResult exampleResult = new ZTMAPIResult();
        exampleResult.addLine(exampleLine);
        server.enqueue(new MockResponse().setBody(gson.toJson(exampleResult)));
        //when
        Observable<ZTMAPIResult> tramObservable = service.getTrams( "11");
        ZTMAPIResult result =  tramObservable.blockingFirst();
        //then
        assertEquals( exampleResult,result);
    }

    @Test
    public void givenInCorrectTramNo_whenCallRetrofit_ThenGetEmptyResponse() throws IOException{
        //given
        String errorResponse = "{ \"result\": [] }";
        server.enqueue(new MockResponse().setBody(errorResponse));
        //when
        Observable<ZTMAPIResult> busObservable = service.getTrams("ABC");
        ZTMAPIResult result =  busObservable.blockingFirst();
        //then
        assertEquals(new ArrayList<ZTMAPILine>(),result.getLinesList());
    }
}