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

/**
 * openai公共类。
 *
 * @author elichow
 * @since 5.8.38
 */
public class OpenaiCommon {

    //openai推理参数
    public enum OpenaiReasoning {

        /**
         * 低。
         */
        LOW("low"),
        /**
         * 中等。
         */
        MEDIUM("medium"),
        /**
         * 高。
         */
        HIGH("high");

        private final String effort;

        OpenaiReasoning(final String effort) {
            this.effort = effort;
        }

        public String getEffort() {
            return effort;
        }
    }

    //openai视觉参数
    public enum OpenaiVision {

        /**
         * 自动。
         */
        AUTO("auto"),
        /**
         * 低。
         */
        LOW("low"),
        /**
         * 高。
         */
        HIGH("high");

        private final String detail;

        OpenaiVision(final String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return detail;
        }
    }

    //openai音频参数
    public enum OpenaiSpeech {

        /**
         * alloy 音色。
         */
        ALLOY("alloy"),
        /**
         * ash 音色。
         */
        ASH("ash"),
        /**
         * coral 音色。
         */
        CORAL("coral"),
        /**
         * echo 音色。
         */
        ECHO("echo"),
        /**
         * fable 音色。
         */
        FABLE("fable"),
        /**
         * onyx 音色。
         */
        ONYX("onyx"),
        /**
         * nova 音色。
         */
        NOVA("nova"),
        /**
         * sage 音色。
         */
        SAGE("sage"),
        /**
         * shimmer 音色。
         */
        SHIMMER("shimmer");

        private final String voice;

        OpenaiSpeech(final String voice) {
            this.voice = voice;
        }

        public String getVoice() {
            return voice;
        }
    }
}
