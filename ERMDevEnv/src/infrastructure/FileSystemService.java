package infrastructure;

import java.io.File;

public class FileSystemService implements IFileSystemService {

	@Override
	public boolean exists(String dataDirectory, String diagramName) {
		File file = new File(dataDirectory + "/" + diagramName + "-comp");
		return file.exists();
	}
	
	
}
