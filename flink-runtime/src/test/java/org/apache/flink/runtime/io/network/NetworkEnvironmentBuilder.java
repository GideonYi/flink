/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.io.network;

import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.clusterframework.types.ResourceID;
import org.apache.flink.runtime.io.disk.iomanager.IOManager;
import org.apache.flink.runtime.io.disk.iomanager.IOManagerAsync;
import org.apache.flink.runtime.io.network.netty.NettyConfig;
import org.apache.flink.runtime.metrics.groups.UnregisteredMetricGroups;
import org.apache.flink.runtime.taskmanager.NetworkEnvironmentConfiguration;

/**
 * Builder for the {@link NetworkEnvironment}.
 */
public class NetworkEnvironmentBuilder {

	private int numNetworkBuffers = 1024;

	private int networkBufferSize = 32 * 1024;

	private int partitionRequestInitialBackoff = 0;

	private int partitionRequestMaxBackoff = 0;

	private int networkBuffersPerChannel = 2;

	private int floatingNetworkBuffersPerGate = 8;

	private boolean isCreditBased = true;

	private boolean isNetworkDetailedMetrics = false;

	private ResourceID taskManagerLocation = ResourceID.generate();

	private NettyConfig nettyConfig;

	private TaskEventDispatcher taskEventDispatcher = new TaskEventDispatcher();

	private MetricGroup metricGroup = UnregisteredMetricGroups.createUnregisteredTaskManagerMetricGroup();

	private IOManager ioManager = new IOManagerAsync();

	public NetworkEnvironmentBuilder setTaskManagerLocation(ResourceID taskManagerLocation) {
		this.taskManagerLocation = taskManagerLocation;
		return this;
	}

	public NetworkEnvironmentBuilder setNumNetworkBuffers(int numNetworkBuffers) {
		this.numNetworkBuffers = numNetworkBuffers;
		return this;
	}

	public NetworkEnvironmentBuilder setNetworkBufferSize(int networkBufferSize) {
		this.networkBufferSize = networkBufferSize;
		return this;
	}

	public NetworkEnvironmentBuilder setPartitionRequestInitialBackoff(int partitionRequestInitialBackoff) {
		this.partitionRequestInitialBackoff = partitionRequestInitialBackoff;
		return this;
	}

	public NetworkEnvironmentBuilder setPartitionRequestMaxBackoff(int partitionRequestMaxBackoff) {
		this.partitionRequestMaxBackoff = partitionRequestMaxBackoff;
		return this;
	}

	public NetworkEnvironmentBuilder setNetworkBuffersPerChannel(int networkBuffersPerChannel) {
		this.networkBuffersPerChannel = networkBuffersPerChannel;
		return this;
	}

	public NetworkEnvironmentBuilder setFloatingNetworkBuffersPerGate(int floatingNetworkBuffersPerGate) {
		this.floatingNetworkBuffersPerGate = floatingNetworkBuffersPerGate;
		return this;
	}

	public NetworkEnvironmentBuilder setIsCreditBased(boolean isCreditBased) {
		this.isCreditBased = isCreditBased;
		return this;
	}

	public NetworkEnvironmentBuilder setNettyConfig(NettyConfig nettyConfig) {
		this.nettyConfig = nettyConfig;
		return this;
	}

	public NetworkEnvironmentBuilder setTaskEventDispatcher(TaskEventDispatcher taskEventDispatcher) {
		this.taskEventDispatcher = taskEventDispatcher;
		return this;
	}

	public NetworkEnvironmentBuilder setMetricGroup(MetricGroup metricGroup) {
		this.metricGroup = metricGroup;
		return this;
	}

	public NetworkEnvironmentBuilder setIOManager(IOManager ioManager) {
		this.ioManager = ioManager;
		return this;
	}

	public NetworkEnvironment build() {
		return NetworkEnvironment.create(
			taskManagerLocation,
			new NetworkEnvironmentConfiguration(
				numNetworkBuffers,
				networkBufferSize,
				partitionRequestInitialBackoff,
				partitionRequestMaxBackoff,
				networkBuffersPerChannel,
				floatingNetworkBuffersPerGate,
				isCreditBased,
				isNetworkDetailedMetrics,
				nettyConfig),
			taskEventDispatcher,
			metricGroup,
			ioManager);
	}
}
