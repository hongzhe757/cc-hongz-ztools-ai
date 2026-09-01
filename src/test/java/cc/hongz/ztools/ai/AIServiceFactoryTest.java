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

import cc.hongz.ztools.ai.AIServiceFactory;
import cc.hongz.ztools.ai.ModelName;
import cc.hongz.ztools.ai.core.AIConfigBuilder;
import cc.hongz.ztools.ai.core.AIService;
import cc.hongz.ztools.ai.model.deepseek.DeepSeekService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * AIServiceFactory 工厂测试。
 */
@DisplayName("AIServiceFactory 工厂测试")
class AIServiceFactoryTest {

    String key = "your key";

    @Test
    @DisplayName("获取AI服务")
    void getAIService() {
        final AIService aiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build());
        assertNotNull(aiService);
    }

    @Test
    @DisplayName("获取指定类型的AI服务")
    void testGetAIService() {
        final DeepSeekService deepSeekService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DEEPSEEK.getValue()).setApiKey(key).build(), DeepSeekService.class);
        assertNotNull(deepSeekService);
    }
}
