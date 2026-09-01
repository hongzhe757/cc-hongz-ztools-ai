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

import cc.hongz.ztools.ai.Models;
import cc.hongz.ztools.ai.core.BaseConfig;

/**
 * Ollama配置类，初始化API接口地址，设置默认的模型。
 *
 * @author yangruoyu-yumeisoft
 * @since 5.8.40
 */
@SuppressWarnings("checkstyle:MemberName")
public class OllamaConfig extends BaseConfig {

    private final String API_URL = "http://localhost:11434";

    private final String DEFAULT_MODEL = Models.Ollama.QWEN3_32B.getModel();

    /**
     * 构造。
     */
    public OllamaConfig() {
        setApiUrl(API_URL);
        setModel(DEFAULT_MODEL);
    }

    /**
     * 构造。
     *
     * @param apiUrl API接口地址
     */
    public OllamaConfig(final String apiUrl) {
        this();
        setApiUrl(apiUrl);
    }

    /**
     * 构造。
     *
     * @param apiUrl API接口地址
     * @param model 模型名称
     */
    public OllamaConfig(final String apiUrl, final String model) {
        this();
        setApiUrl(apiUrl);
        setModel(model);
    }

    @Override
    public String getModelName() {
        return "ollama";
    }

}
