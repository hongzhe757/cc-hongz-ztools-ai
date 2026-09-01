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

package cc.hongz.ztools.ai.model.doubao;

import cc.hongz.ztools.ai.AIServiceFactory;
import cc.hongz.ztools.ai.ModelName;
import cc.hongz.ztools.ai.Models;
import cc.hongz.ztools.ai.core.AIConfigBuilder;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.core.img.ImgUtil;
import cc.hongz.ztools.core.thread.ThreadUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Doubao 服务测试。
 */
@DisplayName("Doubao 服务测试")
class DoubaoServiceTest {

    String key = "your key";
    DoubaoService doubaoService = AIServiceFactory.getAIService(
        new AIConfigBuilder(ModelName.DOUBAO.getValue())
            .setModel(Models.Doubao.DOUBAO_1_5_LITE_32K.getModel()).setApiKey(key).build(),
        DoubaoService.class);

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("对话")
    void chat() {
        final String chat = doubaoService.chat("写一个疯狂星期四广告词");
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("流式对话")
    void chatStream() {
        String prompt = "写一个疯狂星期四广告词";
        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);

        doubaoService.chat(prompt, data -> {
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
        final String chat = doubaoService.chat(messages);
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉对话")
    void chatVision() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_1_5_VISION_PRO_32K.getModel()).build(),
            DoubaoService.class);
        final String base64 = ImgUtil.toBase64DataUri(Toolkit.getDefaultToolkit().createImage("your imageUrl"), "png");
        final String chatVision = newDoubaoService.chatVision("图片上有些什么？", Arrays.asList(base64));
        assertNotNull(chatVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉对话（图片URL）")
    void testChatVision() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_1_5_VISION_PRO_32K.getModel()).build(),
            DoubaoService.class);
        final String chatVision = newDoubaoService.chatVision("图片上有些什么？",
            Arrays.asList("https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544"),
            DoubaoCommon.DoubaoVision.HIGH.getDetail());
        assertNotNull(chatVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉流式对话")
    void testChatVisionStream() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_1_5_VISION_PRO_32K.getModel()).build(),
            DoubaoService.class);

        String prompt = "图片上有些什么？";
        List<String> images = Arrays.asList("https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544");

        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);
        newDoubaoService.chatVision(prompt, images, data -> {
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
    @DisplayName("视频生成任务")
    void videoTasks() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_SEEDDANCE_1_0_lite_I2V.getModel()).build(),
            DoubaoService.class);
        final String videoTasks = newDoubaoService.videoTasks("生成一段动画视频，主角是大耳朵图图，一个活泼可爱的小男孩。视频中图图在公园里玩耍，" +
            "画面采用明亮温暖的卡通风格，色彩鲜艳，动作流畅。背景音乐轻快活泼，带有冒险感，音效包括鸟叫声、欢笑声和山洞回声。",
            "https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544");
        assertNotNull(videoTasks);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("查询视频任务信息")
    void getVideoTasksInfo() {
        //cgt-20250306170051-6r9gk
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).build(),
            DoubaoService.class);
        final String videoTasksInfo = newDoubaoService.getVideoTasksInfo("cgt-20250306170051-6r9gk");
        assertNotNull(videoTasksInfo);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("文本嵌入")
    void embeddingText() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_EMBEDDING_TEXT_240715.getModel()).build(),
            DoubaoService.class);
        final String embeddingText = newDoubaoService.embeddingText(new String[]{"阿斯顿", "马丁"});
        assertNotNull(embeddingText);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视觉嵌入")
    void embeddingVision() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_EMBEDDING_VISION.getModel()).build(),
            DoubaoService.class);
        final String embeddingVision = newDoubaoService.embeddingVision("天空好难",
            "https://img2.baidu.com/it/u=862000265,4064861820&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1544");
        assertNotNull(embeddingVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("Bot对话")
    void botsChat() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your bots id").build(),
            DoubaoService.class);
        final ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是什么都可以"));
        messages.add(new Message("user", "你想做些什么"));
        final String botsChat = newDoubaoService.botsChat(messages);
        assertNotNull(botsChat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("Bot流式对话")
    void botsChatStream() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your bots id").build(),
            DoubaoService.class);
        final ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是什么都可以"));
        messages.add(new Message("user", "你想做些什么"));

        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);
        newDoubaoService.botsChat(messages, data -> {
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
    @DisplayName("分词")
    void tokenization() {
        final String tokenization = doubaoService.tokenization(new String[]{"阿斯顿", "马丁"});
        assertNotNull(tokenization);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("批量对话")
    void batchChat() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final String batchChat = newDoubaoService.batchChat("写首歌词");
        assertNotNull(batchChat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("批量对话（消息列表）")
    void testBatchChat() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是个抽象大师"));
        messages.add(new Message("user", "写一个KFC的抽象广告"));
        final String batchChat = newDoubaoService.batchChat(messages);
        assertNotNull(batchChat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("创建上下文")
    void createContext() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是个抽象大师,你真的很抽象"));
        final String context = newDoubaoService.createContext(messages); //ctx-20250307092153-cvslm
        assertNotNull(context);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("创建上下文（带前缀）")
    void testCreateContext() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("ep-20250305100610-bvbpc").build(),
            DoubaoService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "你是个抽象大师,你真的很抽象"));
        final String context = newDoubaoService.createContext(messages,
            DoubaoCommon.DoubaoContext.COMMON_PREFIX.getMode());
        assertNotNull(context); //ctx-20250307092153-cvslm
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("上下文对话")
    void chatContext() {
        //ctx-20250307092153-cvslm
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final String chatContext = newDoubaoService.chatContext("你是谁？", "your contextId");
        assertNotNull(chatContext);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("上下文对话（消息列表）")
    void testChatContext() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", "你怎么看待意大利面拌水泥？"));
        final String chatContext = newDoubaoService.chatContext(messages, "your contextId");
        assertNotNull(chatContext);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("上下文流式对话")
    void testChatContextStream() {
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel("your Endpoint ID").build(),
            DoubaoService.class);
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", "你怎么看待意大利面拌水泥？"));
        String contextId = "your contextId";

        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);
        newDoubaoService.chatContext(messages, contextId, data -> {
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
        final DoubaoService newDoubaoService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.DOUBAO.getValue())
                .setApiKey(key).setModel(Models.Doubao.DOUBAO_SEEDREAM_3_0_T2I.getModel()).build(),
            DoubaoService.class);
        final String imagesGenerations = newDoubaoService.imagesGenerations("一位年轻的宇航员站在未来感十足的太空站内，"
            + "透过巨大的弧形落地窗凝望浩瀚宇宙。窗外，璀璨的星河与五彩斑斓的星云交织，远处隐约可见未知星球的轮廓，"
            + "仿佛在召唤着探索的脚步。宇航服上的呼吸灯与透明显示屏上的星图交相辉映，象征着人类科技与宇宙奥秘的碰撞。"
            + "画面深邃而神秘，充满对未知的渴望与无限可能的想象。");
        assertNotNull(imagesGenerations);
    }
}
