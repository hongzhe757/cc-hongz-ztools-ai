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

package cc.hongz.ztools.ai;

/**
 * 各模型厂商包含的model（指具体的模型）。
 *
 * @author elichow
 * @since 5.8.38
 */
public class Models {


    // Hutool的模型
    public enum Hutool {
        /**
         * hutool 模型。
         */
        HUTOOL("hutool");

        private final String model;

        Hutool(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // DeepSeek的模型
    public enum DeepSeek {
        /**
         * deepseek-chat 模型。
         */
        DEEPSEEK_CHAT("deepseek-chat"),
        /**
         * deepseek-reasoner 模型。
         */
        DEEPSEEK_REASONER("deepseek-reasoner");

        private final String model;

        DeepSeek(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // Openai的模型
    public enum Openai {
        /**
         * gpt-4.5-preview 模型。
         */
        GPT_4_5_PREVIEW("gpt-4.5-preview"),
        /**
         * gpt-4o 模型。
         */
        GPT_4O("gpt-4o"),
        /**
         * chatgpt-4o-latest 模型。
         */
        CHATGPT_4O_LATEST("chatgpt-4o-latest"),
        /**
         * gpt-4o-mini 模型。
         */
        GPT_4O_MINI("gpt-4o-mini"),
        /**
         * o1 模型。
         */
        O1("o1"),
        /**
         * o1-mini 模型。
         */
        O1_MINI("o1-mini"),
        /**
         * o1-preview 模型。
         */
        O1_PREVIEW("o1-preview"),
        /**
         * o3-mini 模型。
         */
        O3_MINI("o3-mini"),
        /**
         * gpt-4o-realtime-preview 模型。
         */
        GPT_4O_REALTIME_PREVIEW("gpt-4o-realtime-preview"),
        /**
         * gpt-4o-mini-realtime-preview 模型。
         */
        GPT_4O_MINI_REALTIME_PREVIEW("gpt-4o-mini-realtime-preview"),
        /**
         * gpt-4o-audio-preview 模型。
         */
        GPT_4O_AUDIO_PREVIEW("gpt-4o-audio-preview"),
        /**
         * gpt-4o-mini-audio-preview 模型。
         */
        GPT_4O_MINI_AUDIO_PREVIEW("gpt-4o-mini-audio-preview"),
        /**
         * gpt-4-turbo 模型。
         */
        GPT_4_TURBO("gpt-4-turbo"),
        /**
         * gpt-4-turbo-preview 模型。
         */
        GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview"),
        /**
         * gpt-4 模型。
         */
        GPT_4("gpt-4"),
        /**
         * gpt-3.5-turbo-0125 模型。
         */
        GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),
        /**
         * gpt-3.5-turbo 模型。
         */
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        /**
         * gpt-3.5-turbo-1106 模型。
         */
        GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
        /**
         * gpt-3.5-turbo-instruct 模型。
         */
        GPT_3_5_TURBO_INSTRUCT("gpt-3.5-turbo-instruct"),
        /**
         * dall-e-3 模型。
         */
        DALL_E_3("dall-e-3"),
        /**
         * dall-e-2 模型。
         */
        DALL_E_2("dall-e-2"),
        /**
         * tts-1 模型。
         */
        TTS_1("tts-1"),
        /**
         * tts-1-hd 模型。
         */
        TTS_1_HD("tts-1-hd"),
        /**
         * whisper-1 模型。
         */
        WHISPER_1("whisper-1"),
        /**
         * text-embedding-3-large 模型。
         */
        TEXT_EMBEDDING_3_LARGE("text-embedding-3-large"),
        /**
         * text-embedding-3-small 模型。
         */
        TEXT_EMBEDDING_3_SMALL("text-embedding-3-small"),
        /**
         * text-embedding-ada-002 模型。
         */
        TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
        /**
         * omni-moderation-latest 模型。
         */
        OMNI_MODERATION_LATEST("omni-moderation-latest"),
        /**
         * omni-moderation-2024-09-26 模型。
         */
        OMNI_MODERATION_2024_09_26("omni-moderation-2024-09-26"),
        /**
         * text-moderation-latest 模型。
         */
        TEXT_MODERATION_LATEST("text-moderation-latest"),
        /**
         * text-moderation-stable 模型。
         */
        TEXT_MODERATION_STABLE("text-moderation-stable"),
        /**
         * text-moderation-007 模型。
         */
        TEXT_MODERATION_007("text-moderation-007"),
        /**
         * babbage-002 模型。
         */
        BABBAGE_002("babbage-002"),
        /**
         * davinci-002 模型。
         */
        DAVINCI_002("davinci-002");

        private final String model;

        Openai(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // Doubao的模型
    public enum Doubao {
        /**
         * doubao-1.5-pro-32k-250115 模型。
         */
        DOUBAO_1_5_PRO_32K("doubao-1.5-pro-32k-250115"),
        /**
         * doubao-1.5-pro-256k-250115 模型。
         */
        DOUBAO_1_5_PRO_256K("doubao-1.5-pro-256k-250115"),
        /**
         * doubao-1.5-lite-32k-250115 模型。
         */
        DOUBAO_1_5_LITE_32K("doubao-1.5-lite-32k-250115"),
        /**
         * doubao-pro-4k-240515 模型。
         */
        DOUBAO_PRO_4K_240515("doubao-pro-4k-240515"),
        /**
         * doubao-pro-4k-character-240728 模型。
         */
        DOUBAO_PRO_4K_CHARACTER_240728("doubao-pro-4k-character-240728"),
        /**
         * doubao-pro-4k-functioncall-240615 模型。
         */
        DOUBAO_PRO_4K_FUNCTIONCALL_240615("doubao-pro-4k-functioncall-240615"),
        /**
         * doubao-pro-4k-browsing-240524 模型。
         */
        DOUBAO_PRO_4K_BROWSING_240524("doubao-pro-4k-browsing-240524"),
        /**
         * doubao-pro-32k-241215 模型。
         */
        DOUBAO_PRO_32K_241215("doubao-pro-32k-241215"),
        /**
         * doubao-pro-32k-functioncall-241028 模型。
         */
        DOUBAO_PRO_32K_FUNCTIONCALL_241028("doubao-pro-32k-functioncall-241028"),
        /**
         * doubao-pro-32k-browsing-241115 模型。
         */
        DOUBAO_PRO_32K_BROWSING_241115("doubao-pro-32k-browsing-241115"),
        /**
         * doubao-pro-32k-character-241215 模型。
         */
        DOUBAO_PRO_32K_CHARACTER_241215("doubao-pro-32k-character-241215"),
        /**
         * doubao-pro-128k-240628 模型。
         */
        DOUBAO_PRO_128K_240628("doubao-pro-128k-240628"),
        /**
         * doubao-pro-256k-240828 模型。
         */
        DOUBAO_PRO_256K_240828("doubao-pro-256k-240828"),
        /**
         * doubao-lite-4k-240328 模型。
         */
        DOUBAO_LITE_4K_240328("doubao-lite-4k-240328"),
        /**
         * doubao-lite-4k-pretrain-character-240516 模型。
         */
        DOUBAO_LITE_4K_PRETRAIN_CHARACTER_240516("doubao-lite-4k-pretrain-character-240516"),
        /**
         * doubao-lite-32k-240828 模型。
         */
        DOUBAO_LITE_32K_240828("doubao-lite-32k-240828"),
        /**
         * doubao-lite-32k-character-241015 模型。
         */
        DOUBAO_LITE_32K_CHARACTER_241015("doubao-lite-32k-character-241015"),
        /**
         * doubao-lite-128k-240828 模型。
         */
        DOUBAO_LITE_128K_240828("doubao-lite-128k-240828"),
        /**
         * deepseek-r1-250120 模型。
         */
        DEEPSEEK_R1("deepseek-r1-250120"),
        /**
         * deepseek-r1-distill-qwen-32b-250120 模型。
         */
        DEEPSEEK_R1_DISTILL_QWEN_32B("deepseek-r1-distill-qwen-32b-250120"),
        /**
         * deepseek-r1-distill-qwen-7b-250120 模型。
         */
        DEEPSEEK_R1_DISTILL_QWEN_7B("deepseek-r1-distill-qwen-7b-250120"),
        /**
         * deepseek-v3-241226 模型。
         */
        DEEPSEEK_V3("deepseek-v3-241226"),
        /**
         * moonshot-v1-8k 模型。
         */
        MOONSHOT_V1_8K("moonshot-v1-8k"),
        /**
         * moonshot-v1-32k 模型。
         */
        MOONSHOT_V1_32K("moonshot-v1-32k"),
        /**
         * moonshot-v1-128k 模型。
         */
        MOONSHOT_V1_128K("moonshot-v1-128k"),
        /**
         * chatglm3-130b-fc-v1.0 模型。
         */
        CHATGLM3_130B_FC("chatglm3-130b-fc-v1.0"),
        /**
         * chatglm3-130-fin-v1.0-update 模型。
         */
        CHATGLM3_130_FIN("chatglm3-130-fin-v1.0-update"),
        /**
         * mistral-7b-instruct-v0.2 模型。
         */
        MISTRAL_7B("mistral-7b-instruct-v0.2"),
        /**
         * doubao-1.5-vision-pro-32k-250115 模型。
         */
        DOUBAO_1_5_VISION_PRO_32K("doubao-1.5-vision-pro-32k-250115"),
        /**
         * doubao-vision-pro-32k-241008 模型。
         */
        DOUBAO_VISION_PRO_32K("doubao-vision-pro-32k-241008"),
        /**
         * doubao-vision-lite-32k-241015 模型。
         */
        DOUBAO_VISION_LITE_32K("doubao-vision-lite-32k-241015"),
        /**
         * doubao-embedding-large-text-240915 模型。
         */
        DOUBAO_EMBEDDING_LARGE("doubao-embedding-large-text-240915"),
        /**
         * doubao-embedding-text-240715 模型。
         */
        DOUBAO_EMBEDDING_TEXT_240715("doubao-embedding-text-240715"),
        /**
         * doubao-embedding-vision-241215 模型。
         */
        DOUBAO_EMBEDDING_VISION("doubao-embedding-vision-241215"),
        /**
         * doubao-seedream-3-0-t2i-250415 模型。
         */
        DOUBAO_SEEDREAM_3_0_T2I("doubao-seedream-3-0-t2i-250415"),
        /**
         * doubao-seedance-1-0-lite-t2v-250428 模型。
         */
        DOUBAO_SEEDDANCE_1_0_LITE_T2V("doubao-seedance-1-0-lite-t2v-250428"),
        /**
         * doubao-seedance-1-0-lite-i2v-250428 模型。
         */
        DOUBAO_SEEDDANCE_1_0_lite_I2V("doubao-seedance-1-0-lite-i2v-250428"),
        /**
         * wan2-1-14b-t2v-250225 模型。
         */
        WAN2_1_14B_T2V("wan2-1-14b-t2v-250225"),
        /**
         * wan2-1-14b-i2v-250225 模型。
         */
        WAN2_1_14B_I2V("wan2-1-14b-i2v-250225");

        private final String model;

        Doubao(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // Grok的模型
    public enum Grok {
        /**
         * grok-3-beta 模型。
         */
        GROK_3_BETA_LATEST("grok-3-beta"),
        /**
         * grok-3-beta 模型。
         */
        GROK_3_BETA("grok-3-beta"),
        /**
         * grok-3-beta 模型。
         */
        GROK_3("grok-3-beta"),
        /**
         * grok-3-mini-fast-beta 模型。
         */
        GROK_3_MINI_FAST_LATEST("grok-3-mini-fast-beta"),
        /**
         * grok-3-mini-fast-beta 模型。
         */
        GROK_3_MINI_FAST_BETA("grok-3-mini-fast-beta"),
        /**
         * grok-3-mini-fast-beta 模型。
         */
        GROK_3_MINI_FAST("grok-3-mini-fast-beta"),
        /**
         * grok-3-fast-beta 模型。
         */
        GROK_3_FAST_LATEST("grok-3-fast-beta"),
        /**
         * grok-3-fast-beta 模型。
         */
        GROK_3_FAST_BETA("grok-3-fast-beta"),
        /**
         * grok-3-fast-beta 模型。
         */
        GROK_3_FAST("grok-3-fast-beta"),
        /**
         * grok-3-mini-beta 模型。
         */
        GROK_3_MINI_LATEST("grok-3-mini-beta"),
        /**
         * grok-3-mini-beta 模型。
         */
        GROK_3_MINI_BETA("grok-3-mini-beta"),
        /**
         * grok-3-mini-beta 模型。
         */
        GROK_3_MINI("grok-3-mini-beta"),
        /**
         * grok-2-image-1212 模型。
         */
        GROK_2_IMAGE_LATEST("grok-2-image-1212"),
        /**
         * grok-2-image-1212 模型。
         */
        GROK_2_IMAGE("grok-2-image-1212"),
        /**
         * grok-2-image-1212 模型。
         */
        GROK_2_IMAGE_1212("grok-2-image-1212"),
        /**
         * grok-2-1212 模型。
         */
        GROK_2_LATEST("grok-2-1212"),
        /**
         * grok-2-1212 模型。
         */
        GROK_2("grok-2-1212"),
        /**
         * grok-2-1212 模型。
         */
        GROK_2_1212("grok-2-1212"),
        /**
         * grok-2-vision-1212 模型。
         */
        GROK_2_VISION_1212("grok-2-vision-1212"),
        /**
         * grok-beta 模型。
         */
        GROK_BETA("grok-beta"),
        /**
         * grok-vision-beta 模型。
         */
        GROK_VISION_BETA("grok-vision-beta");

        private final String model;

        Grok(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // Ollama的模型
    public enum Ollama {
        /**
         * qwen3:32b 模型。
         */
        QWEN3_32B("qwen3:32b");

        private final String model;

        Ollama(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    // Gemini的模型
    public enum Gemini {
        /**
         * gemini-2.5-pro-preview-tts 模型。
         */
        GEMINI_2_5_PRO_PREVIEW_TTS("gemini-2.5-pro-preview-tts"),
        /**
         * gemini-2.5-flash-preview-tts 模型。
         */
        GEMINI_2_5_FLASH_PREVIEW_TTS("gemini-2.5-flash-preview-tts"),
        /**
         * veo-2.0-generate-001 模型。
         */
        VEO_2_0_GENERATE_001("veo-2.0-generate-001"),
        /**
         * veo-3.0-fast-generate-001 模型。
         */
        VEO_3_0_FAST_GENERATE_001("veo-3.0-fast-generate-001"),
        /**
         * veo-3.0-generate-001 模型。
         */
        VEO_3_0_GENERATE_001("veo-3.0-generate-001"),
        /**
         * veo-3.1-fast-generate-preview 模型。
         */
        VEO_3_1_FAST_GENERATE_PREVIEW("veo-3.1-fast-generate-preview"),
        /**
         * veo-3.1-generate-preview 模型。
         */
        VEO_3_1_GENERATE_PREVIEW("veo-3.1-generate-preview"),
        /**
         * imagen-4.0-generate-001 模型。
         */
        IMAGEN_4_0_GENERATE_001("imagen-4.0-generate-001"),
        /**
         * imagen-4.0-ultra-generate-001 模型。
         */
        IMAGEN_4_0_ULTRA_GENERATE_001("imagen-4.0-ultra-generate-001"),
        /**
         * imagen-4.0-fast-generate-001 模型。
         */
        IMAGEN_4_0_FAST_GENERATE_001("imagen-4.0-fast-generate-001"),
        /**
         * imagen-3.0-generate-002 模型。
         */
        IMAGEN_3_0_GENERATE_002("imagen-3.0-generate-002"),
        /**
         * gemini-3-pro-preview 模型。
         */
        GEMINI_3_PRO_PREVIEW("gemini-3-pro-preview"),
        /**
         * gemini-3-flash 模型。
         */
        GEMINI_3_FLASH("gemini-3-flash"),
        /**
         * gemini-2.5-pro 模型。
         */
        GEMINI_2_5_PRO("gemini-2.5-pro"),
        /**
         * gemini-2.5-flash 模型。
         */
        GEMINI_2_5_FLASH("gemini-2.5-flash"),
        /**
         * gemini-2.5-flash-lite 模型。
         */
        GEMINI_2_5_FLASH_LITE("gemini-2.5-flash-lite"),
        /**
         * gemini-2.5-flash-image 模型。
         */
        GEMINI_2_5_FLASH_IMAGE("gemini-2.5-flash-image"),
        /**
         * gemini-2.0-flash 模型。
         */
        GEMINI_2_0_FLASH("gemini-2.0-flash"),
        /**
         * gemini-2.0-flash-lite 模型。
         */
        GEMINI_2_0_FLASH_LITE("gemini-2.0-flash-lite"),
        /**
         * gemini-2.0-pro-exp 模型。
         */
        GEMINI_2_0_PRO_EXP("gemini-2.0-pro-exp"),
        /**
         * gemini-1.5-flash 模型。
         */
        GEMINI_1_5_FLASH("gemini-1.5-flash"),
        /**
         * gemini-1.5-pro 模型。
         */
        GEMINI_1_5_PRO("gemini-1.5-pro"),
        /**
         * gemini-1.5-flash-8b 模型。
         */
        GEMINI_1_5_FLASH_8B("gemini-1.5-flash-8b"),
        /**
         * gemini-1.0-pro 模型。
         */
        GEMINI_1_0_PRO("gemini-1.0-pro");

        private final String model;

        Gemini(final String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

}
