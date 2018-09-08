package home;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

public class EasyOpen {
	//will only work if all files which are children of current diretory have unique file names....
	private Map<String,File> allFilesinDirectory;
	
	public EasyOpen(String directoryPath) throws  DirectoryDoesntExistException{
		File dir = new File(directoryPath);
		if(dir.exists()){
			allFilesinDirectory  = createMap(dir);
		}
		else throw new DirectoryDoesntExistException("Directory: " +directoryPath+" doesn't exist");
	}
	
	public Map<String,File> getAllFilesInDirectory(){
		return allFilesinDirectory;
	}

	private Map<String, File> createMap(File dir) {
		File[] fArr = dir.listFiles();
		Map<String,File> fileList = new HashMap<>();
		for(File f: fArr) {
			
			if(f.isFile())
				fileList.put(f.getName(), f);
			
			if(f.isDirectory()) {
				Map<String,File> childFileList = createMap(f);
				fileList.putAll(childFileList);
			}				
		}
		return fileList;
	}
	
	public Map<String,String> getFileNamesAndAbsolutePaths(){
		Map<String,String> tempMap = new HashMap<>();
		for(Entry<String, File> e : allFilesinDirectory.entrySet())
			tempMap.put(e.getKey(), e.getValue().getAbsolutePath());
		
		return tempMap;
	}
	
	public static void main (String args[]) throws DirectoryDoesntExistException {
		EasyOpen eo  = new EasyOpen("E:\\nptel");
		
		System.out.println(eo.getFileNamesAndAbsolutePaths());
		System.out.println("\n");
		System.out.println(eo.getAllFilesInDirectory().keySet());
		System.out.println(eo.getAllFilesInDirectory().keySet().size());
		System.out.println(eo.getAllFilesInDirectory().get("aaaa.txt").getAbsolutePath());
	}
}
