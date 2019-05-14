package com.test.TestSpringIntegrationSftp.sftpWrite.Service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class SftpFileWriteService {
	
	private SftpRemoteFileTemplate sftpRemoteFileTemplate;
	
	@Autowired
	@Qualifier("sftpSessionFactory")
	public void setupSftpRemoteFileTemplate(SessionFactory sessionFactory) {
		this.sftpRemoteFileTemplate = new SftpRemoteFileTemplate(sessionFactory);
	}
	
	public SftpFileWriteService() {
		super();
	}

	public void writeFiles(Resource[] resources) throws IOException {
		for(Resource resource : resources) {
			String filename = resource.getFilename();
			InputStream fileStream = resource.getInputStream();

			try {
				this.sftpRemoteFileTemplate.execute(session -> {
					session.write(fileStream, filename);
					return null;
				});
			} finally {
				if(fileStream != null) {
					try {
						fileStream.close();
					} catch (IOException ioe) {
						//ignore close failing, but probably should log it properly
					}
				}
			}
		}
	}
}
