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
package uk.ac.ebi.ega.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uk.ac.ebi.ampt2d.commons.accession.autoconfigure.EnableSpringDataContiguousIdService;
import uk.ac.ebi.ampt2d.commons.accession.generators.DecoratedAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.generators.monotonic.MonotonicAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.persistence.monotonic.service.ContiguousIdBlockService;
import uk.ac.ebi.ega.accession.sample.SampleAccessioningService;
import uk.ac.ebi.ega.accession.sample.SampleModel;
import uk.ac.ebi.ega.accession.sample.persistence.SampleAccessioningDatabaseService;
import uk.ac.ebi.ega.accession.sample.persistence.SampleAccessioningRepository;

@TestConfiguration
@EnableSpringDataContiguousIdService
@EntityScan({"uk.ac.ebi.ega.accession.sample.persistence"})
@EnableJpaRepositories(
        basePackages = {"uk.ac.ebi.ega.accession.sample.persistence"}
)
public class SampleAccessioningDatabaseServiceTestConfiguration {

    @Autowired
    private SampleAccessioningRepository repository;

    @Autowired
    private ContiguousIdBlockService service;

    @Bean
    public SampleAccessioningService sampleAccessionService() {
        return new SampleAccessioningService(DecoratedAccessionGenerator.buildPrefixSuffixMonotonicAccessionGenerator(new MonotonicAccessionGenerator<SampleModel>(1000, "sample",
                "app01", service), "EGAN", ""), sampleAccessioningDatabaseService());
    }

    @Bean
    public SampleAccessioningDatabaseService sampleAccessioningDatabaseService() {
        return new SampleAccessioningDatabaseService(repository);
    }
}
