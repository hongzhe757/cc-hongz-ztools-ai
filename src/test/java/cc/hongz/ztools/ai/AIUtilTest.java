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

import cc.hongz.ztools.ai.AIUtil;
import cc.hongz.ztools.ai.ModelName;
import cc.hongz.ztools.ai.core.AIConfigBuilder;
import cc.hongz.ztools.ai.core.AIService;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.ai.model.deepseek.DeepSeekService;
import cc.hongz.ztools.ai.model.doubao.DoubaoService;
import cc.hongz.ztools.ai.model.gemini.GeminiService;
import cc.hongz.ztools.ai.model.grok.GrokService;
import cc.hongz.ztools.ai.model.hutool.HutoolService;
import cc.hongz.ztools.ai.model.openai.OpenaiService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * AIUtil 工具类测试。
 */
@DisplayName("AIUtil 工具类测试")
class AIUtilTest {

    String key = "your key";

    @Test
    @DisplayName("获取DeepSeek服务")
    void getAIService() {
        final DeepSeekService deepSeekService = AIUtil.getAIService(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build(), DeepSeekService.class);
        assertNotNull(deepSeekService);
    }

    @Test
    @DisplayName("获取OpenAI服务")
    void testGetAIService() {
        final AIService aiService = AIUtil.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue()).setApiKey(key).build());
        assertNotNull(aiService);
    }

    @Test
    @DisplayName("获取Hutool服务")
    void getHutoolService() {
        final HutoolService hutoolService = AIUtil.getHutoolService(
            new AIConfigBuilder(ModelName.HUTOOL.getValue()).setApiKey(key).build());
        assertNotNull(hutoolService);
    }

    @Test
    @DisplayName("获取DeepSeek服务")
    void getDeepSeekService() {
        final DeepSeekService deepSeekService = AIUtil.getDeepSeekService(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build());
        assertNotNull(deepSeekService);
    }

    @Test
    @DisplayName("获取Doubao服务")
    void getDoubaoService() {
        final DoubaoService doubaoService = AIUtil.getDoubaoService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue()).setApiKey(key).build());
        assertNotNull(doubaoService);
    }

    @Test
    @DisplayName("获取Grok服务")
    void getGrokService() {
        final GrokService grokService = AIUtil.getGrokService(
            new AIConfigBuilder(ModelName.GROK.getValue()).setApiKey(key).build());
        assertNotNull(grokService);
    }

    @Test
    @DisplayName("获取OpenAI服务")
    void getOpenAIService() {
        final OpenaiService openAIService = AIUtil.getOpenAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue()).setApiKey(key).build());
        assertNotNull(openAIService);
    }

    @Test
    @DisplayName("获取Gemini服务")
    void getGeminiService() {
        final GeminiService geminiService = AIUtil.getGeminiService(
            new AIConfigBuilder(ModelName.GEMINI.getValue()).setApiKey(key).build());
        assertNotNull(geminiService);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("对话（字符串提示词）")
    void chat() {
        final String chat = AIUtil.chat(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build(), "写一首赞美我的诗");
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("对话（消息列表）")
    void testChat() {
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是财神爷，只会说“我是财神”"));
        messages.add(new Message("user", "你是谁啊？"));
        final String chat = AIUtil.chat(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build(), messages);
        assertNotNull(chat);
    }
}
