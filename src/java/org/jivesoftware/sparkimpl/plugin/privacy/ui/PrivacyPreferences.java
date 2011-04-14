/**
 * $RCSfile: ,v $
 * $Revision: $
 * $Date: $
 * 
 * Copyright (C) 2004-2010 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.sparkimpl.plugin.privacy.ui;

import javax.swing.Icon;

import javax.swing.JComponent;

import org.jivesoftware.resource.Res;
import org.jivesoftware.resource.SparkRes;
import org.jivesoftware.spark.preference.Preference;

public class PrivacyPreferences implements Preference {

    /**
     * @author Holger Bergunde
     */
    String _title = Res.getString("privacy.label.preferences");
    String _toolTip = Res.getString("pricacy.tooltip.preferences");

    
    public PrivacyPreferences()
    {
	
    }
    
    
    
    @Override
    public String getTitle() {
	return _title;
    }

    @Override
    public Icon getIcon() {
	
	return SparkRes.getImageIcon("PRIVACY_ICON");
    }

    @Override
    public String getTooltip() {
	return _toolTip;
    }

    @Override
    public String getListName() {
	return _title;
    }

    @Override
    public String getNamespace() {
	return "privacy";
    }

    @Override
    public JComponent getGUI() {
	return new PrivacyListTree();
    }

    @Override
    public void load() {
	// TODO Auto-generated method stub

    }

    @Override
    public void commit() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isDataValid() {
	return true;
    }

    @Override
    public String getErrorMessage() {
	return "error in privacy plugin?";
    }

    @Override
    public Object getData() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void shutdown() {
	// TODO Auto-generated method stub

    }

}
