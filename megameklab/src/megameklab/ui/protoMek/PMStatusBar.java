/*
 * MegaMekLab - Copyright (C) 2018 - The MegaMek Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package megameklab.ui.protoMek;

import javax.swing.JLabel;

import megamek.client.ui.clientGUI.GUIPreferences;
import megamek.common.verifier.TestProtoMek;
import megameklab.ui.generalUnit.StatusBar;

/**
 * Status bar for ProtoMek construction
 *
 * @author Neoancient
 */
public class PMStatusBar extends StatusBar {

    private static final String SLOTS_LABEL = "Free Slots: %d / %d";

    private final JLabel slots = new JLabel();

    public PMStatusBar(PMMainUI parent) {
        super(parent);
        add(slots);
    }

    @Override
    protected void additionalRefresh() {
        refreshSlots();
    }

    public void refreshSlots() {
        int maxCrits = 0;
        for (int l = 0; l < getProtoMek().locations(); l++) {
            maxCrits += TestProtoMek.maxSlotsByLocation(l, getProtoMek());
        }
        long currentSlots = getProtoMek().getEquipment().stream()
                .filter(m -> TestProtoMek.requiresSlot(m.getType())).count();

        slots.setText(String.format(SLOTS_LABEL, maxCrits - currentSlots, maxCrits));
        slots.setForeground(currentSlots > maxCrits ? GUIPreferences.getInstance().getWarningColor() : null);
    }
}
