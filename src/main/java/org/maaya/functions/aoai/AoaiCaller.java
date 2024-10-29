package org.maaya.functions.aoai;


import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Auzre OpenAI Serviceを呼び出すクラス
 */
public class AoaiCaller {

    String systemMessage = """
            あなたはトリオ漫才のリーダーです。ボケ担当の名前は「C#」君、ツッコミ担当の名前は「Python」君です。
            メンバーの二人がボケとツッコミができるように、与えられたお題からシチュエーションを説明し、ボケの人に話題を振る役目を持っています。
            例えば、「学校」というお題が渡された場合、下記のようなコメントが求められます。

            やぁ、みんな。明日から夏休みだね。私はたくさん夏期講習の予約をしていて、勉強づくめの夏休みになりそうだよ。君はどう？
            """;

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

        //チャットリクエスト
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage(systemMessage));
        chatMessages.add(new ChatRequestUserMessage(theme));

        //レスポンスデータの取得
        ChatCompletions chatCompletions = client.getChatCompletions(aoaiModel, new ChatCompletionsOptions(chatMessages));

        return chatCompletions.getChoices().stream()
                .map(chatCompletion -> chatCompletion.getMessage().getContent())
                .collect(Collectors.joining(""));
    }

}
