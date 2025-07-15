package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Map;
import java.util.HashMap;

public class CdktestApp {
    public static void main(final String[] args) {
        App app = new App();

        // Retrieve the 'env' context passed from the CDK CLI (e.g., --context env=dev)
        // This 'envId' is used for naming and potentially for conditional logic within your stack,
        // but not directly for account/region which will come from environment variables.
        String envId = (String) app.getNode().tryGetContext("env");
        if (envId == null || envId.isEmpty()) {
            System.err.println("Warning: 'env' context not provided. Defaulting to 'dev'.");
            envId = "dev"; // Default to 'dev' if no context is provided
        }

        // The AWS account ID and region will be picked up from environment variables
        // (CDK_DEFAULT_ACCOUNT and CDK_DEFAULT_REGION) which are set by
        // aws-actions/configure-aws-credentials in GitHub Actions.
        Environment awsEnvironment = Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build();

        // Create the CdktestStack with environment-specific props.
        // The stack name can still incorporate the envId for clarity.
        new CdktestStack(app, "CdktestStack-" + envId, StackProps.builder()
                .env(awsEnvironment)
                .build());

        app.synth();
    }
}
