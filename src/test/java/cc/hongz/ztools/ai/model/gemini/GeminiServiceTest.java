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

package cc.hongz.ztools.ai.model.gemini;

import cc.hongz.ztools.ai.AIServiceFactory;
import cc.hongz.ztools.ai.ModelName;
import cc.hongz.ztools.ai.Models;
import cc.hongz.ztools.ai.core.AIConfig;
import cc.hongz.ztools.ai.core.AIConfigBuilder;
import cc.hongz.ztools.ai.core.Message;
import cc.hongz.ztools.core.codec.Base64;
import cc.hongz.ztools.core.io.FileUtil;
import cc.hongz.ztools.core.thread.ThreadUtil;
import cc.hongz.ztools.core.util.StrUtil;
import cc.hongz.ztools.json.JSONArray;
import cc.hongz.ztools.json.JSONObject;
import cc.hongz.ztools.json.JSONUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Gemini 服务测试。
 */
@DisplayName("Gemini 服务测试")
class GeminiServiceTest {

    String key = "your key";
    GeminiService geminiService = AIServiceFactory.getAIService(
        new AIConfigBuilder(ModelName.GEMINI.getValue()).setApiKey(key).build(), GeminiService.class);

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("对话")
    void chat() {
        final String chat = geminiService.chat("我应该怎么度过2025年的最后一天？");
        assertNotNull(chat);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("流式对话")
    void chatStream() {
        String prompt = "写一个疯狂星期四广告词";
        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);

        geminiService.chat(prompt, data -> {
            assertNotNull(data);
            if (data.contains("finishReason")) {
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
    @DisplayName("上传文件")
    void testUpload() {
        String uploadFile = geminiService.uploadFile(new File("/Users/hdbuoge/Desktop/111.mov"));
        assertNotNull(uploadFile);
        //https://generativelanguage.googleapis.com/v1beta/files/xuognhscnd12
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("多模态图片对话")
    void chatMultimodalImage() {
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.GEMINI_2_0_FLASH.getModel()).build(),
            GeminiService.class);
        final String chatVision = newGeminiService.chatMultimodal("图片上有些什么内容？",
            Arrays.asList("https://generativelanguage.googleapis.com/v1beta/files/xuognhscnd12"));
        assertNotNull(chatVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("多模态图片流式对话")
    void chatMultimodalImageSteam() {
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.GEMINI_2_0_FLASH.getModel()).build(),
            GeminiService.class);
        String prompt = "图片上有些什么内容？中文回答";
        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);

        newGeminiService.chatMultimodal(prompt, Arrays.asList("https://generativelanguage.googleapis.com/v1beta/files/xuognhscnd12"),
            data -> {
                assertNotNull(data);
                if (data.contains("finishReason")) {
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
    @DisplayName("多模态视频对话")
    void chatMultimodalVideo() {
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.GEMINI_2_0_FLASH.getModel()).build(),
            GeminiService.class);
        final String chatVision = newGeminiService.chatMultimodal("视频中第3秒发生了什么？",
            Arrays.asList("https://generativelanguage.googleapis.com/v1beta/files/k1whwbqznecz"));
        assertNotNull(chatVision);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("多模态视频流式对话")
    void chatMultimodalVideoStream() {
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.GEMINI_2_0_FLASH.getModel()).build(),
            GeminiService.class);
        String prompt = "视频中第3秒有什么物品？";
        // 使用AtomicBoolean作为结束标志
        AtomicBoolean isDone = new AtomicBoolean(false);

        newGeminiService.chatMultimodal(prompt, Arrays.asList("https://generativelanguage.googleapis.com/v1beta/files/k1whwbqznecz"),
            data -> {
                assertNotNull(data);
                if (data.contains("finishReason")) {
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
    @DisplayName("JSON结构化对话")
    void chatJson() {
        // 测试结构化输出
        final List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", "提取以下信息：张三，男，25岁。返回JSON格式。"));
        final String jsonResponse = geminiService.chatJson(messages);
        assertNotNull(jsonResponse);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片对话")
    void chatImage() {
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.GEMINI_2_5_FLASH_IMAGE.getModel()).build(),
            GeminiService.class);
        final String response = newGeminiService.chat("一只在太空中行走的赛博朋克风格的猫");
        // 注意：Gemini返回是包含base64数据的响应体
        assertNotNull(response);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片生成")
    void predictImage() {
        // 测试 Imagen 4 原生图片生成
        final GeminiService newGeminiService = AIServiceFactory.getAIService(
            new AIConfigBuilder(ModelName.GEMINI.getValue())
                .setApiKey(key).setModel(Models.Gemini.IMAGEN_4_0_GENERATE_001.getModel()).build(),
            GeminiService.class);
        //暂时只支持英文提示词
        final String response = newGeminiService.predictImage("Oil painting of New Year's greetings");
        assertNotNull(response);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("图片生成并保存")
    void predictImageAndSave() {
        AIConfig config = new AIConfigBuilder(ModelName.GEMINI.getValue())
            .setApiKey(key).setModel(Models.Gemini.IMAGEN_4_0_GENERATE_001.getModel()).build();
        config.putAdditionalConfigByKey("numberOfImages", GeminiCommon.GeminiImageCount.TWO.getCount());
        final GeminiService newGeminiService = AIServiceFactory.getAIService(config, GeminiService.class);

        //暂时只支持英文提示词
        final String response = newGeminiService.predictImage(
            "A park in the spring next to a lake, the sun sets across the lake, golden hour, red wildflowers");

        //解析JSON 结构
        JSONObject jsonObject = JSONUtil.parseObj(response);
        JSONArray predictions = jsonObject.getJSONArray("predictions");

        if (predictions != null) {
            for (int i = 0; i < predictions.size(); i++) {
                JSONObject item = predictions.getJSONObject(i);

                //提取Base64数据
                String base64Data = item.getStr("bytesBase64Encoded");

                //划分并保存到本地文件
                String fileName = "generated_image_" + i + ".png";
                FileUtil.writeBytes(Base64.decode(base64Data), fileName);
                FileUtil.writeBytes(Base64.decode(base64Data), "your filePath" + fileName);

                assertNotNull(base64Data);
            }
        }
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("视频生成")
    void generateVideoTest() {
        AIConfig config = new AIConfigBuilder(ModelName.GEMINI.getValue())
            .setApiKey(key).setModel(Models.Gemini.VEO_3_1_GENERATE_PREVIEW.getModel()).build();
        config.putAdditionalConfigByKey("aspectRatio", GeminiCommon.GeminiAspectRatio.LANDSCAPE_16_9.getRatio());
        config.putAdditionalConfigByKey("durationSeconds", GeminiCommon.GeminiDurationSeconds.EIGHT.getValue());
        final GeminiService newGeminiService = AIServiceFactory.getAIService(config, GeminiService.class);

        // 4. 发起异步生成请求
        String initialRes = newGeminiService.predictVideo("在一艘即将沉没的轮船上，男主角从后面抱起展开双手的女主角，女主角说：”u jump, i jump“");
        JSONObject resObj = JSONUtil.parseObj(initialRes);

        String operationName = resObj.getStr("name");

        // 5. 轮询获取结果 (LRO 模式)
        String videoUri = null;
        int maxRetries = 30; // 约等待 5-10 分钟
        for (int i = 0; i < maxRetries; i++) {
            ThreadUtil.sleep(20000); // 每 20 秒查询一次

            String statusJson = newGeminiService.getVideoOperation(operationName);
            JSONObject statusObj = JSONUtil.parseObj(statusJson);

            // 判断是否完成
            if (statusObj.getBool("done", false)) {
                // 路径参考：response.generateVideoResponse.generatedSamples[0].video.uri
                videoUri = statusObj.getByPath(
                    "response.generateVideoResponse.generatedSamples[0].video.uri", String.class);
                break;
            }
        }

        assertNotNull(videoUri);
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("下载视频")
    void downLoadVideo() {
        String videoUri = "geminiService.getVideoOperation返回的videoUri";
        geminiService.downLoadVideo(videoUri, "your filePath");
    }

    @Test
    @Disabled("依赖外部 LLM API，需真实 key")
    @DisplayName("文本转语音（构建配置）")
    void testTTSWithBuildMethod() {
        AIConfig config = new AIConfigBuilder(ModelName.GEMINI.getValue())
            .setApiKey(key).setModel(Models.Gemini.GEMINI_2_5_PRO_PREVIEW_TTS.getModel()).build();
        config.putAdditionalConfigByKey("temperature", 0.7);
        final GeminiService newGeminiService = AIServiceFactory.getAIService(config, GeminiService.class);

        String prompt = "Hello, this is a test of the native text to speech system.";
        String result = newGeminiService.textToSpeech(prompt, GeminiCommon.GeminiVoice.AOEDE.getValue());

        JSONObject json = JSONUtil.parseObj(result);
        String base64Data = json.getByPath("candidates[0].content.parts[0].inlineData.data", String.class);
        byte[] rawPcm = Base64.decode(base64Data);

        //接口返回的是裸PCM流
        byte[] wavFile = newGeminiService.addWavHeader(rawPcm);

        if (StrUtil.isNotBlank(base64Data)) {
            FileUtil.writeBytes(wavFile, "your filePath");
        }

        assertNotNull(wavFile);
    }
}
