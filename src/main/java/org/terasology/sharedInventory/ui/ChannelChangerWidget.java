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
import org.terasology.rendering.nui.databinding.Binding;
import org.terasology.rendering.nui.databinding.DefaultBinding;
import org.terasology.rendering.nui.events.NUIKeyEvent;
import org.terasology.rendering.nui.widgets.UIText;
import org.terasology.sharedInventory.components.SharedInventoryComponent;
import org.terasology.sharedInventory.events.ChangeSharedInventoryChannelEvent;

public class ChannelChangerWidget extends UIText
{
	private Binding<EntityRef> targetEntity = new DefaultBinding<>(EntityRef.NULL);

	@Override
	public boolean onKeyEvent(NUIKeyEvent event)
	{
		if (event.getKey().getDisplayName().equals("Enter"))
		{
			targetEntity.get().send(new ChangeSharedInventoryChannelEvent(getText()));
			return true;
		}
		else
		{
			return super.onKeyEvent(event);
		}
	}

	public void bindTargetEntity(Binding<EntityRef> binding)
	{
		targetEntity = binding;

		setText(targetEntity.get().getComponent(SharedInventoryComponent.class).sharedInventoryId);
	}

	public EntityRef getTargetEntity()
	{
		return targetEntity.get();
	}
}
