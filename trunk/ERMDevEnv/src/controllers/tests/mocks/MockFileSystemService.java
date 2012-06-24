package controllers.tests.mocks;

import infrastructure.IFileSystemService;

public class MockFileSystemService implements IFileSystemService{

	@Override
	public boolean exists(String dataDirectory, String defaultDiagramName) {
		// TODO Auto-generated method stub
		return true;
	}

}
