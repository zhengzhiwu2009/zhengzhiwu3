package com.whaty.filemanager;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

public class JavaUnzipAndZip {
	public JavaUnzipAndZip() {

	}

	public String Unzip(String ZiptoPath, String ZipFilename) {
		String UnzipResult = "";
		String current_path = ZiptoPath;
		if (ZipFilename.length() < 1) {
			UnzipResult = "usage:  Unzip file.zip";
			return UnzipResult;
		}

		if (!(new File(current_path, ZipFilename)).exists()) {
			UnzipResult = "can't open " + ZipFilename;
			return UnzipResult;
		}
		try {
			//ZipFile archive = new ZipFile(current_path + File.separatorChar
			//		+ ZipFilename);
			org.apache.tools.zip.ZipFile archive = new org.apache.tools.zip.ZipFile(current_path + File.separatorChar + ZipFilename);
			// current_path=".";
			// org.apache.tools.zip.ZipFile archive = new org.apache.tools.zip.ZipFile(zipFileName); //使用apache中的zip支持中文文件;

			// do our own buffering; reuse the same buffer.
			byte[] buffer = new byte[16384];
			
			Enumeration e = archive.getEntries();
			for (; e.hasMoreElements();) {
				// get the next entry in the archive
				//ZipEntry entry = (ZipEntry) e.nextElement();
				org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				
				String filename = entry.getName();
				filename = filename.replace('/', File.separatorChar);
				// filename=com.whaty.appbox.convertEncoding("UTF8","GB2312",filename);
				File destFile = new File(current_path, filename);

				if (destFile.exists()) {
					UnzipResult += "<br>..跳过 " + filename + " (存在同名文件或者目录).";
				} else if (filename.endsWith(File.separator)) {

					destFile.mkdirs();
					UnzipResult += "<br>建立目录:" + filename;
				} else {
					UnzipResult += "<br>..展开 " + current_path
							+ File.separatorChar + filename;

					// create the destination path, if needed
					String parent = destFile.getParent();
					if (parent != null) {
						// File parentFile = new File(current_path,parent);
						File parentFile = new File(parent);
						if (!parentFile.exists()) {
							// create the chain of subdirs to the file
							parentFile.mkdirs();
						}
					}

					// get a stream of the archive entry's bytes
					InputStream in = archive.getInputStream(entry);
					// open a stream to the destination file

					OutputStream out = new FileOutputStream(current_path
							+ File.separatorChar + filename);

					// repeat reading into buffer and writing buffer to file,
					// until done. count will always be # bytes read, until
					// EOF when it is -1.
					int count;
					while ((count = in.read(buffer)) != -1)
						out.write(buffer, 0, count);

					in.close();
					out.close();
				}
			}
		} catch (Exception e) {

			UnzipResult = UnzipResult + "error:" + e.toString();
		}

		return UnzipResult;
	}


	public String Zip(String ZipFilename, String FileToZip) {
		int compressionLevel = Deflater.DEFAULT_COMPRESSION;
		String ZipResult = "";
		try {
			// first arg is name of zipfile
			String zipName = ZipFilename;

			// create and initialize a stream to write it
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(
					zipName));
			zip
					.setComment("Created by Whaty Information & Technology CO.,LTD http://www.whaty.com ");
			zip.setMethod(ZipOutputStream.DEFLATED);
			zip.setLevel(compressionLevel);

			// the remaining args are files to put in the zip

			{
				// read a file into memory
				File file = new File(FileToZip);
				FileInputStream in = new FileInputStream(file);
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				in.close();

				// create and initialize a zipentry for it
				ZipEntry entry = new ZipEntry(file.getName());
				entry.setTime(file.lastModified());

				// write the entry header, and the data, to the zip
				zip.putNextEntry(entry);
				zip.write(bytes);

				// write the end-of-entry marker to the zip
				zip.closeEntry();
			}
			// no more files, close the zip. This writes the zip
			// directory, so don't forget it.
			zip.close();
		} catch (Exception e) {
			ZipResult = e.toString();
			return ZipResult;
		}

		return ZipResult;

	}

}
