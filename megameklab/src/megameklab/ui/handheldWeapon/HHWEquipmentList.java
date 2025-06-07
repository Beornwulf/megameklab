/*
 * MegaMekLab - Copyright (C) 2025 The MegaMek Team
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
package megameklab.ui.handheldWeapon;

import megamek.common.AmmoType;
import megamek.common.Entity;
import megamek.common.Mounted;
import megameklab.ui.util.CritCellUtil;
import megameklab.ui.util.RefreshListener;
import megameklab.util.UnitUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class HHWEquipmentList extends JList<String> implements MouseListener {

    private static final String WIDER_CRITCELL_WIDTH_STRING = "X".repeat(32);
    private final Entity entity;
    private final RefreshListener refresh;

    public HHWEquipmentList(Entity entity, RefreshListener refresh) {
        super(equipNames(entity));
        this.entity = entity;
        this.refresh = refresh;
        setCellRenderer(new CritListCellRenderer());
        setVisibleRowCount(getModel().getSize());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setPrototypeCellValue(WIDER_CRITCELL_WIDTH_STRING);
        addMouseListener(this);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, CritCellUtil.CRITCELL_BORDER_COLOR));
    }

    private static Vector<String> equipNames(Entity entity) {
        Vector<String> critNames = new Vector<>();
        for (var m : entity.getEquipment()) {
            if (m.getType() instanceof AmmoType) {
                critNames.add("%s (%d)".formatted(m.getName(), (int) m.getSize()));
            } else {
                critNames.add(m.getName());
            }
        }
        if (critNames.isEmpty()) {
            critNames.add(CritCellUtil.EMPTY_CRITCELL_TEXT);
        }
        return critNames;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        setSelectedIndex(locationToIndex(e.getPoint()));
        if (entity.getEquipment(getSelectedIndex()) != null) {
            if (e.getButton() == MouseEvent.BUTTON3 && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                deleteItem();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                var popup = new JPopupMenu();
                var item = new JMenuItem("Delete " + getMount().getName());
                item.addActionListener(ev -> deleteItem());
                popup.add(item);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    private void deleteItem() {
        UnitUtil.removeMounted(entity, entity.getEquipment().get(getSelectedIndex()));
        refresh.refreshEquipment();
        refresh.refreshStructure();
    }

    private Mounted<?> getMount() {
        return entity.getEquipment().get(getSelectedIndex());
    }

    private class CritListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value.equals(WIDER_CRITCELL_WIDTH_STRING)) {
                setText(WIDER_CRITCELL_WIDTH_STRING);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                return this;
            }

            if (index >= entity.getEquipment().size()) {
                CritCellUtil.formatCell(this, null, true, entity, index);
            } else {
                var m = entity.getEquipment().get(index);
                CritCellUtil.formatCell(this, m, true, entity, index);
            }
            setBorder(BorderFactory.createMatteBorder(index == 0 ? 1 : 0, 0, 1, 0, CritCellUtil.CRITCELL_BORDER_COLOR));
            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension superSize = super.getPreferredSize();
            return new Dimension(superSize.width, superSize.height + CritCellUtil.CRITCELL_ADD_HEIGHT);
        }
    }
}
