/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.sharedInventory.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.common.ActivateEvent;
import org.terasology.logic.health.DoDestroyEvent;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.block.RetainBlockInventoryComponent;
import org.terasology.sharedInventory.components.SharedInventoryComponent;

@RegisterSystem
public class SharedInventorySystem extends BaseComponentSystem
{
	private Logger logger = LoggerFactory.getLogger(SharedInventorySystem.class);

	private InventoryComponent sharedInventoryComponent;

	@ReceiveEvent(priority = EventPriority.PRIORITY_HIGH,
	              components = {InventoryComponent.class, SharedInventoryComponent.class})
	public void onActivate(ActivateEvent activateEvent, EntityRef activatedBlock)
	{
		logger.info("Activated : " + activatedBlock.toString());
		logger.info(activatedBlock.getComponent(InventoryComponent.class).toString());

		InventoryComponent inventoryComponent = activatedBlock.getComponent(InventoryComponent.class);

		if (sharedInventoryComponent == null)
		{
			sharedInventoryComponent = inventoryComponent;
			logger.info("Set shared inventory to this blocks inventory");
		}
		else if (inventoryComponent != sharedInventoryComponent)
		{
			activatedBlock.addOrSaveComponent(sharedInventoryComponent);
			logger.info("Set this blocks inventory component to the shared one");
		}
	}

	@ReceiveEvent(components = {InventoryComponent.class, SharedInventoryComponent.class})
	public void onDoDestroy(DoDestroyEvent doDestroyEvent, EntityRef blockToDestroy)
	{
		logger.info("Destroying : " + blockToDestroy.toString());

		blockToDestroy.addOrSaveComponent(new InventoryComponent());
	}
}
