package com.rjrudin.marklogic.appdeployer.command.databases;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.rjrudin.marklogic.appdeployer.command.ResourceFilenameFilter;

/**
 * Filter for "other" databases, which means it ignores all of the content database filenames and the default schema and
 * triggers database filenames.
 */
public class OtherDatabaseFilenameFilter extends ResourceFilenameFilter {

    private List<String> ignoreFilenames;

    public OtherDatabaseFilenameFilter(List<File> contentDatabaseFiles) {
        ignoreFilenames = new ArrayList<String>();
        if (contentDatabaseFiles != null) {
            for (File f : contentDatabaseFiles) {
                ignoreFilenames.add(f.getName());
            }
        }
        ignoreFilenames.add(DeployTriggersDatabaseCommand.DATABASE_FILENAME);
        ignoreFilenames.add(DeploySchemasDatabaseCommand.DATABASE_FILENAME);
    }

    @Override
    public boolean accept(File dir, String name) {
        return super.accept(dir, name) && !ignoreFilenames.contains(name);
    }
}
