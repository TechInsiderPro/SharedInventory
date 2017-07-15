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
package org.terasology.sharedInventory.ui;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.logic.characters.CharacterComponent;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.registry.In;
import org.terasology.rendering.nui.databinding.ReadOnlyBinding;
import org.terasology.rendering.nui.layers.ingame.inventory.ContainerScreen;

public class ContainerChannelChangerScreen extends ContainerScreen
{
	@In
	private LocalPlayer localPlayer;

	private ChannelChangerWidget channelChangerWidget;

	@Override
	public void initialise()
	{
		super.initialise();

		channelChangerWidget = find("channelChangerWidget", ChannelChangerWidget.class);
	}

	@Override
	public void onOpened()
	{
		super.onOpened();

		channelChangerWidget.bindTargetEntity(new ReadOnlyBinding<EntityRef>()
		{
			@Override
			public EntityRef get()
			{
				return getInteractionTarget();
			}
		});
	}

	protected EntityRef getInteractionTarget()
	{
		EntityRef characterEntity = localPlayer.getCharacterEntity();
		CharacterComponent characterComponent = characterEntity.getComponent(CharacterComponent.class);

		return characterComponent.predictedInteractionTarget;
	}
}
