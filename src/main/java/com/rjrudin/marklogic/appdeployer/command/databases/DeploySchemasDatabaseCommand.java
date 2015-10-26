package com.rjrudin.marklogic.appdeployer.command.databases;

import com.rjrudin.marklogic.appdeployer.command.SortOrderConstants;

public class DeploySchemasDatabaseCommand extends DeployDatabaseCommand {

    public final static String DATABASE_FILENAME = "schemas-database.json";
    
    public DeploySchemasDatabaseCommand() {
        setExecuteSortOrder(SortOrderConstants.DEPLOY_SCHEMAS_DATABASE);
        setUndoSortOrder(SortOrderConstants.DELETE_SCHEMAS_DATABASE);
        setDatabaseFilename(DATABASE_FILENAME);
        setCreateForestsOnEachHost(false);
    }
}
