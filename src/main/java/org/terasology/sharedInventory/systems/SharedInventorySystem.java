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

import java.util.HashMap;
import java.util.Map;

@RegisterSystem
public class SharedInventorySystem extends BaseComponentSystem
{
	private Logger logger = LoggerFactory.getLogger(SharedInventorySystem.class);

	private final Map<Integer, InventoryComponent> inventoryComponentMap = new HashMap<>();

	@ReceiveEvent(priority = EventPriority.PRIORITY_HIGH,
	              components = {InventoryComponent.class, SharedInventoryComponent.class})
	public void onActivate(ActivateEvent activateEvent, EntityRef activatedEntity)
	{
		logger.info("Activated : " + activatedEntity.toString());
		logger.info("Shared inventory component : " + activatedEntity.getComponent(SharedInventoryComponent.class).toString());

		InventoryComponent entityInventory = activatedEntity.getComponent(InventoryComponent.class);

		int sharedInventoryId = activatedEntity.getComponent(SharedInventoryComponent.class).sharedInventoryId;
		InventoryComponent sharedInventory = inventoryComponentMap.get(sharedInventoryId);

		if (sharedInventory == null)
		{
			inventoryComponentMap.put(sharedInventoryId, entityInventory);
			logger.info("Set shared inventory to this entity's inventory");
		}
		else if (entityInventory != sharedInventory)
		{
			activatedEntity.addOrSaveComponent(sharedInventory);
			logger.info("Set this entity's inventory component to the shared one");
		}
	}

	@ReceiveEvent(components = {InventoryComponent.class, SharedInventoryComponent.class})
	public void onDoDestroy(DoDestroyEvent doDestroyEvent, EntityRef entityToDestroy)
	{
		logger.info("Destroying : " + entityToDestroy.toString());

		entityToDestroy.addOrSaveComponent(new InventoryComponent());
	}
}
