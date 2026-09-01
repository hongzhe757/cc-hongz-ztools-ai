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

/**
 * gemini公共类。
 *
 * @author elichow
 * @since 5.8.43
 */
public class GeminiCommon {

    //要生成的图片数量
    public enum GeminiImageCount {

        /**
         * 生成一张图片。
         */
        ONE(1),
        /**
         * 生成两张图片。
         */
        TWO(2),
        /**
         * 生成三张图片。
         */
        THREE(3),
        /**
         * 生成四张图片。
         */
        FOUR(4);

        private final int count;

        GeminiImageCount(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }

    //生成的图片大小 (imageSize) - 仅限 Standard 和 Ultra
    public enum GeminiImageSize {

        /**
         * 1K 分辨率。
         */
        SIZE_1K("1K"),
        /**
         * 2K 分辨率。
         */
        SIZE_2K("2K");

        private final String value;

        GeminiImageSize(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    //宽高比
    public enum GeminiAspectRatio {

        /**
         * 正方形 1:1。
         */
        SQUARE("1:1"),
        /**
         * 竖屏 3:4。
         */
        PORTRAIT_3_4("3:4"),
        /**
         * 横屏 4:3。
         */
        LANDSCAPE_4_3("4:3"),
        /**
         * 竖屏 9:16。
         */
        PORTRAIT_9_16("9:16"),
        /**
         * 横屏 16:9。
         */
        LANDSCAPE_16_9("16:9");

        private final String ratio;

        GeminiAspectRatio(final String ratio) {
            this.ratio = ratio;
        }

        public String getRatio() {
            return ratio;
        }

    }

    //人物生成权限
    public enum GeminiPersonGeneration {

        /**
         * 不允许生成人物。
         */
        DONT_ALLOW("dont_allow"),
        /**
         * 允许生成成人人物。
         */
        ALLOW_ADULT("allow_adult"),
        /**
         * 允许生成所有人物。
         */
        ALLOW_ALL("allow_all");

        private final String value;

        GeminiPersonGeneration(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    //生成的视频的时长
    public enum GeminiDurationSeconds {

        /**
         * 视频时长 4 秒。
         */
        FOUR(4),
        /**
         * 视频时长 6 秒。
         */
        SIX(6),
        /**
         * 视频时长 8 秒。
         */
        EIGHT(8);

        private final Integer value;

        GeminiDurationSeconds(final Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //语音音色
    public enum GeminiVoice {

        /**
         * Aoede 音色。
         */
        AOEDE("Aoede"),
        /**
         * Charon 音色。
         */
        CHARON("Charon"),
        /**
         * Kore 音色。
         */
        KORE("Kore"),
        /**
         * Fenrir 音色。
         */
        FENRIR("Fenrir"),
        /**
         * Puck 音色。
         */
        PUCK("Puck");

        private final String value;

        GeminiVoice(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
