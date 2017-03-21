package com.cs.util;
	
	import java.io.File;

	import com.cs.PropertyReader;

	public class FileUtils {

		public static boolean createDirectories(String path) {// DOES THIS METHOD CREATE A DITECTORY FOR THE FILE TO BE COPIED? WHAT IS BEING VALIDATED?
			boolean isDirCreated = false;
			File aFile = new File(path);
			if (!aFile.exists()) {
				isDirCreated = aFile.mkdirs();
			}
			return isDirCreated;
		}

		public static boolean moveFile(File sourceFile, File destinationPath) {// IS THE FILE BEING MOVED TO THE CREATED DIR? WHAT'S THE FUNCTIONALITY OF THIS METHOD
			return sourceFile.renameTo(new File(destinationPath, sourceFile
					.getName()));
		}

	//WHAT HAPPENS IN THEBLOW METHOD?

		public static boolean moveFiles(File aSourceDir, File aDestinationDir) {
			boolean isFilesMoved = false;
			if (aSourceDir.isDirectory()) {
				File[] aListFiles = aSourceDir.listFiles();
				int aSourceFilesCnt = aListFiles.length;
				int aMoveFilesCnt = 0;
				for (File aFile : aListFiles) {
					boolean isFileMoved = moveFile(aFile, aDestinationDir);
					if (isFileMoved) {
						aMoveFilesCnt++;
						System.out.println("Success fully moved: " + aFile);

					} else {
						System.out.println("File moved unsuccess full: " + aFile);
					}
				}

				if (aSourceFilesCnt == aMoveFilesCnt) {
					isFilesMoved = true;
					System.out.println("All files moved successfully.");
				} else if (aMoveFilesCnt > 0 && aMoveFilesCnt < aSourceFilesCnt) {
					System.out.println("Files moved partially.");
				} else {
					System.out.println("Files are not moved.");
				}
			}
			return isFilesMoved;
		}

	// ARE WE DELETING THE FILE AFTER MOVING?
		public static boolean deleteFile(File aFile) {
			return aFile.delete();
		}

		public boolean deleteDirectory(File aDirectory) {

			boolean isDirDeleted = false;
			if (aDirectory.isDirectory()) {
				if (aDirectory.listFiles().length == 0) {
					if (!(aDirectory.getAbsolutePath())
							.equalsIgnoreCase(PropertyReader
									.getInstance().getProperty(
											PropertyReader.BASE_LOCATION)))
						isDirDeleted = aDirectory.delete();
					System.out.println("Deleted: " + aDirectory.getAbsolutePath());
					deleteDirectory(aDirectory.getParentFile());
				}
			}
			return isDirDeleted;
		}
	}


}
