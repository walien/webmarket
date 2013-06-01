/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.webmarket.backend.datasource.providers;

import fr.webmarket.backend.datasource.EntitySequenceProvider;

public class MemoryEntitySequenceProvider implements EntitySequenceProvider {

    private static int ITEM_ENTITY_SEQUENCE = 100777;

    private static int TAG_ENTITY_SEQUENCE = 100;

    private static int ORDER_ENTITY_SEQUENCE = 1000;

    public String getNewItemId() {
        return Integer.toString(++ITEM_ENTITY_SEQUENCE);
    }

    public String getNewTagId() {
        return Integer.toString(++TAG_ENTITY_SEQUENCE);
    }

    public String getNewOrderId() {
        return Integer.toString(++ORDER_ENTITY_SEQUENCE);
    }
}
