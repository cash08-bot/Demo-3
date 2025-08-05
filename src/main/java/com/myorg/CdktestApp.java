package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Map;

public class CdktestApp {
    public static void main(final String[] args) {
        App app = new App();

        // Get the environment identifier from CDK context (pass with --context env=dev or stg)
        String envId = (String) app.getNode().tryGetContext("env");
        if (envId == null) {
            throw new IllegalArgumentException("Context variable 'env' must be provided, e.g., --context env=dev");
        }

        // Map of environment name to AWS account and region
        Map<String, Environment> envMap = Map.of(
                "dev", Environment.builder()
                        .account("381492133980")
                        .region("us-east-1")
                        .build(),
                "stg", Environment.builder()
                        .account("614056699201")
                        .region("us-east-1")
                        .build()
        );

        Environment targetEnv = envMap.get(envId);
        if (targetEnv == null) {
            throw new IllegalArgumentException("Unknown environment: " + envId);
        }

        new CdktestStack(app, "CdktestStack-" + envId, StackProps.builder()
                .env(targetEnv)
                .build());

        app.synth();
    }
}
