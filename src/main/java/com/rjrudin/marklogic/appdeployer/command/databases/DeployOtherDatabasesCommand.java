package com.rjrudin.marklogic.appdeployer.command.databases;

import java.io.File;
import java.util.List;

import com.rjrudin.marklogic.appdeployer.command.AbstractUndoableCommand;
import com.rjrudin.marklogic.appdeployer.command.CommandContext;
import com.rjrudin.marklogic.appdeployer.command.SortOrderConstants;

public class DeployOtherDatabasesCommand extends AbstractUndoableCommand {

    public DeployOtherDatabasesCommand() {
        setExecuteSortOrder(SortOrderConstants.DEPLOY_OTHER_DATABASES);
        setUndoSortOrder(SortOrderConstants.DELETE_OTHER_DATABASES);
    }

    @Override
    public void execute(CommandContext context) {
        File dbDir = context.getAppConfig().getConfigDir().getDatabasesDir();
        if (dbDir != null && dbDir.exists()) {
            List<File> contentFiles = context.getAppConfig().getConfigDir().getContentDatabaseFiles();
            setResourceFilenameFilter(new OtherDatabaseFilenameFilter(contentFiles));

            for (File f : super.listFilesInDirectory(dbDir)) {
                logger.info("Going to do something with this file: " + f.getAbsolutePath());
            }
        }
    }

    @Override
    public void undo(CommandContext context) {
        // TODO Auto-generated method stub

    }
}
