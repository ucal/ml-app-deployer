package com.marklogic.appdeployer.command.forests;

import com.marklogic.appdeployer.command.AbstractCommand;
import com.marklogic.appdeployer.command.CommandContext;
import com.marklogic.appdeployer.command.SortOrderConstants;
import com.marklogic.mgmt.ResourceManager;
import com.marklogic.mgmt.forests.ForestManager;

import java.io.File;

/**
 * Use this command when you want precise control over the forests that are created for a database. It processes
 * each directory under ml-config/forests (the name of the directory does not matter, but it makes sense to name
 * it after the database that the forests belong to), and each file in a directory can have a single forest object
 * or an array of forest objects.
 *
 * You can also set customForestsPath to specify a directory other than "forest" as the path that contains
 * directories of custom forests. This allows you to easily support different custom forests in different
 * environments.
 */
public class DeployCustomForestsCommand extends AbstractCommand {

	private String customForestsPath = "forests";

	public DeployCustomForestsCommand() {
		setExecuteSortOrder(SortOrderConstants.DEPLOY_FORESTS);
		setExecuteAsync(true);
	}

	@Override
	public void execute(CommandContext context) {
		File dir = new File(context.getAppConfig().getConfigDir().getBaseDir(), customForestsPath);
		if (dir != null && dir.exists()) {
			if (isExecuteAsync()) {
				initializeTaskExecutor();
			}
			for (File f : dir.listFiles()) {
				if (f.isDirectory()) {
					processDirectory(f, context);
				}
			}
			if (isExecuteAsync()) {
				waitForTasksToFinish();
			}
		}
	}

	protected void processDirectory(File dir, final CommandContext context) {
		final ForestManager mgr = new ForestManager(context.getManageClient());
		for (final File f : listFilesInDirectory(dir)) {
			if (isExecuteAsync()) {
				getTaskExecutor().execute(new Runnable() {
					@Override
					public void run() {
						processFile(f, mgr, context);
					}
				});
			} else {
				processFile(f, mgr, context);
			}
		}
	}

	protected void processFile(File f, ForestManager mgr, CommandContext context) {
		String payload = copyFileToString(f, context);
		mgr.saveJsonForests(payload);
	}

	public void setCustomForestsPath(String customForestsPath) {
		this.customForestsPath = customForestsPath;
	}
}
