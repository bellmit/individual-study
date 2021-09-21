package cn.gyw.thirdpart.htttpclient;

import cn.gyw.thirdpart.model.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * retrofit2
 * 网络请求的适配器，它将一个基本的Java接口通过动态代理的方式翻译成一个HTTP请求，并通过OkHttp去发送请求
 */
public class RetrofitUseMain {

	public static void main(String[] args) {
		new RetrofitUseMain().requestRetrofit();
	}

	private void requestRetrofit() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("www.xxxx.com/").build();
		PersonalProtocol personalProtocol = retrofit.create(PersonalProtocol.class);
		Call<Response<Person>> call = personalProtocol.getPersonalListInfo(12);
		call.enqueue(new Callback<Response<Person>>() {
			@Override
			public void onResponse(Call<Response<Person>> call, Response<Response<Person>> response) {
				// 数据请求成功
			}

			@Override
			public void onFailure(Call<Response<Person>> call, Throwable t) {
				// 数据请求失败
			}
		});
	}
}
