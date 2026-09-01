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

package cc.hongz.ztools.ai.model.openai;

import cc.hongz.ztools.ai.AIServiceFactory;
import cc.hongz.ztools.ai.ModelName;
import cc.hongz.ztools.ai.Models;
import cc.hongz.ztools.ai.core.AIConfigBuilder;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.core.io.FileUtil;
import cc.hongz.ztools.core.thread.ThreadUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * OpenAI 代理服务测试。
 */
@DisplayName("OpenAI 代理服务测试")
class OpenaiProxyServiceTest {

    String key = "your key";
    //you proxy hostname
    String hostname = "you proxy hostname";
    //you proxy port
    int port = 7890;
    OpenaiService openaiService = AIServiceFactory.getAIService(
        new AIConfigBuilder(ModelName.OPENAI.getValue())
            .setApiKey(key).setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port))).build(),
        OpenaiService.class);

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("对话")
    void chat() {
        final String chat = openaiService.chat("写一个疯狂星期四广告词");
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("流式对话")
    void chatStream() {
        String prompt = "写一个疯狂星期四广告词";
        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);

        openaiService.chat(prompt, data -> {
            assertNotNull(data);
            if (data.contains("[DONE]")) {
                // 设置结束标志
                isDone.set(true);
            } else if (data.contains("\"error\"")) {
                isDone.set(true);
            }

        });
        // 轮询检查结束标志
        while (!isDone.get()) {
            ThreadUtil.sleep(100);
        }
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("带消息列表对话")
    void testChat() {
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是个抽象大师，会说很抽象的话，最擅长说抽象的笑话"));
        messages.add(new Message("user", "给我说一个笑话"));
        final String chat = openaiService.chat(messages);
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉对话")
    void chatVision() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.GPT_4O_MINI.getModel()).build(),
            OpenaiService.class);
        final String chatVision = newOpenaiService.chatVision("图片上有些什么？",
            Arrays.asList("https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544",
                "https://img2.baidu.com/it/u=1682510685,1244554634&fm=253&fmt=auto&app=138&f=JPEG?w=803&h=800"));
        assertNotNull(chatVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉流式对话")
    void testChatVisionStream() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.GPT_4O_MINI.getModel()).build(),
            OpenaiService.class);
        String prompt = "图片上有些什么？";
        List<String> images = Collections.singletonList("https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544");

        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);
        newOpenaiService.chatVision(prompt, images, data -> {
            assertNotNull(data);
            if (data.contains("[DONE]")) {
                // 设置结束标志
                isDone.set(true);
            } else if (data.contains("\"error\"")) {
                isDone.set(true);
            }

        });
        // 轮询检查结束标志
        while (!isDone.get()) {
            ThreadUtil.sleep(100);
        }
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片生成")
    void imagesGenerations() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.DALL_E_3.getModel()).build(),
            OpenaiService.class);
        final String imagesGenerations = newOpenaiService.imagesGenerations("一位年轻的宇航员站在未来感十足的太空站内，"
            + "透过巨大的弧形落地窗凝望浩瀚宇宙。窗外，璀璨的星河与五彩斑斓的星云交织，远处隐约可见未知星球的轮廓，"
            + "仿佛在召唤着探索的脚步。宇航服上的呼吸灯与透明显示屏上的星图交相辉映，象征着人类科技与宇宙奥秘的碰撞。"
            + "画面深邃而神秘，充满对未知的渴望与无限可能的想象。");
        assertNotNull(imagesGenerations);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片编辑")
    void imagesEdits() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.DALL_E_2.getModel()).build(),
            OpenaiService.class);
        final File file = FileUtil.file("your imgUrl");
        final String imagesEdits = newOpenaiService.imagesEdits("茂密的森林中，有一只九色鹿若隐若现", file);
        assertNotNull(imagesEdits);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片变体")
    void imagesVariations() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.DALL_E_2.getModel()).build(),
            OpenaiService.class);
        final File file = FileUtil.file("your imgUrl");
        final String imagesVariations = newOpenaiService.imagesVariations(file);
        assertNotNull(imagesVariations);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("文本转语音")
    void textToSpeech() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.TTS_1_HD.getModel()).build(),
            OpenaiService.class);
        final InputStream inputStream = newOpenaiService.textToSpeech("万里山河一夜白，\n" +
            "千峰尽染玉龙哀。\n" +
            "长风卷起琼花碎，\n" +
            "直上九霄揽月来。", OpenaiCommon.OpenaiSpeech.NOVA);

        final String filePath = "your filePath";
        final Path path = Paths.get(filePath);
        try (final FileOutputStream outputStream = new FileOutputStream(filePath)) {
            Files.createDirectories(path.getParent());
            final byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("语音转文本")
    void speechToText() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.WHISPER_1.getModel()).build(),
            OpenaiService.class);
        final File file = FileUtil.file("your filePath");
        final String speechToText = newOpenaiService.speechToText(file);
        assertNotNull(speechToText);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("文本嵌入")
    void embeddingText() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.TEXT_EMBEDDING_3_SMALL.getModel()).build(),
            OpenaiService.class);
        final String embeddingText = newOpenaiService.embeddingText("萬里山河一夜白,千峰盡染玉龍哀,長風捲起瓊花碎,直上九霄闌月來");
        assertNotNull(embeddingText);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("内容审核")
    void moderations() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.OMNI_MODERATION_LATEST.getModel()).build(),
            OpenaiService.class);
        final String moderations = newOpenaiService.moderations("你要玩游戏",
            "https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544");
        assertNotNull(moderations);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("推理对话")
    void chatReasoning() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.O3_MINI.getModel()).build(),
            OpenaiService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是现代抽象家"));
        messages.add(new Message("user", "给我一个KFC疯狂星期四的文案"));
        final String chatReasoning = newOpenaiService.chatReasoning(messages,
            OpenaiCommon.OpenaiReasoning.HIGH.getEffort());
        assertNotNull(chatReasoning);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("推理流式对话")
    void chatReasoningStream() {
        final OpenaiService newOpenaiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.OPENAI.getValue())
                .setApiKey(key).setModel(Models.Openai.O3_MINI.getModel()).build(),
            OpenaiService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是现代抽象家"));
        messages.add(new Message("user", "给我一个KFC疯狂星期四的文案"));

        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);
        newOpenaiService.chatReasoning(messages, OpenaiCommon.OpenaiReasoning.HIGH.getEffort(), data -> {
            assertNotNull(data);
            if (data.contains("[DONE]")) {
                // 设置结束标志
                isDone.set(true);
            } else if (data.contains("\"error\"")) {
                isDone.set(true);
            }

        });
        // 轮询检查结束标志
        while (!isDone.get()) {
            ThreadUtil.sleep(100);
        }
    }
}
