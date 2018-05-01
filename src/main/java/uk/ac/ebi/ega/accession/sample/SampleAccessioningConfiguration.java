/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package uk.ac.ebi.ega.accession.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uk.ac.ebi.ampt2d.commons.accession.autoconfigure.EnableSpringDataContiguousIdService;
import uk.ac.ebi.ampt2d.commons.accession.generators.DecoratedAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.generators.monotonic.MonotonicAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.persistence.monotonic.service.ContiguousIdBlockService;
import uk.ac.ebi.ega.accession.sample.persistence.SampleAccessioningDatabaseService;
import uk.ac.ebi.ega.accession.sample.persistence.SampleAccessioningRepository;

@Configuration
@EnableSpringDataContiguousIdService
@EntityScan({"uk.ac.ebi.ega.accession.sample.persistence"})
@EnableJpaRepositories(basePackages = {"uk.ac.ebi.ega.accession.sample.persistence"})
public class SampleAccessioningConfiguration {

    @Autowired
    private ContiguousIdBlockService service;
    @Autowired
    private SampleAccessioningRepository repository;

    @Bean
    @ConfigurationProperties(prefix = "accessioning.sample")
    public SampleApplicationProperties getSampleApplicationProperties() {
        return new SampleApplicationProperties();
    }

    @Bean
    public SampleAccessioningService sampleAccessionService() {
        return new SampleAccessioningService(sampleAccessionGenerator(), sampleAccessioningDatabaseService());
    }

    @Bean
    public SampleAccessioningDatabaseService sampleAccessioningDatabaseService() {
        return new SampleAccessioningDatabaseService(repository);
    }

    @Bean
    public DecoratedAccessionGenerator<SampleModel, Long> sampleAccessionGenerator() {
        SampleApplicationProperties sampleApplicationProperties = getSampleApplicationProperties();
        return new DecoratedAccessionGenerator<>(new
                MonotonicAccessionGenerator<>(sampleApplicationProperties.getBlockSize(),
                sampleApplicationProperties.getCategoryId(), sampleApplicationProperties.getInstanceId(), service),
                accession -> sampleApplicationProperties.getAccessionPrefix() + String.format("%011d", accession),
                decoratedAccession -> Long.parseLong(decoratedAccession.
                        replaceAll(sampleApplicationProperties.getAccessionPrefix(), ""))
        );
    }
}
