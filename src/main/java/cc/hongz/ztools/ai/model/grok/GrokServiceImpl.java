/*
 * Copyright (c) 2025 Hutool Team and hutool.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.hongz.ztools.ai.model.grok;

import cc.hongz.ztools.ai.core.AIConfig;
import cc.hongz.ztools.ai.core.BaseAIService;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.core.thread.ThreadUtil;
import cc.hongz.ztools.http.HttpResponse;
import cc.hongz.ztools.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Grok服务，AI具体功能的实现。
 *
 * @author elichow
 * @since 5.8.38
 */
@SuppressWarnings("checkstyle:MemberName")
public class GrokServiceImpl extends BaseAIService implements GrokService {

    //对话补全
    private final String CHAT_ENDPOINT = "/chat/completions";
    //创建消息回复
    private final String MESSAGES = "/messages";
    //列出模型
    private final String MODELS_ENDPOINT = "/models";
    //列出语言模型
    private final String LANGUAGE_MODELS = "/language-models";
    //分词
    private final String TOKENIZE_TEXT = "/tokenize-text";
    //获取延迟对话
    private final String DEFERRED_COMPLETION = "/chat/deferred-completion";
    //文生图
    private final String IMAGES_GENERATIONS = "/images/generations";

    /**
     * 构造函数。
     *
     * @param config AI配置
     */
    public GrokServiceImpl(final AIConfig config) {
        //初始化grok客户端
        super(config);
    }

    @Override
    public String chat(final List<Message> messages) {
        String paramJson = buildChatRequestBody(messages);
        final HttpResponse response = sendPost(CHAT_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public void chat(final List<Message> messages, final Consumer<String> callback) {
        Map<String, Object> paramMap = buildChatStreamRequestBody(messages);
        ThreadUtil.newThread(() -> sendPostStream(CHAT_ENDPOINT, paramMap, callback::accept),
            "grok-chat-sse").start();
    }

    @Override
    public String message(final List<Message> messages, final int maxToken) {
        String paramJson = buildMessageRequestBody(messages, maxToken);
        final HttpResponse response = sendPost(MESSAGES, paramJson);
        return response.body();
    }

    @Override
    public void message(final List<Message> messages, final int maxToken, final Consumer<String> callback) {
        Map<String, Object> paramMap = buildMessageStreamRequestBody(messages, maxToken);
        ThreadUtil.newThread(() -> sendPostStream(MESSAGES, paramMap, callback::accept),
            "grok-message-sse").start();
    }

    @Override
    public String chatVision(final String prompt, final List<String> images, final String detail) {
        String paramJson = buildChatVisionRequestBody(prompt, images, detail);
        final HttpResponse response = sendPost(CHAT_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public void chatVision(final String prompt, final List<String> images, final String detail,
        final Consumer<String> callback) {
        Map<String, Object> paramMap = buildChatVisionStreamRequestBody(prompt, images, detail);
        ThreadUtil.newThread(() -> sendPostStream(CHAT_ENDPOINT, paramMap, callback::accept),
            "grok-chatVision-sse").start();
    }

    @Override
    public String models() {
        final HttpResponse response = sendGet(MODELS_ENDPOINT);
        return response.body();
    }

    @Override
    public String getModel(final String modelId) {
        final HttpResponse response = sendGet(MODELS_ENDPOINT + "/" + modelId);
        return response.body();
    }

    @Override
    public String languageModels() {
        final HttpResponse response = sendGet(LANGUAGE_MODELS);
        return response.body();
    }

    @Override
    public String getLanguageModel(final String modelId) {
        final HttpResponse response = sendGet(LANGUAGE_MODELS + "/" + modelId);
        return response.body();
    }

    @Override
    public String tokenizeText(final String text) {
        String paramJson = buildTokenizeRequestBody(text);
        final HttpResponse response = sendPost(TOKENIZE_TEXT, paramJson);
        return response.body();
    }

    @Override
    public String deferredCompletion(final String requestId) {
        final HttpResponse response = sendGet(DEFERRED_COMPLETION + "/" + requestId);
        return response.body();
    }

    @Override
    public String imagesGenerations(final String prompt) {
        String paramJson = buildImagesGenerationsRequestBody(prompt);
        final HttpResponse response = sendPost(IMAGES_GENERATIONS, paramJson);
        return response.body();
    }

    // 构建chat请求体
    private String buildChatRequestBody(final List<Message> messages) {
        //使用JSON工具
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    private Map<String, Object> buildChatStreamRequestBody(final List<Message> messages) {
        //使用JSON工具
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", true);
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return paramMap;
    }

    //构建chatVision请求体
    private String buildChatVisionRequestBody(final String prompt, final List<String> images, final String detail) {
        // 定义消息结构
        final List<Message> messages = new ArrayList<>();
        final List<Object> content = new ArrayList<>();

        final Map<String, String> contentMap = new HashMap<>();
        contentMap.put("type", "text");
        contentMap.put("text", prompt);
        content.add(contentMap);
        for (String img : images) {
            HashMap<String, Object> imgUrlMap = new HashMap<>();
            imgUrlMap.put("type", "image_url");
            HashMap<String, String> urlMap = new HashMap<>();
            urlMap.put("url", img);
            urlMap.put("detail", detail);
            imgUrlMap.put("image_url", urlMap);
            content.add(imgUrlMap);
        }

        messages.add(new Message("user", content));

        //使用JSON工具
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());
        return JSONUtil.toJsonStr(paramMap);
    }

    private Map<String, Object> buildChatVisionStreamRequestBody(final String prompt, final List<String> images,
        final String detail) {
        // 定义消息结构
        final List<Message> messages = new ArrayList<>();
        final List<Object> content = new ArrayList<>();

        final Map<String, String> contentMap = new HashMap<>();
        contentMap.put("type", "text");
        contentMap.put("text", prompt);
        content.add(contentMap);
        for (String img : images) {
            HashMap<String, Object> imgUrlMap = new HashMap<>();
            imgUrlMap.put("type", "image_url");
            HashMap<String, String> urlMap = new HashMap<>();
            urlMap.put("url", img);
            urlMap.put("detail", detail);
            imgUrlMap.put("image_url", urlMap);
            content.add(imgUrlMap);
        }

        messages.add(new Message("user", content));

        //使用JSON工具
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", true);
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());
        return paramMap;
    }

    //构建消息回复请求体
    private String buildMessageRequestBody(final List<Message> messages, final int maxToken) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        paramMap.put("max_tokens", maxToken);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    private Map<String, Object> buildMessageStreamRequestBody(final List<Message> messages, final int maxToken) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", true);
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        paramMap.put("max_tokens", maxToken);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return paramMap;
    }

    //构建分词请求体
    private String buildTokenizeRequestBody(final String text) {
        //使用JSON工具
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("text", text);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    //构建文生图请求体
    private String buildImagesGenerationsRequestBody(final String prompt) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("prompt", prompt);
        //合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }
}
