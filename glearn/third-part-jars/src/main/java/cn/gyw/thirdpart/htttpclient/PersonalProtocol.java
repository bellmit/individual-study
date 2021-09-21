package cn.gyw.thirdpart.htttpclient;

import cn.gyw.thirdpart.model.Person;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PersonalProtocol {

	/**
     * 用户信息
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("user/personal_list_info")
    Call<Response<Person>> getPersonalListInfo(@Field("cur_page") int page);
    
}
