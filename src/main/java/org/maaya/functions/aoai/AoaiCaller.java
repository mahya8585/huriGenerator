package org.maaya.functions.aoai;


import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Auzre OpenAI Serviceを呼び出すクラス
 */
public class AoaiCaller {

    String SYSTEM_MESSAGE = "あなたはトリオ漫才のリーダーです。メンバーの二人がボケとツッコミができるように、与えられたお題からシチュエーションを説明し、ボケの人に話題を振る役目を持っています。例えば、下記のようなコメントが求められる立場です。\n"
            + "[お題] 学校\n"
            + "[あなたの役割] やぁ、みんな。明日から夏休みだね。私はたくさん夏期講習の予約をしていて、勉強づくめの夏休みになりそうだよ。B君はどう？";

    /**
     * Azure OpenAI Service を使ってテーマに沿った振り文章を作成する
     * @param theme テーマ
     * @return 振り文章
     */
    public String createSentence(String theme) {
        // Azure OpenAI Serviceのリクエスト設定をする
        String aoaiApiKey = Configuration.getGlobalConfiguration().get("AOAI_API_KEY");
        String aoaiEndpoint = Configuration.getGlobalConfiguration().get("AOAI_ENDPOINT");
        String aoaiModel = Configuration.getGlobalConfiguration().get("AOAI_MODEL");

        OpenAIClient client = new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(aoaiApiKey))
                .endpoint(aoaiEndpoint)
                .buildClient();

        //チャットリクエストの設定
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage(SYSTEM_MESSAGE));
        chatMessages.add(new ChatRequestUserMessage(theme));

        ChatCompletions chatCompletions = client.getChatCompletions(aoaiModel, new ChatCompletionsOptions(chatMessages));

        // TODO レスポンスデータの整形

        return "Hello, " + theme;
    }
}
