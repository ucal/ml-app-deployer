package com.marklogic.appdeployer.command.groups;

import com.marklogic.appdeployer.command.AbstractManageResourceTest;
import com.marklogic.appdeployer.command.Command;
import com.marklogic.rest.mgmt.ResourceManager;
import com.marklogic.rest.mgmt.groups.GroupManager;
import com.marklogic.rest.util.Fragment;

public class ManageGroupsTest extends AbstractManageResourceTest {

    @Override
    protected ResourceManager newResourceManager() {
        return new GroupManager(manageClient);
    }

    @Override
    protected Command newCommand() {
        return new CreateGroupsCommand();
    }

    @Override
    protected String[] getResourceNames() {
        return new String[] { "sample-app-group" };
    }

    @Override
    protected void afterResourcesCreated() {
        GroupManager mgr = new GroupManager(manageClient);
        Fragment f = mgr.getPropertiesAsXml("sample-app-group");
        assertEquals("metering should be turned off as configured in sample-app-group.json", "false",
                f.getElementValue("/m:group-properties/m:metering-enabled"));
    }

}