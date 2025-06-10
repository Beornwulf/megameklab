/*
 * MegaMekLab - Copyright (C) 2008
 *
 * Original author - jtighe (torren@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */

package megameklab.ui.util;

import javax.swing.JPanel;

import megamek.common.*;
import megameklab.ui.EntitySource;

public class IView extends JPanel {

    protected EntitySource eSource;

    public IView(EntitySource eSource) {
        this.eSource = eSource;
    }

    public Entity getEntity() {
        return eSource.getEntity();
    }

    public Mek getMek() {
        return (Mek) eSource.getEntity();
    }

    public ProtoMek getProtoMek() {
        return (ProtoMek) eSource.getEntity();
    }

    public Tank getTank() {
        return (Tank) eSource.getEntity();
    }

    public GunEmplacement getGunEmplacement() {
        return (GunEmplacement) eSource.getEntity();
    }

    public VTOL getVTOL() {
        return (VTOL) eSource.getEntity();
    }

    public Aero getAero() {
        return (Aero) eSource.getEntity();
    }

    public SmallCraft getSmallCraft() {
        return (SmallCraft) eSource.getEntity();
    }

    public Jumpship getJumpship() {
        return (Jumpship) eSource.getEntity();
    }

    public BattleArmor getBattleArmor() {
        return (BattleArmor) eSource.getEntity();
    }

    public Infantry getInfantry() {
        return (Infantry) eSource.getEntity();
    }

    /**
     * @return true if the entity has the Small Craft type flag (no instanceof check).
     * This includes DropShips.
     */
    public boolean isSmallCraft() {
        return getEntity().hasETypeFlag(Entity.ETYPE_SMALL_CRAFT);
    }

    /**
     * @return true if the entity has the JumpShip type flag (no instanceof check).
     * This includes WarShips and Space Stations.
     */
    public boolean isJumpShip() {
        return getEntity().hasETypeFlag(Entity.ETYPE_JUMPSHIP);
    }

    /**
     * @return true if the entity has the WarShip type flag (no instanceof check).
     */
    public boolean isWarShip() {
        return getEntity().hasETypeFlag(Entity.ETYPE_WARSHIP);
    }

    /**
     * @return true if the entity is a VTOL Combat Vehicle. This is an instanceof check and includes
     * Support VTOLs but not VTOL type Infantry.
     */
    public boolean isVTOL() {
        return getEntity() instanceof VTOL;
    }
}
