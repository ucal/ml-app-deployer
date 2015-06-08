package com.marklogic.appdeployer.command.databases;

import org.junit.After;
import org.junit.Test;

import com.marklogic.appdeployer.AbstractAppDeployerTest;
import com.marklogic.appdeployer.command.databases.UpdateContentDatabasesCommand;
import com.marklogic.appdeployer.command.restapis.CreateRestApiServersCommand;
import com.marklogic.rest.mgmt.databases.DatabaseManager;
import com.marklogic.rest.util.Fragment;

public class UpdateContentDatabasesTest extends AbstractAppDeployerTest {

    @Test
    public void updateDatabase() {
        // We want both a main and a test app server in this test
        appConfig.setTestRestPort(SAMPLE_APP_TEST_REST_PORT);

        initializeAppDeployer(new CreateRestApiServersCommand(), new UpdateContentDatabasesCommand());

        appDeployer.deploy(appConfig);

        String rangeIndexXpath = "/m:database-properties/m:range-element-indexes/m:range-element-index"
                + "[m:scalar-type = 'string' and m:namespace-uri = 'urn:sampleapp' and m:localname='id' and m:collation='http://marklogic.com/collation/']";
        DatabaseManager dbMgr = new DatabaseManager(manageClient);

        Fragment db = dbMgr.getDatabasePropertiesAsXml(appConfig.getContentDatabaseName());
        assertTrue(db.elementExists(rangeIndexXpath));

        db = dbMgr.getDatabasePropertiesAsXml(appConfig.getTestContentDatabaseName());
        assertTrue(db.elementExists(rangeIndexXpath));
    }

    @After
    public void teardown() {
        undeploySampleApp();
    }
}