package org.maaya.functions.aoai;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions(HTTP Trigger).
 */
public class HttpTriggerJava {
    /**
     * 漫才トリオの振り役が発しそうな文章を生成するシステム。
     * @param request リクエスト. この中のパラメータに漫才テーマが入るthemeパラメータを含む。
     */
    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("生成開始！");

        // フリ文章作成
        // リクエストパラメータから漫才テーマを受け取る
        String query = request.getQueryParameters().get("theme");
        String theme = request.getBody().orElse(query);

        // Azure OpenAI Service を使ってテーマに沿った振り文章を作成する


        // Json型のレスポンスを作成し、返却する。
        Response response = new Response("Hello, " + name);


        if (response.text() == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("リクエスト変数確認してくれ!").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }
}
