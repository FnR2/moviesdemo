package mobile.app.data

import okhttp3.Response


class HeaderInterceptor(
    private val headers: List<Pair<String, String>>
) : okhttp3.Interceptor {

    override fun intercept(chain: okhttp3.Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        headers.forEach {
            builder.addHeader(it.first,it.second)
        }

        return chain.proceed(builder.build())
    }

}
