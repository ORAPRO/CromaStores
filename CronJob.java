package com.cs;



	/**
	 * @param args
	 */
	import java.io.File;

	import org.apache.hadoop.conf.Configuration;
	import org.apache.hadoop.conf.Configured;
	import org.apache.hadoop.fs.FileSystem;
	import org.apache.hadoop.util.Tool;
	import org.apache.hadoop.util.ToolRunner;
	

import com.cs.util.FileUtils;
import com.cs.util.HDFSUtils;
import com.cs.util.PropertyReader;


	public class CronJob extends Configured implements Tool {

		private static PropertyReader Reader;

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			System.out.println("In MAIN Method");

			// Step-1 validate input arguments
			if (args.length < 1) {
				System.out
						.println("Java Usage "
								+ CronJob.class.getName()
								+ "In valid arguments lenth. Must required LSEnine properties file path.");
				return;
			}

			Reader = PropertyReader.getInstance();
			lsEnginePropertiesReader.loadProperties(args[0]);

			// Step-2 Initialize configuration
			Configuration tConf = new Configuration(Boolean.TRUE);
			tConf.set("fs.defaultFS", "hdfs://localhost:8020");

			// Step-3 Run ToolRunner.run method to set the arguments to config.
			try {
				int i = ToolRunner.run(tConf, new CronJob(), args);
				if (i == 0) {
					System.out.println(HDFSUtils.SUCCESS);
				} else {
					System.out.println(HDFSUtils.FAILED + " STATUS Code: " + i);
				}
			} catch (Exception e) {
				System.out.println(HDFSUtils.FAILED);
				e.printStackTrace();
			}

		}
	// WHAT IS BEING CHECKED?
		@Override
		public int run(String[] paramArrayOfString) throws Exception {
			System.out.println("In Run Method");

			final String tBaseLocation = Reader;
					.getProperty(PropertyReader.BASE_LOCATION);
			final String tFileSourceLocation = tBaseLocation
					+ HDFSUtils.FILE_SEPARATOR
					+ Reader;
							.getProperty(PropertyReader.LANDING_ZONE);
			// Create directory if does not exist.
			FileUtils.createDirectories(tFileSourceLocation);
			final String tArchiveLocation = tBaseLocation
					+ HDFSUtils.FILE_SEPARATOR
					+ Reader;
							.getProperty(PropertyReader.ARCHIVE);
			FileUtils.createDirectories(tArchiveLocation);
			final String tFailedLocation = tBaseLocation
					+ HDFSUtils.FILE_SEPARATOR
					+ Reader;
							.getProperty(PropertyReader.FAILED);

			final String tHDFSBaseLocation = Reader;
					.getProperty(PropertyReader.HDFS_BASE_LOCATION);
			final String tDestinationPath = tHDFSBaseLocation
					+ HDFSUtils.FILE_SEPARATOR
					+ Reader;
							.getProperty(PropertyReader.HDFS_LANDING_ZONE);

			// Load the configuration
			Configuration tConf = getConf();

			// Create a instance for File System object.
			FileSystem hdfs = FileSystem.get(tConf);
			// Create directory on HDFS File System if does not exist.
			HDFSUtils.createHDFSDirectories(hdfs, tDestinationPath);


	// WHAT IS BEING CHECKED
			while (true) {
				File tInboxDir = new File(tFileSourceLocation);
				if(tInboxDir.isDirectory())
				{
					File[] tListFiles = tInboxDir.listFiles();
					for (File tInputFile : tListFiles) {
						String [] args= {tInputFile.getAbsolutePath().toString(), tDestinationPath}; 
						boolean isCopied = HDFSUtils.copyFromLocal(tConf, hdfs, args);
						if(isCopied){
							FileUtils.moveFile(tInputFile, new File(tArchiveLocation));
						} else {
							FileUtils.moveFile(tInputFile, new File(tFailedLocation));
						}
						
					}
				}
				Thread.sleep(1000 * 60 * 5);
			}
		}
	}


	}

}
