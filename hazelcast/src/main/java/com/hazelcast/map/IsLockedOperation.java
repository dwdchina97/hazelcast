/*
 * Copyright (c) 2008-2012, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.map;

import com.hazelcast.nio.Data;
import com.hazelcast.spi.BackupAwareOperation;
import com.hazelcast.spi.NodeEngine;
import com.hazelcast.spi.Operation;
import com.hazelcast.spi.ResponseHandler;
import com.hazelcast.spi.impl.AbstractNamedKeyBasedOperation;

public class IsLockedOperation extends AbstractNamedKeyBasedOperation {

    PartitionContainer pc;
    ResponseHandler responseHandler;
    DefaultRecordStore recordStore;
    MapService mapService;
    NodeEngine nodeEngine;
    boolean locked = false;

    public IsLockedOperation(String name, Data dataKey) {
        super(name, dataKey);
    }

    public IsLockedOperation() {
    }

    protected void init() {
        responseHandler = getResponseHandler();
        mapService = getService();
        nodeEngine = getNodeEngine();
        pc = mapService.getPartitionContainer(getPartitionId());
        recordStore = pc.getMapPartition(name);
    }

    public void beforeRun() {
        init();
    }

    @Override
    public void run() throws Exception {
        doOp();
    }

    public void doOp() {
        locked = recordStore.isLocked(dataKey);
    }

    @Override
    public Object getResponse() {
        return locked;
    }

}