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

/**
 * doubao公共类。
 *
 * @author elichow
 * @since 5.8.38
 */
public class DoubaoCommon {

    //doubao上下文缓存参数
    public enum DoubaoContext {

        /**
         * 会话上下文。
         */
        SESSION("session"),
        /**
         * 公共前缀上下文。
         */
        COMMON_PREFIX("common_prefix");

        private final String mode;

        DoubaoContext(final String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }
    }

    //doubao视觉参数
    public enum DoubaoVision {

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

        DoubaoVision(final String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return detail;
        }
    }

    //doubao视频生成参数
    public enum DoubaoVideo {

        //宽高比例
        /**
         * 宽高比 16:9。
         */
        RATIO_16_9("--rt", "16:9"), //[1280, 720]
        /**
         * 宽高比 4:3。
         */
        RATIO_4_3("--rt", "4:3"), //[960, 720]
        /**
         * 宽高比 1:1。
         */
        RATIO_1_1("--rt", "1:1"), //[720, 720]
        /**
         * 宽高比 3:4。
         */
        RATIO_3_4("--rt", "3:4"), //[720, 960]
        /**
         * 宽高比 9:16。
         */
        RATIO_9_16("--rt", "9:16"), //[720, 1280]
        /**
         * 宽高比 21:9。
         */
        RATIO_21_9("--rt", "21:9"), //[1280, 544]

        //生成视频时长
        /**
         * 视频时长 5 秒。
         */
        DURATION_5("--dur", 5), //文生视频，图生视频
        /**
         * 视频时长 10 秒。
         */
        DURATION_10("--dur", 10), //文生视频

        //帧率，即一秒时间内视频画面数量
        /**
         * 帧率 24。
         */
        FPS_5("--fps", 24),

        //视频分辨率
        /**
         * 分辨率 720p。
         */
        RESOLUTION_5("--rs", "720p"),

        //生成视频是否包含水印
        /**
         * 包含水印。
         */
        WATERMARK_TRUE("--wm", true),
        /**
         * 不包含水印。
         */
        WATERMARK_FALSE("--wm", false);

        private final String type;
        private final Object value;

        DoubaoVideo(final String type, final Object value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        /**
         * 获取参数值。
         *
         * @return 参数值
         */
        public Object getValue() {
            if (value instanceof String) {
                return (String) value;
            } else if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Boolean) {
                return (Boolean) value;
            }
            return value;
        }

    }
}
