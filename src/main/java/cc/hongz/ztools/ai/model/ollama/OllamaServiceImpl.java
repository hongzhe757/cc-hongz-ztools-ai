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

package cc.hongz.ztools.ai.model.ollama;

import cc.hongz.ztools.ai.AIException;
import cc.hongz.ztools.ai.core.AIConfig;
import cc.hongz.ztools.ai.core.BaseAIService;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.core.bean.BeanPath;
import cc.hongz.ztools.core.thread.ThreadUtil;
import cc.hongz.ztools.core.util.StrUtil;
import cc.hongz.ztools.http.Header;
import cc.hongz.ztools.http.HttpRequest;
import cc.hongz.ztools.http.HttpResponse;
import cc.hongz.ztools.json.JSONObject;
import cc.hongz.ztools.json.JSONUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Ollama服务，AI具体功能的实现。
 *
 * @author yangruoyu-yumeisoft
 * @since 5.8.40
 */
public class OllamaServiceImpl extends BaseAIService implements OllamaService {

    // 对话补全
    private static final String CHAT_ENDPOINT = "/api/chat";
    // 文本生成
    private static final String GENERATE_ENDPOINT = "/api/generate";
    // 文本嵌入
    private static final String EMBEDDINGS_ENDPOINT = "/api/embeddings";
    // 列出模型
    private static final String LIST_MODELS_ENDPOINT = "/api/tags";
    // 显示模型信息
    private static final String SHOW_MODEL_ENDPOINT = "/api/show";
    // 拉取模型
    private static final String PULL_MODEL_ENDPOINT = "/api/pull";
    // 删除模型
    private static final String DELETE_MODEL_ENDPOINT = "/api/delete";
    // 复制模型
    private static final String COPY_MODEL_ENDPOINT = "/api/copy";

    /**
     * 构造函数。
     *
     * @param config AI配置
     */
    public OllamaServiceImpl(final AIConfig config) {
        super(config);
    }

    @Override
    public String chat(final List<Message> messages) {
        final String paramJson = buildChatRequestBody(messages);
        final HttpResponse response = sendPost(CHAT_ENDPOINT, paramJson);
        JSONObject responseJson = JSONUtil.parseObj(response.body());
        Object errorMessage = BeanPath.create("error").get(responseJson);
        if (errorMessage != null) {
            throw new RuntimeException(errorMessage.toString());
        }
        return BeanPath.create("message.content").get(responseJson).toString();
    }

    @Override
    public void chat(final List<Message> messages, final Consumer<String> callback) {
        Map<String, Object> paramMap = buildChatStreamRequestBody(messages);
        ThreadUtil.newThread(() -> sendPostStream(CHAT_ENDPOINT, paramMap, callback::accept),
            "ollama-chat-sse").start();
    }

    @Override
    public String generate(final String prompt) {
        final String paramJson = buildGenerateRequestBody(prompt, null);
        final HttpResponse response = sendPost(GENERATE_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public void generate(final String prompt, final Consumer<String> callback) {
        Map<String, Object> paramMap = buildGenerateStreamRequestBody(prompt, null);
        ThreadUtil.newThread(() -> sendPostStream(GENERATE_ENDPOINT, paramMap, callback::accept),
            "ollama-generate-sse").start();
    }

    @Override
    public String generate(final String prompt, final String format) {
        final String paramJson = buildGenerateRequestBody(prompt, format);
        final HttpResponse response = sendPost(GENERATE_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public void generate(final String prompt, final String format, final Consumer<String> callback) {
        Map<String, Object> paramMap = buildGenerateStreamRequestBody(prompt, format);
        ThreadUtil.newThread(() -> sendPostStream(GENERATE_ENDPOINT, paramMap, callback::accept),
            "ollama-generate-sse").start();
    }

    @Override
    public String embeddings(final String prompt) {
        final String paramJson = buildEmbeddingsRequestBody(prompt);
        final HttpResponse response = sendPost(EMBEDDINGS_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public String embedding(final String input) {
        return embeddings(input);
    }

    @Override
    public String listModels() {
        final HttpResponse response = sendGet(LIST_MODELS_ENDPOINT);
        return response.body();
    }

    @Override
    public String showModel(final String modelName) {
        final String paramJson = buildShowModelRequestBody(modelName);
        final HttpResponse response = sendPost(SHOW_MODEL_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public String pullModel(final String modelName) {
        final String paramJson = buildPullModelRequestBody(modelName);
        final HttpResponse response = sendPost(PULL_MODEL_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public String deleteModel(final String modelName) {
        final String paramJson = buildDeleteModelRequestBody(modelName);
        final HttpResponse response = sendDeleteRequest(DELETE_MODEL_ENDPOINT, paramJson);
        return response.body();
    }

    @Override
    public String copyModel(final String source, final String destination) {
        final String paramJson = buildCopyModelRequestBody(source, destination);
        final HttpResponse response = sendPost(COPY_MODEL_ENDPOINT, paramJson);
        return response.body();
    }

    // 构建chat请求体
    private String buildChatRequestBody(final List<Message> messages) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", false);
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        // 合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    // 构建chatStream请求体
    private Map<String, Object> buildChatStreamRequestBody(final List<Message> messages) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", true);
        paramMap.put("model", config.getModel());
        paramMap.put("messages", messages);
        // 合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return paramMap;
    }

    // 构建generate请求体
    private String buildGenerateRequestBody(final String prompt, final String format) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("prompt", prompt);
        if (StrUtil.isNotBlank(format)) {
            paramMap.put("format", format);
        }
        // 合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    // 构建generateStream请求体
    private Map<String, Object> buildGenerateStreamRequestBody(final String prompt, final String format) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stream", true);
        paramMap.put("model", config.getModel());
        paramMap.put("prompt", prompt);
        if (StrUtil.isNotBlank(format)) {
            paramMap.put("format", format);
        }
        // 合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return paramMap;
    }

    // 构建embeddings请求体
    private String buildEmbeddingsRequestBody(final String prompt) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", config.getModel());
        paramMap.put("prompt", prompt);
        // 合并其他参数
        paramMap.putAll(config.getAdditionalConfigMap());

        return JSONUtil.toJsonStr(paramMap);
    }

    // 构建showModel请求体
    private String buildShowModelRequestBody(final String modelName) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", modelName);

        return JSONUtil.toJsonStr(paramMap);
    }

    // 构建pullModel请求体
    private String buildPullModelRequestBody(final String modelName) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", modelName);

        return JSONUtil.toJsonStr(paramMap);
    }

    // 构建deleteModel请求体
    private String buildDeleteModelRequestBody(final String modelName) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", modelName);

        return JSONUtil.toJsonStr(paramMap);
    }

    /**
     * 发送DELETE请求。
     *
     * @param endpoint 请求端点
     * @param paramJson 请求参数JSON
     * @return 响应结果
     */
    private HttpResponse sendDeleteRequest(final String endpoint, final String paramJson) {
        try {
            return HttpRequest.delete(config.getApiUrl() + endpoint)
                .header(Header.CONTENT_TYPE, "application/json")
                .header(Header.ACCEPT, "application/json")
                .body(paramJson)
                .timeout(config.getTimeout())
                .execute();
        } catch (Exception e) {
            throw new AIException("Failed to send DELETE request: " + e.getMessage(), e);
        }
    }

    // 构建copyModel请求体
    private String buildCopyModelRequestBody(final String source, final String destination) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source", source);
        requestBody.put("destination", destination);
        return JSONUtil.toJsonStr(requestBody);
    }

}
