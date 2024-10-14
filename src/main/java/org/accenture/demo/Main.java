package org.accenture.demo;

import org.accenture.demo.pipeline.GCPDataflow;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class Main {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.fromArgs("--project=traindev-gcp").create();
        GCPDataflow gcpDataflow = new GCPDataflow();
        gcpDataflow.etlPipeline(options);
    }
}