/**
 * $Revision: $
 * $Date: $
 *
 * Copyright (C) 2006 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Lesser Public License (LGPL),
 * a copy of which is included in this distribution.
 */

package org.jivesoftware.sparkplugin;

import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.spark.PresenceManager;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.component.RolloverButton;
import org.jivesoftware.spark.util.ModelUtil;
import org.jivesoftware.spark.util.log.Log;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IncomingCallUI extends JPanel {
    private JLabel avatarLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel professionLabel = new JLabel();
    private JLabel nicknameLabel = new JLabel();
    private JLabel lastCalledLabel = new JLabel();
    private JLabel durationLabel = new JLabel();

    private RolloverButton acceptButton;
    private RolloverButton rejectButton;

    private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    private VCard vcard;
    private String jid;


    public IncomingCallUI(String jid) {
        setLayout(new GridBagLayout());

        this.jid = jid;

        vcard = SparkManager.getVCardManager().getVCardFromMemory(jid);

        final JLabel topLabel = new JLabel();
        topLabel.setIcon(JinglePhoneRes.getImageIcon("INCOMING_CALL_IMAGE"));
        topLabel.setHorizontalTextPosition(JLabel.RIGHT);
        topLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        topLabel.setText("Incoming call from...");
        topLabel.setForeground(Color.gray);

        // Add Top Label
        add(topLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        // Add Calller Block
        buildCallerBlock();

        // Add Buttons
        addButtons();


    }


    /**
     * Builds the part of the incoming call UI with the Callers information.
     */
    private void buildCallerBlock() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(new Color(197, 213, 230), 1));

        // Add Avatar
        panel.add(avatarLabel, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));

        // Add Avatar information
        panel.add(titleLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
        panel.add(professionLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 7, 0, 0), 0, 0));
        panel.add(nicknameLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 7, 0, 0), 0, 0));

        // Add History labels
        panel.add(lastCalledLabel, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(15, 5, 0, 0), 0, 0));
        panel.add(durationLabel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));

        // Set default settings
        titleLabel.setForeground(new Color(64, 103, 162));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));


        if (vcard != null) {
            handleVCardInformation(vcard);
        }

        // Update with previous call history.
        Date lastDate = null;
        long callLength = 0;
        /*
        CallList callList = SoftPhoneManager.getInstance().getLogManager().getCallList();
        for (HistoryCall call : callList.getList()) {
            String number = TelephoneUtils.getNumbersFromPhone(call.getNumber());
            if (number.equals(TelephoneUtils.getNumbersFromPhone(phoneNumber))) {
                lastDate = new Date(call.getTime());
            }

            callLength = call.getCallLength();
        }

        */
        final StringBuilder builder = new StringBuilder();
        builder.append("Last called: ");
        if (lastDate == null) {
            builder.append("Never");
            durationLabel.setVisible(false);
        }
        else {
            builder.append(formatter.format(lastDate));
            durationLabel.setText("Duration: " + ModelUtil.getTimeFromLong(callLength));
        }

        lastCalledLabel.setText(builder.toString());

        // Add To Panel
        add(panel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    }

    private void addButtons() {
        // Build Accept Button
        acceptButton = new RolloverButton("      Accept", JinglePhoneRes.getImageIcon("TOASTER_ACCEPT_BUTTON"));
        acceptButton.setHorizontalTextPosition(JLabel.CENTER);
        acceptButton.setFont(new Font("Dialog", Font.BOLD, 11));
        acceptButton.setForeground(new Color(91, 175, 41));
        acceptButton.setMargin(new Insets(0, 0, 0, 0));

        // Build Reject Button
        rejectButton = new RolloverButton("      Reject", JinglePhoneRes.getImageIcon("TOASTER_REJECT_BUTTON"));
        rejectButton.setHorizontalTextPosition(JLabel.CENTER);
        rejectButton.setFont(new Font("Dialog", Font.BOLD, 11));
        rejectButton.setForeground(new Color(153, 32, 10));
        rejectButton.setMargin(new Insets(0, 0, 0, 0));

        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(acceptButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel.add(rejectButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        add(panel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }

    private void handleVCardInformation(VCard vcard) {
        if (vcard.getError() != null) {
            return;
        }


        String firstName = vcard.getFirstName();
        String lastName = vcard.getLastName();
        if (ModelUtil.hasLength(firstName) && ModelUtil.hasLength(lastName)) {
            titleLabel.setText(firstName + " " + lastName);
        }
        else if (ModelUtil.hasLength(firstName)) {
            titleLabel.setText(firstName);
        }

        // Nickname label should show presence and nickname.
        String nickname = SparkManager.getUserManager().getUserNicknameFromJID(jid);
        Icon icon = PresenceManager.getIconFromPresence(PresenceManager.getPresence(jid));

        nicknameLabel.setIcon(icon);
        nicknameLabel.setText(nickname);


        String jobTitle = vcard.getField("TITLE");
        if (jobTitle != null) {
            professionLabel.setText(jobTitle);
        }


        byte[] avatarBytes = null;
        try {
            avatarBytes = vcard.getAvatar();
        }
        catch (Exception e) {
            Log.error("Cannot retrieve avatar bytes.", e);
        }

        if (avatarBytes != null) {
            try {
                ImageIcon avatarIcon = new ImageIcon(avatarBytes);
                avatarLabel.setIcon(avatarIcon);
                avatarLabel.invalidate();
                avatarLabel.validate();
                avatarLabel.repaint();
            }
            catch (Exception e) {
                // no issue
            }
        }


        invalidate();
        validate();
        repaint();
    }

    public RolloverButton getAcceptButton() {
        return acceptButton;
    }

    public RolloverButton getRejectButton() {
        return rejectButton;
    }


    public void paintComponent(Graphics g) {
        BufferedImage cache = new BufferedImage(2, getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = cache.createGraphics();

        GradientPaint paint = new GradientPaint(0, 0, new Color(233, 240, 247), 0, getHeight(), Color.white, true);

        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        g.drawImage(cache, 0, 0, getWidth(), getHeight(), null);
    }
}

