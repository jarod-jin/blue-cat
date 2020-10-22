package cn.jarod.bluecat.core.data.es.component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class AwsCredentialsProvider implements AWSCredentialsProvider {

	private final String secretKey;
	private final String accessId;

	public AwsCredentialsProvider(String accessId, String secretKey) {
		this.accessId = accessId;
		this.secretKey = secretKey;
	}

	@Override
	public AWSCredentials getCredentials() {
		return new BasicAWSCredentials(accessId, secretKey);
	}

	@Override
	public void refresh() {

	}
}